package com.jaehun.SheetHub.controller;

import com.jaehun.SheetHub.domain.sheetdto.CreateSheetDto;
import com.jaehun.SheetHub.domain.sheetdto.ResponseSheetDto;
import com.jaehun.SheetHub.service.SheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SheetController {
    private final SheetService sheetService;

    @PostMapping("/sheetHub/sheets")
    public ResponseSheetDto addSheet(@RequestBody CreateSheetDto dto){
        return sheetService.save(dto);
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
