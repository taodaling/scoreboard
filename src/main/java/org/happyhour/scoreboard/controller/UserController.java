package org.happyhour.scoreboard.controller;

import org.happyhour.scoreboard.model.User;
import org.happyhour.scoreboard.service.UserService;
import org.happyhour.scoreboard.util.BizException;
import org.happyhour.scoreboard.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Resource
    UserService userService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Result<List<User>> getAllUser() {
        return Result.ofSuccess(userService.getAllUser());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<String> login(@RequestBody User user) {
        return Result.ofSuccess(userService.login(user));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<User> getUser(@PathVariable Integer id) {
        if (id == null) {
            throw new BizException("id can't be assigned with null value");
        }
        return Result.ofSuccess(userService.getUser(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Result<Void> update(@PathVariable("id") Integer id, @RequestBody User user) {
        if (!Objects.equals(id, user.getUserid())) {
            throw new BizException("id in url doesn't match the one in post body");
        }
        userService.updateUser(user);
        return Result.ofSuccess();
    }

    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public Result<Void> addUser(@RequestBody User user) {
        userService.addUser(user);
        return Result.ofSuccess();
    }
}
