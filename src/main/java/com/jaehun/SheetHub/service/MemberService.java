package com.jaehun.SheetHub.service;

import com.jaehun.SheetHub.domain.Member;
import com.jaehun.SheetHub.domain.Role;
import com.jaehun.SheetHub.domain.memberdto.CreateMemberDto;
import com.jaehun.SheetHub.domain.memberdto.LoginMemberDto;
import com.jaehun.SheetHub.exception.AuthException;
import com.jaehun.SheetHub.repository.MemberRepository;
import com.jaehun.SheetHub.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MemberService
 *
 * 회원가입
 * 로그인
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void join(CreateMemberDto dto){
        if(memberRepository.findByUsername(dto.getUsername()).isPresent()){
            throw new AuthException("이미 존재하는 유저입니다.");
        }
        String encode = passwordEncoder.encode(dto.getPassword());
        Member member = new Member(dto.getUsername(), encode, Role.USER);
        memberRepository.save(member);
    }

    public String login(LoginMemberDto dto){
        Member member = memberRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new AuthException("일치하는 회원이 없습니다."));
        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
            throw new AuthException("비밀번호가 일치하지 않습니다.");
        }
        return jwtUtil.generateToken(member.getUsername());
    }
}
