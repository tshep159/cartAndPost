package org.kamogelofoundations.service;

import java.util.List;
import java.util.Optional;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Tshepo
 */
public interface UserService {
	
	
boolean authenticate(String userName, String password);
	

public Cart findUserByCartid(long cartId);

public User findUserByEmail(String email);

public void saveUser(User user);
public void updateUser(User user);

public User get(Long id);


public String createResetPasswordToken(User user, Boolean save);

public Boolean resetPassword(User user);



User get(long id);
public User resetActivation(String email);



/*
List<User> findAll();
	Page<User> findAll(Pageable pageable);
	User findByUserName(String userName);
	User findById(Long id);
	User create(User user);
	User edit(User user);
	void deleteById(Long id);
	

	Optional<User> findByUsername(String username);


	
    Optional<User> findByEmail(String email);

    User save(User user);

    User findPasswordByEmail(String email);

    long userNo();

    Iterable<User> listStudentsBySurname(String addressLineOne);

    long totalNormalUsers();


    public void updateLastLogin(String username);

    public User getUserById(Long id);

    public void updateUser(User user);


    public UserDetails loadUserByUsername(String username);

    public String createResetPasswordToken(User user, Boolean save);

    public Boolean resetPassword(User user);

    public void autoLogin(String username);

    User getLoggedInUser();

    public User resetActivation(String email);

    public void updateProfilePicture(User user, String profilePicture);

    public void updateUser(String username, User newData);
*/
	
	
}
