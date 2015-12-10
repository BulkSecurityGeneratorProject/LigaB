package com.polguillen.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Estadisticas.
 */
@Entity
@Table(name = "ESTADISTICAS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Estadisticas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @Min(value = 0)        
    @Column(name = "canastas")
    private Integer canastas;

    @Min(value = 0)        
    @Column(name = "faltas")
    private Integer faltas;

    @Min(value = 0)        
    @Column(name = "asistencias")
    private Integer asistencias;

    @Min(value = 0)        
    @Column(name = "rebotes")
    private Integer rebotes;

    @ManyToOne
    private Jugador jugadorEstadisticas;

    @ManyToOne
    private Partido partidoEstadisticas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCanastas() {
        return canastas;
    }

    public void setCanastas(Integer canastas) {
        this.canastas = canastas;
    }

    public Integer getFaltas() {
        return faltas;
    }

    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }

    public Integer getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(Integer asistencias) {
        this.asistencias = asistencias;
    }

    public Integer getRebotes() {
        return rebotes;
    }

    public void setRebotes(Integer rebotes) {
        this.rebotes = rebotes;
    }

    public Jugador getJugadorEstadisticas() {
        return jugadorEstadisticas;
    }

    public void setJugadorEstadisticas(Jugador jugador) {
        this.jugadorEstadisticas = jugador;
    }

    public Partido getPartidoEstadisticas() {
        return partidoEstadisticas;
    }

    public void setPartidoEstadisticas(Partido partido) {
        this.partidoEstadisticas = partido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Estadisticas estadisticas = (Estadisticas) o;

        if ( ! Objects.equals(id, estadisticas.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Estadisticas{" +
                "id=" + id +
                ", canastas='" + canastas + "'" +
                ", faltas='" + faltas + "'" +
                ", asistencias='" + asistencias + "'" +
                ", rebotes='" + rebotes + "'" +
                '}';
    }
}
