package com.example.t5.pages.list;

import com.example.t5.entities.BuyerEntity;
import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceSorageEntity;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Калантаев Александр on 16.03.2017.
 */
public class ReciptSourceList {

    @Property
    private SourceSorageEntity source;
    @Inject
    private Session session;

    @Persist(PersistenceConstants.FLASH)
    private Long id;

    void onActivate(Long id) {
        this.id = id;
    }

    public List<BuyerList> getSourseList() {
        if (id != null) {
            return session.createQuery("from SourceSorageEntity S where " +
                    "S.source.id = :id and deleted != true ").setParameter("id", id).list();
        } else {
            return session.createQuery("from SourceSorageEntity S where " +
                    "deleted != true").list();
        }
    }

    public String getTitle() {
        if (id != null) {
            SourceEntity se = (SourceEntity) session.get(SourceEntity.class, id);
            return "Список приходов " + se.getName();
        }
        return "Список приходов сырья";
    }

    Long onPassivate() {
        return id;
    }

    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<String> errors;

    @CommitAfter
    public Object onActionFromRemove(Long id) {
        errors = new ArrayList<String>();
        SourceSorageEntity sse = (SourceSorageEntity) session.get(SourceSorageEntity.class, id);
        try {
            Double allResidue = (Double) session.createQuery("select sum(residue) from SourceSorageEntity " +
                    "where deleted != true and id != :id").setParameter("id", id).list().get(0);
            if((allResidue -(sse.getCount()- sse.getResidue())) >= 0 ){
                List<SourceSorageEntity> list = session.createQuery("from SourceSorageEntity S where residue > 0 " +
                        "and deleted != true and id != :entId").setParameter("entId", sse.getId()).list();
                Double needUpd = sse.getCount()- sse.getResidue();
                for (SourceSorageEntity ent : list){
                    if(ent.getResidue()>=needUpd ){
                        ent.setResidue(ent.getResidue()-needUpd);
                        sse.setResidue(sse.getCount());
                        session.update(ent);
                        break;
                    } else {
                        needUpd = needUpd - ent.getResidue();
                        ent.setResidue(0d);
                        session.update(ent);
                    }
                }
            } else {
                errors.add("Невозможно удалить данный приход, так как в случае удаления расход сырья превысит приход." +
                        " Необходимо добавить новый приход и повторить попытку");
                return this;
            }
        } catch (Exception e){
            System.out.println(e);
            errors.add("Невозможно удалить данный приход, так как в случае удаления расход сырья превысит приход." +
                    " Необходимо добавить новый приход и повторить попытку");
            return this;
        }
        sse.setDeleted(true);
        session.update(sse);
        return this;
    }
}
