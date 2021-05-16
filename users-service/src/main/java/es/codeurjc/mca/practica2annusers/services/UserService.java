package es.codeurjc.mca.practica2annusers.services;

import java.util.Collection;

import es.codeurjc.mca.practica2annusers.dtos.requests.UpdateUserEmailRequestDto;
import es.codeurjc.mca.practica2annusers.dtos.requests.UserRequestDto;
import es.codeurjc.mca.practica2annusers.dtos.responses.UserResponseDto;

public interface UserService {

    Collection<UserResponseDto> findAll();

    UserResponseDto save(UserRequestDto userRequestDto);

    UserResponseDto findById(long userId);

    UserResponseDto updateEmail(long userId, UpdateUserEmailRequestDto updateUserEmailRequestDto);

    UserResponseDto delete(long userId);

}
