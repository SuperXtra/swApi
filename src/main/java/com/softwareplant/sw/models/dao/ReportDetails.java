package com.softwareplant.sw.models.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "REPORT_DETAIL")
public class ReportDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long filmId;
    private String filmName;
    private Long characterId;
    private String characterName;
    private Long planetId;
    private String planetName;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Reports reports;
}