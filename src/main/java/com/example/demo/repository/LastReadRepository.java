package com.example.demo.repository;

import com.example.demo.entity.LastRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastReadRepository extends JpaRepository<LastRead, Long> {
}
