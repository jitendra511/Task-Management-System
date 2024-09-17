package com.submission.service;

import com.submission.entity.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "TASK-SERVICE",url = "http://localhost:8082")
public interface TaskService {

    @GetMapping("/tms/tasks/getTaskById/{id}")
    public Task getTaskById(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception;

    @PutMapping("/tms/tasks/setCompleteTask/{taskId}")
    public Task setCompleteTask(@PathVariable Long taskId) throws Exception;


}
