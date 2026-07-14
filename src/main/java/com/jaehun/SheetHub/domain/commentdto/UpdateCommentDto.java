package com.jaehun.SheetHub.domain.commentdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCommentDto {
    @NotBlank(message = "내용을 입력해주세요.")
    private String comment;

    public UpdateCommentDto(String comment) {
        this.comment = comment;
    }
}
