package com.jaehun.SheetHub.domain.commentdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentDto {
    private String comment;

    public CreateCommentDto(String comment) {
        this.comment = comment;
    }
}
