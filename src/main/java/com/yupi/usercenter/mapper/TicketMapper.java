package com.yupi.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.usercenter.model.domain.Ticket;

import java.util.List;

/**
 * @author HP
 * @description 针对表【ticket(不合格票表)】的数据库操作Mapper
 * @createDate 2022-08-26 15:18:17
 * @Entity com.yupi.usercenter.model.domain.Ticket
 */
public interface TicketMapper extends BaseMapper<Ticket> {

    List<Ticket> getTicketList(Page<Ticket> ticketPage, Object o);

    /**
     * 根据ids获取tickets
     *
     * @param ids 包含票的id数组
     * @return ticket
     */
    List<Ticket> getTicketByIds(String[] ids);

    /**
     * 根据ids获取tickets
     *
     * @param list 包含票的id集合
     * @return tickets
     */
    List<Ticket> getTicketByIdsList(List<String> list);
}




