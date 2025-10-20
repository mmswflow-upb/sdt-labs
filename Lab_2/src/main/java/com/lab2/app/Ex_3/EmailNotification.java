package com.lab2.app.Ex_3;

public class EmailNotification extends NotificationServiceBase {

    public EmailNotification() {
        super();
    }

    @Override
    public void handleEvent(SensorEvent e){
        
        doHandleEvent(e);
        if(this.next != null){
            next.handleEvent(e);
        }
        
    }
    
    @Override
    public void doHandleEvent(SensorEvent e){
        System.out.println("Email was sent for event - " + e.toString());
    }
    
}
