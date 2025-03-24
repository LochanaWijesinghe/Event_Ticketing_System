package oop.cw.cli.part1;

import java.util.ArrayList;
import java.util.List;

public class TicketPool {

    private final List<Integer> tickets = new ArrayList<>();    //an arraylist named 'tickets' is created
    private int maximumTicketCapacity;

    public TicketPool(int maximumTicketCapacity){
        this.maximumTicketCapacity = maximumTicketCapacity;
    }

    public synchronized void addTickets(int ticketCount){
        while(tickets.size()+ ticketCount >maximumTicketCapacity){
            System.out.println("TicketPool is full. Vendor is waiting to add tickets...");
            try{
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        //Add ticketCount tickets to the pool
        for(int i=0; i<ticketCount; i++){
            tickets.add(1);
        }
        System.out.println("Added "+ ticketCount+ " tickets. \nTotal tickets: "+tickets.size());

        notifyAll();
        //Wakes up all threads waiting on the same object (both vendor and customer threads).
        // This allows customer threads to act if tickets are now available.
    }

    public synchronized void removeTickets(int retrivealCount){
        while(tickets.size()< retrivealCount){
            if(tickets.isEmpty()){
                System.out.println("No tickets available. Customer is waiting...");
            }

            try{
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        for(int i=0; i<retrivealCount; i++){
            tickets.remove(0);
        }
        System.out.println(retrivealCount+" tickets was purchased. \nRemaining tickets: "+tickets.size());

        notifyAll();
    }
}
