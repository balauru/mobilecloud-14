package org.magnum.dataup;

import org.magnum.dataup.repository.VideoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VideoConf {
    @Bean
    public VideoRepository videoRepository() {
        return new VideoRepository();
    }
}
