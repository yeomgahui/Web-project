package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.Visit;

public class VisitSaveDTO {
    private int numOfVisit;

    public Visit toEntity(){
        return Visit.builder()
                .numOfVisit(1).build();
    }
}
