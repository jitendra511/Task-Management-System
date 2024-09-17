package com.submission.repository;

import com.submission.entity.TaskSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskSubmissionRepository extends JpaRepository<TaskSubmission,Long> {

    public TaskSubmission findByTaskId(Long taskId);
}
