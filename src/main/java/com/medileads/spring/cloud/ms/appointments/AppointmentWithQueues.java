package com.medileads.spring.cloud.ms.appointments;

import java.util.List;

public class AppointmentWithQueues extends Appointment {
	
private List<Queue> queues;
	
	public AppointmentWithQueues(Appointment r, List<Queue> queues) {
		super(r.getName(), r.getId(), r.getState(), r.getCity());
		this.queues = queues;
	}

	public List<Queue> getQueues(){
		return queues;
	}
	
	public void setQueues(List<Queue> queues){
		this.queues = queues;
	}
}
