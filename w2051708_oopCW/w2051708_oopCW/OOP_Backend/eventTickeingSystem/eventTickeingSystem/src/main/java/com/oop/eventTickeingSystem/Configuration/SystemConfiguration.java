package com.oop.eventTickeingSystem.Configuration;

import org.springframework.stereotype.Component;

@Component
public class SystemConfiguration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maximumTicketCapacity;

    private int vendorCount;
    private int customerCount;

    public SystemConfiguration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maximumTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maximumTicketCapacity = maximumTicketCapacity;
    }

    public SystemConfiguration() {
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    public int getVendorCount() {
        return vendorCount;
    }
    public int getCustomerCount() {
        return customerCount;
    }
}
