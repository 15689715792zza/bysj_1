//49.234.209.55
package cn.edu.sdjzu.xg.bysj.controller.basic.department;


import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;

import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/department.ctl")
public class DepartmentController extends HttpServlet {
    //49.234.209.55
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String department_json = JsonUtil.getJson(request);
        JSONObject jsonObject = JSONObject.parseObject(department_json);
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);
        JSONObject message = new JSONObject();
        try {
            DepartmentService.getInstance().add(departmentToAdd);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String id_str = request.getParameter("id");
        String paraType_str = request.getParameter("paraType");
        JSONObject message = new JSONObject();


        try {
            if (id_str != null || paraType_str != null){
                if (paraType_str != null){
                    if (paraType_str.equals("school")){
                        int id = Integer.parseInt(id_str);
                        this.responseDepartmentsBySchool(id,response);
                    } else {
                        response.getWriter().println("Wrong paraType!");
                    }
                } else if (id_str != null){
                    int id = Integer.parseInt(id_str);
                    this.responseDepartment(id,response);
                }


            } else {
                this.responseDepartments(response);
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        JSONObject message = new JSONObject();
        try {
            DepartmentService.getInstance().delete(id);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String department_json = JsonUtil.getJson(request);
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);
        JSONObject message = new JSONObject();
        try {
            DepartmentService.getInstance().update(departmentToAdd);
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
    private void responseDepartment(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Department department = DepartmentService.getInstance().find(id);
        String department_json = JSON.toJSONString(department);
        response.getWriter().println(department_json);
    }
    private void responseDepartmentsBySchool(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Collection<Department> departments = DepartmentService.getInstance().findAllBySchool(id);
        String department_json = JSON.toJSONString(departments);
        response.getWriter().println(department_json);
    }
    private void responseDepartments(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Collection<Department> departments = DepartmentService.getInstance().getAll();
        String departments_json = JSON.toJSONString(departments);

        response.getWriter().println(departments_json);
    }
}
