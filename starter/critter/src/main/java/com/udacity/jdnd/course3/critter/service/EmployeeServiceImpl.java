package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee getEmployee(long id) {
        return employeeRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employeeInDB = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        employeeInDB.setDaysAvailable(daysAvailable);
        employeeRepository.save(employeeInDB);
    }

    @Override
    public List<Employee> findEmployeesForService(DayOfWeek dayOfWeek, Set<EmployeeSkill> employeeSkills) {
        List<Employee> allEmployeeList = employeeRepository.findAll();
        List<Employee> employeeList = new LinkedList<>();
        for (Employee employee:allEmployeeList){
            if (employee.getDaysAvailable().contains(dayOfWeek) && employee.getSkills().containsAll(employeeSkills)){
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

}
