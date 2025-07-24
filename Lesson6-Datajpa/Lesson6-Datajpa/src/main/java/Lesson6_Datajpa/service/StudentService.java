package Lesson6_Datajpa.service;

import Lesson6_Datajpa.dto.StudentDTO;
import Lesson6_Datajpa.entity.Student;
import Lesson6_Datajpa.repository.StudentRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository=studentRepository;
    }
    //List all
    public List<Student> fillAll()
    {
        return studentRepository.findAll();
    }
    //Sava
    public Boolean save(StudentDTO studentDTO)
    {
        Student student=new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setAge(studentDTO.getAge());
        try
        {
            studentRepository.save(student);
            return  true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    //update
    public Student updateStudent(Long id, StudentDTO studentDTO) {
        return studentRepository.findById(id).map(student -> {
            student.setName(studentDTO.getName());
            student.setEmail(studentDTO.getEmail());
            student.setAge(studentDTO.getAge());
            return student; // phải có return trong map
        }).orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
    }

    //delete
    public void deleteStudent(Long id)
    {
        studentRepository.deleteById(id);
    }
}
