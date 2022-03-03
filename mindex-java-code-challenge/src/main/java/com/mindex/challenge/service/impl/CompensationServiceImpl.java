package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeService emplyoeeService;

    /**
     * Add the new compensation to the CompensationRepository so it is persisted
     */
    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);
        compensationRepository.insert(compensation);
        return compensation;
    }

    /**
     * When reading based on the employeeID, we first need to get the employee via the existing
     * endpoints. Once we have the employee, we can find the compensation based on the reported employee
     */
    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation information for employee with id [{}]", id);
        
        Employee employee = emplyoeeService.read(id);

        if(employee == null){
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        Compensation compensation = compensationRepository.findByEmployee(employee);

        if(compensation == null){
            throw new RuntimeException("No compensation found for employee with ID: " + id);
        }

        return compensation;
    }
    
}
