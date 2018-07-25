package services;

import pojo.Message;
import services.impl.ConsumerImpl;

import java.util.List;

public interface InMemoryQueue {

	boolean registerConsumer(ConsumerImpl consumer);
	boolean registerConsumerWithDependency(ConsumerImpl consumerToBeAdded, List<ConsumerImpl> consumerToBeDependentOnList);
	boolean addMessageToQueue(Message message);
	
}
