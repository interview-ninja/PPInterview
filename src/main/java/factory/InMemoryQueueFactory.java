package factory;

import services.InMemoryQueue;
import services.impl.InMemoryQueueImpl;

public abstract class InMemoryQueueFactory {

	public static InMemoryQueue getInstance(int size) {
		return new InMemoryQueueImpl(size);
	}

}
