package com.example.SkinCare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.SkinCare") // ✅ 명시적으로 스캔 범위 지정
public class SkinCareApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkinCareApplication.class, args);
	}
}
