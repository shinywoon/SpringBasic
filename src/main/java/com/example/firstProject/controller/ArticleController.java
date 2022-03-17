package com.example.firstProject.controller;

import com.example.firstProject.DTO.ArticleForm;
import com.example.firstProject.entity.Article;
import com.example.firstProject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j //logging 어노테이션
public class ArticleController {

    //객체 주입하기 DI주입
    @Autowired // 스프링 부트가 미리 생성해 놓은 객체를 가져다가 자동 연결!
    //new asdf();를 안해도 된다
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){

        //System.out.println(form.toString()); logging 기능으로 대체 서버에 악영향을 끼침 println
        log.info(form.toString());
        //1. DTO를 변환! Entity!
        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info(article.toString());
        //2. Repository에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());
        return "";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id,Model model){
        log.info("id = " + id);

        //1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //리턴 타입이 아티클이 아님 Optional<Article>로 해야 Error 안뜸
        //만약 해당 id 값이 없다면 null을 반환하라

        //2. 가져온 데이터를 모델에 등록
        model.addAttribute("article",articleEntity);

        //3. 보여줄 페이지를 설정
        return "articles/show";
    }

}
