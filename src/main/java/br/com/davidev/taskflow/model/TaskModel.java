package br.com.davidev.taskflow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.cglib.core.Local;

import java.text.DateFormat;
import java.time.LocalDateTime;

@Entity // 1. Indica que esta classe é uma entidade JPA (será uma tabela no banco)
@Table(name = "tarefas") // 2. (Opcional) Especifica o nome da tabela no banco. Se omitido, usa o nome da classe.
@Data // 3. Lombok: Gera getters, setters, toString(), equals() e hashCode()
@NoArgsConstructor // 4. Lombok: Gera um construtor sem argumentos (necessário para JPA)
@AllArgsConstructor // 5. Lombok: Gera um construtor com todos os argumentos (conveniente)
public class TaskModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false)
    private String description;

    @Column (nullable = false)
    private String status;

    @Column (name = "data create", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column (name = "end date")
    private LocalDateTime dueDate;

    @Column (name = "complete date")
    private LocalDateTime completionDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        if (this.status == null) { // Garante um status padrão se não for fornecido
            this.status = "Pending";
        }
    }


}
