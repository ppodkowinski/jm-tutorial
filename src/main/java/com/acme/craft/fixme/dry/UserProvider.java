package com.acme.craft.fixme.dry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserProvider {

	private static final int ADULT_AGE = 18;

	public Set<String> doSomething(List<User> userListCollection) {
		Set<String> userNames = new HashSet<String>();
		for (User user : userListCollection) {
			if (user.getAge() > ADULT_AGE) {
				userNames.add(user.getFullName());
			}
		}
		return userNames;
	}

}
