package com.jaehun.SheetHub.controller;

import com.jaehun.SheetHub.domain.memberdto.CreateMemberDto;
import com.jaehun.SheetHub.domain.memberdto.LoginMemberDto;
import com.jaehun.SheetHub.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sheetHub/join")
    public void join(@Valid @RequestBody CreateMemberDto dto) {
        memberService.join(dto);
    }

    @PostMapping("/sheetHub/login")
    public String login(@Valid @RequestBody LoginMemberDto dto){
        return memberService.login(dto);
    }
}
