package com.example.firstProject.repository;

import com.example.firstProject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

//관리대상 type과 대표값 type을 지정
// CrudRepository를 상속 받음
public interface ArticleRepository extends CrudRepository<Article,Long>{
    //마우스 우측 - 제너 - 원하는 메소드 선택

    @Override
    ArrayList<Article> findAll();
}
