package com.example.t5.pages.edit;

import com.example.t5.entities.BuyerEntity;
import com.example.t5.entities.ProviderEntity;
import com.example.t5.pages.list.BuyerList;
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
public class EditBuyer {
    @InjectComponent("createBuyer")
    private Form form;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private List<String> errors;
    @InjectPage
    private BuyerList buyerList;
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

    @CommitAfter
    Object onSuccessFromCreateBuyer() {
        BuyerEntity buyerEntity =   (BuyerEntity) session.get(BuyerEntity.class, id);
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
        buyerEntity.setEmail(email);
        buyerEntity.setDeleted(false);
        session.save(buyerEntity);
        return buyerList;
    }

    @Persist(PersistenceConstants.FLASH)
    @Property
    private Long id;

    public void onActivate(Long id){
        this.id = id;
    }

    void onPrepareForRender() {
        BuyerEntity providerEntity = (BuyerEntity) session.get(BuyerEntity.class, id);
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

    Long onPassivate() {
        return id;
    }
}