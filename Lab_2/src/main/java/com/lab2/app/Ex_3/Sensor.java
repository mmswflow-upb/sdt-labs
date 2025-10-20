package com.lab2.app.Ex_3;

import java.time.LocalDateTime;
import java.util.Random;

public class Sensor extends Thread {

    private NotificationService handlerChain;
    private final String location;
    private final Random random;
    private volatile boolean running = true;

    public Sensor(String location) {
        this.location = location;
        this.random = new Random();
        setupHandlerChain();
    }

   
    
    private void setupHandlerChain() {
        // Create handlers
        Logger logger = new Logger();
        EmailNotification emailNotification = new EmailNotification();
        TelephoneNotification telephoneNotification = new TelephoneNotification();

        // Build the chain: Logger -> Email -> Telephone
        logger.setNextHandler(emailNotification);
        emailNotification.setNextHandler(telephoneNotification);

        // Set the first handler in the chain
        this.handlerChain = logger;
    }

    @Override
    public void run() {
        System.out.println("Sensor at " + location + " started monitoring...");

        while (running) {
            try {
                // Generate a random event
                SensorEvent event = generateRandomEvent();

                // Dispatch the event to the handler chain
                System.out.println("\n[" + location + "] Dispatching event: " + event);
                handlerChain.handleEvent(event);

                // Wait for a random interval (1-5 seconds) before next event
                Thread.sleep(1000 + random.nextInt(4000));

            } catch (InterruptedException e) {
                System.out.println("Sensor at " + location + " interrupted");
                running = false;
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Sensor at " + location + " stopped monitoring.");
    }

    /**
     * Generates a random sensor event
     */
    private SensorEvent generateRandomEvent() {
        EventType[] eventTypes = EventType.values();
        EventType randomType = eventTypes[random.nextInt(eventTypes.length)];
        LocalDateTime timestamp = LocalDateTime.now();

        return new SensorEvent(location, timestamp, randomType);
    }

   
    public void stopSensor() {
        running = false;
        this.interrupt();
    }

}
