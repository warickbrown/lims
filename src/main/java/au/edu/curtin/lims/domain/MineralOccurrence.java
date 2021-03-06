/*
 * Copyright Curtin University, 2015.
 */
// DO NOT MODIFY THIS FILE BY HAND. IT WAS GENERATED BY generate_orm_files.py
package au.edu.curtin.lims.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "mineral_occurrence")
@SequenceGenerator(name = "mineral_occurrence_mineral_occurrence_id_seq", sequenceName = "mineral_occurrence_mineral_occurrence_id_seq", allocationSize = 1)
public class MineralOccurrence implements Comparable<MineralOccurrence> {
    private int mineralOccurrenceId;

    private MindifField mindifField;

    private Mineral mineral;

    private int mineralPixelCount;

    public MineralOccurrence() { }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mineral_occurrence_mineral_occurrence_id_seq")
    @Column(name = "mineral_occurrence_id", nullable = false)
    public Integer getMineralOccurrenceId() {
        return this.mineralOccurrenceId;
    }
    
    @SuppressWarnings("unused")
    private void setMineralOccurrenceId(Integer mineralOccurrenceId) {
        this.mineralOccurrenceId = mineralOccurrenceId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mindif_field_id")
    public MindifField getMindifField() {
        return mindifField;
    }
    
    public void setMindifField(MindifField mindifField) {
        this.mindifField = mindifField;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mineral_id")
    public Mineral getMineral() {
        return mineral;
    }
    
    public void setMineral(Mineral mineral) {
        this.mineral = mineral;
    }

    @Column(name = "mineral_pixel_count", nullable = false)
    public int getMineralPixelCount() {
        return this.mineralPixelCount;
    }
    
    public void setMineralPixelCount(int mineralPixelCount) {
        this.mineralPixelCount = mineralPixelCount;
    }

    @Override
    public int compareTo(MineralOccurrence o) {
        return this.getMineralOccurrenceId().compareTo(o.getMineralOccurrenceId());
    }
}
