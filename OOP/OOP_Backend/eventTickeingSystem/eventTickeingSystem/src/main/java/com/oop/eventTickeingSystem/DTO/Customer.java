package com.oop.eventTickeingSystem.DTO;

import com.oop.eventTickeingSystem.Service.TicketPool;

//considering this as a Data Transfer Object
public class Customer implements Runnable {

    private TicketPool ticketPool;
    private int customerRetrivalRate;
    private String customerId;

    public Customer(TicketPool ticketPool, int customerRetrivalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrivalRate = customerRetrivalRate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            ticketPool.removeTicket(customerRetrivalRate, customerId);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
