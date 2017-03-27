package com.example.t5.pages.edit;

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
 * Created by Калантаев Александр on 20.03.2017.
 */
@Import(stylesheet = "context:style/createform.css")
public class EditProvider {
    @InjectComponent("createProvider")
    private Form form;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<String> errors;
    @InjectPage
    private ProviderList providerList;

    @Property
    private String email;
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

    @Persist(PersistenceConstants.FLASH)
    @Property
    private Long id;

    public void onActivate(Long id){
        this.id = id;
    }

    void onPrepareForRender() {
        ProviderEntity providerEntity = (ProviderEntity) session.get(ProviderEntity.class, id);
        account= providerEntity.getAccount();
        adress= providerEntity.getAdress();
        banc=  providerEntity.getBanc();
        bik= providerEntity.getBik();
        corAccount= providerEntity.getCorAccount();
        inn=  providerEntity.getInn();
        kpp=  providerEntity.getKpp();
        nameHead= providerEntity.getNameHead();
        nameOrganization= providerEntity.getNameOrganization();
        phone= providerEntity.getPhone();
        email = providerEntity.getEmail();
        phoneMobail=  providerEntity.getPhoneMobail();
    }

    @CommitAfter
    Object onSuccessFromCreateProvider() {
        ProviderEntity providerEntity = (ProviderEntity) session.get(ProviderEntity.class, id);
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
        providerEntity.setEmail(email);
        providerEntity.setDeleted(false);
                session.update(providerEntity);
        return providerList;
    }

    Long onPassivate() {
        return id;
    }
}