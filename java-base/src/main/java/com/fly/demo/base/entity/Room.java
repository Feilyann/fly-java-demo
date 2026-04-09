package com.fly.demo.base.entity;

import lombok.Data;

@Data
public class Room {

    private int counter;

    public void increment() {
        synchronized (this) {
            counter++;
        }
    }

    public void decement() {
        synchronized (this) {
            counter--;
        }
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
