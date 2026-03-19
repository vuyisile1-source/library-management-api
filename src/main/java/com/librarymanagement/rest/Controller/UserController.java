package com.librarymanagement.rest.Controller;

import com.librarymanagement.rest.Dao.UserDao;
import com.librarymanagement.rest.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping(value = "/api/users")
    public List<User> getUsers(){
        return userDao.findAll();
    }

    @PostMapping(value = "/api/users")
    public String saveUser(@RequestBody User user){
        System.out.println(user.getName());
        userDao.save(user);
        return "User Saved!";
    }

    @PutMapping(value = "/api/update/{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user){
        User updated = userDao.findById(id).get();
        updated.setName(user.getName());
        userDao.save(updated);
        return "User Updated!";
    }

    @DeleteMapping(value = "/api/delete/{id}")
    public String deleterUser(@PathVariable long id){
        User deleted = userDao.findById(id).get();
        userDao.delete(deleted);
        return "User deleted with id: " + id;
    }
}
