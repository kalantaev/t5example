package com.example.t5.pages.edit;

import com.example.t5.data.Units;
import com.example.t5.entities.ProductEntity;
import com.example.t5.entities.ProviderEntity;
import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceSorageEntity;
import com.example.t5.pages.BasicPanel;
import com.example.t5.pages.list.SourceInStorageList;
import com.example.t5.util.Helper;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Калантаев Александр on 17.03.2017.
 */
@Import(stylesheet = "context:style/createform.css")
public class EditReciptSource extends BasicPanel {

    @InjectComponent("receiptSource")
    private Form form;

    @InjectPage
    private SourceInStorageList sourceList;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String group;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private Long id;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private Date receiptDate;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private BigDecimal price;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private Double count;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String sourceId;
    @Property
    private Long providerId;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String units;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String note;

    @Property
    private SelectModel sourceModel;
    @Persist
    @Property
    private SelectModel providerModel;
    @Persist
    @Property
    private List<String> groupModel;

    @Property
    private List<String> unitses;

    @Inject
    private Request request;

    @Inject
    private Locale currentLocale;

    @Inject
    private Session session;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<String> errors;

    void onActivate(Long id) {
        Long oldId = this.id;
        System.out.println(oldId + " " + id + " " + !id.equals(oldId));
        this.id = id;
        SourceSorageEntity sse = (SourceSorageEntity) session.get(SourceSorageEntity.class, id);
        group = sse.getSource().getGroup();
        sourceId = sse.getSource().getName();
        ProviderEntity pe = sse.getProvider();
        providerId = pe != null ? !id.equals(oldId) ? pe.getId() : providerId : null;
        receiptDate = receiptDate == null && !id.equals(oldId) ? sse.getDate() : receiptDate;
        count = count == null || !id.equals(oldId) ? sse.getCount() : count;
        price = price == null || !id.equals(oldId) ? sse.getPrice() : price;
        note = note == null || !id.equals(oldId) ? sse.getNote() : note;
        units = units == null || !id.equals(oldId) ? Helper.UnitsToString(sse.getSource().getUnits()) : units;
        List<ProviderEntity> providerList = session.createQuery("from ProviderEntity where deleted != true").list();
        providerModel = new ProviderIdSelectModel(providerList);
        unitses = new ArrayList<String>();
        unitses.add(Helper.UnitsToString(sse.getSource().getUnits()));
        if (sse.getSource().getAltunits() != null) {
            unitses.add(Helper.UnitsToString(sse.getSource().getAltunits()));
        }
    }

    private boolean validate() {
        errors = new ArrayList<String>();

        if (count == null || count == 0) {
            errors.add("Поле \"Количество\" должно быть заполнено и не равнятся нулю.");
        }
        if (price == null || BigDecimal.ZERO.compareTo(price) == 0) {
            errors.add("Поле \"Стоимость\" должно быть заполнено и не равнятся нулю.");
        }
        return !errors.isEmpty();
    }

    Long onPassivate() {
        return id;
    }

    public Format getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.SHORT, currentLocale);
    }

    @CommitAfter
    Object onSuccess() {
        if (validate()) return this;
        SourceSorageEntity sse = (SourceSorageEntity) session.get(SourceSorageEntity.class, id);
        Double oldCount = sse.getCount();
        SourceEntity se = sse.getSource();
        Units un = Helper.StringToUnits(units);
        if (un == se.getAltunits()) {
            count = count / se.getRate();
        }
        if (count >= oldCount) {
            Double oldReside = sse.getResidue();
            oldReside = oldReside == null ? 0 : oldReside;
            sse.setResidue(oldReside + count - oldCount);
        } else {
            Double oldReside = sse.getResidue();
            oldReside = oldReside == null ? 0 : oldReside;
            Double d = oldReside - (oldCount - count);
            if (d >= 0) {
                sse.setResidue(d);
            } else {
                d = Math.abs(d);
                List<SourceSorageEntity> list = session.createQuery("from SourceSorageEntity S where residue > :d " +
                        "and deleted != true and id = (select min(id) from S where residue >= :d ) and source = :source")
                        .setParameter("source", sse.getSource()).setParameter("d", d).list();
                if (!list.isEmpty()) {
                    SourceSorageEntity sseUp = list.get(0);
                    sseUp.setResidue(sseUp.getResidue() - d);
                    session.update(sseUp);
                } else {
                    errors.add("Невозможно обновить данный приход сырья, так как в случае обновления расход сырья превысит приход.");
                    return this;
                }
            }
        }
        sse.setPrice(price);
        sse.setDate(receiptDate);
        if (providerId != null) {
            ProviderEntity pe = (ProviderEntity) session.get(ProviderEntity.class, providerId);
            sse.setProvider(pe);
        }
        sse.setCount(count);
        sse.setNote(note);
        session.update(sse);
        group = null;
        return sourceList;
    }

    @InjectPage
    SourceInStorageList sourceInStorageList;

    @CommitAfter
    Object onSelectedFromDeleted() {
        errors = new ArrayList<String>();
        SourceSorageEntity sse = (SourceSorageEntity) session.get(SourceSorageEntity.class, id);
        try {
            Double allResidue = (Double) session.createQuery("select sum(residue) from SourceSorageEntity " +
                    "where deleted != true and id != :id and source = :source")
                    .setParameter("source", sse.getSource()).setParameter("id", id).list().get(0);
            if((allResidue -(sse.getCount()- sse.getResidue())) >= 0 ){
                List<SourceSorageEntity> list = session.createQuery("from SourceSorageEntity S where residue > 0 " +
                        "and deleted != true and id != :entId and source = :source")
                        .setParameter("source", sse.getSource()).setParameter("entId", sse.getId()).list();
                Double needUpd = sse.getCount()- sse.getResidue();
                for (SourceSorageEntity ent : list){
                   if(ent.getResidue()>=needUpd ){
                       ent.setResidue(ent.getResidue()-needUpd);
                       sse.setResidue(sse.getCount());
                       session.update(ent);
                       break;
                   } else {
                       needUpd = needUpd - ent.getResidue();
                       ent.setResidue(0d);
                       session.update(ent);
                   }
                }
            } else {
                errors.add("Невозможно удалить данный приход, так как в случае удаления расход сырья превысит приход." +
                        " Необходимо добавить новый приход и повторить попытку");
                return this;
            }
        } catch (Exception e){
            System.out.println(e);
            List<SourceSorageEntity> list = session.createQuery("from SourceSorageEntity " +
                    "where deleted != true and source = :source")
                    .setParameter("source", sse.getSource()).list();
            if(list !=null && list.size() == 1 && list.get(0).getCount().equals(list.get(0).getResidue())){
                list.get(0).setDeleted(true);
                session.update(list.get(0));
            } else {
                errors.add("Невозможно удалить данный приход, так как в случае удаления расход сырья превысит приход." +
                        " Необходимо добавить новый приход и повторить попытку");
                return this;
            }
        }
        sse.setDeleted(true);
        session.update(sse);
        return sourceList;
    }

}


