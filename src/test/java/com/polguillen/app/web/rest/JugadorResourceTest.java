package com.polguillen.app.web.rest;

import com.polguillen.app.Application;
import com.polguillen.app.domain.Jugador;
import com.polguillen.app.repository.JugadorRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the JugadorResource REST controller.
 *
 * @see JugadorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JugadorResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_BIRTH_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = new LocalDate();

    private static final Integer DEFAULT_CANASTAS = 1;
    private static final Integer UPDATED_CANASTAS = 2;

    private static final Integer DEFAULT_ASISTENCIAS = 1;
    private static final Integer UPDATED_ASISTENCIAS = 2;

    private static final Integer DEFAULT_REBOTES = 1;
    private static final Integer UPDATED_REBOTES = 2;
    private static final String DEFAULT_POSICION = "SAMPLE_TEXT";
    private static final String UPDATED_POSICION = "UPDATED_TEXT";

    @Inject
    private JugadorRepository jugadorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restJugadorMockMvc;

    private Jugador jugador;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JugadorResource jugadorResource = new JugadorResource();
        ReflectionTestUtils.setField(jugadorResource, "jugadorRepository", jugadorRepository);
        this.restJugadorMockMvc = MockMvcBuilders.standaloneSetup(jugadorResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jugador = new Jugador();
        jugador.setName(DEFAULT_NAME);
        jugador.setBirthDate(DEFAULT_BIRTH_DATE);
        jugador.setCanastas(DEFAULT_CANASTAS);
        jugador.setAsistencias(DEFAULT_ASISTENCIAS);
        jugador.setRebotes(DEFAULT_REBOTES);
        jugador.setPosicion(DEFAULT_POSICION);
    }

    @Test
    @Transactional
    public void createJugador() throws Exception {
        int databaseSizeBeforeCreate = jugadorRepository.findAll().size();

        // Create the Jugador

        restJugadorMockMvc.perform(post("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isCreated());

        // Validate the Jugador in the database
        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeCreate + 1);
        Jugador testJugador = jugadors.get(jugadors.size() - 1);
        assertThat(testJugador.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJugador.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testJugador.getCanastas()).isEqualTo(DEFAULT_CANASTAS);
        assertThat(testJugador.getAsistencias()).isEqualTo(DEFAULT_ASISTENCIAS);
        assertThat(testJugador.getRebotes()).isEqualTo(DEFAULT_REBOTES);
        assertThat(testJugador.getPosicion()).isEqualTo(DEFAULT_POSICION);
    }

    @Test
    @Transactional
    public void getAllJugadors() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get all the jugadors
        restJugadorMockMvc.perform(get("/api/jugadors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jugador.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
                .andExpect(jsonPath("$.[*].canastas").value(hasItem(DEFAULT_CANASTAS)))
                .andExpect(jsonPath("$.[*].asistencias").value(hasItem(DEFAULT_ASISTENCIAS)))
                .andExpect(jsonPath("$.[*].rebotes").value(hasItem(DEFAULT_REBOTES)))
                .andExpect(jsonPath("$.[*].posicion").value(hasItem(DEFAULT_POSICION.toString())));
    }

    @Test
    @Transactional
    public void getJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", jugador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jugador.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.canastas").value(DEFAULT_CANASTAS))
            .andExpect(jsonPath("$.asistencias").value(DEFAULT_ASISTENCIAS))
            .andExpect(jsonPath("$.rebotes").value(DEFAULT_REBOTES))
            .andExpect(jsonPath("$.posicion").value(DEFAULT_POSICION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJugador() throws Exception {
        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

		int databaseSizeBeforeUpdate = jugadorRepository.findAll().size();

        // Update the jugador
        jugador.setName(UPDATED_NAME);
        jugador.setBirthDate(UPDATED_BIRTH_DATE);
        jugador.setCanastas(UPDATED_CANASTAS);
        jugador.setAsistencias(UPDATED_ASISTENCIAS);
        jugador.setRebotes(UPDATED_REBOTES);
        jugador.setPosicion(UPDATED_POSICION);
        

        restJugadorMockMvc.perform(put("/api/jugadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jugador)))
                .andExpect(status().isOk());

        // Validate the Jugador in the database
        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeUpdate);
        Jugador testJugador = jugadors.get(jugadors.size() - 1);
        assertThat(testJugador.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJugador.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testJugador.getCanastas()).isEqualTo(UPDATED_CANASTAS);
        assertThat(testJugador.getAsistencias()).isEqualTo(UPDATED_ASISTENCIAS);
        assertThat(testJugador.getRebotes()).isEqualTo(UPDATED_REBOTES);
        assertThat(testJugador.getPosicion()).isEqualTo(UPDATED_POSICION);
    }

    @Test
    @Transactional
    public void deleteJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

		int databaseSizeBeforeDelete = jugadorRepository.findAll().size();

        // Get the jugador
        restJugadorMockMvc.perform(delete("/api/jugadors/{id}", jugador.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Jugador> jugadors = jugadorRepository.findAll();
        assertThat(jugadors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
