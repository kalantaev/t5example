package com.example.t5.pages.product;

import com.example.t5.entities.ProductEntity;
import com.example.t5.entities.ProviderEntity;
import com.example.t5.entities.Setting;
import com.example.t5.entities.SourceInProductEntity;
import com.example.t5.excel.importexcel.ExcelWorker;
import com.example.t5.pages.BasicPanel;
import org.apache.tapestry5.PersistenceConstants;
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
    @Persist(PersistenceConstants.FLASH)
    @Property
    private boolean showxls;

    @Persist
    @Property
    private int index;

    public int getIndx() {
        return ++index;
    }

    public List<ProductEntity> getProductList(){
        return (List<ProductEntity>) session.createQuery("from ProductEntity where dateShipment != null and template = false and deleted != true ").list();
    }

    @CommitAfter
    void onActionFromRemove(Long id) {
        ProductEntity entity = (ProductEntity) session.get(ProductEntity.class, id);
        for (SourceInProductEntity sipe : entity.getSourceList()){
            sipe.setDeleted(true);
            session.update(sipe);
        }
        entity.setDeleted(true);
        session.update(entity);
    }

//    void onActionFromСre(Long id) {
//        System.out.println(id);
//        ExcelWorker.createXLS((ProductEntity) session.get(ProductEntity.class, id));
//    }

    public String getDeteCreate(){
        return getStringFromData(product.getDeteCreate());
    }

    public String getDateShipment(){
        return getStringFromData(product.getDateShipment());
    }

    void onXls(Long id) {
        Setting setting = (Setting) session.get(Setting.class, 1L);
        String path = setting != null ? setting.getValue().endsWith("\\") ? setting.getValue() : (setting.getValue()+"\\") : "C:\\";
        showxls= !ExcelWorker.createXLS((ProductEntity) session.get(ProductEntity.class, id), path);

    }
}
