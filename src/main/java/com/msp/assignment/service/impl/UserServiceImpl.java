package com.msp.assignment.service.impl;

import com.msp.assignment.enumerated.LoginType;
import com.msp.assignment.enumerated.UserType;
import com.msp.assignment.model.Users;
import com.msp.assignment.repository.UsersRepository;
import com.msp.assignment.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users signup(Users user) {
        System.out.println(user.getPassword());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        if(user.getUserType() == null){
            user.setUserType(UserType.ADMIN);
        }
        if(user.getLoginType() == null){
            user.setLoginType(LoginType.GOOGLE);
        }
        return usersRepository.save(user);
    }
    @Override
    public Users login(String email, String password){
        Users user = usersRepository.findByEmail(email);
        if (user != null && DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return user;
        }
        return null;
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
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            }
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
