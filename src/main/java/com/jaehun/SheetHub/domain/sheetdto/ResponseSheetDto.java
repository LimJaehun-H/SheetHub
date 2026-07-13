package com.jaehun.SheetHub.domain.sheetdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ResponseSheetDto {
    private Long id;
    private String title;
    private String artist;

    public ResponseSheetDto(Long id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }
}
