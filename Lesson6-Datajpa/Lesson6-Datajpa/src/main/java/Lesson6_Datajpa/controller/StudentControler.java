package Lesson6_Datajpa.controller;

import Lesson6_Datajpa.dto.StudentDTO;
import Lesson6_Datajpa.entity.Student;
import Lesson6_Datajpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentControler {
    @Autowired
    private StudentService studentService;

    public StudentControler(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.fillAll());
        return "students/student-list";
    }

    @GetMapping("add-new")
    public String addNew(Model model) {
        model.addAttribute("student", new Student());
        return "students/student-add";
    }
    @PostMapping
    public String saveStudent(@ModelAttribute("student")StudentDTO student)
    {
        studentService.save(student);
        return "redirect:/students";
    }
    @GetMapping("/edit/{id}")
    public String showFormUpdate(@PathVariable(value = "id")Long id,Model model)
    {
        StudentDTO student=studentService.findById(id).orElseThrow(()->new IllegalArgumentException("Invail studentId:"+id));
        model.addAttribute("student",student);
        return "redirect:/students";
    }
    @PostMapping("/update/{id}")
    public String updatestudent(@PathVariable(value = "id")Long id, @ModelAttribute("student")StudentDTO student)
    {
        studentService.updateStudent(id,student);
        return "redirect:/students";
    }
    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable(value = "id")Long id)
    {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

}
