package java.poseidon;

import org.laniakeamly.poseidon.framework.tools.Calculator;

/**
 * Copyright: Create by TianSheng on 2019/12/7 21:35
 */
public class CalculatorExample {

    public static void main(String[] args) {
        System.out.println("Java Calc: " + (2000 + 566600 + 7000 * 99 / 20  + 10 / 25 * 60 *20 -10));
        Calculator calculator = new Calculator();
        long sum = calculator.express("2000 + 566600 + 7000 * 99 / 20  + 10 / 25 * 60 *20 -10");
        System.out.println("My Calc: " + sum);
    }

}
