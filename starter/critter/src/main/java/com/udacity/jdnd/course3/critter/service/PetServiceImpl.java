package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    public Pet getPet(long id) {
        return petRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    @Transactional
    public Pet savePet(Pet pet) {
        Pet petInDB = petRepository.save(pet);
        Customer customer = pet.getOwner();
        List<Pet> newPetList = customer.getPetList();
        newPetList.add(pet);
        customer.setPetList(newPetList);
        customerService.saveCustomer(customer);
        return petInDB;
    }

}
