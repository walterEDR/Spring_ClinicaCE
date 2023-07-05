package com.ues.edu.clinicace.modelo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="detalle_consulta")
public class DetalleConsulta {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idDetalleConsulta;
    @ManyToOne
    @JoinColumn(name="id_consulta",nullable=false,
            foreignKey=@ForeignKey(name="FK_consulta_detalle"))
    @JsonBackReference
    private Consulta consulta;
    private String diagnostico;
    private String tratamiento;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="consulta_examanen",
            joinColumns = @JoinColumn(name="id_detalle_consulta",referencedColumnName="idDetalleConsulta"),
            inverseJoinColumns = @JoinColumn(name="id_examen",referencedColumnName="idExamen"))
    private List<Examen> examenes;


}
