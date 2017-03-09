package com.example.t5.pages.create;

import com.example.t5.data.Units;
import com.example.t5.entities.*;
import com.example.t5.pages.BasicPanel;
import com.example.t5.pages.product.ProductList;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.AbstractSelectModel;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Калантаев Александр on 04.03.2017.
 */
public class CreateProduct extends BasicPanel {

    @InjectComponent("createProduct")
    private Form form;
    @Property
    private String name;
    @Property
    private Date dateCreate;
    @Property
    private Date dateShipment;
    @Inject
    private Session session;
    @InjectPage
    private ProductList productList;
    @Inject
    private Locale currentLocale;
    @Property
    private int index;
    @Property
    @Persist
    private SelectModel modelGroup;
    @Persist
    @Property
    private List<Long> listTest;
    @Property
    private Long test;
    @Property
    private BigDecimal priceSource;
    @InjectComponent
    private Zone zoneChange;
    @Property
    @Persist
    private List<Double> countList;
    @Persist
    @Property
    private SelectModel buyerModel;
    @Property
    private Long buyerId;

    public Object onValueChanged(Long id, int index) {
        listTest.set(index, id);
        return zoneChange.getBody();
    }

    public String getUnitsModel() {
        Long id = listTest.get(index);
        for (SourceEntity sourceEntity : sources) {
            if (sourceEntity.getId().equals(id)) {
                Units units = sourceEntity.getUnits();
                return getStringFromUnits(units);
            }
        }
        return "";
    }

    @Persist(PersistenceConstants.SESSION)
    List<SourceEntity> sources;

    @Persist
    @Property
    private Units sourceUnits;

    void onActivate(Long id) {
        if (id != null) {
            ProductEntity entity = (ProductEntity) session.get(ProductEntity.class, id);
            name = entity.getName();
            List<SourceInProductEntity> list = entity.getSourceList();
            listTest = new ArrayList<Long>();
            countList = new ArrayList<Double>();
            for (SourceInProductEntity source : list) {
                listTest.add(source.getSource().getId());
                countList.add(source.getCount());
            }
        }
        if (buyerModel == null) {
            List<BuyerEntity> buyerList = session.createCriteria(BuyerEntity.class).list();
            buyerModel = new BuyerIdSelectModel(buyerList);
        }
        if (listTest == null) {
            listTest = new ArrayList<Long>();
            countList = new ArrayList<Double>();
            listTest.add(null);
            countList.add(0.0);
        }
        if (modelGroup == null) {
            sources = session.createCriteria(SourceEntity.class).list();
            modelGroup = new SourceIdSelectModel(sources);
        }
    }

    public Long getValue() {
        return listTest.get(index);
    }

    public Double getCountValue() {
        return countList.get(index);
    }

    public void setValue(Long id) {

    }

    public void setCountValue(Double countValue) {
        if (countList.size() > index) {
            countList.set(index, countValue);
        }
    }

    public Format getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.SHORT, currentLocale);
    }

    @OnEvent(value = "remove")
    void onEventRemuve(int index) {

    }

    public int getIndexForRender() {
        return index + 1;
    }

    Object onSelectedFromAdd() {
        listTest.add(null);
        countList.add(0.0);
        return this;
    }

    Object onSelectedFromRemove(int index) {
        listTest.remove(index);
        countList.remove(index);
        return this;
    }

    @CommitAfter
    Object onSuccessFromCreateProduct() {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(name);
        productEntity.setDeteCreate(dateCreate);
        productEntity.setDateShipment(dateShipment);
        productEntity.setPrice(null);

        if (buyerId != null) {
            BuyerEntity be = (BuyerEntity) session.get(BuyerEntity.class, buyerId);
            productEntity.setBuyerEntity(be);
        }
        List<SourceInProductEntity> sourseList = new ArrayList<SourceInProductEntity>();
        for (int i = 0; i < listTest.size(); i++) {
            SourceInProductEntity sourceInProductEntity = new SourceInProductEntity();
          SourceEntity entity =(SourceEntity) session.get(SourceEntity.class, listTest.get(i));
            sourceInProductEntity.setSource(entity);
            sourceInProductEntity.setCount(countList.get(i));
            sourceInProductEntity.setUnits(entity.getUnits());
            sourceInProductEntity.setPrice(new BigDecimal(100));
            session.save(sourceInProductEntity);
            sourseList.add(sourceInProductEntity);
        }
        productEntity.setSourceList(sourseList);
        session.save(productEntity);
        return productList;
    }

    @CommitAfter
    Object onSelectedFromSaveAsTemplate() {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setName(name);
        productEntity.setDeteCreate(null);
        productEntity.setDateShipment(null);
        productEntity.setPrice(null);
        if (buyerId != null) {
            BuyerEntity be = (BuyerEntity) session.get(BuyerEntity.class, buyerId);
            productEntity.setBuyerEntity(be);
        }
        List<SourceInProductEntity> sourseList = new ArrayList<SourceInProductEntity>();
        for (int i = 0; i < listTest.size(); i++) {
            SourceInProductEntity sourceInProductEntity = new SourceInProductEntity();
            SourceEntity entity =(SourceEntity) session.get(SourceEntity.class, listTest.get(i));
            sourceInProductEntity.setSource(entity);
            sourceInProductEntity.setCount(countList.get(i));
            sourceInProductEntity.setUnits(entity.getUnits());
            sourceInProductEntity.setPrice(new BigDecimal(100));
            session.save(sourceInProductEntity);
            sourseList.add(sourceInProductEntity);
        }
        productEntity.setSourceList(sourseList);
        session.save(productEntity);
        return productList;
    }
}
