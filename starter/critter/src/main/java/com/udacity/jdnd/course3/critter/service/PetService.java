package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;

import java.util.List;

public interface PetService {
    Pet getPet(long id);

    List<Pet> getAllPets();

    Pet savePet(Pet pet);
}
