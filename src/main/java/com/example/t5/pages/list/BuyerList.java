package com.example.t5.pages.list;

import com.example.t5.entities.BuyerEntity;
import com.example.t5.entities.ProviderEntity;
import com.example.t5.pages.create.CreateBuyer;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Калантаев Александр on 04.03.2017.
 */
public class BuyerList {

    @InjectPage
    private BuyerList buyerList;

    @InjectPage
    private CreateBuyer createBuyer;

    @Inject
    private Session session;


    @Property
    private BuyerEntity provider;

    public List<BuyerEntity> getBuyerList() {
        return session.createQuery("from BuyerEntity where deleted != true").list();
    }


    @CommitAfter
    public Object onActionFromRemove(Long id) {
        BuyerEntity sse = (BuyerEntity) session.get(BuyerEntity.class, id);
        sse.setDeleted(true);
        session.update(sse);
        return this;
    }


}
