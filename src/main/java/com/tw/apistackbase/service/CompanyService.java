package com.tw.apistackbase.service;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private static final String OBJECT_NOT_FOUND = "OBJECT NOT FOUND";

    @Autowired
    private CompanyRepository companyRepository;

    public Iterable<Company> getAll(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize, Sort.by("name").ascending()));
    }

    public Company get(String name) throws NotFoundException {
        Company company = companyRepository.findOneByName(name);
        if (company != null) {
            return company;
        }
        throw new NotFoundException(OBJECT_NOT_FOUND);
    }


    public List<Company> getSpecific(String name) throws NotFoundException {
        List<Company> company = companyRepository.findByNameContaining(name);
        if (company.size() != 0) {
            return companyRepository.findByNameContaining(name);
        }
        throw new NotFoundException(OBJECT_NOT_FOUND);
    }

    public Company delete(Long id) throws NotFoundException {
        Optional<Company> foundCompany = companyRepository.findById(id);
        if (foundCompany.isPresent()) {
            companyRepository.deleteById(id);
            return foundCompany.get();
        }
        throw new NotFoundException(OBJECT_NOT_FOUND);
    }


    public Company modify(Company company, Long id) throws NotFoundException {
        Optional<Company> foundCompany = companyRepository.findById(id);

        if (foundCompany.isPresent()) {
            Company modifyCompany = foundCompany.get();
            modifyCompany.setName(company.getName());
            Company savedCompany = companyRepository.save(modifyCompany);
            return savedCompany;
        }
        throw new NotFoundException(OBJECT_NOT_FOUND);
    }


    public Company add(Company company) {
        return companyRepository.save(company);
    }
}
