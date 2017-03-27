package com.example.t5.pages.edit;

import com.example.t5.data.Units;
import com.example.t5.entities.*;
import com.example.t5.pages.BasicPanel;
import com.example.t5.pages.product.ProductList;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Калантаев Александр on 20.03.2017.
 */
@Import(library = {"context:js/price.js"},
        stylesheet = "context:style/createform.css")
public class EditProduct extends BasicPanel {

    @InjectComponent("createProduct")
    private Form form;
    //поле формы
    @Property
    @Persist
    private String name;
    //поле формы
    @Property
    @Persist
    private Date dateCreate;
    //поле формы
    @Property
    @Persist
    private Date dateShipment;
    //поле формы
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
    @Persist
    private List<BigDecimal> priceSourceList;
    @Property
    private BigDecimal priceSource;
    @InjectComponent
    private Zone zoneChange;
    @Property
    @Persist
    private List<Double> countList;
    @Property
    @Persist
    private List<BigDecimal> priceList;
    @Property
    private BigDecimal price;
    @Persist
    @Property
    private SelectModel buyerModel;
    @Persist
    @Property
    private Long buyerId;
    @Property
    private BigDecimal productPrice;
    @Persist
    @Property
    private List<String> errors;
    @Property
    private String error;
    @Persist
    List<SourceEntity> sources;
    @Persist
    @Property
    private Units sourceUnits;
    @Persist
    @Property
    private String buyer;
    @Persist
    private boolean isOld;

    @Property
    private Long id;
    @Persist
    @Property
    private String note;

    @Persist
    private List<String> stringNameSource;

    public Object onValueChanged(Long id, int index) {
        listTest.set(index, id);
        List prise = session.createQuery("select price from SourceSorageEntity S where source.id = :entId and " +
                "id = (select max(S.id) from S where source.id = :entId)")
                .setParameter("entId", id).list();
        List count = session.createQuery("select S.count from SourceSorageEntity S where source.id = :entId and " +
                "id = (select max(S.id) from S where source.id = :entId)")
                .setParameter("entId", id).list();
        if (prise.isEmpty() || count.isEmpty()) {
            priceList.set(index, BigDecimal.ZERO);
        } else {
            priceList.set(index, ((BigDecimal) prise.get(0)).divide(new BigDecimal((Double) count.get(0)), 2, RoundingMode.HALF_UP));
        }
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

    void onActivate(Long id) {
        this.id = id;
        idFofShipment = id;

    }

    @Persist
    private Long idFofShipment;

    void setupRender() {
        if (id != null) {
            List<BuyerEntity> buyerList = session.createCriteria(BuyerEntity.class).list();
            buyerModel = new BasicPanel.BuyerIdSelectModel(buyerList);
            sources = session.createCriteria(SourceEntity.class).list();
            modelGroup = new BasicPanel.SourceIdSelectModel(sources);
            ProductEntity entity = (ProductEntity) session.get(ProductEntity.class, id);
            BuyerEntity be = entity.getBuyerEntity();
            buyer = be == null ? "" : be.getNameOrganization();
            if (entity.getTemplate()) {
                setTemplateMode();
            } else {
                if (entity.getDateShipment() != null || (new Date(1, 1, 1)).equals(dateShipment)) {
                    setCreatedMode();
                    if ((new Date(1, 1, 1)).equals(dateShipment)){
                        dateShipment = null;
                    }
                } else {
                    setShipmentMode();
                }
            }
            if (isSipmentMode()) {
                stringNameSource = new ArrayList<String>();
                for (SourceInProductEntity inProductEntity : entity.getSourceList()) {
                    stringNameSource.add(inProductEntity.getSource().getName());
                }
            } else {
                stringNameSource = null;
            }
            note = entity.getNote();
            name = entity.getName();
            productPrice = entity.getPrice();
            buyerId = entity.getBuyerEntity() != null ? entity.getBuyerEntity().getId() : null;
            dateCreate = entity.getDeteCreate();
            dateShipment = entity.getDateShipment();
            List<SourceInProductEntity> list = entity.getSourceList();
            listTest = new ArrayList<Long>();
            countList = new ArrayList<Double>();
            priceSourceList = new ArrayList<BigDecimal>();
            priceList = new ArrayList<BigDecimal>();
            for (SourceInProductEntity source : list) {
                List prise = session.createQuery("select price from SourceSorageEntity S where source.id = :entId and " +
                        "id = (select max(S.id) from S where source.id = :entId)")
                        .setParameter("entId", source.getSource().getId()).list();
                List count = session.createQuery("select S.count from SourceSorageEntity S where source.id = :entId and " +
                        "id = (select max(S.id) from S where source.id = :entId)")
                        .setParameter("entId", source.getSource().getId()).list();
                listTest.add(source.getSource().getId());
                countList.add(source.getCount());
                priceSourceList.add(source.getPrice());
                if(prise.isEmpty() || count.isEmpty()){
                    priceList.add(BigDecimal.ZERO);
                } else {
                    priceList.add(((BigDecimal) prise.get(0)).divide(new BigDecimal((Double) count.get(0)), 2, RoundingMode.HALF_UP));
                }
            }
        }
    }

    public boolean isReadOnly() {
        return isShowMode();
    }

    public Long getValue() {
        return listTest.get(index);
    }

    public BigDecimal getPriceSourceValue() {
        return priceSourceList.get(index);
    }

    public void setPriceSourceValue(BigDecimal value) {
        priceSourceList.set(index, value);
    }

    public BigDecimal getPriceFromList() {
        return priceList.get(index);
    }

    public void setPriceFromList(BigDecimal value) {
        priceList.set(index, value);
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

    @OnEvent(value = "remove")
    void onEventRemuve(int index) {

    }

    public int getIndexForRender() {
        return index + 1;
    }

    Object onSelectedFromAdd() {
        listTest.add(null);
        countList.add(0.0);
        priceList.add(BigDecimal.ZERO);
        priceSourceList.add(BigDecimal.ZERO);
        return this;
    }

    Object onSelectedFromRemove(int index) {
        listTest.remove(index);
        countList.remove(index);
        priceList.remove(index);
        priceSourceList.remove(index);
        return this;
    }

    @AfterRender
    private void clinError() {
        if (errors != null) {
            errors.clear();
        }
    }

    private boolean validateAndSave(boolean template) {
        errors = new ArrayList<String>();
        if (name == null) {
            errors.add("Поле наименование должно быть заполнено");
        }
        if (listTest.isEmpty()) {
            errors.add("Не выбрано ни одно сырье");
        }
        if (!template) {
            for (int i = 0; i < listTest.size(); i++) {
                try {
                    Double allResidue = (Double) session.createQuery("select sum(residue) from SourceSorageEntity " +
                            "where deleted != true and source.id = :source").setParameter("source", listTest.get(i)).list().get(0);
                    if (allResidue < countList.get(i)) {
                        errors.add("Невозможно обновить данную продукцию. Недостаточно сырья \""
                                + ((SourceEntity) session.get(SourceEntity.class, listTest.get(i))).getName() + "\" на складе.");
                    }
                } catch (Exception e) {
                    errors.add("Невозможно обновить данную продукцию. Недостаточно сырья \""
                            + ((SourceEntity) session.get(SourceEntity.class, listTest.get(i))).getName() + "\" на складе.");
                }
            }
        }
        return errors.isEmpty();
    }

    public Format getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.SHORT, currentLocale);
    }

