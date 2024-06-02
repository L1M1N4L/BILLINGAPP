package net.enjoy.springboot.registrationlogin.service;

import net.enjoy.springboot.registrationlogin.entity.SerialData;
import net.enjoy.springboot.registrationlogin.repository.SerialDataRepository;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SerialServiceImpl implements SerialService {
    

    private static final Logger logger = LoggerFactory.getLogger(SerialServiceImpl.class);

    @Autowired
    private SerialDataRepository serialDataRepository;
    
    @Override
    public List<SerialData> getAllSerialData() {
        return serialDataRepository.findAll();
    }

    @Override
    public SerialData getSerialDataById(Long id) {
        return serialDataRepository.findById(id).orElse(null);
    }

    @Override
    public SerialData saveSerialData(SerialData serialData) {
        return serialDataRepository.save(serialData);
    }

    @Override
    public void deleteSerialData(Long id) {
        serialDataRepository.deleteById(id);
    }

    private final String webSocketUrl = "ws://localhost:8080"; // WebSocket server URL
    private WebSocketClient webSocketClient;
    private ScheduledExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        connectToWebSocket();
    }

    // Method to connect to the WebSocket server
    private void connectToWebSocket() {
        try {
            webSocketClient = new WebSocketClient(new URI(webSocketUrl)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    logger.info("Connected to WebSocket server.");
                }

                @Override
                public void onMessage(String message) {
                    logger.info("Received message from WebSocket server: {}", message);
                    processCallRecord(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    logger.info("WebSocket connection closed. Reason: {}", reason);
                    scheduleReconnect();
                }

                @Override
                public void onError(Exception ex) {
                    logger.error("WebSocket error occurred.", ex);
                    scheduleReconnect();
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            logger.error("Invalid WebSocket URL.", e);
        } catch (Exception e) {
            logger.error("Error connecting to WebSocket server.", e);
        }
    }

    // Method to process and save call record data
    private void processCallRecord(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length >= 6) {
            String callerNumber = parts[0];
            String lineNumber = parts[1];
            String numberDialed = parts[2];
            String date = parts[3];
            String duration = parts[5];

            SerialData serialData = new SerialData();
            serialData.setCaller(callerNumber);
            serialData.setLineNo(lineNumber);
            serialData.setNumberDialed(numberDialed);
            serialData.setDate(date);
            serialData.setDuration(duration);

            try {
                serialDataRepository.save(serialData);
                logger.info("Data saved successfully: {}", serialData);
            } catch (Exception e) {
                logger.error("Error saving data: {}", serialData, e);
            }
        } else {
            logger.error("Invalid call record format: {}", line);
        }
    }

    // Schedule a reconnect attempt
    private void scheduleReconnect() {
        executorService.schedule(() -> {
            try {
                if (webSocketClient != null && !webSocketClient.isOpen()) {
                    logger.info("Attempting to reconnect to WebSocket server...");
                    webSocketClient.reconnect();
                }
            } catch (Exception e) {
                logger.error("Error during WebSocket reconnection attempt.", e);
                // Retry connection after a delay if an error occurs
                scheduleReconnect();
            }
        }, 5, TimeUnit.SECONDS); // Attempt to reconnect after 5 seconds
    }

    
}
