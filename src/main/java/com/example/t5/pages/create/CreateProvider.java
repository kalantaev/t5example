package com.example.t5.pages.create;

import com.example.t5.entities.ProviderEntity;
import com.example.t5.pages.list.ProviderList;
import com.example.t5.pages.list.SourceList;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 * Created by Калантаев Александр on 16.02.2017.
 */
public class CreateProvider {
    @InjectComponent("createProvider")
    private Form form;

    @InjectPage
    private ProviderList providerList;

    @InjectPage
    private CreateProvider createProvider;


    @Property
    private String nameOrganization;

    @Property
    private String nameHead;
    @Property
    private String inn;

    @Property
    private String kpp;

    @Property
    private String account;

    @Property
    private String banc;

    @Property
    private String bik;
    @Property
    private String corAccount;
    @Property
    private String phone;

    @Property
    private String phoneMobail;

    @Property
    private String adress;

    @Inject
    private Session session;

    @InjectPage
    private SourceList sourceList;

    @CommitAfter
    Object onSuccessFromCreateProvider() {
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setAccount(account);
        providerEntity.setAdress(adress);
        providerEntity.setBanc(banc);
        providerEntity.setBik(bik);
        providerEntity.setCorAccount(corAccount);
        providerEntity.setInn(inn);
        providerEntity.setKpp(kpp);
        providerEntity.setNameHead(nameHead);
        providerEntity.setNameOrganization(nameOrganization);
        providerEntity.setPhone(phone);
        providerEntity.setPhoneMobail(phoneMobail);
        session.save(providerEntity);
        return providerList;
    }

}
