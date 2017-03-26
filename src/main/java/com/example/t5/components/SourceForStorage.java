package com.example.t5.components;

import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceInProductEntity;
import com.example.t5.pages.BasicPanel;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Калантаев Александр on 17.03.2017.
 */
public class SourceForStorage {

    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.PROP)
    private SourceInProductEntity entity;

    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.PROP)
    private Boolean show;

//    @Persist
//    List<SourceEntity> sources;
//    @Property
//    @Persist
//    private SelectModel modelGroup;
//    void onActivate(){
//        modelGroup = new SourceIdSelectModel(sources);
//    }

    public String getNames(){
        if(entity == null) return "entity=null";
        return "";
       /* if(entity.getSource() == null) return "entity.source = null";
        if(entity.getSource().getName() == null) return "full null";
        return entity.getSource().getName();*/
    }

    public void setNames(String name){}

//    public Double getCounts(){
//        return entity.getCount();
//    }
//
//    public void setCounts(Double count){
//        entity.setCount(count);
//    }
//
//    public BigDecimal getPrices(){
//        return entity.getPrice();
//    }
//
//    public void setPrices(BigDecimal price){
//        entity.setPrice(price);
//    }
//
//    public class SourceIdSelectModel extends AbstractSelectModel {
//        private List<SourceEntity> sources;
//
//        public SourceIdSelectModel(List<SourceEntity> sources) {
//            this.sources = sources;
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
//            for (SourceEntity source : sources) {
//                options.add(new OptionModelImpl(source.getName(), source.getId()));
//            }
//            return options;
//        }
//    }
}
