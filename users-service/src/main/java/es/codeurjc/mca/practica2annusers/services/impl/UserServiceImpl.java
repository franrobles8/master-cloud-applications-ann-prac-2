package es.codeurjc.mca.practica2annusers.services.impl;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import es.codeurjc.mca.practica2annusers.dtos.requests.UpdateUserEmailRequestDto;
import es.codeurjc.mca.practica2annusers.dtos.requests.UserRequestDto;
import es.codeurjc.mca.practica2annusers.dtos.responses.UserResponseDto;
import es.codeurjc.mca.practica2annusers.exceptions.UserCanNotBeDeletedException;
import es.codeurjc.mca.practica2annusers.exceptions.UserNotFoundException;
import es.codeurjc.mca.practica2annusers.exceptions.UserWithSameNickException;
import es.codeurjc.mca.practica2annusers.models.User;
import es.codeurjc.mca.practica2annusers.repositories.UserRepository;
import es.codeurjc.mca.practica2annusers.services.CommentServiceProxy;
import es.codeurjc.mca.practica2annusers.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private Mapper mapper;
    private UserRepository userRepository;
    private CommentServiceProxy commentService;

    public UserServiceImpl(Mapper mapper, UserRepository userRepository, CommentServiceProxy commentService) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.commentService = commentService;
    }

    public Collection<UserResponseDto> findAll() {
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(user -> this.mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    public UserResponseDto save(UserRequestDto userRequestDto) {
        if (this.userRepository.existsByNick(userRequestDto.getNick())) {
            throw new UserWithSameNickException();
        }
        User user = this.mapper.map(userRequestDto, User.class);
        user = this.userRepository.save(user);
        return this.mapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto findById(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return this.mapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto updateEmail(long userId, UpdateUserEmailRequestDto updateUserEmailRequestDto) {
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (!user.getEmail().equalsIgnoreCase(updateUserEmailRequestDto.getEmail())) {
            user.setEmail(updateUserEmailRequestDto.getEmail());
            user = this.userRepository.save(user);
        }
        return this.mapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto delete(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (!isEmpty(commentService.getComments(userId))) {
            throw new UserCanNotBeDeletedException();
        }
        this.userRepository.delete(user);
        return this.mapper.map(user, UserResponseDto.class);
    }

    public Boolean existsByNick(String nick) {
        return this.userRepository.existsByNick(nick);
    }

}
