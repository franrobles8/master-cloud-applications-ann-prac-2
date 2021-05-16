package es.codeurjc.mca.practica2annusers.dtos.responses;

import lombok.Data;

@Data
public class UserCommentResponseDto {

    private Long id;
    private String comment;
    private float score;
    private Long bookId;

}
