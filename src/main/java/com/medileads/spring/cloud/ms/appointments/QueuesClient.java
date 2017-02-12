package com.medileads.spring.cloud.ms.appointments;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("queues")
public interface QueuesClient {
	
	@RequestMapping(method=RequestMethod.GET, value="/appointments/{appointmentId}")
	List<Queue> getQueues(@PathVariable("appointmentId") String appointmentId);

}
