package com.lab2.app.Ex_3;

import java.time.LocalDateTime;

public class SensorEvent {

    final String location;
    final LocalDateTime timestamp;
    final EventType eventType;

    public SensorEvent(String location, LocalDateTime timestamp, EventType eventType){
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.location = location;
    }

    @Override
    public String toString(){
        return this.eventType + " - " +location + " - " + timestamp;
    }
}
