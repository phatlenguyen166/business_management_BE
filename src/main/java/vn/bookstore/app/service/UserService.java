package vn.bookstore.app.service;

import vn.bookstore.app.model.User;

import java.util.List;

public interface UserService {
    public List<User> handleFetchAllUser();
    public User handleCreateUser(User user);
    public User handleFetchUserById(Long id);
    public User handleUpdateUser(User user);
    public void handleDeleteUser(Long id);
    public User handleFindUserByUsername(String username);
    public boolean isExistUsername(String username);
}

