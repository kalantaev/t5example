package com.example.t5.entities;

import javax.persistence.*;

/**
 * Created by Калантаев Александр on 28.02.2017.
 */
@Entity
@Table(name = "provider")
public class ProviderEntity {
    private Long id;
    private String nameOrganization;
    private String nameHead;
    private String inn;
    private String kpp;
    private String account;
    private String banc;
    private String bik;
    private String corAccount;
    private String phone;
    private String phoneMobail;
    private String adress;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME_ORGANIZATION")
    public String getNameOrganization() {
        return nameOrganization;
    }

    public void setNameOrganization(String nameOrganization) {
        this.nameOrganization = nameOrganization;
    }

    @Basic
    @Column(name = "NAME_HEAD")
    public String getNameHead() {
        return nameHead;
    }

    public void setNameHead(String nameHead) {
        this.nameHead = nameHead;
    }

    @Basic
    @Column(name = "INN")
    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    @Basic
    @Column(name = "KPP")
    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    @Basic
    @Column(name = "ACCOUNT")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "BANC")
    public String getBanc() {
        return banc;
    }

    public void setBanc(String banc) {
        this.banc = banc;
    }

    @Basic
    @Column(name = "BIK")
    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    @Basic
    @Column(name = "COR_ACCOUNT")
    public String getCorAccount() {
        return corAccount;
    }

    public void setCorAccount(String corAccount) {
        this.corAccount = corAccount;
    }

    @Basic
    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "PHONE_MOBAIL")
    public String getPhoneMobail() {
        return phoneMobail;
    }

    public void setPhoneMobail(String phoneMobail) {
        this.phoneMobail = phoneMobail;
    }

    @Basic
    @Column(name = "ADRESS")
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProviderEntity that = (ProviderEntity) o;

        if (id != that.id) return false;
        if (nameOrganization != null ? !nameOrganization.equals(that.nameOrganization) : that.nameOrganization != null)
            return false;
        if (nameHead != null ? !nameHead.equals(that.nameHead) : that.nameHead != null) return false;
        if (inn != null ? !inn.equals(that.inn) : that.inn != null) return false;
        if (kpp != null ? !kpp.equals(that.kpp) : that.kpp != null) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (banc != null ? !banc.equals(that.banc) : that.banc != null) return false;
        if (bik != null ? !bik.equals(that.bik) : that.bik != null) return false;
        if (corAccount != null ? !corAccount.equals(that.corAccount) : that.corAccount != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (phoneMobail != null ? !phoneMobail.equals(that.phoneMobail) : that.phoneMobail != null) return false;
        if (adress != null ? !adress.equals(that.adress) : that.adress != null) return false;

        return true;
    }


}
