package com.dh.clas35.services;

import com.dh.clas35.entities.Turno;
import com.dh.clas35.repository.IOdontologoRepository;
import com.dh.clas35.repository.IPacienteRepository;
import com.dh.clas35.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoServiceImp implements ITurnoService {

    @Autowired
    private ITurnoRepository turnoRepository;

    @Override
    public List<Turno> listarTurnos() {
        return turnoRepository.findAll();
    }

    @Override
    public Optional<Turno> buscarTurnoPorId(Long id) {
        return turnoRepository.findById(id);
    }

    @Override
    public Turno guardarTurno(Turno turno) {
        return turnoRepository.save(turno);
    }

    @Override
    public Turno actualizarTurno(Turno turno) {
        return turnoRepository.save(turno);
    }

    @Override
    public void eliminarTurno(Long id) {
        turnoRepository.deleteById(id);
    }
}
