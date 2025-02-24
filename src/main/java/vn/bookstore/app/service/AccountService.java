package vn.bookstore.app.service;

import vn.bookstore.app.model.Account;
import vn.bookstore.app.repository.AccountRepository;

import java.util.List;

public interface AccountService {
    public List<Account> handelFetchAllAccount();
    public void saveAccount(Account account);
    public Account handelFetchAccountById(Long id);
    public Account handelUpdateAccount(Account account);
    public void handelDeleteAccount(Long id);
    public Account handelFindAccountByUsername(String username);
}

