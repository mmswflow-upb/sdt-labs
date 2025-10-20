package com.lab2.app.Ex_3;

public class Logger extends NotificationServiceBase {

    public Logger(){
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

        System.out.println("Event: " + e.toString() + " - was logged");
    }
}
