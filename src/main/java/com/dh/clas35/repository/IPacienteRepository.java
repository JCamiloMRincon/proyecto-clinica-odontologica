package com.dh.clas35.repository;

import com.dh.clas35.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> { }
