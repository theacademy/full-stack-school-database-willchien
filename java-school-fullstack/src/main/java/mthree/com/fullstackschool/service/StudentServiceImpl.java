package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.dao.CourseDaoImpl;
import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.dao.TeacherDao;
import mthree.com.fullstackschool.model.Course;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    //YOUR CODE STARTS HERE
    StudentDao dao;

    public StudentServiceImpl(StudentDao dao){
        this.dao = dao;
    }
    //YOUR CODE ENDS HERE

    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE
        return dao.getAllStudents();
        //YOUR CODE ENDS HERE
    }

    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE
        try {
            return dao.findStudentById(id);
        } catch(DataAccessException e){
            Student s = new Student();
            s.setStudentId(id);
            s.setStudentFirstName("Student Not Found");
            s.setStudentLastName("Student Not Found");
            return s;
        }
        //YOUR CODE ENDS HERE
    }

    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE
        if(student.getStudentFirstName().isBlank() || student.getStudentLastName().isBlank()){
            student.setStudentFirstName("First Name blank, student NOT added");
            student.setStudentLastName( "Last Name blank, student NOT added");
        }
        return dao.createNewStudent(student);
        //YOUR CODE ENDS HERE
    }

    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE
        if(id != student.getStudentId()){
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
            return student;
        }
        dao.updateStudent(student);
        return dao.findStudentById(id);
        //YOUR CODE ENDS HERE
    }

    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE
        dao.deleteStudent(id);
        //YOUR CODE ENDS HERE
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        Student s = dao.findStudentById(studentId);
        if(s.getStudentFirstName().equals("Student Not Found")){
            System.out.println("Student not found");
        }
        else{
            System.out.println("Student: " + studentId + " deleted from course: " + courseId);
            dao.deleteStudentFromCourse(studentId, courseId);
        }
        //YOUR CODE ENDS HERE
    }

    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        Student s = dao.findStudentById(studentId);
        if(s.getStudentFirstName().equals("Student Not Found")){
            System.out.println("Student not found");
        }
        else{
            try {
                System.out.println("Student: " + studentId + " added to course: " + courseId);
                dao.addStudentToCourse(studentId, courseId);
            } catch(DataAccessException e){
                System.out.println("Student: " + studentId + " already enrolled in course: " + courseId);
            }
        }
        //YOUR CODE ENDS HERE
    }
}
