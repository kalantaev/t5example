package com.example.t5.pages;

import com.example.t5.entities.Setting;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Калантаев Александр on 23.03.2017.
 */
public class SettingPage {

    @Inject
    private Session session;

    @InjectComponent("actlocation")
    private Form form;

    @Property
    private String dir;

    @Persist
    private Long dirId;

    void setupRender() {
        Setting setting = (Setting) session.get(Setting.class, 1L);
        if (setting != null) {
            dir = setting.getValue();
            dirId = setting.getId();
        } else {
            dirId = null;
            dir = "C:\\";
        }
    }

    @CommitAfter
    Object onSuccessFromActlocation() {
        Setting setting = (Setting) session.get(Setting.class, 1L);
        if (setting == null) {
            setting = new Setting();
        }
        setting.setName("dir");
        setting.setValue(dir);
        session.saveOrUpdate(setting);
        return this;
    }
}
