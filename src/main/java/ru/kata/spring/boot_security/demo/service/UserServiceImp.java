package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImp implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (userFromDB == null) {
            Set<Role> roles = new HashSet<>();
            if (roleRepository.findByRoleName("ROLE_USER").isEmpty()) {
                roles.add(new Role(1, "ROLE_USER"));
            }
            if (roleRepository.findByRoleName("ROLE_ADMIN").isEmpty()) {
                roles.add(new Role(2, "ROLE_ADMIN"));
            }
            user.setRoleSet(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void editUser(int id, User userUpdate) {
        userUpdate.setId(id);
        userUpdate.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        User userFromDB = (User)loadUserByUsername(userUpdate.getUsername());
        userRepository.save(userFromDB);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}