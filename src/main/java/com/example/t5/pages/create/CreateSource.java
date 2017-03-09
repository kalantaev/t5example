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

    void onPrepareForRender() {
        List<SourceEntity> sourceList = session.createCriteria(SourceEntity.class).list();
        Set<String> model = new HashSet<String>();
        for (SourceEntity source : sourceList) {
            model.add(source.getGroup());
        }
        modelGroup = new ArrayList<String>(model);
    }

//
//    @Property
//    private Long providerId;

    //
//@Property
//private SelectModel selectModel;
//
//    void onPrepareForRender() {
//        // Get all persons - ask business service to find them (from the database)
//        List<Provider> providers = .getProviderDAO().getAll();
//
//        selectModel = new ProviderIdSelectModel(providers);
//    }
    @CommitAfter
    Object onSuccessFromCreateSource() {
        SourceEntity source = new SourceEntity();
        source.setName(name);
        source.setCode(code);
        source.setGroup(group);
        source.setAltunits(alternativUnits);
        source.setRate(rate);
        source.setUnits(units);
//        source.setProvider(.getProviderDAO().getProviderById(providerId));
//        .getSourceDAO().save(source);
        session.save(source);
        return sourceList;
    }


//    public class ProviderIdSelectModel extends AbstractSelectModel {
//        private List<Provider> providers;
//
//        public ProviderIdSelectModel(List<Provider> providers) {
//            this.providers = providers;
//        }
//
//        @Override
//        public List<OptionGroupModel> getOptionGroups() {
//            return null;
//        }
//
//        @Override
//        public List<OptionModel> getOptions() {
//            List<OptionModel> options = new ArrayList<OptionModel>();
//            for (Provider provider : providers ) {
//                options.add(new OptionModelImpl(provider.getName(), provider.getId()));
//            }
//            return options;
//        }
//
//    }


}
