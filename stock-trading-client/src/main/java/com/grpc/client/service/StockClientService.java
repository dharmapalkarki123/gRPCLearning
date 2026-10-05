package com.grpc.client.service;


import com.grpc.client.StockRequest;
import com.grpc.client.StockResponse;
import com.grpc.client.StockTradingServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service

public class StockClientService {

    @GrpcClient("stock-service")
    private StockTradingServiceGrpc.StockTradingServiceBlockingStub stub;

    public StockResponse getStockPrice(String stockSymbol) {
        return stub.getStockPrice(
                StockRequest.newBuilder()
                        .setStockSymbol(stockSymbol)
                        .build()
        );
    }

}
