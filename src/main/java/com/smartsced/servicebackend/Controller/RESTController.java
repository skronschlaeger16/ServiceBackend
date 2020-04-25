package com.smartsced.servicebackend.Controller;

import com.smartsced.servicebackend.DataService.ServiceDataService;
import com.smartsced.servicebackend.Dtos.EmployeeDto;
import com.smartsced.servicebackend.Dtos.ServiceDto;
import com.smartsced.servicebackend.Resources.EmployeeResource;
import com.smartsced.servicebackend.Resources.ServiceResource;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @CrossOrigin
    @RestController
    public class RESTController {
        @Autowired
        private ServiceDataService serviceDataService;
        @RequestMapping(value ="/services", method = RequestMethod.GET)
        public HttpEntity<List<ServiceResource>> getAllServices() {
            List<ServiceResource> serviceResources = serviceDataService.getServices();
            return new HttpEntity<>(serviceResources);
        }
        @RequestMapping(value ="/services/{serviceId}", method = RequestMethod.GET)
        public ServiceResource getService(@PathVariable int serviceId) {
           return serviceDataService.getServices(serviceId);

        }

        @RequestMapping(value ="/services", method = RequestMethod.POST)
        public ServiceResource addService(@RequestBody ServiceDto serviceDto) {
            return serviceDataService.addService(serviceDto);
        }
//
        @RequestMapping(value ="/services/{serviceId}", method = RequestMethod.PUT)
        public ServiceResource editService(@PathVariable int serviceId, @RequestBody ServiceDto
                serviceDto) throws NotFound {
            return serviceDataService.editService(serviceId, serviceDto);
        }
//
        @RequestMapping(value ="/services/{serviceId}", method = RequestMethod.DELETE)
        public ServiceResource deleteService(@PathVariable int serviceId) {
            return serviceDataService.deleteService(serviceId);
        }

        @RequestMapping(value ="/employees", method = RequestMethod.GET)
        public HttpEntity<List<EmployeeResource>>  getEmployees() {
            List<EmployeeResource>empResources = serviceDataService.getEmployees();
            return new HttpEntity<>(empResources);
        }

        @RequestMapping(value ="/employees", method = RequestMethod.POST)
        public EmployeeResource  addEmployees(@RequestBody EmployeeDto serviceDto) {

            return serviceDataService.addEmployees(serviceDto);
        }
    }
