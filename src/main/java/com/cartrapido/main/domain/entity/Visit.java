package com.cartrapido.main.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Setter
@Getter
public class Visit {

    @Id
    private Long id;

    @Column(updatable = false,nullable = false)
    private LocalDate date;

    private int numOfVisit;

    @Builder
    public Visit(int numOfVisit){
        this.numOfVisit = numOfVisit;
    }
}
