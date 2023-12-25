package ru.vsu.cs.sheina.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.vsu.cs.sheina.fileservice.configuration.rabbit.RabbitQueues;
import ru.vsu.cs.sheina.fileservice.dto.UrlDTO;

@Service
@RequiredArgsConstructor
public class RabbitService {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessageToSocial(UrlDTO urlDTO) {
        rabbitTemplate.convertSendAndReceive(RabbitQueues.toSocialQueue, urlDTO);
    }

   public void sendMessageToPost(UrlDTO urlDTO) {
       rabbitTemplate.convertSendAndReceive(RabbitQueues.toBlogQueue, urlDTO);
   }
}
