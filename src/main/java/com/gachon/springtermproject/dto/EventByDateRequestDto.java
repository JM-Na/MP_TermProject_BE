package com.gachon.springtermproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventByDateRequestDto {
    private String email;
    private String date;
}
