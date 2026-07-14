package com.jaehun.SheetHub.domain.sheetdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CreateSheetDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "작곡가를 입력해주세요.")
    private String artist;
}
