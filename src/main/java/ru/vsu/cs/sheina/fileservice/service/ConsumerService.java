package ru.vsu.cs.sheina.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.vsu.cs.sheina.fileservice.configuration.rabbit.RabbitQueues;
import ru.vsu.cs.sheina.fileservice.dto.FileDTO;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final MinioService minioService;

    @RabbitListener(queues = RabbitQueues.toFileQueue)
    public void actionWithFile(FileDTO fileDTO){
        if (fileDTO.getFile() == null) {
            minioService.deleteFile(fileDTO);
        } else {
            minioService.saveFile(fileDTO);
        }
    }
}
