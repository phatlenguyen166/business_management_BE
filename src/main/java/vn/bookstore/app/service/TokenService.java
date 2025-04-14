package vn.bookstore.app.service;

import vn.bookstore.app.model.Token;

public interface TokenService {

    public long save(Token token);

    public void delete(String username);

    public Token getByUsername(String username);
}
