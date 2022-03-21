package com.example.firstProject.service;

import com.example.firstProject.DTO.ArticleForm;
import com.example.firstProject.entity.Article;
import com.example.firstProject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    //post는 생성!! 수정이 되서는 안됨
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null)
            return null;

        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {

        Article article = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);
        if (target != null || id != article.getId()) {
            log.info("wrong id: {},  article : {}", id, article.toString());
            return null;
        }
                //4: 엡데이트 및 정상 응답
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;

        //데이터를 일부만 보낼 경우 데이터일부가 날아간다
        //보강 코드 작성
        //Article Entity Method 추가
    }

    public Article delete(Long id) {
        Article target =articleRepository.findById(id).orElse(null);
        if(target == null){
            return null;
        }

        articleRepository.delete(target);
        return target;
    }
    //트랜잭션은 서비스가 담당
    @Transactional // 해당 메소드를 트랜잭션으로 묶는다
    //트랜잭션 도중 실패 할 경우 roll-Back을 함
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1.dto 묶음을 entity 묶음으로 전환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        //2. entity묶음을 DB로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        //3. 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패")
        );

        //4. 결과반환
        return articleList;
    }
}














