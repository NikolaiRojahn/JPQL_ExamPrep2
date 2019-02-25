/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbfacades;

import entity.Semester;
import entity.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author nr
 */
public class Facade {
    
    EntityManagerFactory emf;

    public Facade(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public static void main(String[] args) {
        
    }
    
    public List<Student> findAllStudents() {
        EntityManager em = emf.createEntityManager();
        
        Query q = em.createNamedQuery("Student.findAll");
        List<Student> allStudents = q.getResultList();
        return allStudents;
    }

    public List<Student> findStudentsAnders() {
        EntityManager em = emf.createEntityManager();
        
        Query q = em.createQuery("SELECT s FROM Student s WHERE s.firstname = :firstname");
        List<Student> andersStudents = q.setParameter("firstname", "Anders").getResultList();
        return andersStudents;   
    }

    public void insertStudent(Student s) {
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Student assignStudentToSemester(Student s, Semester sem) {
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            s.setCurrentSemester(sem);
            em.merge(s);
            em.getTransaction().commit();
            return (em.find(Student.class, s.getId()));
        } finally {
            em.close();
        }
    }
    
        public List<Student> findStudentsLastnameAnd() {
        EntityManager em = emf.createEntityManager();
        
        Query q = em.createQuery("SELECT s FROM Student s WHERE s.lastname = :lastname");
        List<Student> andStudents = q.setParameter("lastname", "And").getResultList();
        return andStudents;   
    }

    public List<Student> findStudentsForSemester(String name) {
        EntityManager em = emf.createEntityManager();
        
        Query q = em.createQuery("SELECT COUNT(s) FROM Semester s WHERE s.name = :name");
        q.setParameter("name", name);
        List<Student> studentsForSemester = q.getResultList();
        return studentsForSemester;
    }
    
    
    
}
