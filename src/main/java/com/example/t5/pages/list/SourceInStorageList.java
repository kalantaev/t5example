package com.example.t5.pages.list;

import com.example.t5.data.Units;
import com.example.t5.entities.ProductEntity;
import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceSorageEntity;
import com.example.t5.entities.view.Storage;
import com.example.t5.pages.BasicPanel;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Калантаев Александр on 12.03.2017.
 */
public class SourceInStorageList extends BasicPanel {

    @Inject
    private Session session;

    @Property
    private Storage storage;

    public List<Storage> getListStorage() {
        List<Storage> storageList = new ArrayList<Storage>();
        List<SourceSorageEntity> list = session.createCriteria(SourceSorageEntity.class).list();
        //выбераем все номенклатуры сырья
        List<SourceEntity> listSourceEntity = (List<SourceEntity>) session.createCriteria(SourceEntity.class).list();

        for (SourceEntity entity : listSourceEntity) {
            Storage storage = new Storage();
            storage.setId(entity.getId());
            storage.setName(entity.getName());
            storage.setCode(entity.getCode());
            List resipt = session.createQuery("select sum(S.count) from SourceSorageEntity S where source = :entId")
                    .setParameter("entId", entity).list();
            List rates = session.createQuery("select sum(S.count) from SourceInProductEntity S " +
                    "where source = :entId and S.templates = false")
                    .setParameter("entId", entity).list();
            Double count = (Double) (resipt.get(0) != null ? resipt.get(0) : 0.0);
            Double rate = (Double) (rates.get(0) != null ? rates.get(0) : 0.0);
            storage.setCount(count - rate);
            storage.setUnits(entity.getUnits());
            storageList.add(storage);
        }


        return storageList;
    }

    public String getUnitsFromSource(){
        return getStringFromUnits(storage.getUnits());
    }

}
