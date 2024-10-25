package com.csc340.mvc_demo.animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    // Get all animals
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    // Get animal by ID
    public Animal getAnimalById(int id) {
        return animalRepository.findById(id).orElse(null);
    }

    // Add new animal
    public void addAnimal(Animal animal) {
        animalRepository.save(animal);
    }

    // Update existing animal
    public void updateAnimal(Animal animal) {

        animalRepository.save(animal);
    }

    // Delete animal by ID
    public void deleteAnimal(int id) {
        animalRepository.deleteById(id);
    }

    // Get animals by species
    public List<Animal> getAnimalsBySpecies(String species) {
        return animalRepository.findBySpecies(species);
    }

    // Get animals by name containing a string
    public List<Animal> getAnimalsByName(String name) {
        return animalRepository.findByNameContaining(name);
    }
}
