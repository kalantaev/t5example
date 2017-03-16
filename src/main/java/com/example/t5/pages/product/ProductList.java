package com.example.t5.pages.product;

import com.example.t5.entities.ProductEntity;
import com.example.t5.entities.ProviderEntity;
import com.example.t5.pages.BasicPanel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Калантаев Александр on 02.03.2017.
 */
public class ProductList  extends BasicPanel {

    @Inject
    private Session session;

    @Property
    private ProductEntity product;

    void onActivate(){
        index = 0;
    }

    @Persist
    @Property
    private int index;

    public int getIndx() {
        return ++index;
    }

    public List<ProductEntity> getProductList(){
        return (List<ProductEntity>) session.createQuery("from ProductEntity where dateShipment != null and template = false").list();
    }

    @CommitAfter
    void onActionFromRemove(Long id) {
        List prise = session.createQuery("from SourceSorageEntity S where source.id = :entId and " +
                "id = (select max(S.id) from S where source.id = :entId)")
                .setParameter("entId", id).list();

        ProductEntity entity = (ProductEntity) session.get(ProductEntity.class, id);
        session.delete(entity);
    }

    public String getDeteCreate(){
        return getStringFromData(product.getDeteCreate());
    }

    public String getDateShipment(){
        return getStringFromData(product.getDateShipment());
    }
}
