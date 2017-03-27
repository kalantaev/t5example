package com.example.t5.pages.create;

import com.example.t5.data.Units;
import com.example.t5.entities.ProviderEntity;
import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceSorageEntity;
import com.example.t5.pages.BasicPanel;
import com.example.t5.pages.list.SourceInStorageList;
import com.example.t5.pages.list.SourceList;
import com.example.t5.util.Helper;
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
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.util.AbstractSelectModel;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.util.*;

/**
 * Created by Калантаев Александр on 19.02.2017.
 */
@Import(stylesheet = "context:style/createform.css")
public class ReceiptSource extends BasicPanel {

    @InjectComponent("receiptSource")
    private Form form;

    @InjectPage
    private SourceInStorageList sourceList;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String group;

    @Property
    private Date receiptDate;

    @Property
    private BigDecimal price;

    @Property
    private Double count;

    @Property
    private Long sourceId;
    @Property
    private Long providerId;

    @Property
    private String units;
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

    @InjectComponent
    private Zone sourceModelZone;

    @Inject
    private Session session;


    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<String> errors;

    Object onValueChangedFromGroup(String group) {
        if ("Все категории".equals(group)) {
            sourceModel = new SourceIdSelectModel(session.createQuery("from SourceEntity where deleted != true").list());
        } else {
            group = "Без категории".equals(group) ? null : group;
            sourceModel = new SourceIdSelectModel(session.createQuery("from SourceEntity S where group = :group")
                    .setParameter("group", group).list());
        }
        unitses = new ArrayList<String>();
        return sourceModelZone.getBody();
    }

    Object onValueChangedFromSourceId(Long sourceId) {
        if ("Все категории".equals(group)) {
            sourceModel = new SourceIdSelectModel(session.createQuery("from SourceEntity where deleted != true").list());
        } else {
            group = "Без категории".equals(group) ? null : group;
            sourceModel = new SourceIdSelectModel(session.createQuery("from SourceEntity S where group = :group and deleted != true")
                    .setParameter("group", group).list());
        }
        SourceEntity entity = (SourceEntity) session.get(SourceEntity.class, sourceId);
        unitses = new ArrayList<String>();
        unitses.add(Helper.UnitsToString(entity.getUnits()));
        if (entity.getAltunits() != null) {
            unitses.add(Helper.UnitsToString(entity.getAltunits()));
        } else {
            units = unitses.get(0);
        }
        return sourceModelZone.getBody();
    }

    void onPrepareForRender() {
        List<String> groupSet = session.createQuery("select distinct S.group from SourceEntity S where deleted != true").list();
        List<SourceEntity> sourceList = new ArrayList<SourceEntity>();
        List<ProviderEntity> providerList = session.createQuery("from ProviderEntity where deleted != true").list();
        groupSet.add("Все категории");
        providerModel = new ProviderIdSelectModel(providerList);
        groupModel = new ArrayList<String>(groupSet);
        sourceModel = new SourceIdSelectModel(sourceList);
        unitses = new ArrayList<String>();
    }

    public Format getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.SHORT, currentLocale);
    }

    private boolean validate() {
        errors = new ArrayList<String>();
        if (sourceId == null) {
            errors.add("Поле \"Сырье\" должно быть заполнено.");
        }
        if (count == null || count == 0) {
            errors.add("Поле \"Количество\" должно быть заполнено и не равнятся нулю.");
        }
        if (price == null || BigDecimal.ZERO.compareTo(price) == 0) {
            errors.add("Поле \"Стоимость\" должно быть заполнено и не равнятся нулю.");
        }
        return !errors.isEmpty();
    }

    @CommitAfter
    Object onSuccess() {
        if (validate()) return this;
        SourceSorageEntity sse = new SourceSorageEntity();
        sse.setPrice(price);
        sse.setDate(receiptDate);
        if (providerId != null) {
            ProviderEntity pe = (ProviderEntity) session.get(ProviderEntity.class, providerId);
            sse.setProvider(pe);
        }
        SourceEntity se = (SourceEntity) session.get(SourceEntity.class, sourceId);
        Units un = Helper.StringToUnits(units);
        if (un == se.getAltunits()) {
            count = count * se.getRate();
        }
        sse.setCount(count);
        sse.setSource(se);
        sse.setNote(note);
        sse.setDeleted(false);
        sse.setResidue(count);
        session.save(sse);
        group = null;
        return sourceList;
    }
}
