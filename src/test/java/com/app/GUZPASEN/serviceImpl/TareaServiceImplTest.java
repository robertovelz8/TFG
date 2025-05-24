package com.app.GUZPASEN.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.models.Tarea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.GUZPASEN.controllers.NotificacionController;
import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.repositories.AlumnoRepository;
import com.app.GUZPASEN.repositories.SancionRepository;
import com.app.GUZPASEN.repositories.TareaRepository;

@SpringBootTest
public class TareaServiceImplTest {

    @InjectMocks
    private TareaServiceImpl tareaService;

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private SancionRepository sancionRepository;

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private NotificacionController notificacionController;

    private Sancion sancionConExpulsion;
    private Tarea tarea;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        sancionConExpulsion = new Sancion();
        sancionConExpulsion.setId(1L);
        sancionConExpulsion.setTipoSancion(Sancion.TipoSancion.CON_EXPULSION_FUERA);

        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setTitulo("Tarea prueba");
        tarea.setDescripcion("Descripción");
        tarea.setEstado(Tarea.Estado.PENDIENTE);
        tarea.setSancion(sancionConExpulsion);
    }

    @Test
    void testCreateTareaConExpulsionYNotificacion() {
        when(sancionRepository.findById(1L)).thenReturn(Optional.of(sancionConExpulsion));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea creada = tareaService.createTarea(tarea);

        assertNotNull(creada);
        verify(notificacionController).notificarTareaTutorLegal(any(Tarea.class));
        assertEquals("Tarea prueba", creada.getTitulo());
    }

    @Test
    void testCreateTareaSancionNoEncontrada() {
        Sancion sancion = new Sancion();
        sancion.setId(1L);
        tarea.setSancion(sancion); // aseguramos que no sea null

        when(sancionRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            tareaService.createTarea(tarea);
        });

        assertTrue(ex.getMessage().contains("No se ha encontrado la sanción"));
    }


    @Test
    void testGetTareaByIdExistente() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));

        Tarea encontrada = tareaService.getTareaById(1L);

        assertNotNull(encontrada);
        assertEquals(1L, encontrada.getId());
    }

    @Test
    void testGetTareaByIdNoExistente() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tareaService.getTareaById(1L));
    }

    @Test
    void testGetAllTareas() {
        List<Tarea> lista = List.of(tarea);
        when(tareaRepository.findAll()).thenReturn(lista);

        List<Tarea> resultado = tareaService.getAllTareas();

        assertEquals(1, resultado.size());
    }

    @Test
    void testUpdateTarea() {
        Tarea actualizada = new Tarea();
        actualizada.setTitulo("Nuevo título");
        actualizada.setDescripcion("Nueva descripción");

        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea resultado = tareaService.updateTarea(1L, actualizada);

        assertEquals("Nuevo título", resultado.getTitulo());
        assertEquals("Nueva descripción", resultado.getDescripcion());
    }

    @Test
    void testUpdateTareaNoEncontrada() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tareaService.updateTarea(1L, tarea));
    }

    @Test
    void testDeleteTarea() {
        doNothing().when(tareaRepository).deleteById(1L);

        assertDoesNotThrow(() -> tareaService.deleteTarea(1L));
        verify(tareaRepository).deleteById(1L);
    }

    @Test
    void testDeleteTareaIdNull() {
        assertThrows(ResourceNotFoundException.class, () -> tareaService.deleteTarea(null));
    }

    @Test
    void testGetTareasAlumnoExpulsado() {
        Alumno alumno = new Alumno();
        alumno.setDni("123");
        Sancion sancion = new Sancion();
        sancion.setTipoSancion(Sancion.TipoSancion.CON_EXPULSION_FUERA);
        sancion.setTareas(List.of(tarea));
        alumno.setSanciones(List.of(sancion));

        when(alumnoRepository.findById("123")).thenReturn(Optional.of(alumno));

        List<Tarea> resultado = tareaService.getTareasAlumnoExpulsado("123");

        assertEquals(1, resultado.size());
        assertEquals(tarea.getTitulo(), resultado.get(0).getTitulo());
    }

    @Test
    void testGetTareasAlumnoNoExiste() {
        when(alumnoRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tareaService.getTareasAlumnoExpulsado("123"));
    }
}

