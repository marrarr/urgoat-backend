package demo.czat.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * Klasa konfiguracyjna dla WebSocket, umożliwiająca obsługę wiadomości w czasie rzeczywistym.
 * Konfiguruje brokera wiadomości oraz punkty końcowe STOMP dla komunikacji WebSocket.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Konfiguruje brokera wiadomości dla WebSocket.
     * Ustawia prefiksy dla celów aplikacji oraz włącza prosty broker dla subskrypcji tematów.
     *
     * @param config rejestr brokera wiadomości
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Rejestruje punkty końcowe STOMP dla połączeń WebSocket.
     * Ustawia punkt końcowy "/ws" z obsługą SockJS i zezwala na połączenia z dowolnego źródła.
     *
     * @param registry rejestr punktów końcowych STOMP
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}