package com.jaehun.SheetHub.service;

import com.jaehun.SheetHub.domain.Comment;
import com.jaehun.SheetHub.domain.Member;
import com.jaehun.SheetHub.domain.Sheet;
import com.jaehun.SheetHub.domain.commentdto.CreateCommentDto;
import com.jaehun.SheetHub.domain.commentdto.ResponseMyCommentsDto;
import com.jaehun.SheetHub.domain.commentdto.ResponseCommentDto;
import com.jaehun.SheetHub.domain.commentdto.UpdateCommentDto;
import com.jaehun.SheetHub.exception.AuthException;
import com.jaehun.SheetHub.repository.CommentRepository;
import com.jaehun.SheetHub.repository.MemberRepository;
import com.jaehun.SheetHub.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CommentService
 *
 댓글 저장 : POST /sheetHub/sheets/{sheetId}/comments
 댓글 수정 : PUT /sheetHub/sheets/{sheetId}/comments/{commentId}
 댓글 삭제 : DELETE /sheetHub/sheets/{sheetId}/comments/{commentId}
 내 댓글 모아보기 : GET /sheetHub/members/comments
 * 내 댓글 모아보기
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final SheetRepository sheetRepository;
    private final MemberRepository memberRepository;

    //댓글 저장
    @Transactional
    public ResponseCommentDto save(Long sheetId, CreateCommentDto dto){
        Sheet sheet = sheetRepository.findById(sheetId)
                .orElseThrow(() -> new AuthException("Sheet Not Found"));
        String username = getCurrentUserName();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException("Member Not Found"));
        Comment comment = new Comment(dto.getComment(), sheet, member);

        commentRepository.save(comment);
        return new ResponseCommentDto(comment.getId(), comment.getComment(), username);
    }

    // 댓글 수정
    @Transactional
    public ResponseCommentDto update(Long commentId, UpdateCommentDto dto){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AuthException("Comment Not Found"));
        String username = getCurrentUserName();
        comment.update(dto.getComment());
        return new ResponseCommentDto(commentId, comment.getComment(), username);
    }

    private String getCurrentUserName() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return username;
    }

    // 댓글 삭제
    @Transactional
    public void delete(Long commentId){
        commentRepository.deleteById(commentId);
    }

    // 내 댓글 모아보기
    public List<ResponseMyCommentsDto> findMyComments(){
        String username = getCurrentUserName();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException("Member Not Found"));
        return commentRepository.findByMember(member).stream()
                .map(c->new ResponseMyCommentsDto(c.getId(), c.getComment()))
                .collect(Collectors.toList());
    }
}
