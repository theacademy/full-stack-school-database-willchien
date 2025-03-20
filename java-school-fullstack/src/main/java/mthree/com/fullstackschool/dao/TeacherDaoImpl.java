package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.TeacherMapper;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    public TeacherDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Teacher createNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE
        final String INSERT_TEACHER = "INSERT INTO teacher(tFName, tLName, dept) VALUES(?,?,?)";
        jdbcTemplate.update(INSERT_TEACHER,
                            teacher.getTeacherFName(),
                            teacher.getTeacherLName(),
                            teacher.getDept());
        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        teacher.setTeacherId(newId);
        return teacher;
        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE
        final String SELECT_ALL_TEACHERS = "SELECT * FROM teacher";
        return jdbcTemplate.query(SELECT_ALL_TEACHERS, new TeacherMapper());
        //YOUR CODE ENDS HERE
    }

    @Override
    public Teacher findTeacherById(int id) {
        //YOUR CODE STARTS HERE
        try{
            final String SELECT_TEACHER_BY_ID = "SELECT * FROM teacher WHERE tid = ?";
            return jdbcTemplate.queryForObject(SELECT_TEACHER_BY_ID, new TeacherMapper(), id);
        } catch(DataAccessException e){
            return null;
        }
        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateTeacher(Teacher t) {
        //YOUR CODE STARTS HERE
        final String UPDATE_TEACHER = "UPDATE teacher SET tFName = ?, tLName = ?, dept = ?";
        jdbcTemplate.update(UPDATE_TEACHER,
                            t.getTeacherFName(),
                            t.getTeacherLName(),
                            t.getDept());
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteTeacher(int id) {
        //YOUR CODE STARTS HERE
        final String DELETE_COURSE_STUDENT_BY_TEACHER = "DELETE cs.* FROM course_student cs "
                + "JOIN course c ON cs.course_id = c.cid WHERE c.teacherId = ?";
        jdbcTemplate.update(DELETE_COURSE_STUDENT_BY_TEACHER, id);

        final String DELETE_COURSE_BY_TEACHER = "DELETE FROM course WHERE teacherId = ?";
        jdbcTemplate.update(DELETE_COURSE_BY_TEACHER, id);

        final String DELETE_TEACHER = "DELETE FROM teacher WHERE tid = ?";
        jdbcTemplate.update(DELETE_TEACHER, id);
        //YOUR CODE ENDS HERE
    }
}
