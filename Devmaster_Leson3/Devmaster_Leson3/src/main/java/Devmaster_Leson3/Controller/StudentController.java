package Devmaster_Leson3.Controller;

import Devmaster_Leson3.Entity.Student;
import Devmaster_Leson3.Service.ServicesStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private ServicesStudent servicesStudent;
    @GetMapping("/student-list")
    public List<Student>getAllStudents()
    {
        return servicesStudent.getStudents();
    }
    @GetMapping("/students/{id}")
    public Student getAllStudents(@PathVariable String id)
    {
        Long param=Long.parseLong(id);
        return servicesStudent.getStudentId(param);
    }
    @PostMapping("/student-add")
    public Student addStudent(@RequestBody Student student)
    {
        return servicesStudent.addStudents(student);
    }
    @PutMapping("/student/{id}")
    public Student updateStudent(@PathVariable String id,@RequestBody Student student)
    {
        Long param=Long.parseLong(id);
        return servicesStudent.updateStudent(param,student);
    }
    @DeleteMapping("/student/{id}")
    public boolean deleteStudent(@PathVariable String id)
    {
        Long param=Long.parseLong(id);
        return  servicesStudent.deleteStudent(param);
    }
}
