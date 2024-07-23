package com.example.EventDrivenPubSub;


import com.google.cloud.functions.CloudEventsFunction;
import com.google.events.cloud.pubsub.v1.Message;
import com.google.events.cloud.pubsub.v1.MessagePublishedData;
import com.google.gson.Gson;
import io.cloudevents.CloudEvent;

import java.util.Base64;
import java.util.logging.Logger;

public class EventDrivenPubSubApplication implements CloudEventsFunction {

	private Logger logger = Logger.getLogger(EventDrivenPubSubApplication.class.getName());
	@Override
	public void accept(CloudEvent cloudEvent){

		// Get the Json String
		String data = new String(cloudEvent.getData().toBytes());

		Gson gson = new Gson();
		// Decode JSON event data to the Pub/Sub MessagePublishedData type
		MessagePublishedData messagePublishedData = gson.fromJson(data, MessagePublishedData.class);

		// Get the message from the messagePublishedData
		Message message = messagePublishedData.getMessage();

		//Get the encoded data from the message
		String encodedData = message.getData();

		//Get the decoded Data from the encoded data
		String decodedData = new String(Base64.getDecoder().decode(encodedData));

		logger.info("Event Data : "+decodedData);
	}
}
