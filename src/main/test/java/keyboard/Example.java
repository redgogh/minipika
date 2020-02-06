package java.keyboard;

import org.junit.Test;

public class Example {

    @Test
    public void test1() {

        int value = 47;

        int current = 0;        // 当前结果
        int previous = 0;       // 上一个结果
        for (; ; ) {
            previous = current + 1;
            current = current + 5;
            if (current >= value) {
                System.out.println(String.valueOf(previous).concat("-").concat(String.valueOf(current - (current - value))));
                break;
            }
            System.out.println(String.valueOf(previous).concat("-").concat(String.valueOf(current)));
        }

    }

    @Test
    public void test2() {

        String v1 = "1.9.1";
        String v2 = "1.8.5";

        String[] v1Array = v1.split("\\.");
        String[] v2Array = v2.split("\\.");

        int min = 0;
        if (v1Array.length <= v2Array.length) {
            min = v1Array.length;
        } else {
            min = v2Array.length;
        }

        for (int i = 0; i < min; i++) {

            if(Integer.valueOf(v1Array[i]) > Integer.valueOf(v2Array[i])){

            }

        }

    }

}
