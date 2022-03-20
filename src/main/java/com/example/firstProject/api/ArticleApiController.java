package com.example.firstProject.api;

import com.example.firstProject.DTO.ArticleForm;
import com.example.firstProject.entity.Article;
import com.example.firstProject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    private ArticleRepository articleRepository;
    //get
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }
    //post
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    //patch

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        //1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id : {} , article : {}",id,article.toString());
        //2. 대상 엔티티를 조회'
        Article target = articleRepository.findById(id).orElse(null);
        //3 잘못된 요청 처리 대상이 없거나 id가 다른 경우
        if(target == null || id != article.getId()){
            //400, 잘못된 요청 응답!
            log.info("잘못됨 요청, id : {} , article : {}", id,article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //4: 엡데이트 및 정상 응답
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);

        //데이터를 일부만 보낼 경우 데이터일부가 날아간다
        //보강 코드 작성
        //Article Entity Method 추가
    }

    //delete
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article target =articleRepository.findById(id).orElse(null);
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).body(target);
    }
}
