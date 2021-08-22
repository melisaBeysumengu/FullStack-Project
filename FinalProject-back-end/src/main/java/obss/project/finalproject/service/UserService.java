package obss.project.finalproject.service;

import obss.project.finalproject.dto.SignupRequest;
import obss.project.finalproject.dto.UpdateUserRequest;
import obss.project.finalproject.model.Product;
import obss.project.finalproject.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User createNewUser(User user);

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<Product> getFavoriteList(String username);

    List<Product> addFavoriteList(String username, Product product);

    List<Product> deleteFavoriteList(String username, Product product);

    List<User> getBlackList(String username);

    List<User> addBlackList(String username, User user);

    List<User> deleteBlackList(String username, User user);

    List<User> getAll();

    ResponseEntity<?> removeUserByUsername(String username);

    User updateUser(String username, UpdateUserRequest signupRequest);

}
