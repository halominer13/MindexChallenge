package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    /**
     * ISSUE: This autowiring is causing the application to fail on boot for an unknown reason. I've mirrored the employee
     * and reportingStructure for all classes and only this one fails. The error is as follows:
     * 
     * Filed compensationService in com.mindex.challenge.controller.ComensationController required a bean of type 'com.mindex.challenge.service.CompensationService' that could not be found.
     * The injection point has the following annotations:
     *      - @org.springframework.beans.factory.annotation.Autowired(required=true)
     * 
     * Action:
     * Consider defining a bean of type 'com.mindex.challenge.service.CompensationService' in your configuration
     * 
     * I have ensured that the imports are correct and that the service is in the correct folder, so the Spring framework
     * should be able to find the class but doesn't seem to be able to. I also could not find any file that would be acting
     * as a configuration file where this would have to manually be added (and would be surprised if it did need to be manually
     * added seeing as the reportingStructure worked perfectly fine without any manual involvement). Online resources
     * did not help either as they all referenced the file location or setting up a manual configuration. Unsure of how
     * to proceed, as I cannot test any of my Compensation code with this issue.
     * 
     * Leaving the line commented out still allows the Tests and the application to be run correctly with the exception
     * of the compensation REST endpoints.
     */
    //@Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation){
        LOG.debug("Received request to create compensation [{}]", compensation);

        return compensationService.create(compensation);
    }

    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id){
        LOG.debug("Received request to read compensation for employee with ID [{}]", id);

        return compensationService.read(id);
    }
    
}
