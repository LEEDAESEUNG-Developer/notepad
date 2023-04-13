package com.portfolio.notepad.config;

import com.portfolio.notepad.controller.session.MemberSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginSession implements Filter {

    private static final String SESSION_ID = "member";

    // 세션이 없는 사용자도 접속허가할 URL
    private static final String[] WHITELIST = {"/", "/index", "/member/register", "/member/login", "/stylesheet/", "/h2-console"};


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpServletRequest.getSession();

        MemberSession memberSession = (MemberSession) session.getAttribute("member");

        String requestURI = httpServletRequest.getRequestURI();

        // 화이트 리스트 url 체크
        if (isLoginCheckPath(requestURI)) {
            // 세션 검사
            if (memberSession == null) {
                httpResponse.sendRedirect("/");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public boolean isLoginCheckPath(String uri) {
        return !PatternMatchUtils.simpleMatch(WHITELIST, uri); // uri에 화이트리스트 주소가 있으면 false
    }
}
