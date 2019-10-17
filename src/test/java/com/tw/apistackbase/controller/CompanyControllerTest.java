package com.tw.apistackbase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.core.Employee;
import com.tw.apistackbase.service.CompanyService;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
@ActiveProfiles(profiles = "test")
class CompanyControllerTest {
    @MockBean
    CompanyService companyService;

    @Autowired
    private MockMvc mvc;

    public Company createDummyCompany() {
        Company company = new Company();
        company.setId(1L);
        company.setName("Sample");
        return company;
    }

    public Employee createDummyEmployee() {
        Employee employee = new Employee();
        employee.setAge(20);
        employee.setName("A");
        employee.setId(1L);
        return employee;
    }

    @Test
    void should_return_200_with_get_all() throws Exception {
        when(companyService.getAll(null, null)).thenReturn(Arrays.asList(createDummyCompany()));

        ResultActions result = mvc.perform(get("/companies/all"));

        result.andExpect(status().isOk());
    }

    @Test
    void should_return_200_with_get_all_with_page() throws Exception {
        when(companyService.getAll(0, 1)).thenReturn(Arrays.asList(createDummyCompany()));

        ResultActions result = mvc.perform(get("/companies/all"));

        result.andExpect(status().isOk());
    }

    @Test
    void should_return_specific_data_when_name_is_sample() throws Exception {
        when(companyService.get("Sample")).thenReturn(createDummyCompany());

        ResultActions result = mvc.perform(get("/companies/Sample"));

        result.andExpect(status().isOk());
    }

    @Test
    void should_not_return_specific_data_when_name_doesnt_exist() throws Exception {
        when(companyService.get("Sample")).thenReturn(createDummyCompany());

        ResultActions result = mvc.perform(get("/companies/Test"));

        result.andExpect(status().isNotFound());
    }

    @Test
    void should_return_specific_data_when_name_like_sam() throws Exception {
        when(companyService.getSpecific("Sam")).thenReturn(Arrays.asList(createDummyCompany()));

        ResultActions result = mvc.perform(get("/companies?name=Sam"));

        result.andExpect(status().isOk());
    }

    @Test
    void should_not_return_specific_data_when_name_like_doesnt_exist() throws Exception {
        when(companyService.getSpecific(any())).thenReturn(null);

        ResultActions result = mvc.perform(get("/companies?name=Sample"));

        result.andExpect(status().isNotFound());
    }

    @Test
    void should_return_404_when_called_delete_with_correct_id() throws Exception {
        when(companyService.delete(1L)).thenReturn(Arrays.asList(createDummyCompany()));

        ResultActions result = mvc.perform(delete("/companies/1"));

        result.andExpect(status().isOk());
    }

    @Test
    void should_return_200_when_called_delete_with_incorrect_id() throws Exception {
        when(companyService.delete(1L)).thenReturn(null);

        ResultActions result = mvc.perform(delete("/companies/1"));

        result.andExpect(status().isNotFound());
    }

    @Ignore
    @Test
    void should_return_200_when_passed_with_existing_id_in_patch() throws Exception {
        when(companyService.modify(createDummyCompany(), 1L)).thenReturn(createDummyCompany());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(createDummyCompany());
        ResultActions result = mvc.perform(patch("/companies/{id}", 1L )
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void should_return_404_when_passed_with_existing_id_in_patch() throws Exception {
        when(companyService.modify(createDummyCompany(),1L)).thenReturn(null);

        ResultActions result = mvc.perform(patch("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createDummyCompany())));

        result.andExpect(status().isNotFound());
    }

    @Test
    void should_return_201_when_company_is_created() throws Exception {
        when(companyService.add(createDummyCompany())).thenReturn(createDummyCompany());

        ResultActions result = mvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createDummyCompany())));

        result.andExpect(status().isCreated());
    }

}