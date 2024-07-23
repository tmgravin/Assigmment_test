package com.intern.msp.service.impl;

import com.intern.msp.model.Users;
import com.intern.msp.repository.UsersRepository;
import com.intern.msp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public Users getUserById(Long id) {
        Optional<Users> users = usersRepository.findById(id);
        return users.orElse(null);
    }

    @Override
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users updateUser(Long id, Users user) {
        Optional<Users> existingUserOptional = usersRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            Users existingUser = existingUserOptional.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setUserType(user.getUserType());
            existingUser.setLoginType(user.getLoginType());
            existingUser.setUpdatedAt(user.getUpdatedAt());
            return usersRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
    usersRepository.deleteById(id);
    }
}
