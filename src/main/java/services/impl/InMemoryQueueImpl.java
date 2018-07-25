package services.impl;

import pojo.Message;
import services.InMemoryQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InMemoryQueueImpl implements InMemoryQueue {

    private final static int RETRY_COUNT = 3;
    private final static int DEAD_QUEUE_SIZE = 100;

	private BlockingQueue<Message> inMemoryQueue;
	private BlockingQueue<Message> deadQueue = new ArrayBlockingQueue<Message>(DEAD_QUEUE_SIZE);
	private LinkedList<ConsumerImpl> topologicalSortedCustomers = new LinkedList<>();

	private int sizeOfQueue;

	public InMemoryQueueImpl(int size) {
		this.sizeOfQueue = size;
		inMemoryQueue = new LinkedBlockingQueue<>(size);
	}

	@Override
	public boolean registerConsumer(ConsumerImpl consumerImpl) {
		topologicalSortedCustomers.add(consumerImpl);
		return true;
	}

	@Override
	public boolean registerConsumerWithDependency(ConsumerImpl consumerToBeAdded,
			List<ConsumerImpl> consumerToBeDependentOn) {
	    return true;
	}

	@Override
	public boolean addMessageToQueue(Message message) {
        // To handle threads
		synchronized (inMemoryQueue) {
			try {
				while (true) {
					if (isQueueFull()) {
						    System.out.println("Queue is full sleeping for 10 sec.");
							Thread.sleep(10000);
					}else {
					    break;
                    }
				}
				inMemoryQueue.add(message);
				informConsumers(inMemoryQueue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	private void informConsumers(BlockingQueue<Message> blockingQueue) {
        // To handle threads
        synchronized (blockingQueue) {
            int count = blockingQueue.size();
            while (count > 0) {
                Message message = blockingQueue.poll();
                for (ConsumerImpl consumer : topologicalSortedCustomers) {
                    int retry_count=0;
                    boolean processed = false;
                    while (retry_count < RETRY_COUNT && consumer.matches(message) && processed == false) {
                        processed = consumer.processMessage(message);
                        retry_count++;
                    }
                    if(processed == false && consumer.matches(message)) {
                        System.out.println("Message added to dead queue" + message.toString() + " for consumer " + consumer.getConsumerId());
                        deadQueue.add(message);
                    }
                }
                count--;
                if (inMemoryQueue.isEmpty())
                    return;
            }

        }
    }

	private boolean isQueueFull() {
		if (inMemoryQueue.size() >= sizeOfQueue)
			return true;
		else
			return false;
	}
}
