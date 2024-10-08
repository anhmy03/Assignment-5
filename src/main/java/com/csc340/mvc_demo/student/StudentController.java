package com.csc340.mvc_demo.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    /**
     * Get a list of all Students in the database.
     * <a href="http://localhost:8080/students/all">All</a>
     */
    @GetMapping("/all")
    //public List<Student> getAllStudents() {
    public String getAllStudents(Model model) {
        model.addAttribute("studentList", service.getAllStudents());
        model.addAttribute("title", "All Students");
        //return service.getAllStudents();
        return "student-list";
    }

    /**
     * Get a specific Student by Id.
     * <a href="http://localhost:8080/students/2">One</a>
     *
     * @param studentId the unique Id for a Student.
     * @return One Student object.
     */
    @GetMapping("/{studentId}")
    public String getOneStudent(@PathVariable int studentId, Model model) {
        model.addAttribute("student", service.getStudentById(studentId));
        model.addAttribute("title", studentId);
        return "student-details";
    }

    /**
     * Get a list of Students based on their major.
     * <a href="http://localhost:8080/students?major=csc">...</a>
     *
     * @param major the search key.
     * @return A list of Student objects matching the search key.
     */
    @GetMapping("")
    public String getStudentsByMajor(@RequestParam(name = "major", defaultValue = "csc") String major, Model model) {
        model.addAttribute("studentList", service.getStudentsByMajor(major));
        model.addAttribute("title", "Major Students: "+major);
        return "student-list";
    }


    /**
     * Get a list of students with a GPA above a threshold.
     * <a href="http://localhost:8080/students/honors?gpa=3.6">...</a>
     *
     * @param gpa the minimum GPA
     * @return list of Student objects matching the search key.
     */
    @GetMapping("/honors")
    public String getHonorsStudents(@RequestParam(name = "gpa", defaultValue = "3.0") double gpa, Model model) {
        model.addAttribute("studentList", service.getHonorsStudents(gpa));
        model.addAttribute("title", "Honors Students: "+gpa);
        return "student-list";
    }

    /**
     * Create a new Student entry.
     * <a href="http://localhost:8080/students/new">...</a>
     *
     * @param student the new Student object.
     * @return the updated list of Students.
     */
    @PostMapping("/new")
    public String addNewStudent(Student student) {
        service.addNewStudent(student);
        return "redirect:/students/all";
    }


    /**
     * Show the update form.
     * @param studentId
     * @param model
     * @return
     */
    @GetMapping("/update/{studentId}")
    public String showUpdateForm(@PathVariable int studentId, Model model) {
        model.addAttribute("student", service.getStudentById(studentId));
        return "student-update";
    }

    /**
     * Perform the update.
     * @param student
     * @return
     */
    @PostMapping("/update")
    public String updateStudent(Student student) {
        service.addNewStudent(student);
        return "redirect:/students/" + student.getStudentId();
    }


    /**
     * Delete a Student object.
     * <a href="http://localhost:8080/students/delete/2">Delete One</a>
     *
     * @param studentId the unique Student Id.
     * @return the updated list of Students.
     */
    //@DeleteMapping("/delete/{studentId}")
    @GetMapping("/delete/{studentId}")
    public String deleteStudentById(@PathVariable int studentId) {
        service.deleteStudentById(studentId);
        return "redirect:/students/all";
    }
}
