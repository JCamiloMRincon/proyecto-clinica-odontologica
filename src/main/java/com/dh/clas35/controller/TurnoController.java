package com.dh.clas35.controller;

import com.dh.clas35.entities.Odontologo;
import com.dh.clas35.entities.Paciente;
import com.dh.clas35.entities.Turno;
import com.dh.clas35.exceptions.BadRequestException;
import com.dh.clas35.exceptions.ResourceNotFoundException;
import com.dh.clas35.services.IOdontologoService;
import com.dh.clas35.services.IPacienteService;
import com.dh.clas35.services.ITurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private static final Logger logger = Logger.getLogger(TurnoController.class);
    @Autowired
    private ITurnoService turnoService;
    @Autowired
    private IPacienteService pacienteService;
    @Autowired
    private IOdontologoService odontologoService;

    @GetMapping
    public ResponseEntity<List<Turno>> traerTurnos() {
        List<Turno> listaDeTurnos = turnoService.listarTurnos();
        if (listaDeTurnos.size() == 0) {
            logger.warn("Operación exitosa. Sin embargo, no hay turnos registrados");
        } else {
            logger.info("Operación exitosa. Número de turnos registrados: " + listaDeTurnos.size());
        }
        return ResponseEntity.ok(listaDeTurnos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> traerTurnoPorId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado = turnoService.buscarTurnoPorId(id);
        if (turnoBuscado.isPresent()) {
            logger.info("Operación exitosa. Se consultó el turno con id: " + id +
                    " Paciente: " + turnoBuscado.get().getPaciente().getApellido() + " " +
                    turnoBuscado.get().getPaciente().getNombre() +
                    " Odontólogo: " + turnoBuscado.get().getOdontologo().getApellido() + " " +
                    turnoBuscado.get().getOdontologo().getNombre());
            return ResponseEntity.ok(turnoBuscado.get());
        }
        else {
            logger.error("Operación fallida. El turno no está registrado");
            throw new ResourceNotFoundException("El turno con id: " + id + " no está registrado. " +
                    "¿Ingresó un id correcto?");
        }
    }

    @PostMapping
    public ResponseEntity<Turno> registrarNuevoTurno(@RequestBody Turno turno) throws BadRequestException {

        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());

        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            if (turno.getFecha().isAfter(LocalDate.now())) {
                logger.info("Operación exitosa. El turno fue asignado para el día: " + turno.getFecha().toString() +
                        " al paciente: " + pacienteBuscado.get().getApellido() +
                        " " + pacienteBuscado.get().getNombre() +
                        " con el odontólogo: " + odontologoBuscado.get().getApellido() +
                        " " + odontologoBuscado.get().getNombre());
                return ResponseEntity.ok(turnoService.guardarTurno(turno));
            } else if (turno.getFecha().isEqual(LocalDate.now())) {
                logger.warn("Operación exitosa. ¡ATENCION! El turno vence hoy");
                return ResponseEntity.ok(turnoService.guardarTurno(turno));
            } else {
                logger.error("Operación fallida. La fecha asignada para el turno ya expiró");
                throw new BadRequestException("La fecha asignada para el turno ya expiró");
            }
        } else {
            if (pacienteBuscado.isPresent()) {
                logger.error("Operación fallida. El odontólogo no existe");
                throw new BadRequestException("El odóntologo con id: " + turno.getOdontologo().getId() + " no existe. " +
                        "¿Ingresó el ID correcto?");
            } else if (odontologoBuscado.isPresent()) {
                logger.error("Operación fallida. El paciente no existe");
                throw new BadRequestException("El paciente con id: " + turno.getPaciente().getId() + " no existe. " +
                        "¿Ingresó el ID correcto?");
            } else {
                logger.error("Operación fallida. Ni el odontólogo ni el paciente existen");
                throw new BadRequestException("El odóntologo con id: " + turno.getOdontologo().getId() + " y el" +
                        " paciente con id: " + turno.getPaciente().getId() + " no existen. ¿Ingresó los IDs correcto?");
            }
        }
    }

    @PutMapping
    public ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno) {
        Optional<Turno> turnoBuscado = turnoService.buscarTurnoPorId(turno.getId());
        if (turnoBuscado.isPresent()) {
            logger.info("Operación exitosa. Se actualizó el turno con id: " + turnoBuscado.get().getId());
            return ResponseEntity.ok(turnoService.actualizarTurno(turno));
        }
        else {
            logger.error("Operación fallida. El turno no está registrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado = turnoService.buscarTurnoPorId(id);
        if (turnoBuscado.isPresent()) {
            turnoService.eliminarTurno(id);
            logger.info("Operación exitosa. Se eliminó el turno con id: " + id);
            return ResponseEntity.ok("El turno  con id: " + id + " fue eliminado correctamente");
        }
        else {
            logger.error("Operación fallida. El turno no está registrado");
            throw new ResourceNotFoundException("El turno con id: " + id + " no existe. " +
                    "¿Ingresó un id correcto?");
        }
    }

}
