package com.busanit.repository;

import com.busanit.constant.ItemSellStatus;
import com.busanit.entity.Item;
import com.busanit.entity.QItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    public void createItemList() {
        for(int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
        }
    }

    public void createItemList2() {
        for(int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
        }

        for(int i = 6; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트2")
    public void queryDslTest2() {
        createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세설명";
        int price = 10003;
        String itemSellStat = "SELL";

        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%"));
        booleanBuilder.and(item.price.gt(price));

//        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
//            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
//        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);

        System.out.println("total elements : " + itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        for(Item reusltItem: resultItemList) {
            System.out.println(reusltItem.toString());
        }
    }



    @Test
    @DisplayName("QueryDSL 조회 테스트1")
    public void queryDSLTest() {
        // 10개 item 생성
        createItemList();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세설명" + "%"))
                .orderBy(qItem.price.desc());


        List<Item> itemList = query.fetch();
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품 조회 테스트 @Query NativeQuery 사용")
    public void findByItemDetailNativeTest() {
        // 10개 item 생성
        createItemList();

        List<Item> itemList = itemRepository.findByItemDetailNative("테스트 상품 상세");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품 조회 테스트 @Query 사용")
    public void findByItemDetailTest() {
        // 10개 item 생성
        createItemList();

        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품 조회 테스트 orderby 사용")
    public void findByOrderByPriceDescTest() {
        // 10개 item 생성
        createItemList();

        List<Item> itemList = itemRepository.findByOrderByPriceDesc();
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품 조회 테스트 > 사용")
    public void findByPriceGreaterThanTest() {
        // 10개 item 생성
        createItemList();

        List<Item> itemList = itemRepository.findByPriceGreaterThan(10004);
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품 조회 테스트 OR 사용")
    public void findByItemNmOrItemDetailTest() {
        // 10개 item 생성
        createItemList();

        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1",
                                                            "테스트 상품 상세설명5");
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품 조회 테스트")
    public void findByItemNmTest() {
        // 10개 item 생성
        createItemList();

//        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        List<Item> itemList = itemRepository.findAll();
        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }
}









