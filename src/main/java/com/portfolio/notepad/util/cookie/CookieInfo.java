package com.portfolio.notepad.util.cookie;

import lombok.Builder;
import lombok.Data;

@Data
public class CookieInfo {
        private final String name;
        private final String value;
        private final String domain;

        @Builder
        public CookieInfo(String name, String value, String domain) {
            this.name = name;
            this.value = value;
            this.domain = domain;
        }
    }