package com.app.GUZPASEN.controllers;

import com.app.GUZPASEN.models.Tarea;
import com.app.GUZPASEN.serviceImpl.TareaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TareaControllerTest {

    @InjectMocks
    private TareaController tareaController;

    @Mock
    private TareaServiceImpl tareaServiceImpl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Tarea getMockTarea() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setTitulo("Hacer informe");
        tarea.setDescripcion("Realizar el informe semanal de conducta");
        return tarea;
    }

    @Test
    void testCreateTarea_returnsCreatedTarea() {
        Tarea tarea = getMockTarea();

        when(tareaServiceImpl.createTarea(any(Tarea.class))).thenReturn(tarea);

        Tarea result = tareaController.createTarea(tarea).getBody();

        assertEquals(tarea.getId(), result.getId());
        assertEquals(tarea.getTitulo(), result.getTitulo());
        assertEquals(tarea.getDescripcion(), result.getDescripcion());
    }

    @Test
    void testGetTareaById_returnsTarea() {
        Tarea tarea = getMockTarea();

        when(tareaServiceImpl.getTareaById(1L)).thenReturn(tarea);

        Tarea result = tareaController.getTareaById(1L).getBody();

        assertEquals(tarea.getId(), result.getId());
        assertEquals(tarea.getTitulo(), result.getTitulo());
        assertEquals(tarea.getDescripcion(), result.getDescripcion());
    }

    @Test
    void testGetAllTareas_returnsList() {
        Tarea t1 = getMockTarea();
        Tarea t2 = new Tarea();
        t2.setId(2L);
        t2.setTitulo("Limpieza");
        t2.setDescripcion("Limpiar el aula");

        when(tareaServiceImpl.getAllTareas()).thenReturn(List.of(t1, t2));

        List<Tarea> result = tareaController.getAllTareas().getBody();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateTarea_returnsUpdatedTarea() {
        Tarea tarea = getMockTarea();
        tarea.setTitulo("Informe actualizado");
        tarea.setDescripcion("Informe actualizado con nuevos datos");

        when(tareaServiceImpl.updateTarea(eq(1L), any(Tarea.class))).thenReturn(tarea);

        Tarea result = tareaController.updateTarea(1L, tarea).getBody();

        assertEquals("Informe actualizado", result.getTitulo());
        assertEquals("Informe actualizado con nuevos datos", result.getDescripcion());
    }

    @Test
    void testDeleteTarea_returnsOk() {
        doNothing().when(tareaServiceImpl).deleteTarea(1L);

        tareaController.deleteTarea(1L);

        verify(tareaServiceImpl, times(1)).deleteTarea(1L);
    }

    @Test
    void testGetTareasAlumnoExpulsado_returnsList() {
        Tarea tarea = getMockTarea();

        when(tareaServiceImpl.getTareasAlumnoExpulsado("12345678A")).thenReturn(List.of(tarea));

        List<Tarea> result = tareaController.getTareasAlumnoExpulsado("12345678A").getBody();

        assertEquals(1, result.size());
        assertEquals("Hacer informe", result.get(0).getTitulo());
        assertEquals("Realizar el informe semanal de conducta", result.get(0).getDescripcion());
    }
}
