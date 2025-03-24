package oop.cw.cli.part1;
import java.util.*;    //ArrayList, Collections, List, Scanner

public class Main {
    static Scanner input = new Scanner(System.in);

    private static SystemConfiguration sysConfig;

    private static boolean running = true;

    private static List<Thread> threadList = Collections.synchronizedList(new ArrayList<>());
    //thread-safe list to store Thread objects in a multithreaded environment

    public static void main(String[] args){
        System.out.println("""
                ****** Welcome to the real-time event ticketing system ******
                Enter 'Y' to proceed with the previous configurations\s
                or 'N' to save new configurations""");

        //do-while loop to get the loadOption
        do{
            System.out.println("Do you want to load the configurations from a JSON file? (Y or N): ");
            String loadOption = input.next().toUpperCase();

            if (loadOption.equals("Y")){
                sysConfig = JsonConfiguration.loadConfiguration();    //load existing configuration

                if(sysConfig != null){
                    System.out.println("Data Loaded successfully");
                    displayConfiguration(sysConfig);
                }else{
                    System.out.println("No JSON file is found in the folder. Create new configurations.");
                    getConfigurations();    //get new configurations when 'Yes' is entered and no existing file is found
                }
                break;

            }else if (loadOption.equals("N")) {
                getConfigurations();    //get new configurations when 'No' is entered
                break;

            }else{
                System.out.println("Invalid option! Try again");
            }
        } while(true);

        System.out.println("Press 'S' to start and 'Q' to exit");

        //the menu
        while(running){
            System.out.print("Enter your option: ");
            String option = input.next().toUpperCase();

            switch (option){
                case "S" :
                    startSimulation();
                    break;

                case "Q" :
                    stopAllThreads();
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option! Try again");
            }
        }
    }

    public static void displayConfiguration(SystemConfiguration config){
        System.out.println("  > Total Tickets: "+ config.getTotalTickets());
        System.out.println("  > Ticket Release Rate: "+ config.getTicketReleaseRate());
        System.out.println("  > Customer Retrieval Rate: "+ config.getCustomerRetrievalRate());
        System.out.println("  > Maximum Ticket Capacity: "+ config.getMaximumTicketCapacity());
    }

    public static void getConfigurations(){
        System.out.print("Enter the total number of tickets: ");
        int totalTickets = inputValidation(input);

        System.out.print("Enter the ticket release rate: ");
        int ticketReleaseRate = inputValidation(input);

        System.out.print("Enter the customer retrieval rate: ");
        int customerRetrievalRate = inputValidation(input);

        System.out.print("Enter the maximum ticket capacity: ");
        int maximumTicketCapacity = inputValidation(input);

        sysConfig = new SystemConfiguration(totalTickets, ticketReleaseRate, customerRetrievalRate, maximumTicketCapacity);
        JsonConfiguration.saveConfiguration(sysConfig);

    }

    public static int inputValidation(Scanner input){
        int value;
        while(true){
            if(input.hasNextInt()){     //checks is the input is an integer
                value = input.nextInt();
                if(value>0){     //checks for a positive integer
                    break;
                }else{
                    System.out.print("Invalid input! Please enter a positive integer: ");
                }
            }else{
                System.out.print("Invalid input! Enter the value again: ");
                input.next();
            }
        }
        return value;
    }

    public static void startSimulation(){

        //in case if the user enter 'S' without exiting the current simulation
        if (!threadList.isEmpty()) {
            System.out.println("Simulation is already running. Please stop it before starting again.");
            return;
        }

        TicketPool ticketPool = new TicketPool(sysConfig.getMaximumTicketCapacity());
        //this ensures both the vendor and customer threads interact with the same ticket pool
        //vendorCount and customerCount are hard-coded in SystemConfiguration class

        //create vendor threads and add to threadList
        for(int i=0; i< sysConfig.getVendorCount(); i++){
            Runnable vendor = new Vendor(ticketPool, sysConfig.getTicketReleaseRate());
            Thread vendorThread = new Thread(vendor);
            threadList.add(vendorThread);
        }

        //create customer threads and add to threadList
        for(int i=0; i< sysConfig.getCustomerCount(); i++){
            Runnable customer = new Customer(ticketPool, sysConfig.getCustomerRetrievalRate());
            Thread customerThread = new Thread(customer);
            threadList.add(customerThread);
        }

        threadList.forEach(Thread::start);   //simulation started
    }

    public static void stopAllThreads(){
        threadList.forEach(Thread::interrupt);     //Interrupt all threads using the forEach method
        threadList.forEach(thread -> {           //to ensure main thread waits for all the other threads are terminated
            try{
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();      //wait for running threads to terminate
            }
        });
        threadList.clear();
        System.out.println("All transactions have been stopped.");
    }
}
