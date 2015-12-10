package com.polguillen.app.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.polguillen.app.domain.util.CustomDateTimeDeserializer;
import com.polguillen.app.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Partido.
 */
@Entity
@Table(name = "PARTIDO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Partido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "hora_inicio", nullable = false)
    private DateTime horaInicio;

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "hora_final", nullable = false)
    private DateTime horaFinal;

    @Min(value = 0)        
    @Column(name = "puntos_local")
    private Integer puntosLocal;

    @Min(value = 0)        
    @Column(name = "puntos_visitantes")
    private Integer puntosVisitantes;

    @ManyToOne
    private Temporada temporada;

    @ManyToOne
    private Equipo equipoLocal;

    @ManyToOne
    private Equipo equipoVisitante;

    @ManyToOne
    private Arbitro arbitro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(DateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public DateTime getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(DateTime horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Integer getPuntosLocal() {
        return puntosLocal;
    }

    public void setPuntosLocal(Integer puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    public Integer getPuntosVisitantes() {
        return puntosVisitantes;
    }

    public void setPuntosVisitantes(Integer puntosVisitantes) {
        this.puntosVisitantes = puntosVisitantes;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipo) {
        this.equipoLocal = equipo;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipo) {
        this.equipoVisitante = equipo;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Partido partido = (Partido) o;

        if ( ! Objects.equals(id, partido.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Partido{" +
                "id=" + id +
                ", horaInicio='" + horaInicio + "'" +
                ", horaFinal='" + horaFinal + "'" +
                ", puntosLocal='" + puntosLocal + "'" +
                ", puntosVisitantes='" + puntosVisitantes + "'" +
                '}';
    }
}
