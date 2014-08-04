package org.magnum.dataup;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
public class VideoSvc {
    @Autowired
    public VideoRepository repository;

    @RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.POST)
    public @ResponseBody Video addVideo(@RequestBody Video video) {
        return repository.add(video);
    }

    @RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.GET)
    public @ResponseBody Collection<Video> getVideoList() {
        return repository.get();
    }
}