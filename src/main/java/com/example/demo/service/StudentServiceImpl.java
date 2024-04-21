package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.foodProject.model.Role;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dto.StudentDto;
import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;


@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentRepository studentRepository;
    @Resource
    private CourseRepository courseRepository;
    @Transactional
    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        Student student = new Student();
        mapDtoToEntity(studentDto, student);
        Student savedStudent = studentRepository.save(student);
        return mapEntityToDto(savedStudent);
    }



    @Override
    public List<StudentDto> getAllStudents() {
        List<StudentDto> studentDtos = new ArrayList<>();
        List<Student> students = studentRepository.findAll();
        students.stream().forEach(student -> {
            StudentDto studentDto = mapEntityToDto(student);
            studentDtos.add(studentDto);
        });
        return studentDtos;
    }


    @Transactional
    @Override
    public StudentDto updateStudent(Integer id, StudentDto studentDto) {
        Student std = studentRepository.getOne(id);
        std.getCourses().clear();
        mapDtoToEntity(studentDto, std);
        Student student = studentRepository.save(std);
        return mapEntityToDto(student);
    }


    @Override
    public String deleteStudent(Integer studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        //Remove the related courses from student entity.
        if(student.isPresent()) {
            student.get().removeCourses();
            studentRepository.deleteById(student.get().getId());
            return "Student with id: " + studentId + " deleted successfully!";
        }
        return null;
    }


    private void mapDtoToEntity(StudentDto studentDto, Student student) {
        student.setName(studentDto.getName());
        if (null == student.getCourses()) {
            student.setCourses(new HashSet<>());
        }
        studentDto.getCourses().stream().forEach(courseName -> {
            Course course = courseRepository.findByName(courseName);
            if (null == course) {
                course = new Course();
                course.setStudents(new HashSet<>());
            }
            course.setName(courseName);
            student.addCourse(course);
        });
    }


    private StudentDto mapEntityToDto(Student student) {
        StudentDto responseDto = new StudentDto();
        responseDto.setName(student.getName());
        responseDto.setId(student.getId());
        responseDto.setCourses(student.getCourses().stream().map(Course::getName).collect(Collectors.toSet()));
        return responseDto;
    }


}


class Test{

     private static List<Role> roles = new ArrayList<>();

     static {
         roles.add(new Role(1 , "Bob"));
         roles.add(new Role(2 , "Jone"));
         roles.add(new Role(3 , "Mike"));
         roles.add(new Role(4 , "Artur Alex"));
         roles.add(new Role(5 , "Kate"));
         roles.add(new Role(6 , "Artur"));
    }

    public static void main(String[] args) {

       // System.out.println(getRoleByName("Artur"));
        //sortRoles(roles, 2);

    }



    public static void getRoleByName(String name){
        List<Role> roleList = roles;
         roleList.stream().
                 map(Role::getRole).
                 filter(role -> role.equals(name)).
                 forEach(System.out::println);

//        return  roleList.stream().
//                filter(role1 -> role1.getRole().equals(name)).
//                findFirst().
//                orElse(null);


    }

    public static void sortRoles(List<Role> roleList, int from){
         List<Role> roles1 = roleList;
          roles1.stream().filter(role -> role.getId() > from).
                  forEach(System.out::println);

    }


}
