package com.submission.controller;

import com.submission.entity.TaskSubmission;
import com.submission.entity.User;
import com.submission.service.TaskService;
import com.submission.service.TaskSubmissionService;
import com.submission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tms/taskSubmission")
public class TaskSubmissionController {

    @Autowired
    private TaskSubmissionService taskSubmissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PostMapping("/taskSubmit")
    public ResponseEntity<TaskSubmission> taskSubmit(@RequestParam Long taskId,@RequestParam String githubLink,
                                                     @RequestHeader("Authorization") String jwt) throws Exception
    {
        User user=userService.getUser(jwt).getBody();
        TaskSubmission taskSubmission=taskSubmissionService.taskSubmit(taskId,user.getId(),githubLink,jwt);
        return new ResponseEntity<>(taskSubmission, HttpStatus.OK);
    }

    @GetMapping("/getTaskSubmissionById/{submissionId}")
    public ResponseEntity<TaskSubmission> getTaskSubmissionById(@PathVariable Long submissionId,
                                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.getUser(jwt).getBody();
        TaskSubmission taskSubmission=taskSubmissionService.getTaskSubmissionBySubmissionId(submissionId);
        return new ResponseEntity<>(taskSubmission,HttpStatus.OK);
    }

    @GetMapping("/getAllSubmission")
    public ResponseEntity<List<TaskSubmission>> getAllTaskSubmission(@RequestHeader("Authorization") String jwt)
    {
        User user=userService.getUser(jwt).getBody();
        List<TaskSubmission> allTaskSubmission=taskSubmissionService.getAllTaskSubmission();
        return new ResponseEntity<>(allTaskSubmission,HttpStatus.OK);
    }

    @GetMapping("/getTaskSubmissionByTaskId/{taskId}")
    public ResponseEntity<TaskSubmission> getTaskSubmissionByTaskId(@PathVariable Long taskId,
                                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.getUser(jwt).getBody();
        TaskSubmission taskSubmission=taskSubmissionService.getTaskSubmissionByTaskId(taskId);
        return new ResponseEntity<>(taskSubmission,HttpStatus.OK);
    }

    @PutMapping("/acceptOrDeclineSubmission/{submissionId}")
    public ResponseEntity<TaskSubmission> acceptOrDeclineSubmission(@PathVariable Long submissionId,@RequestParam String status,
                                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.getUser(jwt).getBody();
        TaskSubmission taskSubmission=taskSubmissionService.acceptOrdeclineSubmission(submissionId,status);
        return new ResponseEntity<>(taskSubmission,HttpStatus.OK);
    }

}
