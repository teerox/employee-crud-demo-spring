package com.example.cruddemo.dao_old;

import com.example.cruddemo.entity.Course;
import com.example.cruddemo.entity.Instructor;
import com.example.cruddemo.entity.InstructorDetail;
import com.example.cruddemo.entity.Student;

import java.util.List;


public interface AppDAO {

    public void saveInstructor(Instructor instructor);

    public void saveCourse(Course course);

    public Instructor findInstructorById(int id);

    public List<Course> findCoursesByInstructorId(int id);

    public List<Course> findCoursesByInstructorIdJoinFetch(int id);

    public void updateInstructor(Instructor instructor);

    public void deleteInstructor(int id);

    public void deleteCourse(int id);

    public void createCourseAndReviews(Course course);

    public Course retrieveCourseAndReviews(int id);

    public void saveCourseAndStudents(Course course);

    public Course retrieveCourseAndStudents(int id);

    public Student retrieveStudentAndCourses(int id);

    public void updateStudent(Student student);

    public void deleteStudent(int id);


//    public void deleteInstructor(int id);
//
//    public InstructorDetail findInstructorDetail(int id);

//    public List<Employee> getEmployees();
//
//    public Employee getEmployee(int id);
//
//    public void deleteEmployee(int id);
}
