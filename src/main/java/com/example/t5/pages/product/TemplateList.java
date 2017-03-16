package com.example.t5.pages.product;

import com.example.t5.entities.ProductEntity;
import com.example.t5.pages.BasicPanel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Калантаев Александр on 09.03.2017.
 */
public class TemplateList extends BasicPanel {
    @Inject
    private Session session;

    @Property
    private ProductEntity product;
    @Persist
    @Property
    private int index;

    public int getIndx() {
        return ++index;
    }

    void onActivate(){
        index = 0;
    }
    public List<ProductEntity> getProductList(){
        return (List<ProductEntity>) session.createQuery("from ProductEntity where template = true").list();

    }
}

