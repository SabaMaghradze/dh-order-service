package com.dailyhaul.order.client;

import com.dailyhaul.order.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Optional;

@HttpExchange
public interface UserServiceClient {

    @GetExchange("/api/users/{userId}")
    Optional<UserResponse> getUser(@PathVariable String userId);
}
