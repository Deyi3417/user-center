package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.QcRandomUnqualifiedticketDepartment;
import generator.service.QcRandomUnqualifiedticketDepartmentService;
import generator.mapper.QcRandomUnqualifiedticketDepartmentMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【qc_random_unqualifiedticket_department(随机不合格票责任部门关联表，两者是多对多的关系，一个 随机不合格票 可以对应 多个 责任部门；任意一个 责任部门 可以对)】的数据库操作Service实现
* @createDate 2022-10-20 09:39:59
*/
@Service
public class QcRandomUnqualifiedticketDepartmentServiceImpl extends ServiceImpl<QcRandomUnqualifiedticketDepartmentMapper, QcRandomUnqualifiedticketDepartment>
    implements QcRandomUnqualifiedticketDepartmentService{

}




