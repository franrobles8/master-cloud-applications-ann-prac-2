package es.codeurjc.books.services.impl;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.Collection;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import es.codeurjc.books.dtos.requests.UpdateUserEmailRequestDto;
import es.codeurjc.books.dtos.requests.UserRequestDto;
import es.codeurjc.books.dtos.responses.UserResponseDto;
import es.codeurjc.books.exceptions.UserCanNotBeDeletedException;
import es.codeurjc.books.exceptions.UserNotFoundException;
import es.codeurjc.books.exceptions.UserWithSameNickException;
import es.codeurjc.books.services.CommentService;
import es.codeurjc.books.services.UserService;

@Service
@Profile("microservice")
public class UserServiceProxyImpl implements UserService {

    private Mapper mapper;
    private RestTemplate userRepository;
    private CommentService commentService;
    
    @Value("${service.users.url}")
    private String usersServiceUrl;

    public UserServiceProxyImpl(Mapper mapper, RestTemplate userRepository, CommentService commentService) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.commentService = commentService;
    }

    public Collection<UserResponseDto> findAll() {
        return this.userRepository.getForEntity(usersServiceUrl + "/", Collection.class).getBody();
    }

    public UserResponseDto save(UserRequestDto userRequestDto) {
        boolean userExists = this.userRepository.getForEntity(usersServiceUrl + "/" + userRequestDto.getNick() 
            + "/check", Boolean.class).getBody();

        if(userExists) {
          throw new UserWithSameNickException();
        }

        return this.userRepository.postForEntity(usersServiceUrl + "/", userRequestDto, UserResponseDto.class).getBody();
    }

    public UserResponseDto findById(long userId) {
        ResponseEntity<UserResponseDto> user = this.userRepository.getForEntity(usersServiceUrl + "/" + userId, UserResponseDto.class);

        if(user.getStatusCode() == HttpStatus.NOT_FOUND) {
          throw new UserNotFoundException();
        }

        return user.getBody();
    }

    public UserResponseDto updateEmail(long userId, UpdateUserEmailRequestDto updateUserEmailRequestDto) {
        UserResponseDto user = this.findById(userId);

        UserResponseDto userResponse = null;
        if (!user.getEmail().equalsIgnoreCase(updateUserEmailRequestDto.getEmail())) {
          user.setEmail(updateUserEmailRequestDto.getEmail());
          userResponse = this.save(this.mapper.map(user, UserRequestDto.class));
        }
        return userResponse;
    }

    public UserResponseDto delete(long userId) {
        this.findById(userId);
        
        if(!isEmpty(this.commentService.getComments(userId))) {
          throw new UserCanNotBeDeletedException();
        }

        return this.userRepository
          .exchange(usersServiceUrl + "/" + userId, HttpMethod.DELETE, null, UserResponseDto.class).getBody();
    }

}
