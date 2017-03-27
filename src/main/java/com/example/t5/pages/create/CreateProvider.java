package com.example.t5.pages.create;

import com.example.t5.entities.ProviderEntity;
import com.example.t5.pages.list.ProviderList;
import com.example.t5.pages.list.SourceList;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Калантаев Александр on 16.02.2017.
 */
@Import(stylesheet = "context:style/createform.css")
public class CreateProvider {
    @InjectComponent("createProvider")
    private Form form;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<String> errors;
    @InjectPage
    private ProviderList providerList;
    @Property
    private String email;
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
        providerEntity.setDeleted(false);
        providerEntity.setEmail(email);
        session.save(providerEntity);
        return providerList;
    }

}
