package com.omer.hrapp.responses;

import com.omer.hrapp.entities.Blacklist;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BlacklistResponse {
    private Long id;
    private String explanation;
    private LocalDateTime blackListedAt;
    private Long applicantId;
    private Long specialistId;

    public BlacklistResponse(Blacklist entity) {
        this.id = entity.getId();
        this.explanation = entity.getExplanation();
        this.blackListedAt = entity.getBlackListedAt();
        this.applicantId = entity.getApplicant().getId();
        this.specialistId = entity.getSpecialist().getId();
    }
}
