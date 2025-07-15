package Devmaster_Leson3.Service;

import Devmaster_Leson3.Entity.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ServicesStudent {
    List<Student>students= new ArrayList<Student>();
    public  ServicesStudent()
    {
        students.addAll(Arrays.asList(new Student(1L,"Devmaster1",20,"Non","So 25 vnp","07727722","Chungtrinhj@gamil.com"),new Student(2L,"Devmaseter2",25,"Non","So 25 vnp","908388383","contact@devmaster.edu.vn"),new
                Student(3L,"Devmaseter3",25,"Non","So 25 vnp","308388383","contact@devmaster.edu.vn")));
    }
    // lay toan bo danh sach sinh vien
    public List<Student>getStudents(){
        return students;
    }
    //lay sinh vien theo id
    public Student getStudentId(Long id)
    {
        return students.stream().filter(student -> student.getId().equals(id)).findFirst().orElse(null);
    }
    // them moi 1 sinh vien
    public Student addStudents (Student student)
    {
        students.add(student);
        return student;
    }
    // cap nhat thong tin sinh vien
    public Student updateStudent(Long id,Student student)
    {
        Student check=getStudentId(id);
        if(check==null)
        {
            return  null;
        }
        students.forEach(item->{
            if(item.getId()==id){
                item.setName(student.getName());
                item.setAddress(student.getAddress());
                item.setEmail(student.getEmail());
                item.setPhone(student.getPhone());
                item.setAge(student.getAge());
                item.setGender(student.getGender());
            }
        });
        return student;
    }
    // xoa thong tin sinh vien
    public boolean deleteStudent(Long id)
    {
        Student check=getStudentId(id);
        return students.remove(check);
    }
}
