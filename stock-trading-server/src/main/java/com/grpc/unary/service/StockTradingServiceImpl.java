package com.grpc.unary.service;

import com.grpc.unary.StockRequest;
import com.grpc.unary.StockResponse;
import com.grpc.unary.StockTradingServiceGrpc;
import com.grpc.unary.entity.Stock;
import com.grpc.unary.repository.StockRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class StockTradingServiceImpl extends StockTradingServiceGrpc.StockTradingServiceImplBase {


    private  final StockRepository stockRepository;

    public StockTradingServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    //stockName->DB->MAP RESPONSE-> RETURN
    @Override
    public void getStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {
//        super.getStockPrice(request, responseObserver);

        String stockSymbol=request.getStockSymbol();
        Stock stockEntity= stockRepository.findByStockSymbol(stockSymbol);

       StockResponse stockResponse= StockResponse.newBuilder()
                .setStockSymbol(stockEntity.getStockSymbol())
                .setPrice(stockEntity.getPrice())
                .setTimestamp(stockEntity.getLastUpdated().toString())
                .build();

        responseObserver.onNext(stockResponse);
        responseObserver.onCompleted();

    }
}
