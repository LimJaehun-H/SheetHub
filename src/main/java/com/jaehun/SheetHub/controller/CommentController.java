package com.jaehun.SheetHub.controller;

import com.jaehun.SheetHub.domain.commentdto.CreateCommentDto;
import com.jaehun.SheetHub.domain.commentdto.ResponseCommentDto;
import com.jaehun.SheetHub.domain.commentdto.ResponseMyCommentsDto;
import com.jaehun.SheetHub.domain.commentdto.UpdateCommentDto;
import com.jaehun.SheetHub.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/sheetHub/sheets/{sheetId}/comments")
    public ResponseCommentDto addComment(@PathVariable Long sheetId
                                       , @Valid @RequestBody CreateCommentDto dto) {
        return commentService.save(sheetId, dto);
    }

    // 댓글 수정
    @PutMapping("/sheetHub/sheets/{sheetId}/comments/{commentId}")
    public ResponseCommentDto updateComment(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentDto dto) {
        return commentService.update(commentId, dto);
    }

    // 댓글 삭제
    @DeleteMapping("/sheetHub/sheets/{sheetId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }

    // 내 댓글 조회
    @GetMapping("/sheetHub/members/comments")
    public List<ResponseMyCommentsDto> getMyComments() {
        return commentService.findMyComments();
    }

    // 악보당 댓글 조회
    @GetMapping("/sheetHub/sheets/{sheetId}/comments")
    public List<ResponseCommentDto> getCommentsOfEachSheet(@PathVariable Long sheetId) {
        return commentService.findCommentOfSheet(sheetId);
    }
}
