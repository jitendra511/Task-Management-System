package com.submission.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task
{
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
