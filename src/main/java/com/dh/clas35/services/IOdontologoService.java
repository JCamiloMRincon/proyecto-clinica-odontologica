package com.dh.clas35.services;

import com.dh.clas35.entities.Odontologo;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    List<Odontologo> listarOdontologos();
    Optional<Odontologo> buscarOdontologoPorId(Long id);
    Odontologo guardarOdontologo(Odontologo odontologo);
    Odontologo actualizarOdontologo(Odontologo odontologo);
    void eliminarOdontologo(Long id);

}
