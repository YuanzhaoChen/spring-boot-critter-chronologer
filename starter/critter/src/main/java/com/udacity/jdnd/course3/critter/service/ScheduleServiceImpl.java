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

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

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
        List<Schedule> scheduleInDB = scheduleRepository.findAll();
        List<Schedule> scheduleList = new LinkedList<>();
        for (Schedule schedule : scheduleInDB) {
            if (schedule.getPetSet().contains(petService.getPet(petId))) {
                scheduleList.add(schedule);
            }
        }
        return scheduleList;
    }

    @Override
    public List<Schedule> getScheduleForEmployee(long employeeId) {
        List<Schedule> scheduleInDB = scheduleRepository.findAll();
        List<Schedule> scheduleList = new LinkedList<>();
        for (Schedule schedule : scheduleInDB) {
            if (schedule.getEmployeeSet().contains(employeeService.getEmployee(employeeId))) {
                scheduleList.add(schedule);
            }
        }
        return scheduleList;
    }

    @Override
    public List<Schedule> getScheduleForCustomer(long customerId) {
        List<Schedule> scheduleInDB = scheduleRepository.findAll();
        List<Schedule> scheduleList = new LinkedList<>();
        for (Schedule schedule : scheduleInDB) {
            for (Pet pet : schedule.getPetSet()) {
                if (pet.getOwner().getId()==customerId){
                    scheduleList.add(schedule);
                }
            }
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
