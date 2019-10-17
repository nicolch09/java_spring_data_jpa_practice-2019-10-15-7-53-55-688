package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/all", produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<Company> getAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        return companyService.getAll(page, pageSize);
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<Company> get(@PathVariable String name){
        Company company = companyService.get(name);
        if (company != null) {
            return new ResponseEntity(company, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Company>> getSpecific(@RequestParam(required = false) String name){
        List<Company> company = companyService.getSpecific(name);
        if (company != null) {
            return new ResponseEntity(Arrays.asList(company), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<List<Company>> delete(@PathVariable Long id){
        List<Company> foundCompany = companyService.delete(id);
        if (foundCompany != null) {
            return new ResponseEntity<>(foundCompany, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Company> modify(@RequestBody Company company, @PathVariable Long id){
        Company foundCompany = companyService.modify(company, id);
        if (foundCompany != null) {
            return new ResponseEntity(foundCompany, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public Company add(@RequestBody Company company) {
        return companyService.add(company);
    }
}
