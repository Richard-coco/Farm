package cn.farm.Config.ExceptionHandler;

import cn.farm.Config.Exception.NoLoginException;
import cn.farm.Config.Exception.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoLoginException.class)
    public String dealNoLoginException(){
        return "redirect:/noLogin";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    public String dealUnauthorized(){
        return "redirect:/unauthorized";
    }
}
