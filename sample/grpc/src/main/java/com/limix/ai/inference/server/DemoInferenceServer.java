package com.limix.ai.inference.server;

import inference.GRPCInferenceServiceGrpc;
import inference.GrpcPredictV2;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoInferenceServer {

    public static void main(String[] args) {
        final int nServers = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(nServers);
        for (int i = 0; i < nServers; i++) {
            String name = "Server_" + i;
            int port = 50010 + i;
            executorService.submit(() -> {
                try {
                    startServer(name, port);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void startServer(String name, int port) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(port)
                .addService(new EchoService(name))
                .build();

        server.start();
        System.out.println(name + " server started, listening on port: " + server.getPort());
        server.awaitTermination();
    }

    static class EchoService extends GRPCInferenceServiceGrpc.GRPCInferenceServiceImplBase {

        private final String name;

        EchoService(String name) {
            this.name = name;
        }

        @Override
        public void modelInfer(GrpcPredictV2.ModelInferRequest request, StreamObserver<GrpcPredictV2.ModelInferResponse> responseObserver) {
            System.out.print(request.toString());
            GrpcPredictV2.ModelInferResponse response = GrpcPredictV2.ModelInferResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
