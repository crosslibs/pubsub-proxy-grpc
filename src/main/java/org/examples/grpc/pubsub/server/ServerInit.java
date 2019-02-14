package org.examples.grpc.pubsub.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.examples.grpc.pubsub.utils.ProxyPropertiesUtils;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ServiceOptions;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetInfo;
import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.LegacySQLTypeName;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.StandardTableDefinition;
import com.google.cloud.bigquery.Table;
import com.google.cloud.bigquery.TableDefinition;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.TableInfo;

/**
 * Any one time initialization/heavy-lifting during server startup 
 * 
 * Currently this class does the following:
 * 
 * 1) get service account credentials from user passed json file during proxy startup
 * 
 * 2) bigquery initializations to persist failed messages during publish messages
 */
public class ServerInit {

	private static ServiceAccountCredentials serviceAccount;
	private static BigQuery bigqueryHandler;
	private static String projectId;
	
	public static void preStartup() {
		
		try 
		{
			// Get project id
			setProjectId(ServiceOptions.getDefaultProjectId());
			
			// Get service account json from k8s secret
			InputStream credsStream = new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
			setServiceAccount(ServiceAccountCredentials.fromStream(credsStream));
			
			// Get bigquery handler
			BigQuery bigquery = getBQHandler();
			setBigqueryHandler(bigquery);
			
			// Create dataset if not exists
			createDataSet(bigquery);
						
			// Create table with schema if not exists
			createTable(bigquery);
		}
		catch (IOException e) {
			//TODO
			e.printStackTrace();
		}

	}

	public static String getProjectId() {
		return projectId;
	}

	
	public static void setProjectId(String projectId) {
		ServerInit.projectId = projectId;
	}
	
	
	public static ServiceAccountCredentials getServiceAccount() {
		return serviceAccount;
	}

	
	public static void setServiceAccount(ServiceAccountCredentials serviceAccount) {
		ServerInit.serviceAccount = serviceAccount;
	}


	public static BigQuery getBigqueryHandler() {
		return bigqueryHandler;
	}

	
	public static BigQuery getBQHandler() throws IOException {
		return BigQueryOptions.getDefaultInstance().getService();
	}
	
	public static void setBigqueryHandler(BigQuery bigqueryHandler) {
		ServerInit.bigqueryHandler = bigqueryHandler;
	}

	
	/**
	 * Since pubsub writes are asynchronous, the user always gets a 200OK irrespective of what happens downstream.
	 * Writing to a bq sink allows developers to revisit the failed messages, dissect failures and, if they want, retry messages.
	 * 
	 *------------------- Table schema ----------------------------
	 * Data: data passed in user request
	 * HttpCode: Failure code returned by PubSub
	 * StatusMsg: Failure message
	 * TimeStamp: Timestamp recorded at the time of record entry
	 * -------------------------------------------------------------
	 * 
	 */
	private static void createDataSet(BigQuery bigquery) {

		String datasetName = ProxyPropertiesUtils.getPropertyValue("dataset");

		// Check if dataset exists
		Dataset dataset = bigquery.getDataset(datasetName);
		if (null == dataset) {
			DatasetInfo datasetInfo = DatasetInfo.newBuilder(datasetName).build();
			dataset = bigquery.create(datasetInfo);
		}
	}
	
	private static void createTable(BigQuery bigquery) {
		
		String tableName = ProxyPropertiesUtils.getPropertyValue("table");
		String datasetName = ProxyPropertiesUtils.getPropertyValue("dataset");

		// Check if table exists
		Table table = bigquery.getTable(datasetName, tableName);
		if (null == table) {
			
			TableId tableId = TableId.of(datasetName, tableName);
			List<Field> fields = new ArrayList<Field>();
			
			// Data: data passed in user request
			fields.add(Field.of("Data", LegacySQLTypeName.STRING));
			
			// HttpCode: Failure code returned by PubSub
			fields.add(Field.of("HttpCode", LegacySQLTypeName.INTEGER));
			
			// StatusMsg: Failure message
			fields.add(Field.of("StatusMsg", LegacySQLTypeName.STRING));
			
			// TimeStamp: Timestamp recorded at the time of record entry
			fields.add(Field.of("TimeStamp", LegacySQLTypeName.TIMESTAMP));

			Schema schema = Schema.of(fields);
			TableDefinition tableDefinition = StandardTableDefinition.of(schema);
			TableInfo tableInfo = TableInfo.newBuilder(tableId, tableDefinition).build();

			table = bigquery.create(tableInfo);
		}
	}

}