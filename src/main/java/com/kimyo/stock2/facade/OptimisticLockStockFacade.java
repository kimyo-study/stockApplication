package com.kimyo.stock2.facade;

import org.springframework.stereotype.Service;

import com.kimyo.stock2.service.OptimisticLockStockService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockFacade {
    static int num = 1;
    private final OptimisticLockStockService optimisticLockStockService;

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (true) {
            try {
                optimisticLockStockService.decrease(id, quantity);
                System.out.println(num++);
                break; // 성공시 탈출
            } catch (Exception e) {
                Thread.sleep(10); // 수량 감소에 실패시 50ms 이후 다시 시도
            }
        }

    }

}
