package org.keyboard.framework;

public class Main {

    public static void main(String[] args) {

        ProductService service = new ProductService();

        System.out.println(service.findProductname(1));

        System.err.println("END");

    }

}
