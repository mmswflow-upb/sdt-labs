package com.lab2.app.Ex_3;

public abstract class NotificationServiceBase implements NotificationService {
    protected NotificationService next;

    public NotificationServiceBase(){}

    @Override
    public void setNextHandler(NotificationService next) {
        this.next = next;
    }
    
    @Override
    public abstract void handleEvent(SensorEvent e);
    public abstract void doHandleEvent(SensorEvent e);

}
