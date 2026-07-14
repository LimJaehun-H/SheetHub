package com.jaehun.SheetHub.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Sheet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "sheet")
    private List<Comment> comments = new ArrayList<>();

    private String filePath;

    private String title;
    private String artist;

    public Sheet() {
    }

    public Sheet(Member member, String title, String artist) {
        this.member = member;
        this.title = title;
        this.artist = artist;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}