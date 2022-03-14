package com.limix.ai.inference.client;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import inference.GrpcPredictV2;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 请求服务
 */
public class DemoInference {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50011")
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext()
                .build();
        inference.GRPCInferenceServiceGrpc.GRPCInferenceServiceBlockingStub stub = inference.GRPCInferenceServiceGrpc.newBlockingStub(channel);
        GrpcPredictV2.ModelInferRequest req = getModelInferRequest();
        GrpcPredictV2.ModelInferResponse resp = stub.modelInfer(req);
        System.out.println(resp.getOutputsCount());
    }

    private static GrpcPredictV2.ModelInferRequest getModelInferRequest1() {
        GrpcPredictV2.ModelInferRequest req = null;
        String reqString = "{\n" +
                "  \"model_name\": \"fz-mchbjy\",\n" +
                "  \"model_version\": \"\",\n" +
                "  \"id\": \"fde03ac0-1810-474e-95af-fb1f0529a9b2\",\n" +
                "  \"inputs\": [\n" +
                "    {\n" +
                "      \"name\": \"text\",\n" +
                "      \"datatype\": \"STRING\",\n" +
                "      \"shape\": [\n" +
                "        1\n" +
                "      ],\n" +
                "      \"contents\": {\n" +
                "        \"bytes_contents\": [\n" +
                "          \"5ZaC5ZaC5L2g5aW95Zev44CC5Zev44CC5Zev44CC5ZSJ5L2g5aW977yM6L\"\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            // TODO LIMIX grpc-json互相转换处理逻辑
            req = GrpcPredictV2.ModelInferRequest.parseFrom(reqString.getBytes(StandardCharsets.UTF_8));
            // req = GrpcPredictV2.ModelInferRequest.parseFrom(reqString.getBytes(StandardCharsets.UTF_8));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return req;
    }

    private static GrpcPredictV2.ModelInferRequest getModelInferRequest() {
        GrpcPredictV2.ModelInferRequest.InferInputTensor.Builder inputBuilder = GrpcPredictV2.ModelInferRequest.InferInputTensor.newBuilder();
        GrpcPredictV2.InferTensorContents.Builder contentBuilder = GrpcPredictV2.InferTensorContents.newBuilder();
        // 添加数据记录
        contentBuilder.addBytesContents(ByteString.copyFrom(("[" + Base64.getEncoder().encodeToString("你好".getBytes(StandardCharsets.UTF_8)) + "]").getBytes()));
        contentBuilder.addBytesContents(ByteString.copyFrom((Base64.getEncoder().encodeToString("你好".getBytes(StandardCharsets.UTF_8))).getBytes()));
        inputBuilder.setName("text");
        inputBuilder.setDatatype("STRING");
        inputBuilder.addShape(2);
        inputBuilder.setContents(contentBuilder.build());
        GrpcPredictV2.ModelInferRequest req = GrpcPredictV2.ModelInferRequest.newBuilder()
                .setModelName("fz-mchbjy")
                .setModelVersion("")
                .setId("fde03ac0-1810-474e-95af-fb1f0529a9b2")
                .addInputs(inputBuilder.build()).buildPartial();
        return req;
    }
}