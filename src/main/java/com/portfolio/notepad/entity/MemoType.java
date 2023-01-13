package com.portfolio.notepad.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
public enum MemoType {
    BUSINESS, SOCIAL, IMPORTANT;
}
