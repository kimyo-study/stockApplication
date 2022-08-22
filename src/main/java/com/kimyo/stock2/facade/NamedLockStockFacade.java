package com.kimyo.stock2.facade;

import org.springframework.stereotype.Component;

import com.kimyo.stock2.repository.LockRepository;
import com.kimyo.stock2.service.StockService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

    private final LockRepository lockRepository;

    private final StockService stockService;

    public void decrease(Long id, Long quantity) {
        try {
            lockRepository.getLock(id.toString());

            // 얘는 부모의 트랜젝션과 별도의 트랜젝션에서 실행되어야 하므로 propagation을 변경해주자.
            stockService.decrease(id, quantity);
        } finally {
            lockRepository.releaseLock(id.toString());
        }
    }

}
