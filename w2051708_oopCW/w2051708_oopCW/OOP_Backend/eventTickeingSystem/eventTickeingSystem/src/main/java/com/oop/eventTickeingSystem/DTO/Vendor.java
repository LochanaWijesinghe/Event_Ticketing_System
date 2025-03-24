package com.oop.eventTickeingSystem.DTO;

import com.oop.eventTickeingSystem.Service.TicketPool;

public class Vendor implements Runnable {

    private TicketPool ticketPool;
    private int ticketReleaseRate;
    private String vendorId;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            ticketPool.addTicket(ticketReleaseRate, vendorId );
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
