package com.example.hospitalservice.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetEmployment {
    private String email;
    private UUID hospitalId;
}
