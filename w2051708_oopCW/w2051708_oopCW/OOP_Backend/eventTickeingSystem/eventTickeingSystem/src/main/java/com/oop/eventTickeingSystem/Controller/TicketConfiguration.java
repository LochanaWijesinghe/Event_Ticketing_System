package com.oop.eventTickeingSystem.Controller;

import com.oop.eventTickeingSystem.Configuration.SystemConfiguration;
import com.oop.eventTickeingSystem.Service.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eventTicketing")
public class TicketConfiguration {

    @Autowired
    private TicketPool ticketPool;

    private SystemConfiguration sysConfig;
    private List<Thread> threads = new ArrayList<>();

    @PostMapping("/configController")
    public String configurations(@RequestParam int totalTickets, @RequestParam int ticketReleaseRate, @RequestParam int customerRetrievalRate, @RequestParam int maximumTicketCapacity) {

        sysConfig = new SystemConfiguration(totalTickets,ticketReleaseRate,customerRetrievalRate,maximumTicketCapacity);
        ticketPool.systemConfiguration(sysConfig);
        return "Configuration successful";
    }

    @PostMapping("/vendorAndCustomer")
    public String vendorAndCustomers(@RequestParam int vendorCount, @RequestParam int customerCount) {

        return "Vendor and Customer count successful";
    }

    //startSimulation method will be started when start button is pressed
    @PostMapping("/start")
    public void start() {
        ticketPool.startSimulation();
        System.out.println("Simulation started.");
    }

    //when stop button is pressed all threads will be stopped ending the execution of the system
    @PostMapping("/stop")
    public void stop() {
        ticketPool.stopSimulation();
        System.out.println("All transactions have been stopped.");
    }
}
