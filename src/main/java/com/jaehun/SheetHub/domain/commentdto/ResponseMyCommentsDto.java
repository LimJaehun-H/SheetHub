package com.jaehun.SheetHub.domain.commentdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseMyCommentsDto {
    private Long commentId;
    private String comment;

    public ResponseMyCommentsDto(Long commentId, String comment) {
        this.commentId = commentId;
        this.comment = comment;
    }
}
