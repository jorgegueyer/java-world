package com.jgy.aop.aspect;


import org.aspectj.lang.annotation.Pointcut;

public class AnnotaPointcuts {

    public AnnotaPointcuts() {super();}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {

    }
}
