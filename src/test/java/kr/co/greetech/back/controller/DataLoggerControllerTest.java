package kr.co.greetech.back.controller;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
class DataLoggerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Test
    void create() throws Exception {
        Company company = Company.create(new CompanyCreateDto("company", "abcdefg", "abcdefg1!"));
        em.persist(company);
        Long companyId = company.getId();

        mockMvc.perform(
                post("/dataLogger/{companyId}", companyId)
                        .param("modelName", "dataLogger")
                        .param("unit", "")
                        .param("channelName", "")
        )
                .andExpect(status().isOk());
    }

    @Test
    void one() throws Exception {
        DataLogger dataLogger = DataLogger.create(new DataLoggerCreateDto("dataLogger", "", ""), null);
        em.persist(dataLogger);
        Long dataLoggerId = dataLogger.getId();

        mockMvc.perform(get("/dataLogger")
                .param("dataLoggerId", dataLoggerId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void list() throws Exception {
        Company company = Company.create(new CompanyCreateDto("company", "abcdefg", "abcdefg1!"));
        em.persist(company);
        Long companyId = company.getId();

        em.persist(DataLogger.create(new DataLoggerCreateDto("dataLogger", "", ""), company));
        em.persist(DataLogger.create(new DataLoggerCreateDto("dataLogger", "", ""), company));
        em.persist(DataLogger.create(new DataLoggerCreateDto("dataLogger", "", ""), company));

        mockMvc.perform(get("/dataLogger/{companyId}", companyId))
                .andExpect(status().isOk());
    }
}