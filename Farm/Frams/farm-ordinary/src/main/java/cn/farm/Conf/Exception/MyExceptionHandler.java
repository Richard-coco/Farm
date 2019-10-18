package cn.farm.Conf.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(NoLoginException.class)
    public String dealNoLoginException(){
        return "redirect:/noLogin";
    }
}
