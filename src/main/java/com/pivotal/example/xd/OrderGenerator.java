package com.pivotal.example.xd;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderGenerator implements Runnable {

	@Autowired RabbitClient rabbitClient;
	
	private boolean generating = false;
	
	public void startGen(){
		this.generating = true;
	}

	public void stopGen(){
		this.generating = false;
	}
	
	@Override
	public void run() {
		while (true){
			if (generating){
				Random random = new Random();
				String state = HeatMap.states[random.nextInt(HeatMap.states.length)];
				int value = (1+random.nextInt(4))*10;
				Order order = new Order();
				order.setAmount(value);
				order.setState(state);
				try {
					rabbitClient.post(order);
				} catch (IOException e1) {
					throw new RuntimeException(e1);
				}
			}
			else{
				try{
					Thread.sleep(500);
				}catch(Exception e){ return; }
			}
		try{
			   Thread.sleep(50);
		   }
		   catch(Exception e){ return; }
		}
		
	}

}
