package com.oop.eventTickeingSystem.Service;

import com.oop.eventTickeingSystem.Configuration.SystemConfiguration;
import com.oop.eventTickeingSystem.DTO.Customer;
import com.oop.eventTickeingSystem.DTO.Vendor;
import com.oop.eventTickeingSystem.Repository.TransactionRepository;
import com.oop.eventTickeingSystem.model.Tickets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketPool {
    TransactionRepository transactionRepo;
    //tickets list to store Tickets data
    private List<Tickets> tickets = new ArrayList<>();
    private List<Thread> threadList = new ArrayList<>();
    private int maximumTicketCapacity;

    @Autowired
    private SystemConfiguration sysConfig;

    public TicketPool() {

    }

    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }

    public void systemConfiguration(SystemConfiguration sysConfig) {
        this.sysConfig = sysConfig;
    }

    public synchronized void addTicket(int ticketCount, String vendorId) {
        while (tickets.size()+ticketCount >= maximumTicketCapacity) {
            System.out.println("TicketPool is full. Vendor is waiting to add tickets...");
            try{
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        for(int i=0; i<ticketCount; i++) {
            Tickets ticket = new Tickets();   //creating an object from the Ticket class
            tickets.add(ticket);           //passing that object to the list created using Ticket type
            ticket.setVendorId(vendorId);     //setting a vendorId for each ticket added to the pool
            transactionRepo.save(ticket);      //adding the vendorId of the ticket to the database
        }
        System.out.println("Added "+ ticketCount + " tickets. \nTotal tickets: " + tickets.size());
        notifyAll();
    }


    public synchronized void removeTicket(int retrievalCount, String customerId) {
        while(tickets.size()<retrievalCount) {
            if(tickets.isEmpty()){
                System.out.println("No tickets available. Customer is waiting...");
            }
            try{
                wait();
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            for(int i=0; i<retrievalCount; i++) {
                Tickets ticket = new Tickets();
                tickets.remove(ticket);
                ticket.setCustomerId(customerId);
                transactionRepo.assignNewCustomerIdToNullTickets(customerId);    //adding a customerId to the removed ticket in the database

            }
            System.out.println(retrievalCount + " tickets removed. \nRemaining tickets: " + tickets.size());
            notifyAll();
        }
    }


    public void startSimulation() {

        //in case if the user enters S while the simulation is already running
        if(!threadList.isEmpty()) {
            System.out.println("Simulation is already running. Please exit the system before starting again.");
            return;
        }

        // Create vendor threads and add to threadList
        for(int i=0; i< sysConfig.getVendorCount(); i++){
            Runnable vendor = new Vendor(this, sysConfig.getTicketReleaseRate());
            Thread vendorThread = new Thread(vendor);
            threadList.add(vendorThread);
        }

        // Create customer threads and add to threadList
        for(int i=0; i< sysConfig.getCustomerCount(); i++){
            Runnable customer = new Customer(this, sysConfig.getTicketReleaseRate());
            Thread customerThread = new Thread(customer);
            threadList.add(customerThread);
        }

        //start all thread
        threadList.forEach(Thread::start);
    }

    public void stopSimulation() {

        //Interrupt all running threads
        threadList.forEach(Thread::interrupt);

        //wait for all threads to terminate
        threadList.forEach(thread -> {
            try{
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        //clear the thread list after stopping
        threadList.clear();
    }

}
