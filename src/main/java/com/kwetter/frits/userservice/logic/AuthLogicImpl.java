package com.kwetter.frits.userservice.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwetter.frits.userservice.configuration.KafkaProperties;
import com.kwetter.frits.userservice.interfaces.AuthLogic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Service
public class AuthLogicImpl implements AuthLogic {

    private final Logger log = LoggerFactory.getLogger(TimelineLogicImpl.class);

    private static final String TOPIC = "user-register";

    private final KafkaProperties kafkaProperties;

    private final static Logger logger = LoggerFactory.getLogger(TimelineLogicImpl.class);
    private KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthLogicImpl(KafkaProperties kafkaProperties) { this.kafkaProperties = kafkaProperties; }

    @PostConstruct
    public void initialize(){
        log.info("Kafka producer initializing...");
        this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }
    @Override
    public void registerNewUser(UUID userId, String username, String password) throws Exception {
//        try {
//            UserAuthDTO userAuthDTO = new UserAuthDTO(userId, username, password);
//            String message = objectMapper.writeValueAsString(userAuthDTO);
//            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
//            producer.send(record);
//        } catch (JsonProcessingException e) {
//            logger.error("Could not send new user", e);
//            throw new Exception(e);
//        }
        logger.info("User Registered: userId=" + userId + ", username=" + username + ", password=" + password);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutdown Kafka producer");
        producer.close();
    }
}
