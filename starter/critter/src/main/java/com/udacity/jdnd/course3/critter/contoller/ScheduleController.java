package com.udacity.jdnd.course3.critter.contoller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertScheduleTOScheduleDTO(scheduleService.saveSchedule(convertScheduleDTOToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOList = new LinkedList<>();
        List<Schedule> scheduleList = scheduleService.getAllSchedule();
        for (Schedule schedule : scheduleList) {
            scheduleDTOList.add(convertScheduleTOScheduleDTO(schedule));
        }
        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOList = new LinkedList<>();
        List<Schedule> scheduleList = scheduleService.getScheduleForPet(petId);
        for (Schedule schedule : scheduleList) {
            scheduleDTOList.add(convertScheduleTOScheduleDTO(schedule));
        }
        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOList = new LinkedList<>();
        List<Schedule> scheduleList = scheduleService.getScheduleForEmployee(employeeId);
        for (Schedule schedule : scheduleList) {
            scheduleDTOList.add(convertScheduleTOScheduleDTO(schedule));
        }
        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOList = new LinkedList<>();
        List<Schedule> scheduleList = scheduleService.getScheduleForCustomer(customerId);
        for (Schedule schedule : scheduleList) {
            scheduleDTOList.add(convertScheduleTOScheduleDTO(schedule));
        }
        return scheduleDTOList;
    }

    public ScheduleDTO convertScheduleTOScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> petIds = new LinkedList<>();
        for (Pet pet : schedule.getPetSet()) {
            petIds.add(pet.getId());
        }
        scheduleDTO.setPetIds(petIds);

        List<Long> employeeIds = new LinkedList<>();
        for(Employee employee:schedule.getEmployeeSet()){
            employeeIds.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }

    public Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> petIds = scheduleDTO.getPetIds();
        Set<Pet> petSet = new HashSet<>();
        for (Long id : petIds) {
            petSet.add(petService.getPet(id));
        }
        schedule.setPetSet(petSet);

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        Set<Employee> employeeSet = new HashSet<>();
        for (Long id : employeeIds) {
            employeeSet.add(employeeService.getEmployee(id));
        }
        schedule.setEmployeeSet(employeeSet);

        return schedule;
    }

}
