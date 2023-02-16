package com.restfulwebservice.socialmediaapplication.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	private static int userCount = 0;
	
	static {
		users.add(new User(++userCount, "Adam", LocalDate.now().minusYears(30)));
		users.add(new User(++userCount, "Eve", LocalDate.now().minusYears(25)));
		users.add(new User(++userCount, "Jim", LocalDate.now().minusYears(20)));
	}

	public List<User> findAll(){
		return users;
	}

	public User findById(int id) {
		Predicate<? super User> predicate = user -> user.getId() == id;
		User user = users.stream().filter(predicate).findFirst().orElse(null);
		return user;
	}

	public User save(User user) {
		user.setId(++userCount);
		users.add(user);
		
		return user;
	}
	
	public void deleteById(int id) {
		Predicate<? super User> predicate = user -> user.getId() == id;
		users.removeIf(predicate);
	}
	
	
}
