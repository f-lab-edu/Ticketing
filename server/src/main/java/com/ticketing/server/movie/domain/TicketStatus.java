package com.ticketing.server.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TicketStatus {
    SALE("판매가능"),
    SCHEDULED("환불"),
    SOLD("판매완료");

    private String name;

}
