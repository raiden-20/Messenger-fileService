package ru.vsu.cs.sheina.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.vsu.cs.sheina.fileservice.configuration.rabbit.RabbitQueues;
import ru.vsu.cs.sheina.fileservice.dto.BlogUrlDTO;
import ru.vsu.cs.sheina.fileservice.dto.SocialUrlDTO;

@Service
@RequiredArgsConstructor
public class RabbitService {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessageToSocial(SocialUrlDTO socialUrlDTO) {
        rabbitTemplate.convertSendAndReceive(RabbitQueues.toSocialQueue, socialUrlDTO);
    }

   public void sendMessageToPost(BlogUrlDTO blogUrlDTO) {
       rabbitTemplate.convertSendAndReceive(RabbitQueues.toBlogQueue, blogUrlDTO);
   }
}
