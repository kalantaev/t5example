package com.example.t5.pages.create;

import com.example.t5.data.Units;
import com.example.t5.entities.SourceEntity;
import com.example.t5.pages.list.SourceList;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
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
public class CreateSource {

    @InjectComponent("createSource")
    private Form form;

    @Property
    private String name;

    @Property
    private String code;

    @Property
    private Units units;

    @Inject
    private Session session;
    @Property
    @Persist
    private boolean newGroup;

    @Property
    private Units alternativUnits;

    @Property
    private double rate;

    @Property
    private String group;

    @Property
    @Persist
    private List<String> modelGroup;

    @InjectPage
    private SourceList sourceList;

    public void onActivate(Long id){
        System.out.println(id);
    }

    void onPrepareForRender() {
        List group = session.createQuery("select distinct S.group from SourceEntity S").list();
        modelGroup = new ArrayList<String>(group);
    }

    @CommitAfter
    Object onSuccessFromCreateSource() {
        SourceEntity source = new SourceEntity();
        source.setName(name);
        source.setCode(code);
        source.setGroup(group);
        source.setAltunits(alternativUnits);
        source.setRate(rate);
        source.setUnits(units);
        session.save(source);
        return sourceList;
    }

}
