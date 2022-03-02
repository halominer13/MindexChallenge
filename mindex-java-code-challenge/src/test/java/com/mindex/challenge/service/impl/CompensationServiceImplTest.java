package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

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
public class CompensationServiceImplTest {
    
    private String createCompURL;
    private String readCompURL;

    //@Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        createCompURL = "http://localhost:" + port + "/compensation";
        readCompURL = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void compensationTest(){
        Employee employee = new Employee();

        employee.setEmployeeId(UUID.randomUUID().toString());
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setPosition("Developer");
        employee.setDepartment("Research and Development");

        Double salary = 100000.00;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 2, 1, 10, 0, 0);
        Date effectiveDate = calendar.getTime();
        /*
        Compensation compensation = new Compensation(employee, salary, effectiveDate);

        Compensation returnedCompensation = restTemplate.postForEntity(createCompURL, compensation, Compensation.class).getBody();

        assertNotNull(returnedCompensation.getEmployee());
        assertCompensationEquivalence(compensation, returnedCompensation);*/
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual){
        assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
