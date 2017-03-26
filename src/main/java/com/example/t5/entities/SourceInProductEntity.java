package com.example.t5.entities;

import com.example.t5.data.Units;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Калантаев Александр on 08.03.2017.
 */
@Entity
@Table(name = "source_in_product")
public class SourceInProductEntity {
    private Long id;
    private SourceEntity source;
    private Double count;
    private BigDecimal price;
    private Units units;
    private Boolean templates;
    private Boolean deleted;
    @Basic
    @Column(name = "DELETED")
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public SourceEntity getSource() {
        return source;
    }

    public void setSource(SourceEntity source) {
        this.source = source;
    }

    @Basic
    @Column(name = "COUNT")
    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    @Basic
    @Column(name = "PRICE")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "UNITS")
    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    @Basic
    @Column(name="TEMPLATE")
    public Boolean getTemplates() {
        return templates;
    }

    public void setTemplates(Boolean templates) {
        this.templates = templates;
    }
}
