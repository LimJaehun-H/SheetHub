package com.jaehun.SheetHub.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Sheet> sheets = new ArrayList<>();

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member() {
    }

    public Member(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
