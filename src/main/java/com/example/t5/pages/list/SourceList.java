package com.example.t5.pages.list;

import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceSorageEntity;
import com.example.t5.pages.create.CreateSource;
import com.example.t5.pages.create.ReceiptSource;
import org.apache.tapestry5.annotations.InjectPage;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;


/**
 * Created by Калантаев Александр on 16.02.2017.
 */
public class SourceList {
    //страница добавления нового сырья
    @InjectPage
    private CreateSource createSource;
    @Inject
    private Session session;
    @InjectPage
    private ReceiptSource receiptSource;

    void onPrepareForRender() {
        //метод выполняется до отрендеривания страницы
    }

    @Property
    private SourceSorageEntity storageEntity;

    public List<SourceEntity> getSource() {
        return session.createCriteria(SourceEntity.class).list();
    }

    public List<SourceSorageEntity> getStorage() {


        List<SourceSorageEntity> list = session.createCriteria(SourceSorageEntity.class).list();
        System.out.println(list.get(0).getSource().getName());
        return list;
    }

}
