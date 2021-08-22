package obss.project.finalproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import obss.project.finalproject.dto.MessageResponse;
import obss.project.finalproject.dto.SignupRequest;
import obss.project.finalproject.dto.UpdateUserRequest;
import obss.project.finalproject.exception.UserNotFoundException;
import obss.project.finalproject.model.Product;
import obss.project.finalproject.model.User;
import obss.project.finalproject.repository.UserRepository;
import obss.project.finalproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createNewUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        Objects.requireNonNull(username, "username cannot be null");
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Boolean existsByUsername(String username) {
        Objects.requireNonNull(username, "username cannot be null");
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        Objects.requireNonNull(email, "email cannot be null");
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<Product> getFavoriteList(String username) {
        return userRepository.findByUsername(username).get().getFavoriteList();
    }

    @Override
    public List<Product> addFavoriteList(String username, Product product) {
        User u = userRepository.findByUsername(username).get();
        if(u.getFavoriteList().contains(product)){
            return u.getFavoriteList();
        }
        u.getFavoriteList().add(product);
        userRepository.save(u);
        return u.getFavoriteList();
    }

    @Override
    public List<Product> deleteFavoriteList(String username, Product product) {
        User u = userRepository.findByUsername(username).get();
        u.getFavoriteList().remove(product);
        userRepository.save(u);
        return u.getFavoriteList();
    }

    @Override
    public List<User> getBlackList(String username) {
        return userRepository.findByUsername(username).get().getBlackList();
    }

    @Override
    public List<User> addBlackList(String username, User user) {
        User u = userRepository.findByUsername(username).get();
        if(u.getBlackList().contains(user)){
            return u.getBlackList();
        }
        u.getBlackList().add(user);
        userRepository.save(u);
        return u.getBlackList();
    }

    @Override
    public List<User> deleteBlackList(String username, User user) {
        User u = userRepository.findByUsername(username).get();
        u.getBlackList().remove(user);
        userRepository.save(u);
        return u.getBlackList();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<?> removeUserByUsername(String username) {
        userRepository.removeUserByUsername(username);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

    @Override
    public User updateUser(String username, UpdateUserRequest signupRequest) {
        User newUser = userRepository.findByUsername(username).get();
        newUser.setUsername(signupRequest.getUsername());
        newUser.setEmail(signupRequest.getEmail());
        return userRepository.save(newUser);
    }
}
