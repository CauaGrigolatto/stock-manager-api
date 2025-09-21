package br.com.codebeans.stockapi.model.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDTO<T> {
    private Integer status;
    private String message;
    private T data;

    public static <T> ResponseDTO<T> ok(T data, String message) {
        return new ResponseDTO<T>(
            HttpStatus.OK.value(),
            message, 
            data
        );
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return ResponseDTO.ok(data, null);
    }

    public static <T> ResponseDTO<T> ok(String message) {
        return ResponseDTO.ok(null, message);
    }
}
