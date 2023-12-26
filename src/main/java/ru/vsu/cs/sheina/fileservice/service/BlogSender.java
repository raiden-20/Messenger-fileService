package ru.vsu.cs.sheina.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.vsu.cs.sheina.fileservice.configuration.rabbit.RabbitQueues;
import ru.vsu.cs.sheina.fileservice.dto.BlogUrlDTO;

@Service
@RequiredArgsConstructor
public class BlogSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessageToPost(BlogUrlDTO blogUrlDTO) {
        rabbitTemplate.convertSendAndReceive(RabbitQueues.toBlogQueue, blogUrlDTO);
    }
}
