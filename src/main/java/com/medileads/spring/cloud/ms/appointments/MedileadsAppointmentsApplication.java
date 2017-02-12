package com.medileads.spring.cloud.ms.appointments;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class MedileadsAppointmentsApplication implements CommandLineRunner {

private static List<Appointment> appointments = new ArrayList<Appointment>();
	
	@Autowired
	private QueuesBean queuesBean;

    public static void main(String[] args) {
        SpringApplication.run(MedileadsAppointmentsApplication.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		appointments.add(new Appointment("Apollo Smart Clinic", "123", "HYD", "TG"));
		appointments.add(new Appointment("Image Smart Clinic", "456", "HYD", "TG"));
	}
	
	@RequestMapping("/")
	public List<Appointment> getAppointments() {
		return appointments;
	}
	
	@RequestMapping("/queues")
	public List<AppointmentWithQueues> getAppointmentsWithQueues(){
		
		List<AppointmentWithQueues> returnAppointments = new ArrayList<>();
		for(Appointment r:appointments){
			returnAppointments.add(new AppointmentWithQueues(r, queuesBean.getQueues(r.getId())));
		}
		return returnAppointments;
		
	}
	
}

@Component
class QueuesBean {
	
	@Autowired
	private QueuesClient queuesClient;
	
	@HystrixCommand(fallbackMethod ="defaultQueues")
	public List<Queue> getQueues(String appointmentId){
		return queuesClient.getQueues(appointmentId);
	}
	
	public List<Queue> defaultQueues(String appointmentId) {
		return new ArrayList<Queue>();
	}
}


class Appointment {
	private String name;
	private String id;
	private String state;
	private String city;
	
	public Appointment(String name, String id, String state, String city) {
		super();
		this.name = name;
		this.id = id;
		this.state = state;
		this.city = city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
	class Queue {
		private String firstName;
		private String lastName;
		private String homeState;
		private String shirtSize;
		private List<String> appointments;
		public Queue(String firstName, String lastName, String homeState,
				String shirtSize, List<String> appointments) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.homeState = homeState;
			this.shirtSize = shirtSize;
			this.appointments = appointments;
		}
		
		public Queue(){}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getHomeState() {
			return homeState;
		}
		public void setHomeState(String homeState) {
			this.homeState = homeState;
		}
		public String getShirtSize() {
			return shirtSize;
		}
		public void setShirtSize(String shirtSize) {
			this.shirtSize = shirtSize;
		}
		public List<String> getAppointments() {
			return appointments;
		}
		public void setAppointments(List<String> appointments) {
			this.appointments = appointments;
		}
	
}
