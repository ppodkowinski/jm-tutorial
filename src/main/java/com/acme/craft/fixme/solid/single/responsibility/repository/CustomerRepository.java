package com.acme.craft.fixme.solid.single.responsibility.repository;

public class CustomerRepository {

	public void add() {
		try {
			/*
			 * do some database stuff here
			 */
		} catch (Exception e) {
			Errorutils.handleError("database error");
		}
	}

	
}
