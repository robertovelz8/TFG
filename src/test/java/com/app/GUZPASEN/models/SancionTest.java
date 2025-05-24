package com.app.GUZPASEN.models;

import com.app.GUZPASEN.DTOs.ParteDTO;
import com.app.GUZPASEN.DTOs.TareaDTO;
import com.app.GUZPASEN.models.Parte;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.models.Tarea;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SancionTest {

    @Test
    void testGetParteDTO_whenParteIsNotNull_returnsParteDTO() {
        Parte parte = new Parte();
        parte.setId(1L);
        parte.setMotivo("Motivo de prueba");

        Sancion sancion = new Sancion();
        sancion.setParte(parte);

        ParteDTO parteDTO = sancion.getParteDTO();

        assertNotNull(parteDTO);
        assertEquals(1L, parteDTO.getId());
        assertEquals("Motivo de prueba", parteDTO.getMotivo());
    }

    @Test
    void testGetParteDTO_whenParteIsNull_returnsNull() {
        Sancion sancion = new Sancion();
        sancion.setParte(null);

        ParteDTO parteDTO = sancion.getParteDTO();

        assertNull(parteDTO);
    }

    @Test
    void testGetTareasDTO_returnsCorrectListOfDTOs() {
        Tarea tarea1 = new Tarea();
        tarea1.setTitulo("Tarea 1");
        tarea1.setDescripcion("Desc 1");

        Tarea tarea2 = new Tarea();
        tarea2.setTitulo("Tarea 2");
        tarea2.setDescripcion("Desc 2");

        List<Tarea> tareas = new ArrayList<>();
        tareas.add(tarea1);
        tareas.add(tarea2);

        Sancion sancion = new Sancion();
        sancion.setTareas(tareas);

        List<TareaDTO> tareaDTOList = sancion.getTareasDTO();

        assertEquals(2, tareaDTOList.size());
        assertEquals("Tarea 1", tareaDTOList.get(0).getTitulo());
        assertEquals("Desc 1", tareaDTOList.get(0).getDescripcion());
        assertEquals("Tarea 2", tareaDTOList.get(1).getTitulo());
        assertEquals("Desc 2", tareaDTOList.get(1).getDescripcion());
    }

    @Test
    void testGetTareasDTO_whenEmpty_returnsEmptyList() {
        Sancion sancion = new Sancion();
        sancion.setTareas(new ArrayList<>());

        List<TareaDTO> tareaDTOList = sancion.getTareasDTO();

        assertNotNull(tareaDTOList);
        assertTrue(tareaDTOList.isEmpty());
    }
}

