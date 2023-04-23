package com.portfolio.notepad.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String[] SESSION_URL = new String[]{
            // 메모 관련 주소
            "/notes", "/noteAdd", "/typeupdate", "/deleteNote",
            // 회원 관련 주소
            "/member/delete", "/member/logout"};


    /**
     * 로그인 세션 필터링
     * @return filterFilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean loginSession(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setOrder(0);
        filterFilterRegistrationBean.setFilter(new Authorize());
        filterFilterRegistrationBean.addUrlPatterns(SESSION_URL);
        return filterFilterRegistrationBean;
    }
}
