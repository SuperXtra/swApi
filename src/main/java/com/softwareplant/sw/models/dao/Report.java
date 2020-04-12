package com.softwareplant.sw.models.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "REPORT")
public class Report implements Serializable {

    @Id
    private Long reportId;
    private String queryCriteriaCharacterPhrase;
    private String queryCriteriaPlanetName;

    @OneToMany(
            cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JoinColumn(name = "result_id")
    private List<ReportDetails> results;
}