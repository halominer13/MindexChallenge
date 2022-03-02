package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Generating reporting structure for employee with ID [{}]", id);

        //Find the employee in question
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            //The employee object was null / the employee does not exist
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        
        //Constructor automatically handles finding number of reporting employees
        ReportingStructure reportingStructure = new ReportingStructure(employee, this.employeeRepository);

        return reportingStructure;
    }

    
    
}
