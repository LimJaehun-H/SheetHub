package com.jaehun.SheetHub.repository;

import com.jaehun.SheetHub.domain.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SheetRepository extends JpaRepository<Sheet, Long> {
    List<Sheet> findByTitle(String title);
}
