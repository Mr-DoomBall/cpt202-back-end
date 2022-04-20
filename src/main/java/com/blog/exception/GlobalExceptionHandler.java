package com.blog.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.blog.util.ApiModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 */
public class GlobalExceptionHandler implements HandlerExceptionResolver, Ordered {

    public Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) {
        Map<String, Object> map = specialExceptionResolve(ex);
        Integer httpStatus = (Integer) map.get("status");
        //返回自定义信息
        if (httpStatus != null) {
            log.info("已知异常", ex);//不用记录到error日志文件中
            ApiModel apiModel = (ApiModel) map.get("apiModel");
            apiModel.setStatus(httpStatus);
            response.setStatus(httpStatus);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out;
            try {
                out = response.getWriter();
                out.print(JSON.toJSONString(apiModel, SerializerFeature.WriteMapNullValue));
            } catch (Exception e) {
                log.error("", e);
            }
            return new ModelAndView();
        } else if (ex.getMessage() != null && ex.getMessage().contains("Broken pipe")) {
            log.error("客户端连接中断 ", ex.getMessage());
            return null;
        } else {
            log.error("未知异常: ", ex);
            return null;
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 加入自定义处理，实现对400， 404， 405， 406， 415， 500(参数问题导致)， 503的处理
     * {@link org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleException},
     * {@link org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver},
     *
     * @param ex 异常信息
     */
    private Map<String, Object> specialExceptionResolve(Exception ex) {
        Map<String, Object> map = new HashMap<>();
        ApiModel apiModel = new ApiModel();
        try {
            if (ex instanceof BindException) {
                BindException exception = (BindException) ex;
                BindingResult result = exception.getBindingResult();
                if (result.hasErrors()) {
                    apiModel.setMessage(result.getAllErrors().get(0).getDefaultMessage());
                } else {
                    apiModel.setMessage("非法请求，参数错误");
                }
                map.put("status", 400);
                map.put("apiModel", apiModel);
                return map;
            } else if (ex instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
                BindingResult result = exception.getBindingResult();
                if (result.hasErrors()) {
                    apiModel.setMessage(result.getAllErrors().get(0).getDefaultMessage());
                } else {
                    apiModel.setMessage("非法请求，参数错误");
                }
                map.put("status", 400);
                map.put("apiModel", apiModel);
                return map;
            } else if (ex instanceof ConstraintViolationException) {
                ConstraintViolationException exception = (ConstraintViolationException) ex;
                apiModel.setMessage(exception.getMessage());
                map.put("status", 400);
                map.put("apiModel", apiModel);
                return map;
            } else if (ex instanceof TypeMismatchException) {
                apiModel.setMessage("参数类型错误");
                map.put("status", 400);
                map.put("apiModel", apiModel);
                return map;
            } else if (ex instanceof HttpMessageNotReadableException) {
                apiModel.setMessage("参数错误");
                map.put("status", 400);
                map.put("apiModel", apiModel);
                return map;
            } else if (ex instanceof CustomException) {
                CustomException exception = (CustomException) ex;
                apiModel.setMessage(exception.getMessage());
                map.put("status", exception.getHttpStatus());
                map.put("apiModel", apiModel);
                return map;
            }

        } catch (Exception handlerException) {
            log.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
            apiModel.setMessage("系统异常，请联系管理员");
            map.put("status", 500);
            map.put("apiModel", apiModel);
        }
        return map;
    }

}
