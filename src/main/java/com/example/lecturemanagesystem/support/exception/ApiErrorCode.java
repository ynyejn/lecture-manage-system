package com.example.lecturemanagesystem.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "요청한 자원을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
    LECTURE_NOT_FOUND(HttpStatus.NOT_FOUND, "LECTURE_NOT_FOUND", "강의를 찾을 수 없습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_PARAMETER", "잘못된 요청입니다."),
    INVALID_NAGATIVE_PARAMETER(HttpStatus.BAD_REQUEST, "INVALID_NAGATIVE_PARAMETER", "음수 값은 허용되지 않습니다."),

    // 강의 신청 관련 에러 코드 추가
    ALREADY_ENROLLED_LECTURE(HttpStatus.BAD_REQUEST, "ALREADY_ENROLLED_LECTURE", "이미 신청한 강의입니다."),
    DUPLICATE_TIME_SLOT(HttpStatus.BAD_REQUEST, "DUPLICATE_TIME_SLOT", "동일 시간대에 이미 수강 중인 강의가 있습니다."),
    EXCEEDED_CAPACITY(HttpStatus.BAD_REQUEST, "EXCEEDED_CAPACITY", "수강 인원이 초과되었습니다."),
    LECTURE_UNAVAILABLE(HttpStatus.BAD_REQUEST, "LECTURE_UNAVAILABLE", "신청 불가능한 강의입니다.");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ApiErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }



}
