package com.example.raul;

public class DadosTreino {
    String id;
    String name;
    String payment;

    public DadosTreino(String id, String name, String payment) {
        this.id = id;
        this.name = name;
        this.payment = payment;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPayment() {
        return payment;
    }
}
