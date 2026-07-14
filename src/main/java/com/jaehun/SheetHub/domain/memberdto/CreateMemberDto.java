package com.jaehun.SheetHub.domain.memberdto;

import com.jaehun.SheetHub.domain.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter

public class CreateMemberDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;


}
