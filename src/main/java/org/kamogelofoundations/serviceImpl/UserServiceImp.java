package org.kamogelofoundations.serviceImpl;

 
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.kamogelofoundations.dto.Cart;
import org.kamogelofoundations.dto.Role;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.repository.RoleRepository;
import org.kamogelofoundations.repository.UserRepository;
import org.kamogelofoundations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tshepo
 */
@Service
public class UserServiceImp implements UserService {

   //private final UserRepository userRepository;
	//private final RoleRepository roleRepository;
	//private final PasswordEncoder passwordEncoder;
	//@Value("${app.user.verification}")
	//private Boolean requireActivation;
	//@Value("${app.secret}")
	private String applicationSecret;

	//private static final String USER_ROLE = "ROLE_USER";
/*
	@Autowired
	private HttpSession httpSession;

	@Autowired
	public UserServiceImp(UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	*/
	@Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private RoleRepository roleRespository;
	 
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;

	 @Override
	 public User findUserByEmail(String email) {
	  return userRepository.findByEmail(email);
	 }

	 @Override
	 public void saveUser(User user) {
	  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	  user.setActive(true);
	  Role userRole = roleRespository.findByRole("USER");
	  user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	  userRepository.save(user);
	 }

	 @Override
	 public void updateUser(User user) {
	  user.setActive(true);
	 // Role userRole = roleRespository.findByRole("ADMIN");
	  //user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	  userRepository.save(user);
	 }
	 @Override
	public boolean authenticate(String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User get(long id) {
		// TODO Auto-generated method stub
		return userRepository.getOne(id);
	}

	@Override
	public User get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart findUserByCartid(long cartId) {
		return userRepository.get(cartId);
	}
	
	

	public Boolean resetPassword(User user) {
	User u = this.userRepository.findOneByEmail(user.getEmail());
		if (u != null) {
			u.setPassword(encodeUserPassword(user.getPassword()));
			//u.setToken("1");
		//	this.userRepository.save(u);
			return true;
		}
		return false;
	}

	public String encodeUserPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		return passwordEncoder.encode(password);

	}

	public User resetActivation(String email) {
		User u = this.userRepository.findOneByEmail(email);
		if (u != null) {
			createActivationToken(u, true);
			return u;
		}
		return null;
	}

	public String createActivationToken(User user, Boolean save) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String activationToken = encoder.encodePassword(user.getEmail(),
				applicationSecret);
		if (save) {
			user.setToken(activationToken);
			this.userRepository.save(user);
		}
		return activationToken;
	}


	public String createResetPasswordToken(User user, Boolean save) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String resetToken = encoder.encodePassword(user.getEmail(),
				applicationSecret);
		if (save) {
			user.setToken(resetToken);
			this.userRepository.save(user);
		}
		return resetToken;
	}



	
	/**@Override
	public boolean authenticate(String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<User> findAll() {		
		return this.userRepository.findAll();
	}
	@Override
	public Page<User> findAll(Pageable pageable) {		
		return this.userRepository.findAll(pageable);
	}
	@Override
	public User findById(Long id) {
		return this.userRepository.getOne(id);
	}
	@Override
	public User create(User user) {
		// Encode user's password before adding it to database
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return this.userRepository.save(user);
	}
	@Override
	public User edit(User user) {		
		return this.userRepository.save(user);
	}
	@Override
	public void deleteById(Long id) {
	   
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		// Encode plaintext password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(true);
		// Set Role to ROLE_USER
		user.setRoles(Collections.singletonList(roleRepository
				.findByRole(USER_ROLE)));
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User findPasswordByEmail(String email) {
		User p = userRepository.findPasswordByEmail(email);
		User u = new User();
		u.getPassword();
		System.out.print(p);

		return p;
	}

	@Override
	public Iterable<User> listStudentsBySurname(String addressLineOne) {
		return userRepository.findByLastName(addressLineOne);
	}

	@Override
	public long userNo() {
		return userRepository.count();
	}

	@Override
	public long totalNormalUsers() {
		return userRepository.count();
	}

	@Override
	public User get(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public void updateLastLogin(String username) {
		this.userRepository.updateLastLogin(username);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	
	public String createResetPasswordToken(User user, Boolean save) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String resetToken = encoder.encodePassword(user.getEmail(),
				applicationSecret);
		if (save) {
			user.setToken(resetToken);
			this.userRepository.save(user);
		}
		return resetToken;
	}

	public void autoLogin(User user) {
		autoLogin(user.getUsername());
	}

	public final String CURRENT_USER_KEY = "CURRENT_USER";

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userRepository.findOneByUsernameOrEmail(username, username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		if (requireActivation && !user.getToken().equals("1")) {
			App.log.error("User [" + username
					+ "] tried to login but is not activated");
			throw new UsernameNotFoundException(username
					+ " has not been activated yet");
		}
		httpSession.setAttribute(CURRENT_USER_KEY, user);
		List<GrantedAuthority> auth = AuthorityUtils
				.commaSeparatedStringToAuthorityList(user.getRoles().getClass()
						.getName());

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), auth);
	}

	public void autoLogin(String username) {
		UserDetails userDetails = this.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);
		if (auth.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
	}

	public Boolean resetPassword(User user) {
		Optional<User> u = this.userRepository.getByUsername(user.getUsername());
		if (u != null) {
			//u.setPassword(encodeUserPassword(user.getPassword()));
			//u.setToken("1");
		//	this.userRepository.save(u);
			return true;
		}
		return false;
	}

	public String encodeUserPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		return passwordEncoder.encode(password);

	}

	public User resetActivation(String email) {
		User u = this.userRepository.findOneByEmail(email);
		if (u != null) {
			createActivationToken(u, true);
			return u;
		}
		return null;
	}

	public String createActivationToken(User user, Boolean save) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String activationToken = encoder.encodePassword(user.getUsername(),
				applicationSecret);
		if (save) {
			user.setToken(activationToken);
			this.userRepository.save(user);
		}
		return activationToken;
	}

	public User getLoggedInUser() {
		return getLoggedInUser(false);
	}

	public User getLoggedInUser(Boolean forceFresh) {
		String userName = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		User user = (User) httpSession.getAttribute(CURRENT_USER_KEY);
		if (forceFresh || httpSession.getAttribute(CURRENT_USER_KEY) == null) {
			//user = this.userRepository.getByUsername(userName);
			httpSession.setAttribute(CURRENT_USER_KEY, user);
		}
		return user;
	}

	public void updateProfilePicture(User user, String profilePicture) {
		this.userRepository.updateProfilePicture(user.getUsername(),
				profilePicture);
	}



	@Override
	public User findByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;//userRepository.findByUsername(userName);
	}



	@Override
	public void updateUser(String username, User newData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(String username, User newData) {
		this.userRepository.updateUser(username, newData.getEmail(),

		newData.getContactNumber());

	}
*/
}
