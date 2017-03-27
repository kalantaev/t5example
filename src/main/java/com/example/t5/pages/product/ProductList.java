package com.example.t5.pages.product;

import com.example.t5.entities.*;
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
            List<SourceSorageEntity> list = session.createQuery("from SourceSorageEntity S where deleted != true and" +
                    " S.count != S.residue and source = :source").setParameter("source", sipe.getSource()).list();
            Double count = sipe.getCount();
            for (int i = list.size()-1; i>= 0; i--){
                SourceSorageEntity sse = list.get(i);
                Double difference = sse.getCount()-sse.getResidue();
                if(count<=difference){
                    sse.setResidue(sse.getResidue() + count);
                    session.update(sse);
                    break;
                } else {
                    count = count - difference;
                    sse.setResidue(sse.getCount());
                    session.update(sse);
                }
            }
            sipe.setDeleted(true);
            session.update(sipe);
        }
        entity.setDeleted(true);
        session.update(entity);
    }

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
