package com.fly.demo.base.test.unsafe;

import com.fly.demo.base.entity.Money;
import com.fly.demo.base.entity.Person;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {

    private static Unsafe UNSAFE;

    /*
     * 获取Unsafe对象
     */
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        Money money = new Money();
//        money.setValue(1);
//        Person person = new Person(money);
//        person.run();
        testUnsafe();
    }

    /*
     * Unsafe中的方法可以直接操作硬件内存中的数据
     */
    public static void testUnsafe() {
        try {
            Test test = new Test();
            //直接获取内存方法区中的某个字段的偏移量, 即在内存中的物理地址
            long indexOffset = UNSAFE.objectFieldOffset(Test.class.getDeclaredField("index"));
            //CAS乐观锁, 直接通过比较替换内存中的属性值 (对象本身, 该属性的偏移量, 该属性的当前值, 该属性的预期值), CAS会拿该属性的当前值与内存中的值作比较, 当两值相等时, 才可以替换为预期值
            boolean b = UNSAFE.compareAndSwapInt(test, indexOffset, test.getIndex(), test.getIndex() + 1);
            //获取当前内存中的属性值 (即最新值)
            int index = UNSAFE.getIntVolatile(test, indexOffset);

            /*
             * 关于数组
             */
            Test[] testArray = new Test[10];
            //获取当前数组头位置偏移量
            int testArrayOffset = UNSAFE.arrayBaseOffset(Test[].class);
            //获取单个数组大小
            int arrayIndexScale = UNSAFE.arrayIndexScale(Test[].class);
            //获取数组中下标为i的位置上的数值
            int i = 0;
            //方式一
            Test test1 = (Test) UNSAFE.getObject(testArray, (i << (31 - Integer.numberOfLeadingZeros(arrayIndexScale))) + testArrayOffset);
            //方式二
            long iOffset = testArrayOffset + i * arrayIndexScale; //i的偏移量
            test1 = (Test) UNSAFE.getObjectVolatile(testArray, iOffset);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static class Test{

        private int index;

        private String value;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
