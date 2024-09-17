package com.submission.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long submissionId;
    private Long taskId;
    private String githubLink;
    private Long userId;
    private String status="PEDING";
    private LocalDateTime submissionTime;
}
