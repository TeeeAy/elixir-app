package com.internship.elixirapp.service;

import com.internship.elixirapp.dto.UserDto;
import com.internship.elixirapp.entity.User;
import com.internship.elixirapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with id='%s' does not exist";

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    @Lazy
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistsException {
        if (nameExists(userDto.getName())) {
            throw new UserAlreadyExistsException("There is an account with that username: "
                    + userDto.getName());
        }

        User user = User.builder()
                .withName(userDto.getName())
                .withEmail(userDto.getEmail())
                .withPassword(passwordEncoder.encode(userDto.getPassword()))
                .build();

        return userRepository.save(user);
    }

    private boolean nameExists(String name) {
        return userRepository.findByName(name) != null;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id))
        );
    }
}
