package com.app.GUZPASEN.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.repositories.AlumnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Sort;

class AlumnoServiceImplTest {

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @Mock
    private AlumnoRepository alumnoRepository;

    private Alumno alumno;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alumno = new Alumno("12345678A", "Juan", "Pérez", "María", "Pérez", "maria@mail.com", new ArrayList<>());
    }

    @Test
    void testCreateAlumno() {
        when(alumnoRepository.save(alumno)).thenReturn(alumno);
        Alumno result = alumnoService.createAlumno(alumno);
        assertEquals(alumno, result);
    }

    @Test
    void testGetAlumnoById_found() {
        when(alumnoRepository.findById("12345678A")).thenReturn(Optional.of(alumno));
        Alumno result = alumnoService.getAlumnoById("12345678A");
        assertEquals(alumno, result);
    }

    @Test
    void testGetAlumnoById_notFound() {
        when(alumnoRepository.findById("999")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> alumnoService.getAlumnoById("999"));
    }

    @Test
    void testGetAllAlumnos() {
        List<Alumno> lista = List.of(alumno);
        when(alumnoRepository.findAll()).thenReturn(lista);
        List<Alumno> result = alumnoService.getAllAlumnos();
        assertEquals(1, result.size());
        assertEquals(alumno, result.get(0));
    }

    @Test
    void testGetAllAlumnosOrdenadosAlfabeticamente() {
        List<Alumno> listaOrdenada = List.of(alumno);
        Sort sort = Sort.by(Sort.Direction.ASC, "apellidos", "nombre");
        when(alumnoRepository.findAll(sort)).thenReturn(listaOrdenada);
        List<Alumno> result = alumnoService.getAllAlumnosOrdenadosAlfabeticamente();
        assertEquals(1, result.size());
        assertEquals(alumno, result.get(0));
    }

    @Test
    void testUpdateAlumno_found() {
        Alumno actualizado = new Alumno();
        actualizado.setNombre("Carlos");
        actualizado.setApellidos("López");

        when(alumnoRepository.findById("12345678A")).thenReturn(Optional.of(alumno));
        when(alumnoRepository.save(any(Alumno.class))).thenAnswer(i -> i.getArgument(0));

        Alumno result = alumnoService.updateAlumno("12345678A", actualizado);
        assertEquals("Carlos", result.getNombre());
        assertEquals("López", result.getApellidos());
    }

    @Test
    void testUpdateAlumno_notFound() {
        when(alumnoRepository.findById("999")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> alumnoService.updateAlumno("999", new Alumno()));
    }

    @Test
    void testDeleteAlumno_validDni() {
        doNothing().when(alumnoRepository).deleteById("12345678A");
        assertDoesNotThrow(() -> alumnoService.deleteAlumno("12345678A"));
        verify(alumnoRepository, times(1)).deleteById("12345678A");
    }

    @Test
    void testDeleteAlumno_nullDni() {
        assertThrows(ResourceNotFoundException.class, () -> alumnoService.deleteAlumno(null));
    }
}
