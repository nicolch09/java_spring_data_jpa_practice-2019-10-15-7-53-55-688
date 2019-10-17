package com.tw.apistackbase.service;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Iterable<Company> getAll(Integer page, Integer pageSize) {
        if(page != null || pageSize != null) {
            return companyRepository.findAll(PageRequest.of(page, pageSize, Sort.by("name").ascending()));
        }
        return companyRepository.findAll();
    }

    public Company get(String name){
        Company company = companyRepository.findOneByName(name);
        if (company != null) {
            return companyRepository.findOneByName(name);
        }
        return null;
    }


    public List<Company> getSpecific(String name){
        Optional<List<Company>> company = Optional.ofNullable(companyRepository.findByNameContaining(name));
        if (company != null) {
            return companyRepository.findByNameContaining(name);
        }
        return null;
    }

    public List<Company> delete(Long id){
        Optional<Company> foundCompany = companyRepository.findById(id);
        if (foundCompany.isPresent()) {
            companyRepository.deleteById(id);
            return companyRepository.findAll();
        }
        return null;
    }


    public Company modify(Company company,  Long id){
        Optional<Company> foundCompany = companyRepository.findById(id);

        if (foundCompany.isPresent()) {
            Company modifyCompany = foundCompany.get();
            modifyCompany.setName(company.getName());
            Company savedCompany = companyRepository.save(modifyCompany);
            return savedCompany;
        }
        return null;
    }


    public Company add(Company company) {
        return companyRepository.save(company);
    }
}
