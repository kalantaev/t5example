package com.example.t5.pages.create;

import com.example.t5.data.Units;
import com.example.t5.entities.*;
import com.example.t5.excel.importexcel.ExcelWorker;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Калантаев Александр on 04.03.2017.
 */

@Import(library = {"context:js/price.js"},
        stylesheet = "context:style/createform.css")
public class CreateProduct extends BasicPanel {

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
    @Persist(PersistenceConstants.FLASH)
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
    private Long idForXls;
    @Persist
    @Property
    private String note;
    @Persist
    private boolean isOld;
    private Long id;
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
        if (id == -1L) {
            setCreatedMode();
            name = null;
            buyerId = null;
            dateCreate = null;
            dateCreate = null;
            List<BuyerEntity> buyerList = session.createCriteria(BuyerEntity.class).list();
            buyerModel = new BuyerIdSelectModel(buyerList);
            listTest = new ArrayList<Long>();
            countList = new ArrayList<Double>();
            priceList = new ArrayList<BigDecimal>();
            priceSourceList = new ArrayList<BigDecimal>();
            priceList.add(BigDecimal.ZERO);
            listTest.add(null);
            countList.add(0.0);
            priceSourceList.add(BigDecimal.ZERO);
            sources = session.createQuery("from SourceEntity where deleted != true").list();
            modelGroup = new SourceIdSelectModel(sources);
            this.id = null;
        }
    }

    void setupRender() {
        if (id != null) {
            idForXls = id;
            List<BuyerEntity> buyerList = session.createCriteria(BuyerEntity.class).list();
            buyerModel = new BuyerIdSelectModel(buyerList);
            sources = session.createCriteria(SourceEntity.class).list();
            modelGroup = new SourceIdSelectModel(sources);
            ProductEntity entity = (ProductEntity) session.get(ProductEntity.class, id);
            buyer = entity.getBuyerEntity().getNameOrganization();
            if (entity.getTemplate()) {
                setTemplateMode();
            } else {
                if (entity.getDateShipment() != null) {
                    setShowMode();
                } else {
                    setShipmentMode();
                }
            }
            if (isShowMode() || isSipmentMode()) {
                stringNameSource = new ArrayList<String>();
                for (SourceInProductEntity inProductEntity : entity.getSourceList()) {
                    stringNameSource.add(inProductEntity.getSource().getName());
                }
            } else {
                stringNameSource = null;
            }
            name = entity.getName();
            note = entity.getNote();
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
                listTest.add(source.getSource().getId());
                countList.add(source.getCount());
                priceSourceList.add(source.getPrice());
                priceList.add(BigDecimal.ZERO);
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
                        errors.add("Невозможно списать данную продукцию. Недостаточно сырья \""
                                + ((SourceEntity) session.get(SourceEntity.class, listTest.get(i))).getName() + "\" на складе.");
                    }
                } catch (Exception e) {
                    errors.add("Невозможно списать данную продукцию. Недостаточно сырья \""
                            + ((SourceEntity) session.get(SourceEntity.class, listTest.get(i))).getName() + "\" на складе.");
                }
            }
        }
        return errors.isEmpty();
    }

    @CommitAfter
    Object onSuccessFromCreateProduct() {
        if (!validateAndSave(false)) return this;
        ProductEntity productEntity = createEntity(false);
        session.save(productEntity);
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


    Object onSelectedFromEditSH() {
        setCreatedMode();
        return this;
    }

    Object onSelectedFromShipment() {
        setShipmentMode();
        return this;
    }


    void onXls() {
        System.out.println(idForXls);
        Setting setting = (Setting) session.get(Setting.class, 1L);

        String path = setting != null ? setting.getValue().endsWith("\\") ? setting.getValue() : (setting.getValue() + "\\") : "C:\\";
        ExcelWorker.createXLS((ProductEntity) session.get(ProductEntity.class, idForXls), path);

    }

    private ProductEntity createEntity(Boolean template) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(name);
        productEntity.setDeleted(false);
        productEntity.setTemplate(template);
        if (dateCreate == null && !template) {
            dateCreate = new Date();
        }
        productEntity.setDeteCreate(dateCreate);
        productEntity.setDateShipment(dateShipment);
        productEntity.setNote(note);
        productEntity.setPrice(productPrice);
        if (buyerId != null) {
            BuyerEntity be = (BuyerEntity) session.get(BuyerEntity.class, buyerId);
            productEntity.setBuyerEntity(be);
        }
        if (!template) {
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
        }
        List<SourceInProductEntity> sourseList = new ArrayList<SourceInProductEntity>();
        for (int i = 0; i < listTest.size(); i++) {
            if (listTest.get(i) != null) {
                SourceInProductEntity sourceInProductEntity = new SourceInProductEntity();
                SourceEntity entity = (SourceEntity) session.get(SourceEntity.class, listTest.get(i));
                sourceInProductEntity.setSource(entity);
                sourceInProductEntity.setCount(countList.get(i));
                sourceInProductEntity.setUnits(entity.getUnits());
                sourceInProductEntity.setPrice(priceSourceList.get(i));
                sourceInProductEntity.setTemplates(template);
                sourceInProductEntity.setDeleted(false);
                session.save(sourceInProductEntity);
                sourseList.add(sourceInProductEntity);
            }
        }
        productEntity.setSourceList(sourseList);
        return productEntity;
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
        dateCreate = null;
        dateShipment = null;
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

    public String getStringDateShipment() {
        return getStringFromData(dateShipment);
    }

    public void setStringDateShipment(String s) {
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
