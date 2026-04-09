package com.fly.demo.base.entity;

public class TicketWindow {

    private int ticketCount;

    public TicketWindow(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public synchronized int sell(int amount) {
        if (ticketCount >= amount) {
            ticketCount -= amount;
            return amount;
        }
        return 0;
    }
}
