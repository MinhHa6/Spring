package Devmaster_Lesson4.service;

import Devmaster_Lesson4.dto.EmploeeDTO;
import Devmaster_Lesson4.entity.Employee;
import Devmaster_Lesson4.entity.Khoa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    List<Employee>employees= new ArrayList<>();
    public EmployeeService()
    {
        employees.add(new Employee(1L,"MinhHa","Nam",22,20000f));
        employees.add(new Employee(2L,"Hoang","Nam",26,220000f));
        employees.add(new Employee(3L,"Hien","Nu",11,2000f));
        employees.add(new Employee(4L,"Minh","Nam",23,2000330f));
    }
    public List<Employee> findAll()
    {
        return employees;
    }
    public Boolean create(EmploeeDTO employeeDTO) {
        try {
            Employee employee = new Employee();
            employee.setId((long) (employees.size() + 1));
            employee.setFullName(employeeDTO.getFullName());
            employee.setGender(employeeDTO.getGender());
            employee.setAge(employeeDTO.getAge());
            employee.setSalary(employeeDTO.getSalary());
            employees.add(employee); // thiếu dòng này
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Lay thong tin theo Id
    public Employee getEmployee(Long id)
    {
        return employees.stream().filter(employeess ->employeess.getId().equals(id) ).findFirst().orElse(null);
    }
    //xoa khoa
    public Boolean deleteEmployee(Long id)
    {
        Employee check=getEmployee(id);
        return employees.remove(check);
    }
}
