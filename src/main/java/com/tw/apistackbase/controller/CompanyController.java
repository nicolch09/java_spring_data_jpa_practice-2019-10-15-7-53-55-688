package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<Company> list() {
        return companyRepository.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<List<Company>> delete(@PathVariable Long id){
        Optional<Company> foundCompany = companyRepository.findById(id);
        if (foundCompany.isPresent()) {
            companyRepository.deleteById(id);
            return new ResponseEntity<>(companyRepository.findAll(), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Company> modify(@RequestBody Company company, @PathVariable Long id){
        Optional<Company> foundCompany = companyRepository.findById(id);

        if (foundCompany.isPresent()) {
            Company modifyCompany = foundCompany.get();
            modifyCompany.setName(company.getName());
            Company savedCompany = companyRepository.save(modifyCompany);
            return new ResponseEntity<>(savedCompany, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public Company add(@RequestBody Company company) {
        return companyRepository.save(company);
    }
}
