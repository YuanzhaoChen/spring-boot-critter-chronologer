package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @Override
    public Schedule getSchedule(long id) {
        return scheduleRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Schedule> getAllSchedule() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getScheduleForPet(long petId) {
        List<Schedule> scheduleList = new LinkedList<>();
        Pet pet = petService.getPet(petId);
        for (Schedule schedule : pet.getScheduleList()) {
            scheduleList.add(schedule);
        }
        return scheduleList;
    }

    @Override
    public List<Schedule> getScheduleForEmployee(long employeeId) {
        List<Schedule> scheduleList = new LinkedList<>();
        Employee employee = employeeService.getEmployee(employeeId);
        for (Schedule schedule : employee.getScheduleList()) {
            scheduleList.add(schedule);
        }
        return scheduleList;
    }

    @Override
    public List<Schedule> getScheduleForCustomer(long customerId) {
        List<Schedule> scheduleList = new LinkedList<>();
        List<Pet> petList = customerService.getCustomer(customerId).getPetList();
        for (Pet pet : petList) {
            scheduleList = Stream.concat(scheduleList.stream(), getScheduleForPet(pet.getId()).stream()).collect(Collectors.toList());
        }
        return scheduleList;
    }

    @Override
    @Transactional
    public Schedule saveSchedule(Schedule schedule) {
        Schedule scheduleInDB = scheduleRepository.save(schedule);
        for (Employee employee : scheduleInDB.getEmployeeSet()) {
            List<Schedule> scheduleList = employee.getScheduleList();
            scheduleList.add(scheduleInDB);
            employee.setScheduleList(scheduleList);
            employeeService.saveEmployee(employee);
        }
        for (Pet pet : scheduleInDB.getPetSet()) {
            List<Schedule> scheduleList = pet.getScheduleList();
            scheduleList.add(scheduleInDB);
            pet.setScheduleList(scheduleList);
            petService.savePet(pet);
        }
        return scheduleInDB;
    }
}
