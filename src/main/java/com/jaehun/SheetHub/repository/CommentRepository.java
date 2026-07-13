package com.jaehun.SheetHub.repository;

import com.jaehun.SheetHub.domain.Comment;
import com.jaehun.SheetHub.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMember(Member member);
}
