package cn.gugufish.service.impl;

import cn.gugufish.entity.dto.Account;
import cn.gugufish.entity.dto.Student;
import cn.gugufish.mapper.StudentMapper;
import cn.gugufish.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student findStudentById(int id) {
        return baseMapper.selectById(id);
    }
}
