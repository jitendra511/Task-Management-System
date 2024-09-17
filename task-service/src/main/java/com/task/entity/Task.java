package com.task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;
    private String title;
    private String description;
    private String image;
    private Long assignUserId;
    private TaskStatus taskStatus;
    private LocalDateTime taskStartDate;
    private LocalDateTime taskEndDate;
    private List<String> skillUsed=new ArrayList<>();
}
