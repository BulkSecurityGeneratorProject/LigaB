package com.polguillen.app.web.rest;

import com.polguillen.app.Application;
import com.polguillen.app.domain.Estadisticas;
import com.polguillen.app.repository.EstadisticasRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EstadisticasResource REST controller.
 *
 * @see EstadisticasResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EstadisticasResourceTest {


    private static final Integer DEFAULT_CANASTAS = 0;
    private static final Integer UPDATED_CANASTAS = 1;

    private static final Integer DEFAULT_FALTAS = 0;
    private static final Integer UPDATED_FALTAS = 1;

    private static final Integer DEFAULT_ASISTENCIAS = 0;
    private static final Integer UPDATED_ASISTENCIAS = 1;

    private static final Integer DEFAULT_REBOTES = 0;
    private static final Integer UPDATED_REBOTES = 1;

    @Inject
    private EstadisticasRepository estadisticasRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restEstadisticasMockMvc;

    private Estadisticas estadisticas;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EstadisticasResource estadisticasResource = new EstadisticasResource();
        ReflectionTestUtils.setField(estadisticasResource, "estadisticasRepository", estadisticasRepository);
        this.restEstadisticasMockMvc = MockMvcBuilders.standaloneSetup(estadisticasResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        estadisticas = new Estadisticas();
        estadisticas.setCanastas(DEFAULT_CANASTAS);
        estadisticas.setFaltas(DEFAULT_FALTAS);
        estadisticas.setAsistencias(DEFAULT_ASISTENCIAS);
        estadisticas.setRebotes(DEFAULT_REBOTES);
    }

    @Test
    @Transactional
    public void createEstadisticas() throws Exception {
        int databaseSizeBeforeCreate = estadisticasRepository.findAll().size();

        // Create the Estadisticas

        restEstadisticasMockMvc.perform(post("/api/estadisticass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estadisticas)))
                .andExpect(status().isCreated());

        // Validate the Estadisticas in the database
        List<Estadisticas> estadisticass = estadisticasRepository.findAll();
        assertThat(estadisticass).hasSize(databaseSizeBeforeCreate + 1);
        Estadisticas testEstadisticas = estadisticass.get(estadisticass.size() - 1);
        assertThat(testEstadisticas.getCanastas()).isEqualTo(DEFAULT_CANASTAS);
        assertThat(testEstadisticas.getFaltas()).isEqualTo(DEFAULT_FALTAS);
        assertThat(testEstadisticas.getAsistencias()).isEqualTo(DEFAULT_ASISTENCIAS);
        assertThat(testEstadisticas.getRebotes()).isEqualTo(DEFAULT_REBOTES);
    }

    @Test
    @Transactional
    public void getAllEstadisticass() throws Exception {
        // Initialize the database
        estadisticasRepository.saveAndFlush(estadisticas);

        // Get all the estadisticass
        restEstadisticasMockMvc.perform(get("/api/estadisticass"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(estadisticas.getId().intValue())))
                .andExpect(jsonPath("$.[*].canastas").value(hasItem(DEFAULT_CANASTAS)))
                .andExpect(jsonPath("$.[*].faltas").value(hasItem(DEFAULT_FALTAS)))
                .andExpect(jsonPath("$.[*].asistencias").value(hasItem(DEFAULT_ASISTENCIAS)))
                .andExpect(jsonPath("$.[*].rebotes").value(hasItem(DEFAULT_REBOTES)));
    }

    @Test
    @Transactional
    public void getEstadisticas() throws Exception {
        // Initialize the database
        estadisticasRepository.saveAndFlush(estadisticas);

        // Get the estadisticas
        restEstadisticasMockMvc.perform(get("/api/estadisticass/{id}", estadisticas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(estadisticas.getId().intValue()))
            .andExpect(jsonPath("$.canastas").value(DEFAULT_CANASTAS))
            .andExpect(jsonPath("$.faltas").value(DEFAULT_FALTAS))
            .andExpect(jsonPath("$.asistencias").value(DEFAULT_ASISTENCIAS))
            .andExpect(jsonPath("$.rebotes").value(DEFAULT_REBOTES));
    }

    @Test
    @Transactional
    public void getNonExistingEstadisticas() throws Exception {
        // Get the estadisticas
        restEstadisticasMockMvc.perform(get("/api/estadisticass/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadisticas() throws Exception {
        // Initialize the database
        estadisticasRepository.saveAndFlush(estadisticas);

		int databaseSizeBeforeUpdate = estadisticasRepository.findAll().size();

        // Update the estadisticas
        estadisticas.setCanastas(UPDATED_CANASTAS);
        estadisticas.setFaltas(UPDATED_FALTAS);
        estadisticas.setAsistencias(UPDATED_ASISTENCIAS);
        estadisticas.setRebotes(UPDATED_REBOTES);
        

        restEstadisticasMockMvc.perform(put("/api/estadisticass")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estadisticas)))
                .andExpect(status().isOk());

        // Validate the Estadisticas in the database
        List<Estadisticas> estadisticass = estadisticasRepository.findAll();
        assertThat(estadisticass).hasSize(databaseSizeBeforeUpdate);
        Estadisticas testEstadisticas = estadisticass.get(estadisticass.size() - 1);
        assertThat(testEstadisticas.getCanastas()).isEqualTo(UPDATED_CANASTAS);
        assertThat(testEstadisticas.getFaltas()).isEqualTo(UPDATED_FALTAS);
        assertThat(testEstadisticas.getAsistencias()).isEqualTo(UPDATED_ASISTENCIAS);
        assertThat(testEstadisticas.getRebotes()).isEqualTo(UPDATED_REBOTES);
    }

    @Test
    @Transactional
    public void deleteEstadisticas() throws Exception {
        // Initialize the database
        estadisticasRepository.saveAndFlush(estadisticas);

		int databaseSizeBeforeDelete = estadisticasRepository.findAll().size();

        // Get the estadisticas
        restEstadisticasMockMvc.perform(delete("/api/estadisticass/{id}", estadisticas.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Estadisticas> estadisticass = estadisticasRepository.findAll();
        assertThat(estadisticass).hasSize(databaseSizeBeforeDelete - 1);
    }
}
