package com.librarymanagement.rest.Dao;

import com.librarymanagement.rest.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
}
