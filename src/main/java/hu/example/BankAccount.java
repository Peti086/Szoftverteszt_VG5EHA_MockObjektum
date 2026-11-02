package hu.example;

public class BankAccount {
    private double balance = 0.0;

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("A befizetés összegének nagyobbnak kell lennie 0-nál.");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("A felvét összegének nagyobbnak kell lennie 0-nál.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Nincs elegendő fedezet.");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
