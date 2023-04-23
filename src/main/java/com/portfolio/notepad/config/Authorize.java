package com.portfolio.notepad.config;

import com.portfolio.notepad.controller.session.MemberSession;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;

@Slf4j
public class Authorize implements Filter {

    private static final String SESSION_ID = "member";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpServletRequest.getSession();

        MemberSession memberSession = (MemberSession) session.getAttribute(SESSION_ID);

        // 화이트 리스트 url 체크
            // 세션 검사
            if (memberSession == null) {
                httpResponse.sendError(SC_UNAUTHORIZED);
                return;
            }

        chain.doFilter(request, response);
    }
}
