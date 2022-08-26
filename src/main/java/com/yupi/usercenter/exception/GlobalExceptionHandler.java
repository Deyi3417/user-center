package com.yupi.usercenter.exception;

import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ErrorCode;
import com.yupi.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 *
 * @author yupi
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.info("MethodArgumentNotValidException", e);
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        BindingResult exceptions = e.getBindingResult();
        if (exceptions.hasErrors()) {
            List<FieldError> fieldErrors = exceptions.getFieldErrors();
            if (!fieldErrors.isEmpty()) {
                FieldError fieldError = fieldErrors.get(0);
                return ResultUtils.error(Integer.valueOf(fieldError.getCode()),fieldError.getDefaultMessage(),"");
            }
        }
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, e.getMessage(),"");

    }
}
