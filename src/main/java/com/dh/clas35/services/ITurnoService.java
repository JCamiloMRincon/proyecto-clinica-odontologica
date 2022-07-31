package com.dh.clas35.services;

import com.dh.clas35.entities.Odontologo;
import com.dh.clas35.entities.Turno;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    List<Turno> listarTurnos();
    Optional<Turno> buscarTurnoPorId(Long id);
    Turno guardarTurno(Turno turno);
    Turno actualizarTurno(Turno turno);
    void eliminarTurno(Long id);
}
