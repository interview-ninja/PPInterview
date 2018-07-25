package Main;

import factory.InMemoryQueueFactory;
import pojo.Message;
import pojo.PhonePeProducer;
import services.InMemoryQueue;
import services.impl.ConsumerImpl;

import java.util.ArrayList;
import java.util.List;

public class PhonePeApplication {

	public static void main(String[] args) {

		InMemoryQueue inMemoryQueue = InMemoryQueueFactory.getInstance(5);
		ConsumerImpl consumer1 = new ConsumerImpl(101, "abcd");
		ConsumerImpl consumer2 = new ConsumerImpl(102, "a.cd");
		ConsumerImpl consumer3 = new ConsumerImpl(103, "a..a");
		inMemoryQueue.registerConsumer(consumer1);
		inMemoryQueue.registerConsumer(consumer2);
		inMemoryQueue.registerConsumer(consumer3);

		PhonePeProducer phonePeProducer = new PhonePeProducer("Producer");
		List<Message> list = new ArrayList<>();

		list.add(new Message("abcd"));
		list.add(new Message("akja"));
		list.add(new Message("aecd"));
		list.add(new Message("aacd"));
		list.add(new Message("aaaa"));

		phonePeProducer.sendMessages(list, inMemoryQueue);
	}

}
