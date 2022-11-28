package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.model.domain.dto.TicketDTO;
import com.yupi.usercenter.model.domain.dto.UpdateTicketDTO;
import com.yupi.usercenter.model.domain.vo.PageSearchDTO;

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

    /**
     * 分页搜索
     *
     * @param ticketPage 分页查询
     * @param o          任意对象
     * @return List<Ticket>
     */
    List<Ticket> selectList(Page<Ticket> ticketPage, Object o);

    /**
     * 分页搜索
     *
     * @param page       分页查询
     * @param pageParams 条件查询、精确查询条件
     * @return List<Ticket>
     */
    List<Ticket> getTicketList(Page<Ticket> page, PageSearchDTO pageParams);

    /**
     * 根据ids获取tickets
     *
     * @param ids 包含票的id数组
     * @return tickets
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
