package client;

import java.util.ArrayList;
import java.util.List;
import account.Account;

public class Client {
    private String clientId;
    private String name;
    private String password;
    private List<Account> accounts;

    public Client(String clientId, String name, String password) {
        this.clientId = clientId;
        this.name = name;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public String getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public boolean removeAccount(String accountNumber) {
        return accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
