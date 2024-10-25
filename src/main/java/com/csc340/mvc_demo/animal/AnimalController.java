package com.csc340.mvc_demo.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    /**
     * Get a list of all animals.
     * <a href="http://localhost:8080/animals/all">All Animals</a>
     */
    @GetMapping("/all")
    public String getAllAnimals(Model model) {
        model.addAttribute("animalList", animalService.getAllAnimals());
        model.addAttribute("title", "All Animals");
        return "animal-all";
    }

    /**
     * Get a specific animal by its ID.
     * <a href="http://localhost:8080/animals/{animalId}">Animal Details</a>
     */

    @GetMapping("/{animalId}")
    public String getAnimalById(@PathVariable int animalId, Model model) {
        model.addAttribute("animal", animalService.getAnimalById(animalId));
        model.addAttribute("title", animalId);
        return "animal-details";
    }

    /**
     * Get animals by their name (search).
     * <a href="http://localhost:8080/animals/search?name=lion">Search by Name</a>
     */
    @GetMapping("/search")
    public String getAnimalsByName(@RequestParam(name = "name", required = false) String name, Model model) {
        model.addAttribute("animalList", animalService.getAnimalsByName(name));
        model.addAttribute("title", "Search Results for: " + name);
        return "animal-all";
    }

    /**
     * Display the form for creating a new animal.
     * <a href="http://localhost:8080/animals/create">Create New Animal</a>
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "animal-create";
    }

    /**
     * Handle the submission of the create form.
     * <a href="http://localhost:8080/animals/new">Submit New Animal</a>
     */
    @PostMapping("/new")
    public String createAnimal(@ModelAttribute Animal animal) {
        animalService.addAnimal(animal);
        return "redirect:/animals/all";
    }

    /**
     * Display the update form for a specific animal.
     * <a href="http://localhost:8080/animals/update/{animalId}">Update Animal</a>
     */
    @GetMapping("/update/{animalId}")
    public String showUpdateForm(@PathVariable int animalId, Model model) {
        model.addAttribute("animal", animalService.getAnimalById(animalId));
        return "animal-update";
    }

    /**
     * Handle the submission of the update form.
     */
    @PostMapping("/update")
    public String updateAnimal(@ModelAttribute Animal animal) {
        animalService.updateAnimal(animal);
        return "redirect:/animals/" + animal.getAnimalId();
    }

    /**
     * Delete an animal by its ID.
     * <a href="http://localhost:8080/animals/delete/{animalId}">Delete Animal</a>
     */
    @GetMapping("/delete/{animalId}")
    public String deleteAnimalById(@PathVariable int animalId) {
        animalService.deleteAnimal(animalId);
        return "redirect:/animals/all";
    }
}