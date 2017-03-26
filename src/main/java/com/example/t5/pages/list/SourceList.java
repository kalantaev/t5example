package com.example.t5.pages.list;

import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceSorageEntity;
import com.example.t5.pages.create.CreateSource;
import com.example.t5.pages.create.ReceiptSource;
import com.example.t5.util.Helper;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;


/**
 * Created by Калантаев Александр on 16.02.2017.
 */
public class SourceList {
    //страница добавления нового сырья
    @InjectPage
    private CreateSource createSource;
    @Inject
    private Session session;
    @InjectPage
    private ReceiptSource receiptSource;
    @Property
    private SourceEntity source;

    void onActivate(){
        index = 0;
    }

    @Persist
    @Property
    private int index;

    public int getIndx() {
        return ++index;
    }

    public List<SourceEntity> getSources() {
        return session.createQuery("from SourceEntity S where " +
                "deleted != true").list();
    }

    public String getUnits(){
        return Helper.UnitsToString(source.getUnits());
    }
    public String getAltUnits(){
        return Helper.UnitsToString(source.getAltunits());
    }

    @CommitAfter
    public Object onActionFromRemove(Long id){
        SourceEntity sse = (SourceEntity) session.get(SourceEntity.class, id);
        sse.setDeleted(true);
        session.update(sse);
        return this;
    }

}
