package com.lckb.lck_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// 패키지 구조 예시 (src/main/kotlin/com/lckb/lck_backend/)
// domain/         : 엔티티, DTO 등 도메인 모델
// repository/     : JPA 레포지토리 인터페이스
// service/        : 비즈니스 로직 서비스
// controller/     : REST API 컨트롤러
// config/         : 환경설정(Spring Security 등)
// util/           : 유틸리티 클래스
// exception/      : 커스텀 예외


@SpringBootApplication
class LckBackendApplication

fun main(args: Array<String>) {
	runApplication<LckBackendApplication>(*args)
}
