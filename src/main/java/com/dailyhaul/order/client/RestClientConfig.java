package com.dailyhaul.order.client;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Autowired(required = false)
    private ObservationRegistry observationRegistry; // This is an interface that represents a registry for observing various aspects of the application, such as performance metrics or tracing information.

    @Autowired(required = false)
    private Tracer tracer; // This is an interface that represents a tracer, which is used to track the execution of requests and generate tracing information.

    @Autowired(required = false)
    private Propagator propagator; // This is an interface that represents a propagator, which is used to propagate tracing information across different requests.

    @Bean
    @LoadBalanced
    public RestClient.Builder restClientBuilder() {

        RestClient.Builder builder = RestClient.builder();

        if (observationRegistry != null) {
            builder.requestInterceptor(createTracingInterceptor());
        }

        return builder;
    }


    // this basically lumps related api calls together so that they are displayed under the same span
    // in zipkin. (e.g. when we call add to cart, addition to cart, product and user existence check
    // requests are lumped together instead of zipkin dispersing treating them as different spans)
    // what we are doing on technical front is that we are injecting tracer to request headers so that
    // it propagates the next related request with details.
    private ClientHttpRequestInterceptor createTracingInterceptor() {
        return ((request, body, execution) -> {
            if (tracer != null && propagator != null && tracer.currentSpan() != null) {
                propagator.inject(tracer.currentTraceContext().context(), request.getHeaders(),
                        (carrier, key, value) -> {
                            carrier.add(key, value);
                        });
            }
            return execution.execute(request, body);
        });
    }
}
