package com.busanit.repository;

import com.busanit.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item> {
    /*
    JpaRepository에서 지원하는 메소드 예시
    - <S extends T> save(S entity) : 엔티티 저장 및 수정
    - void delete(T entity) : 엔티티 삭제
    - count() : 엔티티 총 갯수 반환
    - Iterable<T> findAll() : 모든 엔티티 조회
     */

    /*
    find + (엔티티 이름) + By + 변수이름
    - 엔티티 이름은 생략 가능, By 뒤에는 검색할 때 사용할 변수의 이름 적어줌
     */


    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceGreaterThan(Integer price);

    List<Item> findByOrderByPriceDesc();

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value = "select * from item where itemDetail like %:itemDetail% order by price desc",
        nativeQuery = true)
    List<Item> findByItemDetailNative(@Param("itemDetail") String itemDetail);

}













