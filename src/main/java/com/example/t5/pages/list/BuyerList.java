package com.example.t5.pages.list;

import com.example.t5.entities.BuyerEntity;
import com.example.t5.pages.create.CreateBuyer;
import org.apache.tapestry5.annotations.InjectPage;
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

    public List<BuyerList> getBuyerList(){
        return session.createCriteria(BuyerEntity.class).list();
    }

}
