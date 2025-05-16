package com.example.SkinCare.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 성분 이름

    private String effect; // 효능
    private String riskLevel; // 위험도 (낮음, 중간, 높음)
    private String suitableSkinType; // 적합 피부 타입
    private String description; // 성분 설명
    private String source; // 성분 유래 (ex. 식물성, 합성 등)
    private String caution; // 주의사항
    private String comedogenic; // 코메도제닉 등급 (0~5)

}
