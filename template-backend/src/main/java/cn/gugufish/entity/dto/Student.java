package cn.gugufish.entity.dto;

import cn.gugufish.entity.BaseData;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 学生表
 */
@Data
@TableName("db_student")
@AllArgsConstructor
public class Student implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    String className;
    String major;
    String studentId;
}
