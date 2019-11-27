//49.234.209.55
package cn.edu.sdjzu.xg.bysj.controller.basic.teacher;

import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import cn.edu.sdjzu.xg.bysj.service.DegreeService;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/teacher.ctl")
public class TeacherControl extends HttpServlet {
    //49.234.209.55
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String teacher_json = JsonUtil.getJson(request);
        JSONObject jsonObject = JSONObject.parseObject(teacher_json);
        Teacher teacherToAdd = JSON.parseObject(teacher_json,Teacher.class);

        JSONObject message = new JSONObject();
        try {
            TeacherService.getInstance().add(teacherToAdd);

        } catch (SQLException e) {
            message.put("message","数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        } catch (Exception e){
            message.put("message","网络异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }
    }
    //49.234.209.55

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String id_str = request.getParameter("id");
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            if (id_str == null) {
                responseTeachers(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseTeacher(id, response);
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }

    }
    //49.234.209.55
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        JSONObject message = new JSONObject();
        try {
            TeacherService.getInstance().delete(id);
            message.put("message","删除成功");
            response.getWriter().println(message);
        } catch (SQLException e) {

            message.put("message", "数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        } catch (Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }


    }
    //49.234.209.55
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String teacher_json = JsonUtil.getJson(request);
        Teacher teacherToAdd = JSON.parseObject(teacher_json, Teacher.class);
        JSONObject message = new JSONObject();
        try {
            TeacherService.getInstance().update(teacherToAdd);
            message.put("message","修改成功");
            response.getWriter().println(message);
        } catch (SQLException e) {

            message.put("message", "数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        } catch (Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }


    }
    private void responseTeacher(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Teacher teacher = TeacherService.getInstance().find(id);
        String teacher_json = JSON.toJSONString(teacher);
        response.getWriter().println(teacher_json);
    }
    private void responseTeachers(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Collection<Teacher> teachers = TeacherService.getInstance().findAll();
        String teachers_json = JSON.toJSONString(teachers);

        response.getWriter().println(teachers_json);
    }
}
