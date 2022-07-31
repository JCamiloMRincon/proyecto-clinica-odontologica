package com.dh.clas35.controller;

import com.dh.clas35.entities.Odontologo;
import com.dh.clas35.exceptions.BadRequestException;
import com.dh.clas35.exceptions.ResourceNotFoundException;
import com.dh.clas35.services.IOdontologoService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private static final Logger logger = Logger.getLogger(OdontologoController.class);
    @Autowired
    private IOdontologoService odontologoService;

    @Operation(summary = "Lista de todos los odontólogos registrados")
    @GetMapping
    public ResponseEntity<List<Odontologo>> traerOdontologos() {
        List<Odontologo> listaDeOdontologos = odontologoService.listarOdontologos();
        if (listaDeOdontologos.size() == 0) {
            logger.warn("Operación exitosa. Sin embargo, no hay odontólogos registrados");
        } else {
            logger.info("Operación exitosa. Número de odontólogos registrados: " + listaDeOdontologos.size());
        }
        return ResponseEntity.ok(listaDeOdontologos);
    }

    @Operation(summary = "Buscar un odontólogo por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> traerOdontologoPorId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
        if (odontologoBuscado.isPresent()) {
            logger.info("Operación exitosa. Se consultó el odontólogo con id: " + id);
            return ResponseEntity.ok(odontologoBuscado.get());
        }
        else {
            logger.error("Operación fallida. El odontólogo no existe");
            throw new ResourceNotFoundException("El odontologo con id: " + id + " no existe. " +
                    "¿Ingresó un id correcto?");
        }
    }

    @Operation(summary = "Crear un nuevo odontólogo. El ID no se debe pasar, éste es autogenerado")
    @PostMapping
    public ResponseEntity<Odontologo> registrarNuevoOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        if (odontologo.getMatricula() != 0) {
            logger.info("Operación exitosa. Se registró el odontólogo: " +
                    odontologo.getApellido() + " " + odontologo.getNombre() +
                    " con matricula: " + odontologo.getMatricula());
            return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
        } else {
            logger.error("Operación fallida. El odontólogo no puede tener una matrícula igual a 0");
            throw new BadRequestException("La matrícula no puede ser 0. Inténtelo de nuevo");
        }
    }

    @Operation(summary = "Actualizar la información de un odontólogo. Debe aparecer toda la información")
    @PutMapping
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(odontologo.getId());
        if (odontologoBuscado.isPresent()) {
            logger.info("Operación exitosa. Se actualizó el odontólogo con id: " + odontologoBuscado.get().getId());
            return ResponseEntity.ok(odontologoService.actualizarOdontologo(odontologo));
        } else {
            logger.error("Operación fallida. El odontólogo no existe");
            throw new ResourceNotFoundException("El odontologo con id: " + odontologo.getId() + " no existe. " +
                    "¿Ingresó un id correcto?");
        }
    }

    @Operation(summary = "Eliminar un odontólogo con un ID específico")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
        if (odontologoBuscado.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            logger.info("Operación exitosa. Se eliminó el odontólogo con id: " + id);
            return ResponseEntity.ok("El odontologo  con id: " + id + " fue eliminado correctamente");
        } else {
            logger.error("Operación fallida. El odontólogo no existe");
            throw new ResourceNotFoundException("El odontologo con id: " + id + " no existe. " +
                    "¿Ingresó un id correcto?");
        }
    }
}
