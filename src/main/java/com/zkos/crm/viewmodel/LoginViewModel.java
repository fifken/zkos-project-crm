package com.zkos.crm.viewmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.zkos.crm.acl.entity.User;
import com.zkos.crm.acl.services.UserDao;

@VariableResolver(DelegatingVariableResolver.class)
public class LoginViewModel {

    private String username;
    private String password;
    private String errorMsg;

    @WireVariable
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Init
    public void init() {
        username = "";
        password = "";
        errorMsg = "";
    }

    @Command
    @NotifyChange("errorMsg")
    public void login() {
        User user = userDao.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            Executions.sendRedirect("/index.zul");
        } else {
            errorMsg = "Username atau password salah!";
        }
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
