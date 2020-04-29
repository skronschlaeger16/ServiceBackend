package com.smartsced.servicebackend.DataService;

import com.smartsced.servicebackend.Dtos.EmployeeDto;
import com.smartsced.servicebackend.Dtos.LongitudeLatitude;
import com.smartsced.servicebackend.Dtos.ServiceDto;
import com.smartsced.servicebackend.Entities.ServiceEntity;
import com.smartsced.servicebackend.Exceptions.EmployeesBadRequestException;
import com.smartsced.servicebackend.Exceptions.NotFoundException;
import com.smartsced.servicebackend.Manager.Manager;
import com.smartsced.servicebackend.Repository.ServiceRepository;
import com.smartsced.servicebackend.Resources.EmployeeResource;
import com.smartsced.servicebackend.Resources.ServiceResource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ServiceDataService {
    @Autowired
    Manager manager;

    @Autowired
    private LocationIQDataService locationIQDataService;

    private HashMap<Integer, ServiceDto> services = new HashMap<>();
    private HashMap<Integer, EmployeeDto> employees = new HashMap<>();



    public List<EmployeeResource> getEmployees()
    {
       return manager.getEmployees();
    }

    public EmployeeResource addEmployees(EmployeeDto employeeDto)
    {
        if(isNullOrEmpty(employeeDto.getName())) {
            throw new EmployeesBadRequestException("The name of the employee must be set");

        }
        if(isNullOrEmpty(employeeDto.getAddress())) {
            throw new EmployeesBadRequestException("The name of the address must be set");

        }


        if(employeeDto.getName().length()<=3)
        {

       throw new EmployeesBadRequestException("The length of the name of the employee is smaller than 3");


        }
       /* EmployeeDto newService = new EmployeeDto();
        newService.setId(this.employees.size() + 1);

        newService.setLongitude(employeeDto.getLongitude());
        newService.setLatitude(employeeDto.getLatitude());
        newService.setName(employeeDto.getName());


        this.employees.put(newService.getId(), newService);*/
        return manager.addEmployee(convertEmployeeToEmployeeResource(employeeDto));
    }

    private boolean isNullOrEmpty(String name) {
        return name == null || name.equals("");
    }

    private EmployeeResource convertEmployeeToEmployeeResource(EmployeeDto emp) {
        EmployeeResource result = new EmployeeResource();
        result.setName(emp.getName());
        LongitudeLatitude longitudeLatitude = locationIQDataService.getLongitudeLatitudeByAddress(emp.getAddress());

        result.setLatitude(longitudeLatitude.getLatitude());
        result.setLongitude(longitudeLatitude.getLongitude());

        return result;
    }

    public List<ServiceResource> getServices() {

        return manager.getServices();
    }

    public ServiceResource getServices(int serviceId)
    {     ServiceResource result;

        try{
            result =  manager.getService(serviceId);
        }catch(Exception e){
            throw new NotFoundException(String.format("The service with the serviceId: %d is not existing.", serviceId));
        }

        return result;
    }

    private ServiceResource convertServiceToService(ServiceDto sService) {
        ServiceResource result = new ServiceResource();
     //   String[] longAndLati = getLongitudeAndLatitudeFromAddress(sService.getAddress());


            result.setEmployee(manager.getEmployee(sService.getEmployeeId()));

        result.setDate(sService.getDate());


        LongitudeLatitude longitudeLatitude = locationIQDataService.getLongitudeLatitudeByAddress(sService.getAddress());

        result.setLatitude(longitudeLatitude.getLatitude());
        result.setLongitude(longitudeLatitude.getLongitude());
      //  result.setId(sService.getId());

        result.setName(sService.getName());
        return result;
    }

    private String[] getLongitudeAndLatitudeFromAddress(String address) {
        String[] result = address.split(",");

        return result;
    }

    public ServiceResource addService(ServiceDto serviceDto) {
        if(serviceDto.getName().length()<=2)
        {

            throw new EmployeesBadRequestException("The length of the service of the employee is smaller than 2");


        }
        if(serviceDto.getEmployeeId()==0)
        {
            throw new NotFoundException("employee not found");
        }
        if(isNullOrEmpty(serviceDto.getName()))
        {
            throw new EmployeesBadRequestException("name is null");
        }
        if(isNullOrEmpty(serviceDto.getDate()))
        {
            throw new EmployeesBadRequestException("date is null");
        }
        if(isNullOrEmpty(serviceDto.getAddress()))
        {
            throw new EmployeesBadRequestException("address is null");
        }

        if(serviceDto.getAddress().length()<=3)
        {
            throw new EmployeesBadRequestException("Address is smaller than 3");
        }

//        ServiceDto newService = new ServiceDto();
//        newService.setId(this.services.size() + 1);
//
//        newService.setAddress(serviceDto.getAddress());
//        newService.setName(serviceDto.getName());
//        newService.setDate(serviceDto.getDate());
//        newService.setEmployeeId(serviceDto.getEmployeeId());
//
//     ServiceEntity serviceEntity = this.serviceRepository.save(convertServiceToServiceEntity(newService));
        return manager.addService(convertServiceToService(serviceDto));
    }
//
//    private int psToInt(String psString) {
//        String psRaw = psString.replace(" ", "").replace("PS", "");
//        return Integer.parseInt(psRaw);
//    }
//
    public ServiceResource editService(int serviceId, ServiceDto serviceDto) {
      ServiceResource result;
      try {
          result = manager.updateService(serviceId,convertServiceToService(serviceDto));
      }
      catch(Exception e)
      {
          throw new NotFoundException(String.format("The service with the serviceId: %d is not existing.", serviceId));
      }

        return result;
    }
    
//
    public ServiceResource deleteService(int serviceId) {

        ServiceResource res;
        try {
            res = manager.deleteService(serviceId);
        }
        catch(Exception e)
        {
            throw new NotFoundException(String.format("The service with the serviceId: %d is not existing.", serviceId));
        }
        return res;
    }

    public ServiceEntity convertServiceToServiceEntity(ServiceDto s)
    {
        ServiceEntity result = new ServiceEntity();

        if(s.getId() != -1)
        {
            result.setId(s.getId());
        }

       // result.setAddress(s.getAddress());
        result.setDatee(s.getDate());
    //    result.setEmployeeId(s.getEmployeeId());
        result.setName(s.getName());
        return result;
    }

    public ServiceDto convertServiceEntityToService(ServiceEntity se)
    {
        ServiceDto result = new ServiceDto();

        result.setId(se.getId());
       // result.setAddress(se.getAddress());
        result.setDate(se.getDatee());
       // result.setEmployeeId(se.getEmployeeId());
        result.setName(se.getName());

        return result;
    }




}
