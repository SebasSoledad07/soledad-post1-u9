package com.universidad.seguridad.service;

import com.universidad.seguridad.model.Curso;
import com.universidad.seguridad.model.Estudiante;
import com.universidad.seguridad.repository.CursoRepository;
import com.universidad.seguridad.repository.EstudianteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class CursoService {
    private final CursoRepository cursoRepo;
    private final EstudianteRepository estudianteRepo;
    public CursoService(CursoRepository cursoRepo, EstudianteRepository
            estudianteRepo) {
        this.cursoRepo = cursoRepo;
        this.estudianteRepo = estudianteRepo;
    }
    public List<Curso> listarTodos() {
        return cursoRepo.findAllConEstudiantes();
    }
    public Curso buscarPorId(Long id) {
        return cursoRepo.findByIdConEstudiantes(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + id));
    }
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepo.save(curso);
    }
    @Transactional
    public void inscribirEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = buscarPorId(cursoId);
        Estudiante estudiante = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        curso.agregarEstudiante(estudiante); // helper method sincroniza ambos lados
        cursoRepo.save(curso);
    }
    @Transactional
    public void desinscribirEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = buscarPorId(cursoId);
        Estudiante estudiante = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        curso.quitarEstudiante(estudiante);
        cursoRepo.save(curso);
    }
}