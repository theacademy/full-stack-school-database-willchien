package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseServiceInterface {

    //YOUR CODE STARTS HERE
    CourseDao dao;

    public CourseServiceImpl(CourseDao dao){
        this.dao = dao;
    }
    //YOUR CODE ENDS HERE

    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE
        return dao.getAllCourses();
        //YOUR CODE ENDS HERE
    }

    public Course getCourseById(int id) {
        //YOUR CODE STARTS HERE
        try {
            return dao.findCourseById(id);
        } catch (DataAccessException e){
            Course c = new Course();
            c.setCourseId(id);
            c.setCourseDesc("Course Not Found");
            c.setCourseName("Course Not Found");
            return c;
        }
        //YOUR CODE ENDS HERE
    }

    public Course addNewCourse(Course course) {
        //YOUR CODE STARTS HERE
        if(course.getCourseDesc().isBlank() || course.getCourseName().isBlank()){
            course.setCourseName("Name blank, course NOT added");
            course.setCourseDesc("Description blank, course NOT added");
        }
        return dao.createNewCourse(course);
        //YOUR CODE ENDS HERE
    }

    public Course updateCourseData(int id, Course course) {
        //YOUR CODE STARTS HERE
        if(id != course.getCourseId()){
            course.setCourseName("IDs do not match, course not updated");
            course.setCourseDesc("IDs do not match, course not updated");
            return course;
        }
        dao.updateCourse(course);
        return dao.findCourseById(id);
        //YOUR CODE ENDS HERE
    }

    public void deleteCourseById(int id) {
        //YOUR CODE STARTS HERE
        dao.deleteCourse(id);
        System.out.println("Course ID: " + id + " deleted");
        //YOUR CODE ENDS HERE
    }
}
