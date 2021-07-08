package com.bloxbean.cardano.client.backend.gql;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Mutation;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.bloxbean.cardano.client.backend.gql.adapter.AddHeadersInterceptor;
import com.bloxbean.cardano.client.backend.gql.adapter.JSONCustomTypeAdapter;
import com.bloxbean.cardano.client.backend.model.Result;
import com.bloxbean.cardano.client.util.JsonUtil;
import com.bloxbean.cardano.gql.type.CustomType;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BaseGqlService {
    protected String gqlUrl;
    protected ApolloClient apolloClient;

    public BaseGqlService(String gqlUrl) {
        this(gqlUrl, null);
    }

    public BaseGqlService(String gqlUrl, Map<String, String> headers) {
        this.gqlUrl = gqlUrl;

        ApolloClient.Builder builder = ApolloClient.builder();
        if(headers != null && headers.size() > 0) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder();
            okHttpClientBuilder.addInterceptor(new AddHeadersInterceptor(headers));

            builder.okHttpClient(okHttpClientBuilder.build());
        }

        apolloClient = builder
                .serverUrl(gqlUrl)
                .addCustomTypeAdapter(CustomType.JSON, new JSONCustomTypeAdapter())
                .build();
    }

    protected <T> T execute(Query operation) {
        CompletableFuture<T> future = new CompletableFuture<>();
        apolloClient.query(operation).enqueue(new ApolloCall.Callback<T>() {
            @Override
            public void onResponse(@NotNull Response<T> response) {
                if (response.hasErrors()) {
                    String errors = response.getErrors().stream().map(Object::toString).collect(Collectors.joining(", "));
                    future.completeExceptionally(new ApolloException(errors));
                    return;
                }
                future.complete(response.getData());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                future.completeExceptionally(e);
            }
        });

        return Mono.fromFuture(future).block();
    }

    protected <T> T executeMutatation(Mutation operation) {
        CompletableFuture<T> future = new CompletableFuture<>();
        apolloClient.mutate(operation).enqueue(new ApolloCall.Callback<T>() {
            @Override
            public void onResponse(@NotNull Response<T> response) {
                if (response.hasErrors()) {
                    String errors = response.getErrors().stream().map(Object::toString).collect(Collectors.joining(", "));
                    future.completeExceptionally(new ApolloException(errors));
                    return;
                }
                future.complete(response.getData());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                future.completeExceptionally(e);
            }
        });

        return Mono.fromFuture(future).block();
    }

    public <T> Result<T> processSuccessResult(T t) {
        return Result.success(JsonUtil.getPrettyJson(t))
                .withValue(t)
                .code(200);
    }
}
