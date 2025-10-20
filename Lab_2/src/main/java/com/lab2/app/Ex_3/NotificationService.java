package com.lab2.app.Ex_3;

public interface NotificationService {
    public void setNextHandler(NotificationService next);
    public void handleEvent(SensorEvent e);
}
