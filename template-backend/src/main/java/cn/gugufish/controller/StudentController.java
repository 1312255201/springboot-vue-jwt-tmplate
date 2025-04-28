package cn.gugufish.controller;

import cn.gugufish.entity.RestBean;
import cn.gugufish.entity.dto.Account;
import cn.gugufish.entity.dto.Student;
import cn.gugufish.entity.vo.response.StudentVO;
import cn.gugufish.service.StudentService;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Min;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Resource
    private StudentService studentService;
    @GetMapping("/list")
    public RestBean<JSONObject> accountList(int page, int size) {
        JSONObject object = new JSONObject();
        List<StudentVO> list = studentService.page(Page.of(page, size))
                .getRecords()
                .stream()
                .map(a -> a.asViewObject(StudentVO.class))
                .toList();
        object.put("total", studentService.count());
        object.put("list", list);
        return RestBean.success(object);
    }
    @PostMapping("/save")
    public RestBean<Void> saveStudent(@RequestBody JSONObject object) {
        int id = object.getInteger("id");
        Student student = studentService.findStudentById(id);
        Student save = object.toJavaObject(Student.class);
        BeanUtils.copyProperties(save, student, "id");
        studentService.saveOrUpdate(student);
        return RestBean.success();
    }

    @PostMapping("/add")
    public RestBean<Void> addStudent(@RequestBody JSONObject object) {
        Student student = object.toJavaObject(Student.class);
        studentService.save(student);
        return RestBean.success();
    }

    @PostMapping("/delete/{id}")
    public RestBean<Void> deleteStudent(@PathVariable("id") int id) {
        studentService.removeById(id);
        return RestBean.success();
    }
}
