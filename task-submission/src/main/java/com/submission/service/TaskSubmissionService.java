package com.submission.service;

import com.submission.entity.Task;
import com.submission.entity.TaskSubmission;
import com.submission.repository.TaskSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskSubmissionService {

    @Autowired
    private TaskSubmissionRepository taskSubmissionRepository;

    @Autowired
    private TaskService taskService;

    public TaskSubmission taskSubmit(Long taskId,Long userId,String githubLink,String jwt) throws Exception
    {
        Task task=taskService.getTaskById(taskId,jwt);
        if(task!=null)
        {
            TaskSubmission taskSubmission=new TaskSubmission();
            taskSubmission.setTaskId(taskId);
            taskSubmission.setGithubLink(githubLink);
            taskSubmission.setUserId(userId);
            taskSubmission.setSubmissionTime(LocalDateTime.now());
            return taskSubmissionRepository.save(taskSubmission);
        }
        throw  new Exception("task is not found with id"+taskId);
    }

    public TaskSubmission getTaskSubmissionBySubmissionId(Long submissionId) throws Exception {
        return taskSubmissionRepository.findById(submissionId).orElseThrow(()->new Exception("Task submission is not found with id"+submissionId));
    }

    public List<TaskSubmission> getAllTaskSubmission()
    {
        return taskSubmissionRepository.findAll();
    }

    public TaskSubmission getTaskSubmissionByTaskId(Long taskId) throws Exception {
        return taskSubmissionRepository.findById(taskId).orElseThrow(()->new Exception("task submission is not found with task id"+taskId));
    }

    public TaskSubmission acceptOrdeclineSubmission(Long submissionId,String status) throws Exception {
        TaskSubmission taskSubmission=getTaskSubmissionBySubmissionId(submissionId);
        taskSubmission.setStatus(status);
        if(status.equals("ACCEPT"))
        {
            taskService.setCompleteTask(taskSubmission.getTaskId());
        }
        return taskSubmissionRepository.save(taskSubmission);
    }
}
