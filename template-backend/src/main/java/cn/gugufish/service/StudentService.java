package cn.gugufish.service;

import cn.gugufish.entity.dto.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
public interface StudentService extends IService<Student> {
    Student findStudentById(int id);
}
