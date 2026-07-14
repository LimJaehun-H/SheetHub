package com.jaehun.SheetHub.service;

import com.jaehun.SheetHub.domain.Member;
import com.jaehun.SheetHub.domain.Sheet;
import com.jaehun.SheetHub.domain.sheetdto.CreateSheetDto;
import com.jaehun.SheetHub.domain.sheetdto.ResponseSheetDto;
import com.jaehun.SheetHub.exception.AuthException;
import com.jaehun.SheetHub.exception.NotFoundException;
import com.jaehun.SheetHub.repository.MemberRepository;
import com.jaehun.SheetHub.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 악보 등록
    @Transactional
    public ResponseSheetDto save(String title, String artist, MultipartFile file) throws IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("일치하는 멤버가 없습니다."));

        Sheet sheet = new Sheet(member, title, artist);
        sheetRepository.save(sheet);
        // 파일 추가

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = sheet.getId() + "_" + file.getOriginalFilename();
        File dest = new File(dir.getAbsolutePath() + "/" + fileName);
        file.transferTo(dest);

        sheet.setFilePath(dest.getAbsolutePath());


        //

        return new ResponseSheetDto(sheet.getId(), sheet.getTitle(), sheet.getArtist());
    }

    // 악보 다운로드
    public byte[] downloadFile(Long sheetId) throws IOException {
        Sheet sheet = sheetRepository.findById(sheetId)
                .orElseThrow(() -> new NotFoundException("일치하는 악보가 없습니다."));

        if (sheet.getFilePath() == null) {
            throw new NotFoundException("파일이 없습니다.");
        }

        return Files.readAllBytes(Paths.get(sheet.getFilePath()));
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
        // sheet id로 member얻기
        Sheet sheet = sheetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("일치하는 악보가 없습니다."));
        Member findMemberFromSheet = sheet.getMember();

        // 현재 토큰에서 member얻기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 두 멤버 이름으로 비교하기
        if(findMemberFromSheet.getUsername().equals(username)){
            sheetRepository.deleteById(id);
        } else{
            throw new AuthException("악보 삭제 권한이 없습니다.");
        }
    }
}
