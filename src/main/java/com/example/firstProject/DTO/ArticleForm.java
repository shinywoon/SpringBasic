package com.example.firstProject.DTO;

import com.example.firstProject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //생성자
@ToString // toString method
public class ArticleForm {

    private Long id;
    private String title;
    private String content;

//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
//
//    @Override
//    public String toString() {
//        return "ArticleForm{" +
//                "title='" + title + '\'' +
//                ", cotent='" + content + '\'' +
//                '}';
//    }

    public Article toEntity() {
        return new Article(id,title,content);
    }
}
