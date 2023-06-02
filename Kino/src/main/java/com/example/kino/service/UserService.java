package com.example.kino.service;

import com.example.kino.dto.UserDto;
import com.example.kino.exception.MainException;
import com.example.kino.mapper.UserMapper;
import com.example.kino.model.User;
import com.example.kino.repository.UserRepository;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
/*
    @PersistenceContext
*/
    private final UserRepository userRepo;
/*
    private final RoleService roleService;
*/

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserDto> allUsers() {
        List<User> users = userRepo.findAll();
        return convertToUserDtoList(users);
    }

    public boolean isValidUser(String username, String password) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Проверка пароля
            return user.getPassword().equals(password);
        }
        return false;
    }

    public void saveUser(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent() || userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new MainException("User with this email or nickname already exists!");
        }
        try {
            userRepo.save(user);
        } catch (DataAccessException e) {
            throw new MainException("Unable to register user. Please, try again later");
        }
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepo.findById(userId);
        return userFromDb.orElse(new User());
    }

    public boolean deleteUser(Long userId) {
        if (userRepo.findById(userId).isPresent()) {
            userRepo.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean isUserAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object userAttribute = session.getAttribute("user");

            return userAttribute instanceof User;
        }
        return false;
    }

    public UserDto getUserDtoByUsername(String username) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Создание объекта UserDto на основе объекта User
            return convertToUserDto(user);
        }
        return null;
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    private List<UserDto> convertToUserDtoList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            /*UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());*/

            UserDto ud = convertToUserDto(user);

            userDtos.add(ud);
        }

        return userDtos;
    }


/*    public UserDto setAdminRole(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);

        if (optionalUser.isEmpty()) {
            throw new MainException("Unable to change user's role. Please try again later.");
        }

        User user = optionalUser.get();
        roleService.setAdminRole(user);
        User savedUser = userRepo.save(user);

        return UserMapper.toUserDto(savedUser);
    }*/

}