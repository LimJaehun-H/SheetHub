package com.jaehun.SheetHub.domain.commentdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseCommentDto {
    private Long id;
    private String comment;
    private String name;

    public ResponseCommentDto(Long id, String comment, String name) {
        this.id = id;
        this.comment = comment;
        this.name = name;
    }
}
