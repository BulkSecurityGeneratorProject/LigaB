package com.polguillen.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.polguillen.app.domain.Estadisticas;
import com.polguillen.app.repository.EstadisticasRepository;
import com.polguillen.app.web.rest.util.HeaderUtil;
import com.polguillen.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Estadisticas.
 */
@RestController
@RequestMapping("/api")
public class EstadisticasResource {

    private final Logger log = LoggerFactory.getLogger(EstadisticasResource.class);

    @Inject
    private EstadisticasRepository estadisticasRepository;

    /**
     * POST  /estadisticass -> Create a new estadisticas.
     */
    @RequestMapping(value = "/estadisticass",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Estadisticas> create(@Valid @RequestBody Estadisticas estadisticas) throws URISyntaxException {
        log.debug("REST request to save Estadisticas : {}", estadisticas);
        if (estadisticas.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new estadisticas cannot already have an ID").body(null);
        }
        Estadisticas result = estadisticasRepository.save(estadisticas);
        return ResponseEntity.created(new URI("/api/estadisticass/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("estadisticas", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /estadisticass -> Updates an existing estadisticas.
     */
    @RequestMapping(value = "/estadisticass",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Estadisticas> update(@Valid @RequestBody Estadisticas estadisticas) throws URISyntaxException {
        log.debug("REST request to update Estadisticas : {}", estadisticas);
        if (estadisticas.getId() == null) {
            return create(estadisticas);
        }
        Estadisticas result = estadisticasRepository.save(estadisticas);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("estadisticas", estadisticas.getId().toString()))
                .body(result);
    }

    /**
     * GET  /estadisticass -> get all the estadisticass.
     */
    @RequestMapping(value = "/estadisticass",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Estadisticas>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Estadisticas> page = estadisticasRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estadisticass", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /estadisticass/:id -> get the "id" estadisticas.
     */
    @RequestMapping(value = "/estadisticass/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Estadisticas> get(@PathVariable Long id) {
        log.debug("REST request to get Estadisticas : {}", id);
        return Optional.ofNullable(estadisticasRepository.findOne(id))
            .map(estadisticas -> new ResponseEntity<>(
                estadisticas,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /estadisticass/:id -> delete the "id" estadisticas.
     */
    @RequestMapping(value = "/estadisticass/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Estadisticas : {}", id);
        estadisticasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("estadisticas", id.toString())).build();
    }
}
