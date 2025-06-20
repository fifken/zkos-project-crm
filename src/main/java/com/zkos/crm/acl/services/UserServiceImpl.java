package com.zkos.crm.acl.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zkos.crm.acl.entity.User;

@Service("userService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceImpl implements UserDetailsService {

  @Autowired
  UserDao userDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      User user = userDao.get(username);
      Set<GrantedAuthority> authorities = user.getRoles()
          .stream()
          .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new RuntimeException("Invalid Login");
    }

  }
}
