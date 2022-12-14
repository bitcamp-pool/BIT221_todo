package com.bitcamp.todo.controller;

import com.bitcamp.todo.dto.ResponseDTO;
import com.bitcamp.todo.dto.TodoDTO;
import com.bitcamp.todo.model.TodoEntity;
import com.bitcamp.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    /**
     * 생성 (Create Todo 구현)
     */
    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId,
                                        @RequestBody TodoDTO dto){

        try {
//            String temporaryUserId = "temporary-user";

            // (1) TodoEntity 로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            // (2) id를 null 로 초기화 한다. 생성 당시에는 id가 없어야 하기 때문
            entity.setId(null);

            // (3) 임시 유저 아이디를 설정해준다
            entity.setUserId(userId);

            // (4) 서비스를 이용해 엔티티를 생성한다.
            List<TodoEntity> entities = service.create(entity);

            // (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO 를 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().resList(dtos).build();

            // (7) ResponseDTO 를 리턴
            return ResponseEntity.ok().body(response);

        } catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**조회(Retrieve Todo 구현)
     * */
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
//        String temporaryUserId = "temporary-user";

        List<TodoEntity> entities = service.retrieve(userId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().resList(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    /** 수정(Udate Todo 구현)*/
    @PutMapping
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId,
                                        @RequestBody TodoDTO dto){
//        String temporaryUserId = "temporary-user";

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(userId);
        List<TodoEntity> entities = service.update(entity);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().resList(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    /** 삭제 (DELETE)*/
    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId,
                                        @RequestBody TodoDTO dto){
        try {
//            String temporaryUserId = "temporary-user";

            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(userId);
            List<TodoEntity> entities = service.delete(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().resList(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}





















