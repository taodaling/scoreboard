package org.happyhour.scoreboard.controller;

import org.happyhour.scoreboard.model.User;
import org.happyhour.scoreboard.service.UserService;
import org.happyhour.scoreboard.util.BizException;
import org.happyhour.scoreboard.util.Context;
import org.happyhour.scoreboard.util.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        List<User> users = userService.getAllUser();
        users.forEach(user -> user.setPassword(null));
        return Result.ofSuccess(users);
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
        User user = userService.getUser(id);
        user.setPassword(null);
        return Result.ofSuccess(user);
    }

    @RequestMapping(value = "/self", method = RequestMethod.GET)
    public Result<User> getSelf() {
        User user = userService.getUser(Context.getNotNullUser());
        user.setPassword(null);
        return Result.ofSuccess(user);
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
