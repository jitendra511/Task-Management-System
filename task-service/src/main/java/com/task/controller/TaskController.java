package com.task.controller;

import com.task.entity.Task;
import com.task.entity.TaskStatus;
import com.task.entity.User;
import com.task.service.TaskService;
import com.task.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tms/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping("/createTask")
    @CircuitBreaker(name = "userBreaker",fallbackMethod = "userFallback")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.getUser(jwt);
        Task taskCreated=taskService.createTask(task,user.getRole());
        return new ResponseEntity<>(taskCreated,HttpStatus.CREATED);
    }

    @GetMapping("/getTaskById/{id}")
    @CircuitBreaker(name = "userBreaker",fallbackMethod = "userFallback")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.getUser(jwt);
        Task task=taskService.getTaskById(id);
        return new ResponseEntity<>(task,HttpStatus.OK);
    }

    @GetMapping("/assignedTasksOfUser")
    @CircuitBreaker(name = "userBreaker",fallbackMethod = "userFallback")
    public ResponseEntity<List<Task>> assignedTasksOfUser(@RequestParam(required = false
    ) TaskStatus taskStatus,@RequestHeader("Authorization") String jwt)
    {
        User user=userService.getUser(jwt);
        List<Task> tasks=taskService.assignedTasksOfUser(user.getId(),taskStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/assignedTasksToUser/{taskId}/userId")
    @CircuitBreaker(name = "userBreaker",fallbackMethod = "userFallback")
    public ResponseEntity<Task> assignedTasksToUser(@PathVariable Long taskId,@PathVariable Long userId,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.getUser(jwt);
        Task task=taskService.assignedTaskToUser(taskId,userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAllTask")
    @CircuitBreaker(name = "userBreaker",fallbackMethod = "userFallback")
    public ResponseEntity<List<Task>> getAllTask(@RequestParam(required = false
    ) TaskStatus taskStatus,@RequestHeader("Authorization") String jwt)
    {
        User user=userService.getUser(jwt);
        List<Task> getAllTask=taskService.getAllTask(taskStatus);
        return new ResponseEntity<>(getAllTask,HttpStatus.OK);
    }

    @DeleteMapping("/deleteTask/{taskId}")
    @CircuitBreaker(name = "userBreaker",fallbackMethod = "userFallback")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskid,@RequestHeader("Authorization") String jwt)
    {
        User user=userService.getUser(jwt);
        taskService.deleteTask(taskid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateTask/{taskId}")
    @CircuitBreaker(name = "userBreaker",fallbackMethod = "userFallback")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId,@RequestBody Task update,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.getUser(jwt);
        Task task=taskService.updateTask(taskId,update, user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/setCompleteTask/{taskId}")
    public ResponseEntity<Task> setCompleteTask(@PathVariable Long taskId) throws Exception {
        taskService.setCompleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Task>> userFallback(TaskStatus taskStatus, String jwt, Throwable exception) {
        System.out.println("Fallback method is executed due to service is down" + exception.getMessage());
        Task fallbackTask=new Task();
        fallbackTask.setTitle("Fallback Task");
        fallbackTask.setDescription("This is a fallback task because the service is unavailable.");
        List<Task> fallbackTaskList = new ArrayList<>();
        fallbackTaskList.add(fallbackTask);
        return new ResponseEntity<>(fallbackTaskList,HttpStatus.SERVICE_UNAVAILABLE); // A fallback task to return when the circuit breaker is open
    }

}

