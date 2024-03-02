package org.example;

public class Order {
    int id;
    String position;
    int price;

    public Order(int id, String position, int price) {
        this.id = id;
        this.position = position;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", position='" + position + '\'' +
                ", price=" + price +
                '}';
    }
}
