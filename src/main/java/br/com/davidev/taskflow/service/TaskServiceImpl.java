package br.com.davidev.taskflow.service;

import br.com.davidev.taskflow.model.TaskModel;
import br.com.davidev.taskflow.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository; 
    }

    @Override
    @Transactional
    public TaskModel criarTask (TaskModel task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskModel> listarTodasTasks() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskModel> buscarTaskPorId(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    @Transactional

    public TaskModel atualizarTask (Long id, TaskModel taskAtualizada) {
        TaskModel taskExistente = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada com o id: " + id));
        taskExistente.setDescricao(taskAtualizada.getDescricao());
        taskExistente.setStatus(taskAtualizada.getStatus());
        taskExistente.setDataConclusaoPrevista(taskAtualizada.getDataConclusaoPrevista());

        if ("Concluída".equalsIgnoreCase(taskAtualizada.getStatus())) {
            if (taskExistente.getDataFinalizacao() == null) {
                taskExistente.setDataFinalizacao(LocalDateTime.now());
                }
            } else {
                taskExistente.setDataFinalizacao(null);
            }

        return taskRepository.save(taskExistente);
    }

    @Override
    @Transactional
    public void deletarTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task não encontrada com o Id: " + id);
        }
        taskRepository.deleteById(id);
    }


}
