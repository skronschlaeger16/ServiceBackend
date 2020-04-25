package com.smartsced.servicebackend.Repository;

import com.smartsced.servicebackend.Entities.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity,Integer> {
}
