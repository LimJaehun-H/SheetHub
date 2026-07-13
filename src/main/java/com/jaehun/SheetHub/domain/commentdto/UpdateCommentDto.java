package com.jaehun.SheetHub.domain.commentdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCommentDto {
    private String comment;

    public UpdateCommentDto(String comment) {
        this.comment = comment;
    }
}
