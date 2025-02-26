package com.example.band_authentication.user;

import com.example.band_authentication.user.form.UserInfoChangeForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/health_check")
    public ResponseEntity<?> welcome(){
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @GetMapping("/authorize_test")
    public String test(HttpServletRequest request){
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            System.out.println("Attribute: " + name + " " + request.getAttribute(name));
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            System.out.println("Header: " + name + " " + request.getHeader(name));
        }

        return request.getHeader("username");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader String token, @RequestHeader String provider){
        Map<String,String> tokens = userService.login(token, provider);

        return ResponseEntity.ok().body(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader String token){

        Map<String, String> tokens = userService.refresh(token);

        return ResponseEntity.ok().body(tokens);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader String username){
        userService.logout(username);

        return ResponseEntity.ok().build();
    }


    @PatchMapping("/profile")
    public ResponseEntity<?> changeUserInfo(@RequestHeader String username, @ModelAttribute UserInfoChangeForm changeForm){
        userService.changeUserInfo(username, changeForm);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/me")
    public ResponseEntity<?> getMyInfo(@RequestHeader String username){
        return ResponseEntity.ok().body(userService.getUserInfo(username));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username){
        return ResponseEntity.ok().body(userService.getUserInfo(username));
    }

    @GetMapping("/profile/{username}/simple")
    public ResponseEntity<?> getUserSimpleInfo(@PathVariable String username){
        return ResponseEntity.ok().body(userService.getSimpleUserInfo(username));
    }
}
