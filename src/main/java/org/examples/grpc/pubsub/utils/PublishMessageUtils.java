package org.examples.grpc.pubsub.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.examples.grpc.pubsub.server.ServerInit;

import com.google.api.client.util.DateTime;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.InsertAllRequest;
import com.google.cloud.bigquery.TableId;

public class PublishMessageUtils {

	private final static String data = "Data";
	private final static String timestamp = "TimeStamp";
	private final static String code = "HttpCode";
	private final static String statusmsg = "StatusMsg";
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
	public static void insertFailedMessagesInBQ(org.examples.grpc.pubsub.generated.PubsubMessage msg, ApiException apiException) {
		// Read init variables from context and config
		String dataset = ProxyPropertiesUtils.getPropertyValue("dataset");
		String table = ProxyPropertiesUtils.getPropertyValue("table");
		
		// Get BQ handler 
		BigQuery bigquery = ServerInit.getBigqueryHandler();

		// Values of the row to insert
		Map<String, Object> rowContent = new HashMap<String, Object>();
		rowContent.put(data, msg.getData());
		rowContent.put(code, apiException.getStatusCode().getCode().getHttpStatusCode());
		rowContent.put(statusmsg, apiException.getMessage());
		rowContent.put(timestamp, new DateTime(new Date()));

		// Insert values
		TableId tableId = TableId.of(dataset, table);
	    bigquery.insertAll(InsertAllRequest.newBuilder(tableId)
				.addRow(rowContent)
				.build());
	}
}