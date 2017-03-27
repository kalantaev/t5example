package com.example.t5.pages.create;

import com.example.t5.entities.BuyerEntity;
import com.example.t5.entities.ProviderEntity;
import com.example.t5.pages.list.BuyerList;
import com.example.t5.pages.list.SourceList;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Калантаев Александр on 04.03.2017.
 */
@Import(stylesheet = "context:style/createform.css")
public class CreateBuyer {
    @InjectComponent("createBuyer")
    private Form form;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<String> errors;
    @InjectPage
    private BuyerList buyerList;

    @InjectPage
    private CreateBuyer createBuyer;

    @Property
    private String nameOrganization;

    @Property
    private String nameHead;
    @Property
    private String email;
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

    @CommitAfter
    Object onSuccessFromCreateBuyer() {
        BuyerEntity buyerEntity = new BuyerEntity();
        buyerEntity.setAccount(account);
        buyerEntity.setAdress(adress);
        buyerEntity.setBanc(banc);
        buyerEntity.setBik(bik);
        buyerEntity.setCorAccount(corAccount);
        buyerEntity.setInn(inn);
        buyerEntity.setKpp(kpp);
        buyerEntity.setNameHead(nameHead);
        buyerEntity.setNameOrganization(nameOrganization);
        buyerEntity.setPhone(phone);
        buyerEntity.setPhoneMobail(phoneMobail);
        buyerEntity.setDeleted(false);
        buyerEntity.setEmail(email);
        session.save(buyerEntity);
        return buyerList;
    }

}

