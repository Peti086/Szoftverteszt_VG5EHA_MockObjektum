package hu.example;

public class BankService {
    private final NotificationService notificationService;

    public BankService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void deposit(BankAccount account, double amount) {
        account.deposit(amount);

        notificationService.send("Deposit successful");
    }
}
