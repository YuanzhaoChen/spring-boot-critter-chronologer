package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    Employee getEmployee(long id);

    Employee saveEmployee(Employee employee);

    void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId);

    List<Employee> findEmployeesForService(DayOfWeek dayOfWeek, Set<EmployeeSkill> employeeSkills);
}
