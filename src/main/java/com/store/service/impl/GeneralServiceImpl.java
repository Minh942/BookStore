package com.store.service.impl;

import java.sql.Timestamp;

import com.store.dao.UserDao;
import com.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.store.model.EmployeeForm;
import com.store.service.GeneralService;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class GeneralServiceImpl implements GeneralService {
	@Autowired
    UserDao userDao;

	@Override
	public EmployeeForm createEmployee(EmployeeForm employeeForm) {
		// Them user
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		User user = new User();
		user.setEmail(employeeForm.getEmail());
		user.setPassword("1234567");
		user.setFullname(employeeForm.getFullname());
		user.setCreateday(timestamp.toString());
		userDao.save(user);

		// Them moi mot employee
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		return employeeForm;
	}

	@Override
	public EmployeeForm getOneUserById(Integer id) {
		User user = userDao.findById(id).get();
		EmployeeForm employeeForm = new EmployeeForm();
		employeeForm.setFullname(user.getFullname());
		employeeForm.setEmail(user.getEmail());

		return employeeForm;
	}

	@Override
	public EmployeeForm updateEmployee(EmployeeForm employeeForm) {
		// Cap nhat user
		User user = userDao.findById(employeeForm.getId()).get();
		user.setEmail(employeeForm.getEmail());
		user.setFullname(employeeForm.getFullname());

		// Cap nhat employee
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();

		return employeeForm;
	}

}
