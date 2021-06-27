package com.kwetter.frits.userservice.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwetter.frits.userservice.configuration.KafkaProperties;
import com.kwetter.frits.userservice.entity.User;
import com.kwetter.frits.userservice.interfaces.ModerationLogic;
import com.kwetter.frits.userservice.logic.dto.UserModerationDTO;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class ModerationLogicImpl implements ModerationLogic {

    private final Logger log = LoggerFactory.getLogger(ModerationLogicImpl.class);
    private final KafkaProperties kafkaProperties;
    private static final Logger logger = LoggerFactory.getLogger(ModerationLogicImpl.class);
    private KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ModerationLogicImpl(KafkaProperties kafkaProperties) { this.kafkaProperties = kafkaProperties; }

    @PostConstruct
    public void initialize(){
        log.info("Kafka producer initializing...");
        this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }

    @Override
    public void moderationUserCreate(User user) {
        try {
            var userModerationDTO = new UserModerationDTO(user.getUserId(), user.getUsername(), user.getNickName(), user.getProfileImage(), user.getVerified(), user.getBiography(), user.getRole());
            var message = objectMapper.writeValueAsString(userModerationDTO);
            ProducerRecord<String, String> record = new ProducerRecord<>("mod-user-created", message);
            producer.send(record);
        } catch (JsonProcessingException e) {
            logger.error("Could not send new user", e);
        }
    }

    @Override
    public void moderationUserEdit(User user) {
        try {
            var userModerationDTO = new UserModerationDTO(user.getUserId(), user.getUsername(), user.getNickName(), user.getProfileImage(), user.getVerified(), user.getBiography(), user.getRole());
            var message = objectMapper.writeValueAsString(userModerationDTO);
            ProducerRecord<String, String> record = new ProducerRecord<>("mod-user-edited", message);
            producer.send(record);
        } catch (JsonProcessingException e) {
            logger.error("Could not send updated user", e);
        }
    }

    @Override
    public void moderationUserDelete(User user) {
        try {
            var userModerationDTO = new UserModerationDTO(user.getUserId(), user.getUsername(), user.getNickName(), user.getProfileImage(), user.getVerified(), user.getBiography(), user.getRole());
            var message = objectMapper.writeValueAsString(userModerationDTO);
            ProducerRecord<String, String> record  = new ProducerRecord<>("mod-user-deleted", message);
            producer.send(record);
        } catch (JsonProcessingException e) {
            logger.error("Could not send deleted user", e);
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutdown Kafka producer");
        producer.close();
    }
}
