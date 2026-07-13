package com.jaehun.SheetHub.service;

import com.jaehun.SheetHub.domain.Member;
import com.jaehun.SheetHub.domain.Sheet;
import com.jaehun.SheetHub.domain.sheetdto.CreateSheetDto;
import com.jaehun.SheetHub.domain.sheetdto.ResponseSheetDto;
import com.jaehun.SheetHub.exception.AuthException;
import com.jaehun.SheetHub.repository.MemberRepository;
import com.jaehun.SheetHub.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SheetService
 *
 * 악보 등록 : Post "/sheetHub/sheets"
 * 악보 삭제(아이디로) : Delete "/sheetHub/sheets/{id}"
 * 악보 전체 조회 : Get "/sheetHub/sheets"
 * 제목으로 검색 : Get "/sheetHub/sheets?title=lyricName"
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SheetService {
    private final SheetRepository sheetRepository;
    private final MemberRepository memberRepository;

    // 악보 등록
    @Transactional
    public ResponseSheetDto save(CreateSheetDto dto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException("member not found"));

        Sheet sheet = new Sheet(member, dto.getTitle(), dto.getArtist());
        sheetRepository.save(sheet);
        return new ResponseSheetDto(sheet.getId(), sheet.getTitle(), sheet.getArtist());
    }

    // 악보 전체 조회
    public List<ResponseSheetDto> findAll(){
        return sheetRepository.findAll().stream()
                .map(sheet -> new ResponseSheetDto(sheet.getId(), sheet.getTitle(), sheet.getArtist()))
                .collect(Collectors.toList());
    }

    // 제목으로 악보 조회
    public List<ResponseSheetDto> findByTitle(String title){
        return sheetRepository.findByTitle(title).stream()
                .map(sheet -> new ResponseSheetDto(sheet.getId(), sheet.getTitle(), sheet.getArtist()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id){
        sheetRepository.deleteById(id);
    }
}
