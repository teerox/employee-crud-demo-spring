package com.example.cruddemo.dao_old;

import com.example.cruddemo.entity.Course;
import com.example.cruddemo.entity.Instructor;
import com.example.cruddemo.entity.InstructorDetail;
import com.example.cruddemo.entity.Student;
import com.example.cruddemo.repository_new.AppRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {
    private final EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveInstructor(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    @Transactional
    public void saveCourse(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class, id);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {
        return entityManager
                .createQuery("select c from Course c where c.instructor.id = :instructorId", Course.class)
                .setParameter("instructorId", id)
                .getResultList();
    }

    @Override
    public List<Course> findCoursesByInstructorIdJoinFetch(int id) {
        return entityManager
                .createQuery("select c from Course c join fetch c.instructor where c.instructor.id = :instructorId", Course.class)
                .setParameter("instructorId", id)
                .getResultList();
    }

    @Override
    @Transactional
    public void updateInstructor(Instructor instructor) {
        entityManager.createQuery("update Instructor i set i.firstName = :firstName, i.lastName = :lastName, i.email = :email where i.id = :id")
                .setParameter("firstName", instructor.getFirstName())
                .setParameter("lastName", instructor.getLastName())
                .setParameter("email", instructor.getEmail())
                .setParameter("id", instructor.getId())
                .executeUpdate();
    }

    @Override
    @Transactional
    public void deleteInstructor(int id) {
        Instructor instructor = entityManager.find(Instructor.class, id);
        if (instructor != null) {
            for (Course course : instructor.getCourses()) {
                course.setInstructor(null);
            }
        }

        entityManager.remove(instructor);
    }

    @Override
    @Transactional
    public void deleteCourse(int id) {
        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            course.setInstructor(null);
        }

        entityManager.remove(course);
    }

    @Override
    @Transactional
    public void createCourseAndReviews(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course retrieveCourseAndReviews(int id) {
        return entityManager.createQuery("select c from Course c join fetch c.review where c.id = :id", Course.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void saveCourseAndStudents(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course retrieveCourseAndStudents(int id) {
        return entityManager.createQuery(
                "select c from Course c join fetch c.students where c.id = :id", Course.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Student retrieveStudentAndCourses(int id) {
        return entityManager.createQuery(
                "select s from Student s join fetch s.courses where s.id = :id", Student.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void updateStudent(Student student) {
        entityManager.merge(student);
    }

    @Override
    @Transactional
    public void deleteStudent(int id) {
        Student student = entityManager.find(Student.class, id);
//        if (student != null) {
//            for (Course course : student.getCourses()) {
//                course.getStudents().remove(student);
//            }
//        }

        entityManager.remove(student);
    }
}
