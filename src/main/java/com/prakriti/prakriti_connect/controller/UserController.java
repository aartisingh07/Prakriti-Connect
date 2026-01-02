package com.prakriti.prakriti_connect.controller;

import com.prakriti.prakriti_connect.dto.DisplayDto;
import com.prakriti.prakriti_connect.dto.LoginDto;
import com.prakriti.prakriti_connect.dto.UpdateDto;
import com.prakriti.prakriti_connect.model.User;
import com.prakriti.prakriti_connect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserRepo userRepo;
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userRepo.save(user);

//        History h1 = new History();
//        h1.setDescription("User Self Created: "+user.getUsername());
//        historyRepo.save(h1);

        return "Signup Successful";
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginDto u) {

        User user = userRepo.findByUsername(u.getUsername());

        if (user == null) {
            return "User Not Found";   // ✅ semicolon fixed
        }

        if (!u.getPassword().equals(user.getPassword())) {
            return "Password Incorrect";
        }
        return String.valueOf(user.getId()); // ✅ success
    }

    @GetMapping("/get-details/{id}")
    public DisplayDto display(@PathVariable int id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));

        DisplayDto displayDto = new DisplayDto();
        displayDto.setId(user.getId());          // 👈 IMPORTANT
        displayDto.setUsername(user.getUsername());

        return displayDto;
    }

    @PostMapping("/update")
    public String update(@RequestBody UpdateDto obj){
        User user = userRepo.findById(obj.getId()).orElseThrow(()->new RuntimeException("Not Found"));

//        History h1 = new History();

        if(obj.getKey().equalsIgnoreCase("name")){
            if(user.getName().equalsIgnoreCase(obj.getValue())) return "Cannot be same";

//            h1.setDescription("User updated Name from: "+user.getName()+" to "+obj.getValue());

            user.setName(obj.getValue());
        }
        else if(obj.getKey().equalsIgnoreCase("password")){
//            h1.setDescription("User "+user.getUsername()+" updated Password!");

            user.setPassword(obj.getValue());
        }
        else if(obj.getKey().equalsIgnoreCase("email")){
            User user2 = userRepo.findByEmail(obj.getValue());
            if(user2 != null) return "Email already exists";

//            h1.setDescription("User updated Email from: "+user.getEmail()+" to "+obj.getValue());

            user.setEmail(obj.getValue());
        }
        else{
            return "Field update not supported";
        }
//        historyRepo.save(h1);
        userRepo.save(user);
        return "Profile Update Done Successfully";
    }
}