    @CommitAfter
    Object onSuccessFromCreateProduct() {
        if (!validateAndSave(false)) return this;
        if(dateShipment == null) {
            errors = new ArrayList<String>();
            errors.add("Данная продукция уже отгружена, по этому дата отгрузки обязательно должна быть указана");
            return this;
        }
        ProductEntity productEntity = (ProductEntity) session.get(ProductEntity.class, idFofShipment);
        updateEntity(productEntity);
        session.update(productEntity);
        clearPanel();
        return productList;
    }

    @CommitAfter
    Object onSelectedFromSaveAsTemplate() {
        if (!validateAndSave(true)) return this;
        ProductEntity productEntity = createEntity(true);
        session.save(productEntity);
        clearPanel();
        return productList;
    }

    Object onSelectedFromEdit() {
        setCreatedMode();
        return this;
    }

    Object onSelectedFromEditSH() {
        setCreatedMode();
        dateShipment = new Date(1,1,1);
        System.out.println(dateShipment);
        return this;
    }

    @InjectPage
    ProductList productListPage;

    @CommitAfter
    Object onSelectedFromShipment() {
        ProductEntity pe = (ProductEntity) session.get(ProductEntity.class, idFofShipment);
        if (dateShipment == null) dateShipment = new Date();
        pe.setDateShipment(dateShipment);
        session.update(pe);
        return productListPage;
    }

