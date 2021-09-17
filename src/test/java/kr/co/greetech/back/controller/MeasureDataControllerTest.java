package kr.co.greetech.back.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.business.measuredata.service.MeasureDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class MeasureDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private MeasureDataService measureDataService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void add() throws Exception {
        // given
        DataLogger dataLogger = DataLogger.create(new DataLoggerCreateDto("dataLogger"), null);
        em.persist(dataLogger);
        Long dataLoggerId = dataLogger.getId();

        List<MeasureDataDto> dataDtos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dataDtos.add(new MeasureDataDto("data" + i, LocalDateTime.now()));
        }

        // when
        String jsonString = mapper.writeValueAsString(dataDtos);
        mockMvc.perform(post("/measureData/{dataLoggerId}", dataLoggerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        ).andExpect(status().isOk());

        // then
        LocalDateTime start = LocalDateTime.now().minusDays(1L);
        LocalDateTime end = LocalDateTime.now().plusDays(1L);
        List<MeasureDataDto> dtos = measureDataService.select(dataLoggerId, start, end);
        assertThat(dtos.size()).isGreaterThan(0);
    }

    @Test
    void select() throws Exception {
        // given
        DataLogger dataLogger = DataLogger.create(new DataLoggerCreateDto("dataLogger"), null);
        em.persist(dataLogger);
        Long dataLoggerId = dataLogger.getId();

        List<MeasureDataDto> dataDtos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dataDtos.add(new MeasureDataDto("data" + i, LocalDateTime.now()));
        }
        measureDataService.addMeasureDataDtos(dataLoggerId, dataDtos);

        //when
        LocalDateTime start = LocalDateTime.now().minusDays(1L);
        LocalDateTime end = LocalDateTime.now().plusDays(1L);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        System.out.println("start date: " + formatter.format(start));
        MvcResult response = mockMvc.perform(get("/measureData/" + dataLoggerId.toString())
                .param("from", "2021-09-13T16:00:14.611")
                .param("to", "2021-09-14T23:00:14.611")
        ).andExpect(status().isOk())
                .andReturn();

        // then
        String jsonString = response.getResponse().getContentAsString();
        Result result = mapper.readValue(jsonString, new TypeReference<>() {});
        List<MeasureDataDto> measureDataDtos = result.data;
        assertThat(measureDataDtos.size()).isGreaterThan(0);
    }

    static class Result {
        private int count;
        private List<MeasureDataDto> data;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<MeasureDataDto> getData() {
            return data;
        }

        public void setData(List<MeasureDataDto> data) {
            this.data = data;
        }
    }
}