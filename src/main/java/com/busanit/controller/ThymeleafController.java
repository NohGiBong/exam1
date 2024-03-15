package com.busanit.controller;

import com.busanit.dto.ItemDto;
import com.busanit.entity.Item;
import com.busanit.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
@RequiredArgsConstructor
public class ThymeleafController {

    private final ItemRepository itemRepository;

    @GetMapping("/ex01")
    public String ex01(Model model) {
        model.addAttribute("data", "타임리프 예제입니다.");

        return "thymeleaf/thymeleafEx01";
    }

    @GetMapping("/ex02")
    public String ex02(Model model) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemNm("테스트 상품1");
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setPrice(5000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);

        return "thymeleaf/thymeleafEx02";
    }

    @GetMapping("/ex03")
    public String ex03(Model model) {

        for(int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setItemDetail("상품 설명" + i);
            item.setPrice(10000 + i);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            itemRepository.save(item);
        }
        model.addAttribute("itemList", itemRepository.findAll());

        return "thymeleaf/thymeleafEx03";
    }

    @GetMapping("/ex04")
    public String ex04(Model model) {

        for(int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setItemDetail("상품 설명" + i);
            item.setPrice(1000 * i);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            itemRepository.save(item);
        }
        model.addAttribute("itemList", itemRepository.findAll());

        return "thymeleaf/thymeleafEx04";
    }

    @GetMapping("/ex05")
    public String ex05(Model model) {

        List<ItemDto> itemDtoList = new ArrayList<>();

        for(int i = 1; i <= 10; i++) {
            ItemDto ItemDto = new ItemDto();
            ItemDto.setItemNm("테스트 상품" + i);
            ItemDto.setItemDetail("상품 설명" + i);
            ItemDto.setPrice(1000 * i);
            ItemDto.setRegTime(LocalDateTime.now());
            ItemDto.setUpdateTime(LocalDateTime.now());

            itemDtoList.add(ItemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);

        return "thymeleaf/thymeleafEx05";
    }

    @GetMapping("/ex06")
    public String ex06() {
        return "thymeleaf/thymeleafEx06";

    }

    @GetMapping("/ex07")
    public String ex07(String param1, String param2, Model model) {

        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);

        return "thymeleaf/thymeleafEx07";

    }

    @GetMapping("/ex08")
    public String ex08() {
        return "thymeleaf/thymeleafEx08";
    }

    @PostMapping("/ex08")
    public String ex08(ItemDto itemDto) {

        Item item = new Item();
        item.setItemNm(itemDto.getItemNm());
        item.setItemDetail(itemDto.getItemDetail());
        item.setPrice(itemDto.getPrice());
        item.setStockNumber(itemDto.getStockNumber());
        item.setRegTime(itemDto.getRegTime());

        itemRepository.save(item);

        return "redirect:/thymeleaf/ex08";
    }

    @GetMapping("/ex09")
    public String ex09(Long id, Model model) {

        Item item = itemRepository.findById(id).orElseThrow(() -> new NullPointerException("item Null"));
        model.addAttribute("item", item);

        return "thymeleaf/thymeleafEx09";
    }

    @PostMapping("/ex09")
    public String ex09(ItemDto itemDto) {

        Item item = new Item();
        item.setId(itemDto.getId());
        item.setItemNm(itemDto.getItemNm());
        item.setItemDetail(itemDto.getItemDetail());
        item.setPrice(itemDto.getPrice());
        item.setStockNumber(itemDto.getStockNumber());
        item.setUpdateTime(LocalDateTime.now());

        itemRepository.save(item);

        return "redirect:/thymeleaf/ex08";
    }

    @PostMapping("/ex10")
    public String ex10(Long id) {

        itemRepository.deleteById(id);

        return "redirect:/thymeleaf/ex08";
    }

    @GetMapping("/layout")
    public String layout() {
        return "thymeleaf/thymeleafEx10";
    }
}
