package vn.bookstore.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.model.Account;
import vn.bookstore.app.service.AccountService;

import java.util.List;

@RestController
public class AccountController {
    private AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> fetchAllAccounts() {
        return ResponseEntity.ok().body(this.accountService.handelFetchAllAccount());
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        this.accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> fetchAccountById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.accountService.handelFetchAccountById(id));
    }
    @PutMapping("/accounts")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        return ResponseEntity.ok().body(this.accountService.handelUpdateAccount(account));
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable Long id) {
        this.accountService.handelDeleteAccount(id);
        return ResponseEntity.ok().body(null);
    }

}
