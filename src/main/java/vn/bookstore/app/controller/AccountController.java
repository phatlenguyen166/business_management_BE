package vn.bookstore.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bookstore.app.model.Account;
import vn.bookstore.app.model.request.LoginDTO;
import vn.bookstore.app.service.impl.AccountServiceImpl;
import vn.bookstore.app.util.error.IdInvalidException;

import java.util.List;

@RestController
public class AccountController {
    private AccountServiceImpl accountService;
    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> fetchAllAccounts() {
        return ResponseEntity.ok().body(this.accountService.handelFetchAllAccount());
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody LoginDTO reqAccount) throws IdInvalidException {
        Account account = this.accountService.handelFindAccountByUsername(reqAccount.getUsername());
        if (account.getUsername().equals(reqAccount.getUsername()) ) {
            throw new  IdInvalidException("Tài khoản đã tồn tại trong hệ thống");
        }
        this.accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> fetchAccountById(@PathVariable Long id) throws IdInvalidException {
        Account account = this.accountService.handelFetchAccountById(id);
        if (account == null) {
            throw new IdInvalidException("Tài khoản không tồn tại");
        }
        return ResponseEntity.ok().body(account);
    }
    @PutMapping("/accounts")
    public ResponseEntity<Account> updateAccount(@RequestBody Account updateAccount) throws IdInvalidException {
        if(this.accountService.handelFetchAccountById(updateAccount.getId()) == null) {
            throw  new IdInvalidException("Tài khoản không tồn tại trong hệ thống");
        }
        return ResponseEntity.ok().body(this.accountService.handelUpdateAccount(updateAccount));
    }

    //API delete
    @PutMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable Long id) {
        this.accountService.handelDeleteAccount(id);
        return ResponseEntity.ok().body(null);
    }

}
