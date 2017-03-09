package com.example.t5.entities;

import com.example.t5.data.Units;
import org.apache.tapestry5.ioc.annotations.Inject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Калантаев Александр on 27.02.2017.
 */
@Entity
@Table(name = "source")
public class SourceEntity implements Serializable{
    private Long id;
    private String name;
    private String code;
    private String group;
    private Double rate;
    private Units units;
    private Units altunits;

    @Inject
    public SourceEntity () {

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

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "GROUPS")
    public String getGroup() {
        return group;
    }

    public void setGroup(String groups) {
        this.group = groups;
    }

    @Basic
    @Column(name = "RATE")
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
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
    @Enumerated(EnumType.STRING)

    public Units getAltunits() {
        return altunits;
    }

    public void setAltunits(Units altunits) {
        this.altunits = altunits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SourceEntity that = (SourceEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (units != null ? !units.equals(that.units) : that.units != null) return false;
        if (altunits != null ? !altunits.equals(that.altunits) : that.altunits != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? Integer.parseInt(id.toString()) : 25;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (units != null ? units.hashCode() : 0);
        result = 31 * result + (altunits != null ? altunits.hashCode() : 0);
        return result;
    }
}
