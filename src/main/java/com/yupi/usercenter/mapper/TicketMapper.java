package com.yupi.usercenter.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.usercenter.model.domain.Ticket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HP
* @description 针对表【ticket(不合格票表)】的数据库操作Mapper
* @createDate 2022-08-26 15:18:17
* @Entity com.yupi.usercenter.model.domain.Ticket
*/
public interface TicketMapper extends BaseMapper<Ticket> {

    List<Ticket> getTicketList(Page<Ticket> ticketPage, Object o);
}




