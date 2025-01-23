package account;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private List<Account> accounts;

    public AccountDAO() {
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean removeAccount(String accountNumber) {
        return accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
