package org.kamogelofoundations.repository;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author Tshepo
 */
public interface UserRepository extends JpaRepository<User, Long> {

	
	User findByEmail(String email);
	
	@Query("FROM User WHERE cart_id = :cartId ")
	Cart get(@Param("cartId")long cartId);
	

    User findOneByEmail(String email);

    User findOneByFirstnameOrEmail(String firstname, String email);

    User findOneByToken(String token);


	//public Cart findByUser(@Param ("cartId")User user );
	
}














	
	
    //User findByUsername(String username);

	
/*	
Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByUsername(@Param("username") String username);
    

    Optional<User> getByUsername(@Param("username") String username);

    User findPasswordByEmail(@Param("email") String email);

    Iterable<User> findByLastName(
            @Param("lastName") String addressLineOne);

    @Modifying
    @Transactional
    @Query("update User u set u.lastLogin = CURRENT_TIMESTAMP where u.username = ?1")
    int updateLastLogin(String username);

    User findOneByEmail(String email);

    User findOneByUsernameOrEmail(String username, String email);

    User findOneByToken(String token);


    @Modifying
    @Transactional
    @Query("update User u set u.profilePicture = ?2 where u.username = ?1")
    int updateProfilePicture(String userName, String profilePicture);

    @Modifying
    @Transactional
    @Query("update User u set u.email = :email, u.contactNumber = :contactNumber where u.username = :username")
    int updateUser(@Param("username") String username,
            @Param("email") String email,
            @Param("contactNumber") String contactNumber);
}*/
