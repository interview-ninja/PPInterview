package services.impl;


import pojo.Message;
import services.Consumer;

import java.util.Random;
import java.util.regex.Pattern;

public class ConsumerImpl implements Consumer {

	private int consumerId;
	private String expression;

	public ConsumerImpl() {
		super();
	}

	public ConsumerImpl(int consumerId, String expression) {
		super();
		this.consumerId = consumerId;
		this.expression = expression;
	}

	@Override
	public boolean processMessage(Message message) {
        int randomNumber = new Random().nextInt(10);
        if(randomNumber < 5) {
            System.out.println("Message not processed : " + message.getData() + " By consumer id " + consumerId + " Expression of consumer " + expression);
            return false;
        }
        System.out.println("Received message : " + message.getData() + " By consumer id " + consumerId + " Expression of consumer " + expression);
        return true;
	}

    @Override
    public boolean matches(Message message) {
        return Pattern.compile(expression).matcher(message.getData()).matches();
    }

    public int getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(int consumerId) {
		this.consumerId = consumerId;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

}
