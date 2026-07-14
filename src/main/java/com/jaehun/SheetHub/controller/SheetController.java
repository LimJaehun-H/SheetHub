package com.jaehun.SheetHub.controller;

import com.jaehun.SheetHub.domain.sheetdto.CreateSheetDto;
import com.jaehun.SheetHub.domain.sheetdto.ResponseSheetDto;
import com.jaehun.SheetHub.service.SheetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SheetController {
    private final SheetService sheetService;

    @PostMapping("/sheetHub/sheets")
    public ResponseSheetDto addSheet(
            @RequestParam String title,
            @RequestParam String artist,
            @RequestParam MultipartFile file) throws IOException {
        return sheetService.save(title, artist, file);
    }

    @GetMapping("/sheetHub/sheets/{id}/file")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
        byte[] file = sheetService.downloadFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"file\"")
                .body(file);
    }

//    @GetMapping("/sheetHub/sheets")
//    public List<ResponseSheetDto> getAllSheets(){
//        return sheetService.findAll();
//    }
//
//    @GetMapping("/sheetHub/sheets")
//    public List<ResponseSheetDto> getSheetByLyricName(@RequestParam String title){
//       return  sheetService.findByTitle(title);
//    }

    @GetMapping("/sheetHub/sheets")
    public List<ResponseSheetDto> getSheets(@RequestParam(required = false) String title){
        if (title == null){
            return sheetService.findAll();
        } else{
            return  sheetService.findByTitle(title);
        }
    }

    @DeleteMapping("/sheetHub/sheets/{id}")
    public void deleteById(@PathVariable Long id){
        sheetService.deleteById(id);
    }

}
