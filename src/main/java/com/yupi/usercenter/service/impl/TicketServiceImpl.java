package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.service.TicketService;
import com.yupi.usercenter.mapper.TicketMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【ticket(不合格票表)】的数据库操作Service实现
* @createDate 2022-08-26 15:18:17
*/
@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket>
    implements TicketService{

}




