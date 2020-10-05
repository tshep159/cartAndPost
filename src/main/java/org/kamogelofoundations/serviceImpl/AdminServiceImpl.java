package org.kamogelofoundations.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.repository.AdminRepository;
import org.kamogelofoundations.repository.RoleRepository;
import org.kamogelofoundations.service.AdminService;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Iterable<User> listUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getByUsername(String username) {
		return null;
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public void saveUser(User user) {
		user.setActive(user.getActive());
		
		userRepository.save(user);
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public long TotalUsers() {
		return userRepository.count();
	}

	@Override
	public User get(Long id) {
		return (User) userRepository.findOne(id);
	}

    @Override
    public void listWinners() {
//        QuizRepository.findWinners();
    }

}
