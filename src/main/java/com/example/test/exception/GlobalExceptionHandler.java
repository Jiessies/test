package com.example.test.exception;

import com.example.test.entity.ResponseObj;
import com.example.test.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
@SuppressWarnings("all")
public class GlobalExceptionHandler {

    /**
     * @param request
     * @param ex
     * @return
     * @throws Exception
     */
    /*@ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = ClientAbortException.class)
    public void defaultErrorHandler(HttpServletRequest request, ClientAbortException ex) throws Exception {
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        }
        String requestURI = request.getRequestURI();
        log.warn("do [{}] on [{}] failed. exMsg:{}", request.getMethod(), requestURI,
                ex.getLocalizedMessage());
    }*/

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseObj<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Global Exception is," + e.getMessage(), e);
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseObj.fail(ResultCode.INVALID_PARAM, defaultMessage);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseObj defaultErrorHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) throws Exception {
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        }
        String requestURI = request.getRequestURI();
        log.warn("do [{}] on [{}] failed. exMsg:{}", request.getMethod(), requestURI,
                ex.getLocalizedMessage());
        return ResponseObj.fail("请求方式错误");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseObj<?> handleBusinessException(BusinessException e) {
        ResponseObj<StackTraceElement[]> responseData = new ResponseObj<>();
        responseData.setData(null);
        responseData.setCode(e.getErrorCode());
        responseData.setMessage(e.getMessage());
        log.error("Global Exception is," + e.getMessage(), e);
        return responseData;
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseObj<?> illegalArgumentExceptionException(HttpServletRequest request, IllegalArgumentException ex) {
        String requestURI = request.getRequestURI();
        log.error("do [{}] on [{}] failed. exMsg:{}", request.getMethod(), requestURI,
                ex.getLocalizedMessage(), ex);
        log.error("queryString:{}, parameterMap: {}", request.getQueryString(), request);
        return ResponseObj.fail(ex.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseObj<?> exception(HttpServletRequest request, Exception ex) {
        String requestURI = request.getRequestURI();
        log.error("do [{}] on [{}] failed. exMsg:{}", request.getMethod(), requestURI,
                ex.getLocalizedMessage(), ex);
        log.error("queryString:{}, parameterMap: {}", request.getQueryString(), request);
        return ResponseObj.fail(ex.getLocalizedMessage());
    }
}
