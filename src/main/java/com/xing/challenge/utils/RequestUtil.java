package com.xing.challenge.utils;

import com.google.gson.JsonSyntaxException;
import dev.failsafe.CircuitBreaker;
import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeExecutor;
import dev.failsafe.okhttp.FailsafeCall;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class RequestUtil {

    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    private static final Logger log = LoggerFactory.getLogger(RequestUtil.class);


    public <T> T request(String url, Map<String, String> params, Class<T> cls) {
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addEncodedQueryParameter(param.getKey(), param.getValue());
            }
        }
        okhttp3.Request request = new okhttp3.Request.Builder().url(httpBuilder.build()).build();
        log.info("Url {}", request.url());
        Call call = client.newCall(request);

        CircuitBreaker<Response> breaker = CircuitBreaker.<Response>builder()
                .handle(IOException.class)
                .withFailureThreshold(3, 10)
                .withSuccessThreshold(5)
                .withDelay(Duration.ofSeconds(1))
                .build();
        FailsafeExecutor<Response> failsafe = Failsafe.with(breaker);
        FailsafeCall failsafeCall = FailsafeCall.with(failsafe).compose(call);
        Response response;
        T result;
        try {
            response = failsafeCall.execute();
            if (!response.isSuccessful()) {
                throw new RequestErrorException(response.code(), response.request().url().toString());
            }
            result = JsonUtil.fromStringToObject(Objects.requireNonNull(response.body()).string(), cls);
        } catch (IOException | JsonSyntaxException e) {
            throw new RequestErrorException(450, request.url().toString());
        }
        return result;
    }

}
