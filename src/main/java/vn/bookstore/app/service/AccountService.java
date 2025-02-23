package vn.bookstore.app.service;

import org.springframework.stereotype.Service;
import vn.bookstore.app.model.Account;
import vn.bookstore.app.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> handelFetchAllAccount() {
        return accountRepository.findAll();
    }
    public void saveAccount(Account account) {
        account.setStatus(1);
        this.accountRepository.save(account);
    }
    public Account handelFetchAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElse(null);
    }
    public Account handelUpdateAccount(Account account) {
        Account currentAccount = handelFetchAccountById(account.getId());
        if(currentAccount != null) {
            if (account.getPassword() != null) currentAccount.setPassword(account.getPassword());
            this.accountRepository.save(currentAccount);
        }
        return currentAccount;
    }
    public void handelDeleteAccount(Long id) {
        Account currentAccount = handelFetchAccountById(id);
        if(currentAccount != null) {
            currentAccount.setStatus(0);
            this.accountRepository.save(currentAccount);
        }
    }
}
