package com.lab2.app.Ex_3;

public class TelephoneNotification extends NotificationServiceBase {
    
    public TelephoneNotification() {
        super();
    }

    @Override
    public void handleEvent(SensorEvent e){
        
        if (e.eventType == EventType.Fire || e.eventType == EventType.Intrusion) {
            doHandleEvent(e);
        }
        if(this.next != null){
            next.handleEvent(e);
        }
        
    }
    
    @Override
    public void doHandleEvent(SensorEvent e){
        System.out.println("A call was made for event - " + e.toString());
    }
}
