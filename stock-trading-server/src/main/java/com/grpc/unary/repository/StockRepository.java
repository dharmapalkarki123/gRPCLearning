package com.grpc.unary.repository;

import com.grpc.unary.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
    Stock findByStockSymbol(String stockSymbol);
}
