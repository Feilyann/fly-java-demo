package com.fly.demo.base.test.demo;

public interface IMessage {
    void sendMsg(String msg);
}

class Test {
    
    public void test(IMessage message) {
        message.sendMsg("测试消息");
    }
    
    public static void main(String[] args) {
        Test test = new Test();
        //普通形式
        test.test(new IMessage() {
            @Override
            public void sendMsg(String msg) {
                System.out.println(msg);
            }
        });
        //Lambda表达式
        test.test((msg) -> System.out.println("log打印：" + msg));
    }
}