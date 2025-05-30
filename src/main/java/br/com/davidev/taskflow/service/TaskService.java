package br.com.davidev.taskflow.service;

import br.com.davidev.taskflow.model.TaskModel;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    TaskModel criarTask(TaskModel task);
    List<TaskModel> listarTodasTasks();
    Optional<TaskModel> buscarTaskPorId(Long id);
    TaskModel atualizarTask(Long id, TaskModel tarefaAtualizada);
    void deletarTask(Long id);

}
