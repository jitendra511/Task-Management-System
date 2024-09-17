package com.task.service;

import com.task.entity.Task;
import com.task.entity.TaskStatus;
import com.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task,String requesterRole) throws Exception
    {
        if(!requesterRole.equals("ROLE_ADMIN"))
        {
            throw new  Exception("Task can be create by only admin");
        }
        task.setTaskStatus(TaskStatus.PENDING);
        task.setTaskStartDate(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) throws Exception
    {
        return taskRepository.findById(id).orElseThrow(()->new Exception("This task is not present"+id));
    }

    public List<Task> getAllTask(TaskStatus taskStatus)
    {
        List<Task> allTask=taskRepository.findAll();
        List<Task> filteredTask=allTask.stream().filter(
                task->taskStatus==null||task.getTaskStatus().name().equalsIgnoreCase(taskStatus.toString())
        ).collect(Collectors.toList());
        return filteredTask;
    }

    public Task updateTask(Long id,Task updateTask,long userId) throws Exception
    {
        Task existingTask=taskRepository.findById(id).get();
        if(updateTask.getTitle()!=null)
        {
            existingTask.setTitle(updateTask.getTitle());
        }
        if(updateTask.getImage()!=null)
        {
            existingTask.setImage(updateTask.getImage());
        }
        if(updateTask.getDescription()!=null)
        {
            existingTask.setDescription(updateTask.getDescription());
        }
        if(updateTask.getTaskStatus()!=null)
        {
            existingTask.setTaskStatus(updateTask.getTaskStatus());
        }
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id)
    {
        taskRepository.deleteById(id);
    }

    public Task assignedTaskToUser(Long taskId,Long userId) throws Exception
    {
        Task task=getTaskById(taskId);
        task.setAssignUserId(userId);
        task.setTaskStatus(TaskStatus.DONE);
        return task;
    }

    public List<Task> assignedTasksOfUser(long userId,TaskStatus taskStatus)
    {
        List<Task> allAssignTask=taskRepository.findByAssignUserId(userId);
        List<Task> filteredTask=allAssignTask.stream().filter(
                task->taskStatus==null||task.getTaskStatus().name().equalsIgnoreCase(taskStatus.toString())
        ).collect(Collectors.toList());
        return filteredTask;
    }

    public Task setCompleteTask(long taskId) throws Exception
    {
        Task task=getTaskById(taskId);
        task.setTaskStatus(TaskStatus.DONE);
        taskRepository.save(task);
        return task;
    }
}
