package pojo;

import services.InMemoryQueue;

import java.util.List;


public class PhonePeProducer {

	private String name;

	public PhonePeProducer(String name) {
		super();
		this.name = name;
	}

	public void sendMessages(List<Message> messages, InMemoryQueue inMemoryQueue) {
		// It will add all the messages in queue.
		for (Message message : messages) {
			inMemoryQueue.addMessageToQueue(message);
		}
	}

}
