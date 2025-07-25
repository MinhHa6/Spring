package Lesson6_Datajpa.service;

import Lesson6_Datajpa.dto.StudentDTO;
import Lesson6_Datajpa.entity.Student;
import Lesson6_Datajpa.repository.StudentRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<StudentDTO> fillAll()
    {
        return studentRepository.findAll()
                .stream()
                .map(student -> {
                    StudentDTO dto = new StudentDTO();
                    dto.setId(student.getId());
                    dto.setName(student.getName());
                    dto.setEmail(student.getEmail());
                    dto.setAge(student.getAge());
                    return dto;
                })
                .toList(); // Hoặc .collect(Collectors.toList())
    }
    public Optional<StudentDTO> findById(Long id)
    {
        Student student= studentRepository.findById(id).orElse(null);
        StudentDTO studentDTO= new StudentDTO();
        studentDTO.setId(id);
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setAge(student.getAge());
        return Optional.of(studentDTO);
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
            return studentRepository.save(student); // phải có return trong map
        }).orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
    }

    //delete
    public void deleteStudent(Long id)
    {
        studentRepository.deleteById(id);
    }
}
