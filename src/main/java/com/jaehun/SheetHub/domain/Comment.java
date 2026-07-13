package com.jaehun.SheetHub.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sheet_id")
    private Sheet sheet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Comment() {
    }

    public Comment(String comment, Sheet sheet, Member member) {
        this.comment = comment;
        this.sheet = sheet;
        this.member = member;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
