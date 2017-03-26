package com.example.t5.pages.list;

import com.example.t5.data.Units;
import com.example.t5.entities.ProductEntity;
import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceSorageEntity;
import com.example.t5.entities.view.Storage;
import com.example.t5.pages.BasicPanel;
import com.example.t5.util.Helper;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Калантаев Александр on 12.03.2017.
 */
@Import(library = {"context:js/scrolList.js"},
        stylesheet = {"context:style/sourceinstorage.css"})
public class SourceInStorageList extends BasicPanel {

    @Inject
    private Session session;

    @Property
    private String group;

    @Property
    private Storage storage;

    @Persist
    private int cssCount;

    public String getCss() {
        System.out.println(cssCount);
        return cssCount++ % 2 == 0 ? "border" : "border1";

    }

    public List<String> getGroups() {
        List<String> groups = session.createQuery("select group from SourceEntity where deleted != true ")
                .list();
        Set<String> setGroup = new HashSet<String>(groups);
        groups = new ArrayList<String>(setGroup);
        Collections.sort(groups);
        return groups;
    }

    public List<Storage> getListStorage() {
        List<Storage> storageList = new ArrayList<Storage>();
        //выбераем все номенклатуры сырья
        List<SourceEntity> listSourceEntity =
                (List<SourceEntity>) session.createQuery("from SourceEntity S where deleted != true" +
                        " and group = :group")
                        .setParameter("group", group).list();
        for (SourceEntity entity : listSourceEntity) {
            Storage storage = new Storage();
            List prise = session.createQuery("select price from SourceSorageEntity S where source = :entId and deleted != true and " +
                    "id = (select max(S.id) from S where source.id = :entId)")
                    .setParameter("entId", entity).list();
            List lastCount = session.createQuery("select S.count from SourceSorageEntity S where source = :entId and deleted != true and  " +
                    "id = (select max(S.id) from S where source.id = :entId)")
                    .setParameter("entId", entity).list();
            if (prise.isEmpty() || lastCount.isEmpty()) {
                storage.setPrice("----");
            } else {
                String priceForOne = ((BigDecimal) prise.get(0)).divide(new BigDecimal((Double) lastCount.get(0)), 2, RoundingMode.HALF_UP).toString();
                storage.setPrice(priceForOne + "p/" + Helper.UnitsToString(entity.getUnits()));
            }

            storage.setId(entity.getId());
            storage.setName(entity.getName());
            storage.setCode(entity.getCode());
            List resipt = session.createQuery("select sum(S.count) from SourceSorageEntity S where source = :entId " +
                    "and deleted != true ")
                    .setParameter("entId", entity).list();
            List rates = session.createQuery("select sum(S.count) from SourceInProductEntity S " +
                    "where source = :entId and S.templates = false and deleted != true ")
                    .setParameter("entId", entity).list();
            System.out.println(resipt);
            System.out.println(rates);
            Double count = (Double) (resipt.get(0) != null ? resipt.get(0) : 0.0);
            Double rate = (Double) (rates.get(0) != null ? rates.get(0) : 0.0);
            Double ostatok = (count - rate);
            storage.setCount(ostatok.toString() + Helper.UnitsToString(entity.getUnits()));
            if (entity.getRate() != null) {
                Double alt = ostatok / entity.getRate();
                storage.setAltCount(alt.toString() + Helper.UnitsToString(entity.getAltunits()));
            }
            storage.setUnits(entity.getUnits());
            storageList.add(storage);

        }
        return storageList;
    }

    public String getUnitsFromSource() {
        return getStringFromUnits(storage.getUnits());
    }

}
