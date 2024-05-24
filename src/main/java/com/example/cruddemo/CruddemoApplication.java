package com.example.cruddemo;

import com.example.cruddemo.dao_old.AppDAO;
import com.example.cruddemo.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return args -> {
			//System.out.println("Hello Spring Boot");
			//addMoreCourseForStudent(appDAO);
			//createInstructorWithCourses(appDAO);
			//retrieveStudentAndCourses(appDAO);


			//createCourseAndReviews(appDAO);
			//retrieveCourseAndReviews(appDAO);
			//updateInstructor(appDAO);
			//findCoursesByInstructorId(appDAO);
			//deleteCourse(appDAO);
			//deleteStudent(appDAO);
		};
	}

	private void findInstructorWithCourses(AppDAO appDAO) {
		Instructor instructor = appDAO.findInstructorById(1);
		System.out.println("Instructor: " + instructor);
		System.out.println("Courses: " + instructor.getCourses());


	}

	private void createInstructorWithCourses(AppDAO appDAO) {
		Instructor instructor = new Instructor("John", "Doe", "joh@gmail.com");
		InstructorDetail instructorDetail = new InstructorDetail("http://www.youtube.com", "coding");
		instructor.setInstructorDetail(instructorDetail);

		Course course1 = new Course("Course 1");
		Course course2 = new Course("Course 2");

		instructor.setCourses(Arrays.asList(course1, course2));
		course1.setInstructor(instructor);
		course2.setInstructor(instructor);

		course1.addReview(new Review("Great course"));
		course2.addReview(new Review("Awesome course"));

		course1.addStudent(new Student("John", "Doe", "oduekene@gmail.com"));
		course2.addStudent(new Student("Anthony", "Odu", "ekene@gmail.com"));

		System.out.println("Instructor before saving: " + instructor);
		appDAO.saveInstructor(instructor);

		System.out.println("Instructor after saving: " + instructor);
	}


	private void  findCoursesByInstructorId(AppDAO appDAO) {
		Instructor instructor = appDAO.findInstructorById(1);

		List<Course> courses = appDAO.findCoursesByInstructorId(1);

		instructor.setCourses(courses);

		System.out.println("Cour: " + instructor.getCourses());
		System.out.println("Courses: " + courses);
	}

	private void findCoursesByInstructorIdJoinFetch(AppDAO appDAO) {
		List<Course> courses = appDAO.findCoursesByInstructorIdJoinFetch(1);

		System.out.println("All Courses: " + courses);
	}

	private void updateInstructor(AppDAO appDAO) {
		Instructor instructor = appDAO.findInstructorById(1);
		instructor.setFirstName("Anthony");
		instructor.setLastName("Odu");
		appDAO.updateInstructor(instructor);
	}

	private void deleteInstructor(AppDAO appDAO) {
		appDAO.deleteInstructor(1);
	}

	private void deleteCourse(AppDAO appDAO) {
		appDAO.deleteCourse(3);
	}

	private void createCourseAndReviews(AppDAO appDAO) {
		Course course = new Course("Course 3");
		course.addReview(new Review("Great course"));
		course.addReview(new Review("Awesome course"));
		appDAO.createCourseAndReviews(course);
	}

	private void retrieveCourseAndReviews(AppDAO appDAO) {
		Course course = appDAO.retrieveCourseAndReviews(1);
		System.out.println("Course: " + course);
		//System.out.println("Reviews: " + course.getReview());

	}

	private void saveCourseAndStudents(AppDAO appDAO) {
		Course course = new Course("Course 4");
		course.addStudent(new Student("John", "Doe", "oduekene@gmail.com"));
		course.addStudent(new Student("Anthony", "Odu", "ekene@gmail.com"));
		appDAO.saveCourseAndStudents(course);

	}

	private void retrieveCourseAndStudents(AppDAO appDAO) {
		Course course = appDAO.retrieveCourseAndStudents(1);
		System.out.println("Course: " + course);
		System.out.println("Students: " + course.getStudents());
	}

	private void retrieveStudentAndCourses(AppDAO appDAO) {
		Student student = appDAO.retrieveStudentAndCourses(1);
		System.out.println("Course: " + student);
		System.out.println("Students: " + student.getCourses());
	}

	private void addMoreCourseForStudent(AppDAO appDAO) {
		Student student = appDAO.retrieveStudentAndCourses(1);
		Course course = new Course("Course 5");
		Course course2 = new Course("Game Development");
		Course course3 = new Course("Car Racing");
		student.addCourse(course);
		student.addCourse(course2);
		student.addCourse(course3);

		System.out.println("Student: " + student);
		System.out.println("Courses: " + student.getCourses());

		appDAO.updateStudent(student);
	}

	private void deleteStudent(AppDAO appDAO) {
		appDAO.deleteStudent(1);
	}
}
