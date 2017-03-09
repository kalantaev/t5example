package com.example.t5.pages.product;

import com.example.t5.entities.ProductEntity;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Калантаев Александр on 02.03.2017.
 */
public class ProductList {

    @Inject
    private Session session;

    @Property
    private ProductEntity product;

    public List<ProductEntity> getProductList(){
        return session.createCriteria(ProductEntity.class).list();
    }
}
