package ca.gbc.apigateway.routes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.beans.factory.annotation.Value;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
@Slf4j
public class Routes {
    @Value("${services.room.url}")
    public String roomServiceUrl;
    @Value("${services.booking.url}")
    public String bookingServiceUrl;
    @Value("${services.user.url}")
    public String userServiceUrl;
    @Value("${services.approval.url}")
    public String approvalServiceUrl;
    @Value("${services.event.url}")
    public String eventServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> roomServiceRoute(){
        //printf("%s", .. variable);
        log.info("Initialising room service route with URL: {}",roomServiceUrl);

        return GatewayRouterFunctions.route("room_service")
                .route(RequestPredicates.path("/api/room"), request -> {
                    log.info("Received request for room-service: {}",request.uri());
                    try {
                        ServerResponse response= HandlerFunctions.http(roomServiceUrl).handle(request);
                        log.info("Respomse Status: {}",response.statusCode());
                        return response;
                    }catch (Exception e){
                        log.error("Error occured while routing to {}",e.getMessage(),e);
                        return ServerResponse.status(500).body("Error occured when routing to room-service");
                    }
                })
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoute(){
        //printf("%s", .. variable);
        log.info("Initialising booking service route with URL: {}",bookingServiceUrl);

        return GatewayRouterFunctions.route("booking_service")
                .route(RequestPredicates.path("/api/bookings"), request -> {
                    log.info("Received request for booking-service: {}",request.uri());

                    try {
                        ServerResponse response= HandlerFunctions.http(bookingServiceUrl).handle(request);
                        log.info("Respomse Status: {}",response.statusCode());
                        return response;
                    }catch (Exception e){
                        log.error("Error occured while routing to {}",e.getMessage(),e);
                        return ServerResponse.status(500).body("Error occured when routing to booking-service");
                    }
                })
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> userServiceRoute(){
        //printf("%s", .. variable);
        log.info("Initialising user service route with URL: {}",userServiceUrl);

        return GatewayRouterFunctions.route("user_service")
                .route(RequestPredicates.path("/api/users"), request -> {
                    log.info("Received request for user-service: {}",request.uri());

                    try {
                        ServerResponse response= HandlerFunctions.http(userServiceUrl).handle(request);
                        log.info("Respomse Status: {}",response.statusCode());
                        return response;
                    }catch (Exception e){
                        log.error("Error occured while routing to {}",e.getMessage(),e);
                        return ServerResponse.status(500).body("Error occured when routing to user-service");
                    }
                })
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> roomServiceSwagger(){
        return GatewayRouterFunctions.route("room_service_swagger")
                .route(RequestPredicates.path("/aggregate/room_service/api-docs"),
                        HandlerFunctions.http(roomServiceUrl + "/api-docs"))
                .filter(setPath("/api-docs"))// Add `/api-docs` to the URL
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceSwagger(){
        return GatewayRouterFunctions.route("booking_service_swagger")
                .route(RequestPredicates.path("/aggregate/booking_service/api-docs"),
                        HandlerFunctions.http(bookingServiceUrl + "/api-docs"))
                .filter(setPath("/api-docs"))// Add `/api-docs` to the URL
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceSwagger(){
        return GatewayRouterFunctions.route("user_service_swagger")
                .route(RequestPredicates.path("/aggregate/user_service/api-docs"),
                        HandlerFunctions.http(userServiceUrl))
                .filter(setPath("/api-docs"))// Add `/api-docs` to the URL
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> approvalServiceRoute(){
        //printf("%s", .. variable);
        log.info("Initialising approval service route with URL: {}",approvalServiceUrl);

        return GatewayRouterFunctions.route("approval_service")
                .route(RequestPredicates.path("/api/approvals"), request -> {
                    log.info("Received request for approval-service: {}",request.uri());

                    try {
                        ServerResponse response= HandlerFunctions.http(approvalServiceUrl).handle(request);
                        log.info("Respomse Status: {}",response.statusCode());
                        return response;
                    }catch (Exception e){
                        log.error("Error occured while routing to {}",e.getMessage(),e);
                        return ServerResponse.status(500).body("Error occured when routing to approval-service");
                    }
                })
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> approvalServiceSwagger(){
        return GatewayRouterFunctions.route("approval_service_swagger")
                .route(RequestPredicates.path("/aggregate/approval_service/api-docs"),
                        HandlerFunctions.http(approvalServiceUrl + "/api-docs"))
                .filter(setPath("/api-docs"))// Add `/api-docs` to the URL
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> eventServiceRoute(){
        //printf("%s", .. variable);
        log.info("Initialising event service route with URL: {}",eventServiceUrl);

        return GatewayRouterFunctions.route("event_service")
                .route(RequestPredicates.path("/api/events"), request -> {
                    log.info("Received request for event-service: {}",request.uri());

                    try {
                        ServerResponse response= HandlerFunctions.http(eventServiceUrl).handle(request);
                        log.info("Respomse Status: {}",response.statusCode());
                        return response;
                    }catch (Exception e){
                        log.error("Error occured while routing to {}",e.getMessage(),e);
                        return ServerResponse.status(500).body("Error occured when routing to event-service");
                    }
                })
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> eventServiceSwagger(){
        return GatewayRouterFunctions.route("event_service_swagger")
                .route(RequestPredicates.path("/aggregate/event_service/api-docs"),
                        HandlerFunctions.http(eventServiceUrl + "/api-docs"))
                .filter(setPath("/api-docs"))// Add `/api-docs` to the URL
                .build();
    }


}
