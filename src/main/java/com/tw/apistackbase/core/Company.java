package com.tw.apistackbase.core;

import javax.persistence.*;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private CompanyProfile companyProfile;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }
}
