package com.acme.craft.fixme.dry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Collection;

public class UserService {

	private static final int AGE_ADULT = 18;

	public HashSet doSomethingDifferent(List<User> users) {
		HashSet<String> usrnms = new HashSet<String>();
		for (int i = users.size(); i >= 0; i--) {
			if (users.get(i).getAge() > AGE_ADULT) {
				String temp = users.get(i).getFullName();
				usrnms.add(temp);
			}
		}
		return usrnms;
	}
}
