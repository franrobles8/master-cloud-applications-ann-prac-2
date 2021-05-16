package es.codeurjc.mca.practica2annusers.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.mca.practica2annusers.dtos.requests.UpdateUserEmailRequestDto;
import es.codeurjc.mca.practica2annusers.dtos.requests.UserRequestDto;
// import es.codeurjc.mca.practica2annusers.dtos.responses.UserCommentResponseDto;
import es.codeurjc.mca.practica2annusers.dtos.responses.UserResponseDto;
// import es.codeurjc.mca.practica2annusers.services.CommentService;
import es.codeurjc.mca.practica2annusers.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;
//     private CommentService commentService;

//     public UserController(UserService userService, CommentService commentService) {
//         this.userService = userService;
//         this.commentService = commentService;
//     }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public Collection<UserResponseDto> getUsers() {
        return this.userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUser(@PathVariable long userId) {
        return this.userService.findById(userId);
    }

    @PostMapping("/")
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return this.userService.save(userRequestDto);
    }

    @PatchMapping("/{userId}")
    public UserResponseDto updateUserEmail(@PathVariable long userId,
                                           @Valid @RequestBody UpdateUserEmailRequestDto updateUserEmailRequestDto) {
        return this.userService.updateEmail(userId, updateUserEmailRequestDto);
    }

    @DeleteMapping("/{userId}")
    public UserResponseDto deleteUser(@PathVariable long userId) {
        return this.userService.delete(userId);
    }

    // @GetMapping("/{userId}/comments")
    // public Collection<UserCommentResponseDto> getUserComments(@PathVariable long userId) {
    //     return this.commentService.getComments(userId);
    // }

}
