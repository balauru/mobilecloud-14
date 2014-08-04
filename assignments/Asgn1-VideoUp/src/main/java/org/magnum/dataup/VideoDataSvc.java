package org.magnum.dataup;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.repositories.VideoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class VideoDataSvc {
    private final VideoRepository videoRepository = new VideoRepository();

    @RequestMapping(value = VideoSvcApi.VIDEO_DATA_PATH, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<VideoStatus> setVideoData(@PathVariable(VideoSvcApi.ID_PARAMETER) long videoId,
                                                  @RequestParam(VideoSvcApi.DATA_PARAMETER) MultipartFile videoData) throws IOException {
        final Video video = videoRepository.get(videoId);
        final VideoFileManager videoFileManager = VideoFileManager.get();

        HttpStatus status = HttpStatus.OK;

        if (video != null) {
            videoFileManager.saveVideoData(video, videoData.getInputStream());
        }
        else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(new VideoStatus(VideoStatus.VideoState.READY), status);
    }

    @RequestMapping(value = VideoSvcApi.VIDEO_DATA_PATH, method = RequestMethod.GET)
    public void getVideoData(@PathVariable(VideoSvcApi.ID_PARAMETER) long videoId,
                             HttpServletResponse response) throws IOException {
        final Video video = videoRepository.get(videoId);
        final VideoFileManager videoFileManager = VideoFileManager.get();

        if (video != null && videoFileManager.hasVideoData(video)) {
            videoFileManager.copyVideoData(video, response.getOutputStream());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
