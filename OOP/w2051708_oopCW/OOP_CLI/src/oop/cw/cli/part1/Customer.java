package oop.cw.cli.part1;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private int customerRetrievalRate;

    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            ticketPool.removeTickets(customerRetrievalRate);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
