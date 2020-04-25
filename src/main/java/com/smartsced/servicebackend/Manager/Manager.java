package com.smartsced.servicebackend.Manager;

import com.smartsced.servicebackend.Entities.EmployeeEntity;
import com.smartsced.servicebackend.Entities.ServiceEntity;
import com.smartsced.servicebackend.Repository.EmployeeRepository;
import com.smartsced.servicebackend.Repository.ServiceRepository;
import com.smartsced.servicebackend.Resources.EmployeeResource;
import com.smartsced.servicebackend.Resources.ServiceResource;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Manager {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ServiceRepository serviceRepository;


    public List<EmployeeResource> getEmployees() {
        List<EmployeeResource> result = new ArrayList<>();

        for (EmployeeEntity cEmployee : employeeRepository.findAll()){

            result.add(convertEmployeeEntityToEmployeeResource(cEmployee));
        }

        return result;
    }


    public EmployeeResource addEmployee(EmployeeResource employeeResource) {

        EmployeeResource newEmp = employeeResource;

        newEmp.setId(-1);

        EmployeeEntity storedEmployee = employeeRepository.save(convertEmployeeResourceToEmployeeEntity(newEmp));

        return convertEmployeeEntityToEmployeeResource(storedEmployee);
    }

    public List<ServiceResource> getServices() {
        List<ServiceResource> result = new ArrayList<>();

        for (ServiceEntity cService : serviceRepository.findAll()){

            result.add(convertServiceEntityToServiceResource(cService));
        }

        return result;
    }

    public ServiceResource addService(ServiceResource serviceResource) {
        ServiceResource newService = serviceResource;

        newService.setId(-1);

        ServiceEntity storedService = serviceRepository.save(convertServiceResourceToServiceEntity(newService));

        return convertServiceEntityToServiceResource(storedService);
    }



    public ServiceResource deleteService(int serviceId) throws NotFound {

        if(!serviceRepository.findById(serviceId).isPresent())
            throw new NotFound();

        ServiceResource serviceToRemove = convertServiceEntityToServiceResource(serviceRepository.findById(serviceId).get());

        serviceRepository.deleteById(serviceId);

        return serviceToRemove;
    }

    public ServiceResource getService(int serviceId) throws NotFound {
        Optional<ServiceEntity> foundService = serviceRepository.findById(serviceId);
        if(!foundService.isPresent()){
            throw new NotFound();
        }

        return convertServiceEntityToServiceResource(foundService.get());
    }

    public ServiceResource updateService(int serviceId, ServiceResource serviceResource) throws NotFound {
        if(!serviceRepository.findById(serviceId).isPresent()) {
            throw new NotFound();
        }
        ServiceResource serviceToUpdate = convertServiceEntityToServiceResource(serviceRepository.findById(serviceId).get());

        serviceToUpdate.setEmployee(serviceResource.getEmployee());
        serviceToUpdate.setDate(serviceResource.getDate());
        serviceToUpdate.setLatitude(serviceResource.getLatitude());
        serviceToUpdate.setLongitude(serviceResource.getLongitude());
        serviceToUpdate.setName(serviceResource.getName());

        ServiceEntity changedS = serviceRepository.save(convertServiceResourceToServiceEntity(serviceToUpdate));

        return convertServiceEntityToServiceResource(changedS);

    }

    public boolean employeeResourceExists(int employeeId){
        return employeeRepository.findById(employeeId).isPresent();
    }

    private EmployeeEntity convertEmployeeResourceToEmployeeEntity(EmployeeResource employeeResource){
        EmployeeEntity result = new EmployeeEntity();

        if(employeeResource.getId() != -1){
            result.setId(employeeResource.getId());
        }
        result.setLatitude(employeeResource.getLatitude());
        result.setLongitude(employeeResource.getLongitude());
        result.setName(employeeResource.getName());

        return result;
    }
    private EmployeeResource convertEmployeeEntityToEmployeeResource(EmployeeEntity employeeEntity){
        EmployeeResource result = new EmployeeResource();

        result.setId(employeeEntity.getId());
        result.setLongitude(employeeEntity.getLongitude());
        result.setLatitude(employeeEntity.getLatitude());
        result.setName(employeeEntity.getName());

        return result;
    }
    private ServiceEntity convertServiceResourceToServiceEntity(ServiceResource serviceResource){
        ServiceEntity result = new ServiceEntity();

        if(serviceResource.getId() != -1){
            result.setId(serviceResource.getId());
        }
        result.setLatitude(serviceResource.getLatitude());
        result.setLongitude(serviceResource.getLongitude());
        result.setName(serviceResource.getName());
        result.setDatee(serviceResource.getDate());
        result.setEmployee(convertEmployeeResourceToEmployeeEntity(serviceResource.getEmployee()));


        return result;
    }
    private ServiceResource convertServiceEntityToServiceResource(ServiceEntity serviceEntity){
        ServiceResource result = new ServiceResource();

        result.setId(serviceEntity.getId());
        result.setLongitude(serviceEntity.getLongitude());
        result.setLatitude(serviceEntity.getLatitude());
        result.setName(serviceEntity.getName());
        result.setDate(serviceEntity.getDatee());
        result.setEmployee(convertEmployeeEntityToEmployeeResource(serviceEntity.getEmployee()));

        return result;
    }

    public EmployeeResource getEmployee(int employeeId) throws NotFound {
        Optional<EmployeeEntity> foundEmp = employeeRepository.findById(employeeId);
        if(!foundEmp.isPresent()){
            throw new NotFound();
        }

        return convertEmployeeEntityToEmployeeResource(foundEmp.get());
    }
}
