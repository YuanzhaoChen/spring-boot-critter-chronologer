package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule getSchedule(long id);

    List<Schedule> getAllSchedule();

    List<Schedule> getScheduleForPet(long petId);

    List<Schedule> getScheduleForEmployee(long employeeId);

    List<Schedule> getScheduleForCustomer(long customerId);

    Schedule saveSchedule(Schedule schedule);
}
