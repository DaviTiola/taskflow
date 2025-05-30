    package br.com.davidev.taskflow.controller;



    import br.com.davidev.taskflow.model.TaskModel;
    import br.com.davidev.taskflow.service.TaskService;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/api/v1/tasks")
    public class TaskController {

        private final TaskService taskService;

        public TaskController(TaskService taskService) {
            this.taskService = taskService;
        }

        @PostMapping
        public ResponseEntity<TaskModel> createTask(@RequestBody TaskModel task) {
            TaskModel newTask = taskService.criarTask(task);
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        }

        @GetMapping
        public ResponseEntity<List<TaskModel>> listarTodasTasks() {
            List<TaskModel> todasAsTasks = taskService.listarTodasTasks();
            return ResponseEntity.ok(todasAsTasks);
        }

        @GetMapping ("/{id}")
        public ResponseEntity<TaskModel> buscarTaskPorId(@PathVariable Long id) {
            Optional<TaskModel> optionalTaskModel = taskService.buscarTaskPorId(id);
            if (optionalTaskModel.isPresent()) {
                return ResponseEntity.ok(optionalTaskModel.get());
            } else {
                return ResponseEntity.notFound().build();
            }

        }

        @PutMapping("/{id}")
        public ResponseEntity<TaskModel> atualizarTask(@PathVariable Long id, @RequestBody TaskModel taskDetails) {
            TaskModel taskAtualizada = taskService.atualizarTask(id, taskDetails);
            return ResponseEntity.ok(taskAtualizada);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletarTask (@PathVariable Long id) {
            taskService.deletarTask(id);
            return ResponseEntity.noContent().build();
        }

    }


