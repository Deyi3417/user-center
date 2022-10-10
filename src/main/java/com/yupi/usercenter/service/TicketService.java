package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.model.domain.dto.TicketDTO;
import com.yupi.usercenter.model.domain.dto.UpdateTicketDTO;

import java.util.List;

/**
 * @author HP
 * @description 针对表【ticket(不合格票表)】的数据库操作Service
 * @createDate 2022-08-26 15:18:17
 */
public interface TicketService extends IService<Ticket> {

    /**
     * 根据ID获取票信息
     *
     * @param id 票主键ID
     * @return TicketDTO
     */
    TicketDTO getTicketById(Long id);

    /**
     * 跟新票信息
     *
     * @param updateTicketDTO 要更新的字段内容
     * @return BaseResponse
     */
    BaseResponse updateTicketInfo(UpdateTicketDTO updateTicketDTO);

    /**
     * 使用互斥锁解决 缓存击穿 问题
     *
     * @param id 票主键ID
     * @return TicketDTO
     */
    TicketDTO getTicketThroughLock(Long id);

    /**
     * 插入ticket
     */
    void insertTicket();

    List<Ticket> selectList(Page<Ticket> ticketPage, Object o);
}
