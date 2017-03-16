package com.example.t5.pages.list;

import com.example.t5.entities.ProviderEntity;
import com.example.t5.pages.BasicPanel;
import com.example.t5.pages.create.CreateProvider;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Калантаев Александр on 04.03.2017.
 */
public class ProviderList extends BasicPanel {

    @InjectPage
    private ProviderList providerList;

    @InjectPage
    private CreateProvider createProvider;

    @Inject
    private Session session;

    public List<ProviderList> getProviderList() {
        return session.createCriteria(ProviderEntity.class).list();
    }

}