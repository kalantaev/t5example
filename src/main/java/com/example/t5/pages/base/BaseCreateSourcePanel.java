package com.example.t5.pages.base;

import com.example.t5.data.Units;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Калантаев Александр on 25.03.2017.
 */
public class BaseCreateSourcePanel {

    @Persist(PersistenceConstants.FLASH)
    @Property
    private String name;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String code;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private Units units;


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

    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<String> errors;


    protected boolean validate() {
        errors = new ArrayList<String>();
        if (isNull(group)) {
            errors.add("Поле \"Группа\" должно быть заполнено");
        }
        if (isNull(name)) {
            errors.add("Поле \"Наименование\" должно быть заполнено");
        }
        if (units == null) {
            errors.add("Поле \"Еденицы измерения\" должно быть заполнено");
        }
        if (alternativUnits != null && rate == 0) {
            errors.add("При указании альтернативной еденицы измерения поле \"Коэффициент перевода\" должно быть заполнено. Необходимо или указать коэффициент или не указывать альтернативные еденицы измерения.");
        }
        return !errors.isEmpty();
    }

    private boolean isNull(String value) {
        return value == null || value.equals("");
    }

    public String getsName() {
        return name;
    }


    public String getsCode() {
        return code;
    }


    public Units getUnit() {
        return units;
    }


    public boolean isNewGroup() {
        return newGroup;
    }


    public Units getAlternativUnit() {
        return alternativUnits;
    }


    public double getsRate() {
        return rate;
    }


    public String getsGroup() {
        return group;
    }


}
