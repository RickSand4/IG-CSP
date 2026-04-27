// CompetenciaAdminController.java
package com.system.proyect.controllers;

import com.system.proyect.models.Competencia;
import com.system.proyect.models.EstadoCompetencia;
import com.system.proyect.repositories.CompetenciaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/competencias")
public class CompetenciaAdminController {

    private final CompetenciaRepository competenciaRepository;

    public CompetenciaAdminController(CompetenciaRepository competenciaRepository) {
        this.competenciaRepository = competenciaRepository;
    }

    // ===================== LECTURA (LISTAR) =====================
    @GetMapping
    public String listarCompetencias(Model model) {
        List<Competencia> competencias = competenciaRepository.findByEstadoNot(EstadoCompetencia.ARCHIVADA);
        model.addAttribute("competencias", competencias);
        model.addAttribute("totalActivas",
                competenciaRepository.countByEstado(EstadoCompetencia.ACTIVA));
        model.addAttribute("totalFinalizadas",
                competenciaRepository.countByEstado(EstadoCompetencia.FINALIZADA));
        return "admin/competencias/lista";
    }

    // ===================== VER DETALLE =====================
    @GetMapping("/{id}")
    public String verCompetencia(@PathVariable Long id, Model model,
                                 RedirectAttributes redirectAttrs) {
        Optional<Competencia> competenciaOpt = competenciaRepository.findById(id);
        if (competenciaOpt.isPresent()) {
            model.addAttribute("competencia", competenciaOpt.get());
            return "admin/competencias/detalle";
        } else {
            redirectAttrs.addFlashAttribute("error", "Competencia no encontrada.");
            return "redirect:/admin/competencias";
        }
    }

    // ===================== CREACIÓN (FORMULARIO) =====================
    @GetMapping("/nueva")
    public String formularioCreacion(Model model) {
        model.addAttribute("competencia", new Competencia());
        model.addAttribute("estados", EstadoCompetencia.values());
        return "admin/competencias/formulario";
    }

    // ===================== CREACIÓN (GUARDAR) =====================
    @PostMapping("/nueva")
    public String guardarCompetencia(@Valid @ModelAttribute Competencia competencia,
                                     BindingResult result,
                                     RedirectAttributes redirectAttrs,
                                     Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estados", EstadoCompetencia.values());
            return "admin/competencias/formulario";
        }

        // Validación de negocio: si estado es ACTIVA, debe tener problemas
        if (competencia.getEstado() == EstadoCompetencia.ACTIVA &&
                (!competencia.tieneProblemas())) {
            redirectAttrs.addFlashAttribute("warning",
                    "La competencia se creó pero no está ACTIVA. Asigne al menos un problema para activarla.");
            competencia.setEstado(EstadoCompetencia.BORRADOR); // Forzar a borrador
        }

        competenciaRepository.save(competencia);
        redirectAttrs.addFlashAttribute("exito",
                "Competencia '" + competencia.getTitulo() + "' creada exitosamente.");
        return "redirect:/admin/competencias";
    }

    // ===================== EDICIÓN (FORMULARIO) =====================
    @GetMapping("/{id}/editar")
    public String formularioEdicion(@PathVariable Long id, Model model,
                                    RedirectAttributes redirectAttrs) {
        Optional<Competencia> competenciaOpt = competenciaRepository.findById(id);
        if (competenciaOpt.isPresent()) {
            model.addAttribute("competencia", competenciaOpt.get());
            model.addAttribute("estados", EstadoCompetencia.values());
            return "admin/competencias/formulario";
        } else {
            redirectAttrs.addFlashAttribute("error", "Competencia no encontrada.");
            return "redirect:/admin/competencias";
        }
    }

    // ===================== EDICIÓN (GUARDAR CAMBIOS) =====================
    @PostMapping("/{id}/editar")
    public String actualizarCompetencia(@PathVariable Long id,
                                        @Valid @ModelAttribute Competencia competencia,
                                        BindingResult result,
                                        RedirectAttributes redirectAttrs,
                                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("estados", EstadoCompetencia.values());
            return "admin/competencias/formulario";
        }

        Optional<Competencia> existenteOpt = competenciaRepository.findById(id);
        if (existenteOpt.isPresent()) {
            Competencia existente = existenteOpt.get();

            // Validación de negocio: ¿se intenta activar sin problemas?
            if (competencia.getEstado() == EstadoCompetencia.ACTIVA &&
                    !existente.tieneProblemas()) {
                redirectAttrs.addFlashAttribute("error",
                        "No se puede activar la competencia. Debe tener al menos un problema asignado.");
                return "redirect:/admin/competencias/" + id + "/editar";
            }

            // Actualizar campos
            existente.setTitulo(competencia.getTitulo());
            existente.setDescripcion(competencia.getDescripcion());
            existente.setEstado(competencia.getEstado());
            existente.setFechaInicio(competencia.getFechaInicio());
            existente.setFechaFin(competencia.getFechaFin());

            competenciaRepository.save(existente);
            redirectAttrs.addFlashAttribute("exito",
                    "Competencia actualizada correctamente.");
        } else {
            redirectAttrs.addFlashAttribute("error", "Competencia no encontrada.");
        }
        return "redirect:/admin/competencias";
    }

    // ===================== ELIMINAR / ARCHIVAR =====================
    @PostMapping("/{id}/eliminar")
    public String eliminarCompetencia(@PathVariable Long id,
                                      @RequestParam(required = false, defaultValue = "false")
                                      boolean forzar,
                                      RedirectAttributes redirectAttrs) {
        Optional<Competencia> competenciaOpt = competenciaRepository.findById(id);
        if (competenciaOpt.isPresent()) {
            Competencia competencia = competenciaOpt.get();

            // Regla de negocio: si tiene inscritos o historial, solo archivar
            if (competencia.tieneInscritos() && !forzar) {
                competencia.setEstado(EstadoCompetencia.ARCHIVADA);
                competenciaRepository.save(competencia);
                redirectAttrs.addFlashAttribute("info",
                        "La competencia tiene estudiantes inscritos. Se ha archivado en lugar de eliminar.");
            } else {
                competenciaRepository.delete(competencia);
                redirectAttrs.addFlashAttribute("exito",
                        "Competencia eliminada permanentemente.");
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "Competencia no encontrada.");
        }
        return "redirect:/admin/competencias";
    }

    // Listar competencias archivadas
    @GetMapping("/archivadas")
    public String listarArchivadas(Model model) {
        List<Competencia> competencias = competenciaRepository.findByEstado(EstadoCompetencia.ARCHIVADA);
        model.addAttribute("competencias", competencias);
        model.addAttribute("mostrandoArchivadas", true);
        model.addAttribute("totalActivas", competenciaRepository.countByEstado(EstadoCompetencia.ACTIVA));
        model.addAttribute("totalFinalizadas", competenciaRepository.countByEstado(EstadoCompetencia.FINALIZADA));
        return "admin/competencias/lista";
    }
}