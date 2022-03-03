package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;

import com.mindex.challenge.data.ReportingStructure;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureURL;
    private String johnLennonId;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        //Use the 'John Lennon' employee from the server snapshot as he is a known employee value for this test
        reportingStructureURL = "http://localhost:" + port + "/ReportingStructure/{id}";
        johnLennonId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
    }

    @Test
    public void reportingReadTest() {
        //Test to see if 'John Lennon' in the server snapshot database is registered to have 4 reports
        ReportingStructure reportingStructure =  restTemplate.getForEntity(reportingStructureURL, ReportingStructure.class, johnLennonId).getBody();
        assertEquals(reportingStructure.getNumberOfReports(),4);
    }
}
