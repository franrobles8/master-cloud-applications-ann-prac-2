package es.codeurjc.mca.practica2annusers.services.impl;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.codeurjc.mca.practica2annusers.dtos.responses.UserCommentResponseDto;
import es.codeurjc.mca.practica2annusers.services.CommentServiceProxy;

@Service
public class CommentServiceProxyImpl implements CommentServiceProxy {

    private RestTemplate commentRepository;

    @Value("${service.monolith.url}")
    private String monolithUrl;

    private static final String PATH = "/api/v1/users/";

    public CommentServiceProxyImpl(RestTemplate commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Collection<UserCommentResponseDto> getComments(long userId) {
        return this.commentRepository.getForEntity(monolithUrl + PATH + userId + "/comments", Collection.class).getBody();
    }

}
