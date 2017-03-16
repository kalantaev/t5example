package com.example.t5.pages.create;

import com.example.t5.data.Units;
import com.example.t5.entities.ProviderEntity;
import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceSorageEntity;
import com.example.t5.pages.BasicPanel;
import com.example.t5.pages.list.SourceList;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
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
public class ReceiptSource extends BasicPanel {

    @InjectComponent("receiptSource")
    private Form form;

    @InjectPage
    private SourceList sourceList;

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
    private Units units;

    @Property
    private SelectModel sourceModel;
    @Persist
    @Property
    private SelectModel providerModel;

    @Property
    private List<String> groupModel;

    @Inject
    private Request request;

    @Inject
    private Locale currentLocale;

    @InjectComponent
    private Zone sourceModelZone;

    @Inject
    private Session session;

    Object onValueChangedFromGroup(String group) {

        List<SourceEntity> sourceList = session.createCriteria(SourceEntity.class).list();
        List<SourceEntity> filtratedSourse = new ArrayList<SourceEntity>();
        if ("Все категории".equals(group)) {
            sourceModel = new SourceIdSelectModel(sourceList);
        } else {
            for (SourceEntity source : sourceList) {
                if (group.equals(source.getGroup())) {
                    filtratedSourse.add(source);
                }
                if (group.equals("Без категории") && source.getGroup() == null) {
                    filtratedSourse.add(source);
                }
            }
            sourceModel = new SourceIdSelectModel(filtratedSourse);
        }

        return sourceModelZone.getBody();

    }

    public boolean showSource() {
        return !(group == null);
    }

    void onPrepareForRender() {
        List<String> groupSet = session.createQuery("select distinct S.group from SourceEntity S").list();
        List<SourceEntity> sourceList = session.createCriteria(SourceEntity.class).list();
        List<ProviderEntity> providerList = session.createCriteria(ProviderEntity.class).list();
       groupSet.add("Все категории");
       groupSet.add("Без категории");

        providerModel = new ProviderIdSelectModel(providerList);
        groupModel = new ArrayList<String>(groupSet);
        sourceModel = new SourceIdSelectModel(sourceList);
    }

    public Format getDateFormat() {
        return DateFormat.getDateInstance(DateFormat.SHORT, currentLocale);
    }

    @CommitAfter
    Object onSuccess() {
        SourceSorageEntity sse = new SourceSorageEntity();
        sse.setCount(count);
        sse.setPrice(price);
        sse.setDate(receiptDate);
        if (providerId != null) {
            ProviderEntity pe = (ProviderEntity) session.get(ProviderEntity.class, providerId);
            sse.setProvider(pe);
        }
        SourceEntity se = (SourceEntity) session.get(SourceEntity.class, sourceId);
        sse.setSource(se);
        session.save(sse);

        return sourceList;
    }
}
