package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.service.CompanyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    //todo: default value for page and page size value
    @GetMapping(value = "/all", produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<Company> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return companyService.getAll(page, pageSize);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/{name}")
    public Company get(@PathVariable String name) throws NotFoundException {
        return companyService.get(name);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public List<Company> getSpecific(@RequestParam(required = false) String name) throws NotFoundException {
        return companyService.getSpecific(name);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @DeleteMapping(path = "/{id}")
    public Company delete(@PathVariable Long id) throws NotFoundException {
        return companyService.delete(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public Company modify(@RequestBody Company company, @PathVariable Long id) throws NotFoundException {
        return companyService.modify(company, id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(produces = {"application/json"})
    public Company add(@RequestBody Company company) {
        return companyService.add(company);
    }
}
