package com.grpc.client.service;


import com.grpc.client.StockRequest;
import com.grpc.client.StockResponse;
import com.grpc.client.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service

public class StockClientService {

    @GrpcClient("stock-service")
    private StockTradingServiceGrpc.StockTradingServiceStub stockTradingServiceStub;

//    public StockResponse getStockPrice(String stockSymbol) {
//        return stockTradingServiceStub.getStockPrice(
//                StockRequest.newBuilder()
//                        .setStockSymbol(stockSymbol)
//                        .build()
//        );
//    }


    public void subscribeStockPrice(String symbol) {
        StockRequest request = StockRequest.newBuilder()
                .setStockSymbol(symbol)
                .build();
        stockTradingServiceStub.subscribeStockPrice(request, new StreamObserver<StockResponse>() {

            @Override
            public void onNext(StockResponse response) {
                System.out.println("Stock Price Update: " + response.getStockSymbol() +
                        " Price: " + response.getPrice() + " " +
                        " Time: " + response.getTimestamp());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error : " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("stock price stream live update completed !");
            }
        });
    }


}
