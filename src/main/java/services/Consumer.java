package services;

import pojo.Message;

public interface Consumer {
	boolean processMessage(Message message);
	boolean matches(Message message);
}
