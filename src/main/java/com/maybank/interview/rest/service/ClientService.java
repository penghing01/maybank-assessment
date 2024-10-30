package com.maybank.interview.rest.service;

public interface ClientService {
    /**
     * Validate user
     *
     * @param username Username to check (Real use case should be something more unique like `IC`)
     */
    void validateUser(String username);
}
