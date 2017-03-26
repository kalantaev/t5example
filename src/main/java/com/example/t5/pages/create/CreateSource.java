package com.example.t5.pages.create;

import com.example.t5.data.Units;
import com.example.t5.entities.SourceEntity;
import com.example.t5.pages.list.SourceList;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Калантаев Александр on 16.02.2017.
 */
@Import(stylesheet = "context:style/createform.css")
public class CreateSource {

    @InjectComponent("createSource")
    private Form form;
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

    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<String> errors;

    @InjectPage
    private SourceList sourceList;


    void onPrepareForRender() {
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
            errors.add("При указании альтернативной единицы измерения поле \"Коэффициент перевода\" должно быть заполнено. Необходимо или указать коэффициент или не указывать альтернативные еденицы измерения.");
        }
        return !errors.isEmpty();
    }

    @CommitAfter
    Object onSuccessFromCreateSource() {
        if (validate()) return this;
        SourceEntity source = new SourceEntity();
        source.setName(name);
        source.setCode(code);
        if(isNull(group)){
            group = "Без категории";
        }
        source.setGroup(group);
        source.setAltunits(alternativUnits);
        source.setRate(rate);
        source.setUnits(units);
        source.setDeleted(false);
        session.save(source);
        return sourceList;
    }

    private boolean isNull(String value){
        return value == null || value.trim().equals("");
    }

}
