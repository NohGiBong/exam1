package com.busanit.controller;

import com.busanit.dto.BlogDto;
import com.busanit.entity.Blog;
import com.busanit.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogRepository blogRepository;

    @GetMapping("/list")
    public String list(Model model) {

        model.addAttribute("blogList", blogRepository.findAll());

        return "blog/blogList";
    }

    @GetMapping("/reg")
    public String reg() {
        return "blog/blogReg";
    }

    @PostMapping("/reg")
    public String reg(BlogDto blogDto) {

        Blog blog = new Blog();
        blog.setName(blogDto.getName());
        blog.setTitle(blogDto.getTitle());
        blog.setContent(blogDto.getContent());
        blog.setRegDate(LocalDateTime.now());

        blogRepository.save(blog);

        return "redirect:/blog/page?bno=" + blog.getBno();
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("bno") Long bno, Model model) {

        Blog blog = blogRepository.findById(bno).orElseThrow(() -> new NullPointerException("item Null"));
        model.addAttribute("blog", blog);

        return "blog/blogEdit";
    }

    @PostMapping("/edit")
    public String edit(BlogDto blogDto) {

        Blog blog = new Blog();
        blog.setBno(blogDto.getBno());
        blog.setName(blogDto.getName());
        blog.setTitle(blogDto.getTitle());
        blog.setContent(blogDto.getContent());
        blog.setRegDate(LocalDateTime.now());
        blog.setUpdateDate(LocalDateTime.now());

        blogRepository.save(blog);

        return "redirect:/blog/page?bno=" + blog.getBno();
    }


    @PostMapping("/del")
    public String del(@RequestParam("bno") Long bno) {

        blogRepository.deleteById(bno);

        return "redirect:/blog/list";
    }

    @GetMapping("/page")
    public String page(Long bno, Model model) {
        Blog blog = blogRepository.findById(bno)
                .orElseThrow(() -> new NullPointerException("해당 게시글을 찾을 수 없습니다: " + bno));
        model.addAttribute("blog", blog);

        return "blog/blogPage";
    }
}
