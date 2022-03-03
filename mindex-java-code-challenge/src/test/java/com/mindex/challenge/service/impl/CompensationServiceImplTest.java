package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

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

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        createCompURL = "http://localhost:" + port + "/compensation";
        readCompURL = "http://localhost:" + port + "/compensation/{id}";
    }

    /**
     * THIS TEST IS KNOWN TO FAIL
     * 
     * Due to an issue with the CompensationController class being unable to use @Autowired on the CompensationService,
     * this test is guaranteed to fail as the Compensation code is not working and cannot be tested properly
     */
    @Test
    public void compensationTest(){
        //Setup
        Employee employee = new Employee();

        String employeeID = UUID.randomUUID().toString();

        employee.setEmployeeId(employeeID);
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setPosition("Developer");
        employee.setDepartment("Research and Development");

        Double salary = 100000.00;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 2, 1, 10, 0, 0);
        Date effectiveDate = calendar.getTime();
        
        Compensation compensation = new Compensation(employee, salary, effectiveDate);

        //Create Test
        Compensation returnedCompensation = restTemplate.postForEntity(createCompURL, compensation, Compensation.class).getBody();

        //This assertion is failing because the returned compensation value is all Nulls (due to issues up the line as described in the method header)
        assertNotNull(returnedCompensation.getEmployee());
        assertCompensationEquivalence(compensation, returnedCompensation);

        //Read Test
        Compensation readTestCompensation = restTemplate.getForEntity(readCompURL, Compensation.class, employeeID).getBody();
        assertNotNull(readTestCompensation.getEmployee());
        assertCompensationEquivalence(compensation, readTestCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual){
        assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
