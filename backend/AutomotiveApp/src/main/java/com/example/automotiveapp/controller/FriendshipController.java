package com.example.automotiveapp.controller;

import com.example.automotiveapp.dto.UserDto;
import com.example.automotiveapp.reponse.ApiResponse;
import com.example.automotiveapp.service.Command;
import com.example.automotiveapp.service.CommandInvoker;
import com.example.automotiveapp.service.FriendshipService;
import com.example.automotiveapp.service.RemoveFriendCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/friendship")
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final CommandInvoker invoker;

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> getUserFriends(@RequestParam Long userId) {
        return ResponseEntity.ok(friendshipService.getUserFriends(userId));
    }

//    @PostMapping("/add")
//    public ResponseEntity<String> addFriend(@RequestParam Long friendId) {
//        friendshipService.addFriend(friendId);
//        return ResponseEntity.ok(new ApiResponse(""));
//    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse> removeFriend(@RequestParam("friendId") Long friendId) {
        // L3 Command - third usage
        Command command = new RemoveFriendCommand(friendshipService, friendId);
        invoker.setCommand(command);
        invoker.executeCommand();
        return ResponseEntity.ok(new ApiResponse("Użytkownik został usunięty z listy znajomych", HttpStatus.OK));
    }

    @GetMapping("/not-friends")
    public ResponseEntity<List<UserDto>> findNotFriends() {
        return ResponseEntity.ok(friendshipService.findNotFriends());
    }
}
