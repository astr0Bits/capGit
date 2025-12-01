package service;

import repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User registerUser(String name, String email, String password, User.Role role) {
		// Check if user already exists
		if (userRepository.existsByEmail(email)) {
			throw new RuntimeException("User already exists with this email");
		}

		// Create new user
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(role);
		user.setCreatedAt(LocalDateTime.now());
		user.setVerificationToken(UUID.randomUUID().toString());
		user.setEmailVerified(true);

		// Save current password to history (first password)
		user.getPasswordHistory().add(user.getPassword());

		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public boolean verifyPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	public boolean verifyEmail(String token) {
		Optional<User> userOpt = userRepository.findByVerificationToken(token);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setEmailVerified(true);
			user.setVerificationToken(null);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	public void updatePassword(User user, String newPassword) {
		String encodedPassword = passwordEncoder.encode(newPassword);

		// Check last 10 passwords
		List<String> lastPasswords = user.getPasswordHistory();
		for (String oldPassword : lastPasswords) {
			if (passwordEncoder.matches(newPassword, oldPassword)) {
				throw new RuntimeException("Cannot use one of your last 10 passwords");
			}
		}

		// Update password and maintain history
		user.setPassword(encodedPassword);
		lastPasswords.add(encodedPassword);

		// Keep only last 10 passwords
		if (lastPasswords.size() > 10) {
			lastPasswords.remove(0);
		}

		userRepository.save(user);
	}

	public void addPoints(User user, int points) {
		user.setPoints(user.getPoints() + points);
		checkAndAwardBadges(user);
		userRepository.save(user);
	}
	private void checkAndAwardBadges(User user) {
		int points = user.getPoints();
		Set<User.Badge> badges = user.getBadges();

		if (points >= 100 && !badges.contains(User.Badge.BEGINNER_LEARNER)) {
			badges.add(User.Badge.BEGINNER_LEARNER);
		}
		if (points >= 500 && !badges.contains(User.Badge.SKILL_SEEKER)) {
			badges.add(User.Badge.SKILL_SEEKER);
		}
		if (points >= 1000 && !badges.contains(User.Badge.LEARNING_CHAMPION)) {
			badges.add(User.Badge.LEARNING_CHAMPION);
		}
	}

	public void awardBadge(User user, User.Badge badge) {
		user.getBadges().add(badge);
		userRepository.save(user);
	}
}