package br.com.davidev.taskflow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.cglib.core.Local;

import java.text.DateFormat;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false)
    private String descricao;

    @Column (nullable = false)
    private String status;

    @Column (name = "Data de Criação", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column (name = "end date")
    private LocalDateTime dataConclusaoPrevista;

    @Column (name = "complete date")
    private LocalDateTime dataFinalizacao;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        if (this.status == null) { // Garante um status padrão se não for fornecido
            this.status = "Pending";
        }
    }


}
