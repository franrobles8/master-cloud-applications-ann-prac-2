package es.codeurjc.mca.practica2annusers.services;

import java.util.Collection;

import es.codeurjc.mca.practica2annusers.dtos.responses.UserCommentResponseDto;

public interface CommentServiceProxy {

    Collection<UserCommentResponseDto> getComments(long userId);

}
