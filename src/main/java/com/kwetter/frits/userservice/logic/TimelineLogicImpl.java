package com.kwetter.frits.userservice.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwetter.frits.userservice.configuration.KafkaProperties;
import com.kwetter.frits.userservice.entity.User;
import com.kwetter.frits.userservice.interfaces.TimelineLogic;
import com.kwetter.frits.userservice.logic.dto.UserTimelineDTO;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class TimelineLogicImpl implements TimelineLogic {

    private final Logger log = LoggerFactory.getLogger(TimelineLogicImpl.class);
    private final KafkaProperties kafkaProperties;
    private static final Logger logger = LoggerFactory.getLogger(TimelineLogicImpl.class);
    private KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TimelineLogicImpl(KafkaProperties kafkaProperties) { this.kafkaProperties = kafkaProperties; }

    @PostConstruct
    public void initialize(){
        log.info("Kafka producer initializing...");
        this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }

    @Override
    public void timeLineUserCreate(User user) throws Exception {
        try {
            var userTimeLineDTO = new UserTimelineDTO(user.getUserId(), user.getUsername(), user.getNickName(), user.getProfileImage(), user.getVerified(), user.getBiography());
            var message = objectMapper.writeValueAsString(userTimeLineDTO);
            ProducerRecord<String, String> record = new ProducerRecord<>("user-created", message);
            producer.send(record);
        } catch (JsonProcessingException e) {
            logger.error("Could not send new user", e);
            throw new Exception(e);
        }
    }

    @Override
    public void timeLineUserEdit(User user) {
        try {
            var userTimeLineDTO = new UserTimelineDTO(user.getUserId(), user.getUsername(), user.getNickName(), user.getProfileImage(), user.getVerified(), user.getBiography());
            var message = objectMapper.writeValueAsString(userTimeLineDTO);
            ProducerRecord<String, String> record = new ProducerRecord<>("user-edited", message);
            producer.send(record);
        } catch (JsonProcessingException e) {
            logger.error("Could not send updated user", e);
        }
    }

    @Override
    public void timeLineUserPermanentDelete(User user) {
        try {
            var userTimeLineDTO = new UserTimelineDTO(user.getUserId(), user.getUsername(), user.getNickName(), user.getProfileImage(), user.getVerified(), user.getBiography());
            var message = objectMapper.writeValueAsString(userTimeLineDTO);
            ProducerRecord<String, String> record = new ProducerRecord<>("permanent-user-deleted", message);
            producer.send(record);
        } catch (JsonProcessingException e) {
            logger.error("Could not send deleted user", e);
        }
    }

    @Override
    public void timeLineUserDelete(User user) {
        try {
            var userTimeLineDTO = new UserTimelineDTO(user.getUserId(), user.getUsername(), user.getNickName(), user.getProfileImage(), user.getVerified(), user.getBiography());
            var message = objectMapper.writeValueAsString(userTimeLineDTO);
            ProducerRecord<String, String> record  = new ProducerRecord<>("user-deleted", message);
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
