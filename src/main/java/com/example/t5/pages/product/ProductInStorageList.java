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
public class ProductInStorageList extends BasicPanel {
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

    public String getBuyerName(){
        return product.getBuyerEntity() != null ? product.getBuyerEntity().getNameOrganization() != null ?
                product.getBuyerEntity().getNameOrganization() : "" : "";
    }

    public List<ProductEntity> getProductList(){
        return (List<ProductEntity>) session.createQuery("from ProductEntity where dateShipment = null and deteCreate != null and template = false").list();
    }

    public String getDeteCreate(){
        return getStringFromData(product.getDeteCreate());
    }
}

