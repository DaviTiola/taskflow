package br.com.davidev.taskflow.repository;

import br.com.davidev.taskflow.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {
}
