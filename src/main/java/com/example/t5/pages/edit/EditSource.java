package com.example.t5.pages.edit;

import com.example.t5.data.Units;
import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceSorageEntity;
import com.example.t5.pages.list.SourceList;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Калантаев Александр on 20.03.2017.
 */
@Import(stylesheet = "context:style/createform.css")
public class EditSource {
    @InjectComponent("createSource")
    private Form form;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<String> errors;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String name;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String code;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private Units units;

    @Inject
    private Session session;
    @Property
    @Persist
    private boolean newGroup;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private Units alternativUnits;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private double rate;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String group;

    @Property
    @Persist
    private List<String> modelGroup;

    @InjectPage
    private SourceList sourceList;

    @Persist
    @Property
    private Long id;

    public void onActivate(Long id){
        Long oldId = this.id;
        this.id = id;
        SourceEntity sourceEntity = (SourceEntity) session.get(SourceEntity.class, id);
        group = (group != null && id.equals(oldId)) ? group : sourceEntity.getGroup();
        name = name != null  && id.equals(oldId) ? name : sourceEntity.getName();
        code = code != null  && id.equals(oldId) ? code  : sourceEntity.getCode();
        units = units != null && id.equals(oldId) ? units : sourceEntity.getUnits();
        alternativUnits = alternativUnits != null  && id.equals(oldId) ? alternativUnits : sourceEntity.getAltunits();
        rate = rate != 0  && id.equals(oldId) ? rate : sourceEntity.getRate();
        List group = session.createQuery("select distinct S.group from SourceEntity S").list();
        modelGroup = new ArrayList<String>(group);
    }



    private boolean validate(){
        errors = new ArrayList<String>();
        if (isNull(name)){
            errors.add("Поле \"Наименование\" должно быть заполнено");
        }
        if (units == null){
            errors.add("Поле \"Единицы измерения\" должно быть заполнено");
        } if (alternativUnits != null && rate == 0){
            errors.add("При указании альтернативной единицы измерения поле \"Коэффициент перевода\" должно быть заполнено и не равняться нулю. Необходимо или указать коэффициент или не указывать альтернативные еденицы измерения.");
        }
        return !errors.isEmpty();
    }

    private boolean isNull(String value){
        return value == null || value.trim().equals("");
    }

    @CommitAfter
    Object onSuccessFromCreateSource() {
        if(validate()) return this;
        if(isNull(group)){
           group = "Без категории";
        }
        SourceEntity source = (SourceEntity) session.get(SourceEntity.class, id);
        source.setName(name);
        source.setCode(code);
        source.setGroup(group);
        source.setAltunits(alternativUnits);
        source.setRate(rate);
        source.setUnits(units);
        source.setDeleted(false);
        session.update(source);
        return sourceList;
    }

    @CommitAfter
    Object onSelectedFromDeleted() {
        SourceEntity sse = (SourceEntity) session.get(SourceEntity.class, id);
        sse.setDeleted(true);
        session.update(sse);
        return sourceList;
    }

    Long onPassivate(){
        return id;
    }
}