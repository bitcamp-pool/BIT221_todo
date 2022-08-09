package com.bitcamp.todo.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity             // 자바클래스를 엔티티로 지정
@Table(name="todo") // 테이블 이름을 지정
public class TodoEntity {

    // 오브젝트 아이디 @Id 는 기본 키가 될 필드에 지정한다.
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // 정수 자동생성
    @GeneratedValue(generator = "uuid2") // ID 자동생성
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String userId;
    private String title;
    private boolean done;
}
