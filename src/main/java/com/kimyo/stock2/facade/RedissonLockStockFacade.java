package com.kimyo.stock2.facade;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import com.kimyo.stock2.service.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {
    private final RedissonClient redissonClient;

    private final StockService stockService;

    public void decrease(Long key, Long quantity) {
        var lock = redissonClient.getLock(key.toString());
        try {
            var available = lock.tryLock(5, 1, TimeUnit.SECONDS);
            if (!available) {
                log.info("lock 획득 실패");
                return;
            }
            stockService.decrease(key, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }
}
