package kr.co.greetech.back.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.greetech.back.dto.DataLoggerCreateDto;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.entity.DataLogger;
import kr.co.greetech.back.entity.MeasureData;
import kr.co.greetech.back.repository.MeasureDataRepository;
import kr.co.greetech.back.service.MeasureDataService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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

//    @Test
//    void add() throws Exception {
//        DataLogger dataLogger = DataLogger.create(new DataLoggerCreateDto("dataLogger"), null);
//        em.persist(dataLogger);
//        Long dataLoggerId = dataLogger.getId();
//
//        List<MeasureDataDto> dataDtos = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            dataDtos.add(new MeasureDataDto("data" + i));
//        }
//
//        String jsonString = mapper.writeValueAsString(dataDtos);
//        mockMvc.perform(post("/measureData/{dataLoggerId}", dataLoggerId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonString)
//        ).andExpect(status().isOk());
//
//        Long start = System.currentTimeMillis() - (1000 * 3600 * 24);
//        Long end = System.currentTimeMillis();
//        List<MeasureDataDto> dtos = measureDataService.select(dataLoggerId, start, end);
//        assertThat(dtos.size()).isEqualTo(5);
//    }

    @Test
    void select() throws Exception {
        DataLogger dataLogger = DataLogger.create(new DataLoggerCreateDto("dataLogger"), null);
        em.persist(dataLogger);
        Long dataLoggerId = dataLogger.getId();

        List<MeasureDataDto> dataDtos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dataDtos.add(new MeasureDataDto("data" + i, LocalDateTime.now()));
        }
        measureDataService.addMeasureDataDtos(dataLoggerId, dataDtos);

        LocalDateTime start = LocalDateTime.now().minusDays(1L);
        LocalDateTime end = LocalDateTime.now().plusDays(1L);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        MvcResult result = mockMvc.perform(get("/measureData")
                .param("dataLoggerId", dataLoggerId.toString())
                .param("start", formatter.format(start))
                .param("end", formatter.format(end))
        ).andExpect(status().isOk())
                .andReturn();

        String jsonString = result.getResponse().getContentAsString();
//        MeasureDataDto[] measureDataDtos = mapper.readValue(jsonString, MeasureDataDto[].class);
        List<MeasureDataDto> measureDataDtos = mapper.readValue(jsonString, new TypeReference<>() {});

        System.out.println("formatter.format(start) = " + formatter.format(start));
        System.out.println("formatter.format(end) = " + formatter.format(end));

        for (MeasureDataDto measureDataDto : measureDataDtos) {
            System.out.println("measureDataDto = " + measureDataDto);
        }

//        for (MeasureDataDto measureDataDto : measureDataDtos) {
//            System.out.println("measureDataDto = " + measureDataDto);
//        }

        assertThat(measureDataDtos.size()).isEqualTo(5);
//        assertThat(measureDataDtos.length).isEqualTo(5);
    }
}