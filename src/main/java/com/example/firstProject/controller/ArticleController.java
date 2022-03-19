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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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


        //redirect하기
        return "redirect:/articles/" + saved.getId();
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

    @GetMapping("/articles")
    public String index(Model model){
        // 1. 모든 Article 가져오기
        //type 캐스팅 = (List<Article>)
        List<Article> articleEntityList= articleRepository.findAll();
        //메서드 오버라이딩
        //repository

        // 2. 가져온 Article 묶음을 뷰로 전달!
        model.addAttribute("articleList",articleEntityList);

        //3. 뷰페이지 설정
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id,Model model){

        Article articleEntity = articleRepository.findById(id).orElse(null);

        model.addAttribute("article",articleEntity);

        return "articles/edit1";
    }
    //@PachMapping
    @PostMapping("/articles/update")
    public String upate(ArticleForm form){

        log.info(form.toString());

        //1. DTO를 Entity 변화
        Article articleEntity = form.toEntity();
        //2.Entity를 DB에 저장
        //2-1:DB에 기존 데이터를 가져온다.
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        //2-2:  기존데이터의 값을 갱신
        if(target != null){
            articleRepository.save(articleEntity);
        }

        //3. 수정된 article로 페이지 리다이렉트
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제요청");
        //1. 삭제 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        //2. 대상을 삭제 한다.
        if(target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료 되었습니다.");
        }
        //3, 결과 페이지로 리다이렉트 한다.
        return "redirect:/articles";
    }
}
