package com.example.firstProject.service;

import com.example.firstProject.DTO.ArticleForm;
import com.example.firstProject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링 부트랑 연동되어 테스팅 된다
class ArticleServiceTest {

    @Autowired ArticleService articleService;

    @Test
    void index() {
        //예상
        Article a = new Article(1L,"1111","1111");
        Article b = new Article(1L,"1111","1111");
        Article c = new Article(1L,"1111","1111");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));
        // 실제
        List<Article> Articles =articleService.index();

        //비교
        assertEquals(expected.toString(),articleService.toString());
    }


    @Test
    void show_성공____존재하는_id_입력() {
        //예상
        Long id = 1L;
        Article expected = new Article(id,"1111","1111");
        //실제
        Article article = articleService.show(id);
        //비교
        assertEquals(expected.toString(),article.toString());
    }
    @Test
    void show_실패_____존재하지않는_id_입력() {
        //예상
        Long id = -1L;
        Article expected = null;
        //실제
        Article article = articleService.show(id);
        //비교
        assertEquals(expected,article);
    }

    @Test
    @Transactional
    void create_성공__title과__content만_있는_dto_입력() {
        //예상
        String title = "4444";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null,title,content);
        Article expected = new Article(1L,title,content);
        //실제
        Article article = articleService.create(dto);
        //비교
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    @Transactional
    void create_실패__id가포함된_DTO_입력() {
        //예상
        Long id = 1L;
        String title = "4444";
        String content = "4444";
        ArticleForm dto = new ArticleForm(1L,title,content);
        Article expected = null;
        //실제
        Article article = articleService.create(dto);
        //비교
        assertEquals(expected,article);
    }

}