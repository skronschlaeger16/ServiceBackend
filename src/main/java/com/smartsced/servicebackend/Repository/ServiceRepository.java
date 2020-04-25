package com.smartsced.servicebackend.Repository;

import com.smartsced.servicebackend.Entities.ServiceEntity;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<ServiceEntity,Integer> {

}
