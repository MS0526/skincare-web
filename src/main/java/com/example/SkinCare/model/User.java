package com.example.SkinCare.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// 이 클래스가 JPA 엔티티임을 나타냅니다. 데이터베이스의 테이블과 매핑되는 객체라는 의미입니다.
@Entity
// 이 엔티티가 "users"라는 이름의 테이블과 매핑되도록 지정합니다.
@Table(name = "users")
// Lombok 어노테이션: 모든 필드에 대한 getter 메소드를 자동으로 생성해줍니다. (e.g., getId(),
// getUsername())
@Getter
// Lombok 어노테이션: 모든 필드에 대한 setter 메소드를 자동으로 생성해줍니다. (e.g., setId(),
// setUsername())
@Setter
public class User {

    // 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
    @Id
    // 기본 키의 값을 데이터베이스가 자동으로 생성하도록 설정합니다.
    // GenerationType.IDENTITY 전략은 MySQL의 AUTO_INCREMENT와 같이 DB에 키 생성을 위임하는 방식입니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이 필드가 테이블의 컬럼과 매핑됨을 나타냅니다.
    // nullable = false: 이 컬럼은 null 값을 허용하지 않습니다.
    // unique = true: 이 컬럼의 값은 테이블 내에서 유일해야 합니다. (중복 아이디 방지)
    @Column(nullable = false, unique = true)
    // 유효성 검사(Validation) 어노테이션: 이 필드는 null이거나 빈 문자열("")일 수 없습니다.
    @NotEmpty(message = "아이디는 필수 입력 항목입니다.")
    private String username;

    // 이 필드가 "password"라는 이름의 컬럼과 매핑되며, null 값을 허용하지 않습니다.
    @Column(nullable = false)
    // 유효성 검사: 이 필드는 null이거나 빈 문자열일 수 없습니다.
    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    // 유효성 검사: 이 필드의 길이는 최소 6자 이상이어야 합니다.
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    private String password;

    // 이 필드가 "role"이라는 이름의 컬럼과 매핑되며, null 값을 허용하지 않습니다.
    // 사용자의 역할(예: "USER", "ADMIN")을 저장하는 컬럼입니다.
    @Column(nullable = false)
    private String role;
}
