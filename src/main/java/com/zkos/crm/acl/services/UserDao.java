package com.zkos.crm.acl.services;

import java.util.List;

import com.zkos.crm.acl.entity.User;

public interface UserDao {

    User findByUsername(String username);

    List<User> findAll();

    void save(User user);

    void deleteById(Long id);
}
