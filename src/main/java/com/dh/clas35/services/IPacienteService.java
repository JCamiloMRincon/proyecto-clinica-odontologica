package com.dh.clas35.services;

import com.dh.clas35.entities.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {

    List<Paciente> listarPacientes();
    Optional<Paciente> buscarPacientePorId(Long id);
    Paciente guardarPaciente(Paciente paciente);
    Paciente actualizarPaciente(Paciente paciente);
    void eliminarPaciente(Long id);

}
