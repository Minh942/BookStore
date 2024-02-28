package com.store.service;

import java.util.List;

import com.store.entity.Employee;
import com.store.model.EmployeeModel;

public interface EmployeeService {

	List<EmployeeModel> getListEmployee();

	void save(Employee employee);
	
}
