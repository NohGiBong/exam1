package com.busanit.repository;

import com.busanit.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findAllByOrderByBnoDesc();


}
