/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dbfacades.Facade;
import entity.Semester;
import entity.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nr
 */
public class FacadeTest {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu", null);

    Facade facade = new Facade(emf);

    @Test
    public void testFindAllStudents() {
        List<Student> allStudents = facade.findAllStudents();
        assertEquals((allStudents.size() >= 6), true);
    }
    
    @Test
    public void testFindStudentsFirstNameAnders() {
        List<Student> andersStudents = facade.findStudentsAnders();
        assertEquals(1, andersStudents.size());
    }
    
    @Test
    public void testInsertStudent() {
        Student s = new Student("Nikolai", "Rojahn");
        
        facade.insertStudent(s);
        assertEquals(facade.findAllStudents().contains(s), true );
    }

    @Test
    public void testAssignStudentToSemester() {
        Semester sem = new Semester("Vinterhold Fagteknikkere", "Fagteknik-19");
        Student s = new Student("Julie", "Friis");
        facade.insertStudent(s);
        Student sUpdated = facade.assignStudentToSemester(s, sem);
        assertEquals("Fagteknik-19", sem.getName());
    }
    
    @Test
    public void testFindStudentsLastnameAnd() {
        List<Student> andStudents = facade.findStudentsLastnameAnd();
        assertEquals(2, andStudents.size());
    }
    
    @Test
    public void testFindStudentsForSemester() {
        List<Student> studentsForSemester = facade.findStudentsForSemester("CLcos-v14e");
        assertEquals(2, studentsForSemester.size());
    }
    
    
    @BeforeClass
    public static void setUpClass() {
        EntityManager em = emf.createEntityManager();
        Query q1 = em.createNativeQuery("INSERT INTO `SEMESTER` VALUES (1,'Computer Science 3. sem','CLcos-v14e'),(2,'Datamatiker 3. sem','CLdat-a14e'),(3,'Datamatiker 3. sem','CLdat-b14e');");
        Query q2 = em.createNativeQuery("INSERT INTO `STUDENT` VALUES (1,'Jens','Jensen',1),(2,'Hans','Hansen',2),(3,'John','Doe',3),(4,'Jane','Doe',3),(5,'Andersine','And',2),(6,'Anders','And',1);");
        Query q3 = em.createNativeQuery("INSERT INTO `TEACHER` VALUES (1,'Sofus','Albertsen'),(2,'Thomas','Hartmann'),(3,'Lars','Mortensen');");
        Query q4 = em.createNativeQuery("INSERT INTO `TEACHER_SEMESTER` VALUES (1,1),(3,1),(1,2),(2,2),(1,3),(2,3),(3,3);");

        try {
            em.getTransaction().begin();
            q1.executeUpdate();
            q2.executeUpdate();
            q3.executeUpdate();
            q4.executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
