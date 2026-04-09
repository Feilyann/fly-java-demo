package com.fly.demo.base.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
@Data
public class MessageQueue {

    private LinkedList<Message> list = new LinkedList();

    private int capacity;

    public MessageQueue() {
        this.capacity = 30;
    }

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public void take() {
        synchronized (list) {
            while (true) {
                if (list.isEmpty()) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    list.removeFirst();
                }
            }
        }
    }

    public void put(Message message) {
        synchronized (list) {
            while(true) {
                if (list.size() >= capacity) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    list.add(message);
                    list.notifyAll();
                }
            }
        }
    }

    final class Message{
        private int id;

        private Object value;

        public Message(int id, Object value) {
            this.id = id;
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public Object getValue() {
            return value;
        }
    }
}
