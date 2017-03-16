package com.example.t5.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Калантаев Александр on 04.03.2017.
 */
@Entity
@Table(name = "product")
public class ProductEntity {

    private Long id;
    private String name;
    private Date deteCreate;
    private Date dateShipment;
    private List<SourceInProductEntity> sourceList;
    private BigDecimal price;
    private BuyerEntity buyerEntity;
    private Boolean template;

    public ProductEntity() {
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "dete_create")
    @Temporal(value=TemporalType.DATE)
    public Date getDeteCreate() {
        return deteCreate;
    }

    public void setDeteCreate(Date deteCreate) {
        this.deteCreate = deteCreate;
    }

    @Basic
    @Column(name = "date_shipment")
    @Temporal(value=TemporalType.DATE)
    public Date getDateShipment() {
        return dateShipment;
    }

    public void setDateShipment(Date dateShipment) {
        this.dateShipment = dateShipment;
    }

    @OneToMany
    public List<SourceInProductEntity> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<SourceInProductEntity> sourceList) {
        this.sourceList = sourceList;
    }

    @Basic
    @Column(name = "PRICE")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public BuyerEntity getBuyerEntity() {
        return buyerEntity;
    }

    public void setBuyerEntity(BuyerEntity buyerEntity) {
        this.buyerEntity = buyerEntity;
    }
    @Basic
    @Column(name = "template")
    public Boolean getTemplate() {
        return template;
    }

    public void setTemplate(Boolean template) {
        this.template = template;
    }
}
