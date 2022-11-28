package com.yupi.usercenter.service.impl;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.power.common.util.DateTimeUtil;
import com.power.common.util.UUIDUtil;
import com.spire.ms.System.DateTime;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ErrorCode;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.constant.RedisConstants;
import com.yupi.usercenter.mapper.TicketMapper;
import com.yupi.usercenter.mapstruct.basic.TicketConvert2DTO;
import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.model.domain.dto.TicketDTO;
import com.yupi.usercenter.model.domain.dto.UpdateTicketDTO;
import com.yupi.usercenter.model.domain.vo.PageSearchDTO;
import com.yupi.usercenter.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author HP
 * @description 针对表【ticket(不合格票表)】的数据库操作Service实现
 * @createDate 2022-08-26 15:18:17
 */
@Service
@Slf4j
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket>
        implements TicketService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public TicketDTO getTicketById(Long id) {
        // 1. 判断 redis 是否有 ticket 信息
        String redisKey = RedisConstants.REDIS_TICKET_HASH_ID_KEY + id;
        Map entries = redisTemplate.opsForHash().entries(redisKey);
        if (!entries.isEmpty()) {
            // 1.1 redis 中存在直接返回票信息
            TicketDTO ticketDTO = JSON.parseObject(JSON.toJSONString(entries), TicketDTO.class);
            return ticketDTO;
        }
        // 2. 从数据库中查询票数据
        Ticket ticket = this.getById(id);
        if (ticket == null) {
            // throw new BusinessException(ErrorCode.NULL_ERROR);
            // 缓存 空值，并设置过期时间为 2分钟
            redisTemplate.opsForHash().putAll(redisKey, new HashMap<>());
            redisTemplate.expire(redisKey, 2L, TimeUnit.MINUTES);
            return null;
        }
        // 2.1 票信息脱敏
        TicketDTO ticketDTO = TicketConvert2DTO.INSTANCE.toConvertTicketDTO(ticket);
        // 2.1 ticketDTO 转为map
        Map ticketMap = JSON.parseObject(JSON.toJSONString(ticketDTO), Map.class);
        // 3. 将票信息保存到 Redis 中
        redisTemplate.opsForHash().putAll(redisKey, ticketMap);
        // 3.1 Redis key 设置过期时间
        redisTemplate.expire(redisKey, 5L, TimeUnit.MINUTES);
        return ticketDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateTicketInfo(UpdateTicketDTO updateTicketDTO) {
        // 先操作数据库
        Long id = updateTicketDTO.getId();
        Ticket ticket = this.getById(id);
        if (ticket == null) {
            return ResultUtils.error(ErrorCode.NULL_ERROR, "there is no data in the database when id = " + id);
        }
        ticket.setTicketLevel(updateTicketDTO.getTicketLevel());
        ticket.setHandleWay(updateTicketDTO.getHandleWay());
        ticket.setPrice(updateTicketDTO.getPrice());
        // 更新 ticket
        this.updateById(ticket);
        // 删除 redis 缓存
        redisTemplate.delete(RedisConstants.REDIS_TICKET_HASH_ID_KEY + id);
        return ResultUtils.success();
    }

    @Override
    public TicketDTO getTicketThroughLock(Long id) {
        // 1.判断 redis 是否有 缓存
        String redisKey = RedisConstants.REDIS_TICKET_HASH_ID_KEY + id;
        Map entries = redisTemplate.opsForHash().entries(redisKey);
        if (!entries.isEmpty()) {
            // 2. 存在则直接返回
            TicketDTO ticketDTO = JSON.parseObject(JSON.toJSONString(entries), TicketDTO.class);
            return ticketDTO;
        }

        String lockKey = null;
        TicketDTO ticketDTO = null;

        try {
            // 3. 缓存不存在，获取锁
            lockKey = RedisConstants.REDIS_TICKET_LOCK_KEY + id;
            if (!tryLock(lockKey)) {
                // 4. 获取锁失败，休眠50ms，重新执行
                Thread.sleep(50L);
                return getTicketThroughLock(id);
            }
            // 5. 获取锁成功，再次查看缓存是否存在，DoubleCheck，存在则无需再构建缓存
            entries = redisTemplate.opsForHash().entries(redisKey);
            if (!entries.isEmpty()) {
                return JSON.parseObject(JSON.toJSONString(entries), TicketDTO.class);
            }
            // 6. 从数据库查询商铺信息
            Ticket ticket = this.getById(id);
            // 模拟重构时间
            Thread.sleep(200L);
            if (ticket == null) {
                // 缓存空值，并设置过期时间
                redisTemplate.opsForHash().putAll(redisKey, new HashMap());
                redisTemplate.expire(redisKey, 2L, TimeUnit.MINUTES);
                return null;
            }
            // 保存检索到的信息，并保存到redis
            ticketDTO = TicketConvert2DTO.INSTANCE.toConvertTicketDTO(ticket);
            Map ticketMap = JSON.parseObject(JSON.toJSONString(ticketDTO), Map.class);
            redisTemplate.opsForHash().putAll(redisKey, ticketMap);
            redisTemplate.expire(redisKey, 5L, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            unlock(lockKey);
        }
        // 返回信息
        return ticketDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertTicket() {
        Random random = new Random();
        DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss");
        for (int i = 0; i < 50; i++) {
            int randomInt = random.nextInt(10000);
            Ticket ticket = new Ticket();
            ticket.setTicketCode(DateTimeUtil.dateToStr(new Date(),"yyyyMMddHHmmss"));
            ticket.setTicketLevel(random.nextInt(10));
            ticket.setHandleWay(random.nextInt(5));
            ticket.setUserId(0L);
            ticket.setCreateId(random.nextLong());
            if (randomInt % 2 == 0) {
                ticket.setCreateName("偶数");
            }else {
                ticket.setCreateName("奇数");
            }
            ticket.setCreateTime(new Date());
            ticket.setPrice(0.0D);
            this.baseMapper.insert(ticket);
        }
        log.info("====插入数据成功====");
    }

    @Override
    public List<Ticket> selectList(Page<Ticket> ticketPage, Object o) {
        return this.baseMapper.getTicketList(ticketPage,null);
    }

    @Override
    public List<Ticket> getTicketList(Page<Ticket> page, PageSearchDTO pageParams) {
        return this.baseMapper.getTicketList(page, pageParams);
    }

    @Override
    public List<Ticket> getTicketByIds(String[] ids) {
        return this.baseMapper.getTicketByIds(ids);
    }

    @Override
    public List<Ticket> getTicketByIdsList(List<String> list) {
        return this.baseMapper.getTicketByIdsList(list);
    }

    private void unlock(String lockKey) {
        stringRedisTemplate.delete(lockKey);
        log.info("====根据 lockKey 删除 Redis 成功====：lockKey=" + lockKey);
    }

    private boolean tryLock(String lockKey) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "1", 100L, TimeUnit.SECONDS);
        return BooleanUtils.isTrue(flag);
    }
}