    private ProductEntity createEntity(Boolean template) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(name);
        productEntity.setNote(note);
        productEntity.setDeleted(false);
        productEntity.setTemplate(template);
        productEntity.setDeteCreate(dateCreate);
        productEntity.setDateShipment(dateShipment);
        for (BigDecimal p : priceSourceList) {
            productPrice = productPrice.add(p);
        }
        productEntity.setPrice(productPrice);
        if (buyerId != null) {
            BuyerEntity be = (BuyerEntity) session.get(BuyerEntity.class, buyerId);
            productEntity.setBuyerEntity(be);
        }
        List<SourceInProductEntity> sourseList = new ArrayList<SourceInProductEntity>();
        for (int i = 0; i < listTest.size(); i++) {
            SourceInProductEntity sourceInProductEntity = new SourceInProductEntity();
            SourceEntity entity = (SourceEntity) session.get(SourceEntity.class, listTest.get(i));
            sourceInProductEntity.setSource(entity);
            sourceInProductEntity.setCount(countList.get(i));
            sourceInProductEntity.setUnits(entity.getUnits());
            sourceInProductEntity.setPrice(priceSourceList.get(i));
            sourceInProductEntity.setTemplates(template);
            session.save(sourceInProductEntity);
            sourseList.add(sourceInProductEntity);
        }
        productEntity.setSourceList(sourseList);
        return productEntity;
    }

    private void updateEntity(ProductEntity productEntity) {
        productEntity.setName(name);
        productEntity.setNote(note);
        productEntity.setDeleted(false);
        productEntity.setDeteCreate(dateCreate);
        productEntity.setDateShipment(dateShipment);
        //устанавливаем общую сумму
        productEntity.setPrice(productPrice);
        if (buyerId != null) {
            BuyerEntity be = (BuyerEntity) session.get(BuyerEntity.class, buyerId);
            productEntity.setBuyerEntity(be);
        }
        for (SourceInProductEntity sipe : productEntity.getSourceList()){
            List<SourceSorageEntity> list = session.createQuery("from SourceSorageEntity S where deleted != true and" +
                    " S.count != S.residue and source = :source").setParameter("source", sipe.getSource()).list();
            Double count = sipe.getCount();
            for (int i = list.size()-1; i>= 0; i--){
                SourceSorageEntity sse = list.get(i);
                Double difference = sse.getCount()-sse.getResidue();
                if(count<=difference){
                    sse.setResidue(sse.getResidue() + count);
                    session.update(sse);
                    break;
                } else {
                    count = count - difference;
                    sse.setResidue(sse.getCount());
                    session.update(sse);
                }
            }
            sipe.setDeleted(true);
            session.update(sipe);
        }

            for (int i = 0; i < listTest.size(); i++) {
                List<SourceSorageEntity> list = session.createQuery("from SourceSorageEntity S where residue > 0 " +
                        "and deleted != true and source.id = :entId").setParameter("entId", listTest.get(i)).list();
                Double needUpd = countList.get(i);
                for (SourceSorageEntity ent : list) {
                    if (ent.getResidue() >= needUpd) {
                        ent.setResidue(ent.getResidue() - needUpd);
                        session.update(ent);
                        break;
                    } else {
                        needUpd = needUpd - ent.getResidue();
                        ent.setResidue(0d);
                        session.update(ent);
                    }
                }
            }

        List<SourceInProductEntity> sourseList = new ArrayList<SourceInProductEntity>();
        for (int i = 0; i < listTest.size(); i++) {
            SourceInProductEntity sourceInProductEntity = new SourceInProductEntity();
            SourceEntity entity = (SourceEntity) session.get(SourceEntity.class, listTest.get(i));
            sourceInProductEntity.setSource(entity);
            sourceInProductEntity.setTemplates(false);
            sourceInProductEntity.setDeleted(false);
            sourceInProductEntity.setCount(countList.get(i));
            sourceInProductEntity.setUnits(entity.getUnits());
            sourceInProductEntity.setPrice(priceSourceList.get(i));
            session.save(sourceInProductEntity);
            sourseList.add(sourceInProductEntity);
        }
        productEntity.setSourceList(sourseList);

    }

    void clearPanel() {
        buyerModel = null;
        listTest = null;
        countList = null;
        sources = null;
        modelGroup = null;
        errors = null;
        priceList = null;
        priceSourceList = null;
        note = null;
    }

    public boolean hasError() {
        return errors != null && !errors.isEmpty();
    }

    public String getDateCreateinStr() {
        return getStringFromData(dateCreate);
    }

    public void setDateCreateinStr(String str) {
    }

    public String getStringDShipment() {
        return getStringFromData(dateShipment);
    }

    public void setStringDShipment(String s) {

    }

    public String getStringNameSource() {
        return stringNameSource.get(index);
    }

    public void setStringNameSource(String s) {
    }

    public boolean isShow() {
        return isShowMode() || isSipmentMode();
    }

}
