package com.system.proyect.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "competencias")
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCompetencia estado;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    // --- Relaciones se implementarán más adelante ---
    // @OneToMany(mappedBy = "competencia", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<CompetenciaProblema> problemas = new ArrayList<>();

    // @OneToMany(mappedBy = "competencia")
    // private List<Inscripcion> inscripciones = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoCompetencia.BORRADOR;
        }
    }

    // Métodos de negocio temporales (devolverán los valores reales cuando existan las relaciones)
    public boolean tieneInscritos() {
        return false;  // Se implementará cuando Inscripcion esté lista
    }

    public boolean tieneProblemas() {
        return false;  // Se implementará cuando CompetenciaProblema esté lista
    }
}