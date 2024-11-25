package ca.gbc.apigateway.routes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
@Slf4j
public class Route {
    @Value("${service.booking-service}")
    private String bookingServiceUrl;

    @Value("${service.approval-service}")
    private String approvalServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoute() {
        log.info("initializing booking routes url : {}", bookingServiceUrl);

        return GatewayRouterFunctions.route("booking_service")
                .route(RequestPredicates.path("/api/bookings"), request -> {
                   log.info("Received request for booking-service: {}", request.uri());

                   try {
                       ServerResponse response = HandlerFunctions.http(bookingServiceUrl).handle(request);
                       log.info("Response status: {}", response.statusCode());
                       return response;
                   }catch(Exception ex) {
                       log.error("Error occurred while routing to booking service: {}", ex.getMessage());
                       return ServerResponse.status(500).body("Error occurred when routing to booking-service ");
                   }

                }).build();

    }

    @Bean
    public RouterFunction<ServerResponse> approvalServiceRoute() {
        log.info("initializing approval routes url : {}", approvalServiceUrl);

        return GatewayRouterFunctions.route("approval_service")
                .route(RequestPredicates.path("/api/approvals"), request -> {
                    log.info("Received request for approval-service: {}", request.uri());

                    try {
                        ServerResponse response = HandlerFunctions.http(approvalServiceUrl).handle(request);
                        log.info("Response status: {}", response.statusCode());
                        return response;
                    }catch(Exception ex) {
                        log.error("Error occurred while routing to approval-service : {}", ex.getMessage());
                        return ServerResponse.status(500).body("Error occurred when routing to approval-service");
                    }

                }).build();
    }
}
