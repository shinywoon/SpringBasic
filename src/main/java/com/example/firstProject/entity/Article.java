package com.example.firstProject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity // DB가 해당 객체를 인식 가능!
@AllArgsConstructor
@NoArgsConstructor // 디폴트 생성자를 추가 하는 어노테이션
@ToString
@Getter
public class Article {

    @Id //
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 어노테이션 1,2,3,
    //DB가 id를 자동 생성 어노테이션
    private Long id; //대표값 primary Key

    @Column //DB에서 이해하도록
    private String title;

    @Column
    private String content;

    public void patch(Article article) {
        if(article.title != null){
            this.title = article.title;
        }
        if(article.content != null)
            this.content = article.content;
    }

//    public Article(Long id, String title, String content) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }

//    @Override
//    public String toString() {
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}
