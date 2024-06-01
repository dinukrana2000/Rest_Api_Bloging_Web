package com.exmple.dinuk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WhatappDTO {
    private String recipient;
    private String message;

}
