package com.universidad.seguridad.service;


import com.universidad.seguridad.model.Estudiante;
import com.universidad.seguridad.repository.EstudianteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class EstudianteService {
    private final EstudianteRepository repo;
    public EstudianteService(EstudianteRepository repo) {
        this.repo = repo;
    }
    public List<Estudiante> listarTodos() {
        return repo.findAll();
    }
    public Estudiante buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + id));
    }
    @Transactional
    public Estudiante guardar(Estudiante estudiante) {
        return repo.save(estudiante);
    }
    @Transactional
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
