package generator.service;

import generator.domain.QcRandomUnqualifiedticketDepartment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author HP
* @description 针对表【qc_random_unqualifiedticket_department(随机不合格票责任部门关联表，两者是多对多的关系，一个 随机不合格票 可以对应 多个 责任部门；任意一个 责任部门 可以对)】的数据库操作Service
* @createDate 2022-10-20 09:39:59
*/
public interface QcRandomUnqualifiedticketDepartmentService extends IService<QcRandomUnqualifiedticketDepartment> {

}
