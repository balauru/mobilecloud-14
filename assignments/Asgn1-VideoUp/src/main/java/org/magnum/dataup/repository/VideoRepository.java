package org.magnum.dataup.repository;

import org.magnum.dataup.model.Video;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class VideoRepository {
    private static final Map<Long, Video> videos = new HashMap<>();

    private final AtomicLong currentId = new AtomicLong(0L);

    public Video add(Video video) {
        video.setId(currentId.incrementAndGet());
        video.setDataUrl(getDataUrl(currentId.get()));
        videos.put(currentId.get(), video);

        return video;
    }

    public Collection<Video> get() {
        return videos.values();
    }

    public Video get(long videoId) {
        return videos.get(videoId);
    }

    private String getDataUrl(long videoId) {
        return getUrlBaseForLocalServer() + "/video/" + videoId + "/data";
    }

    private String getUrlBaseForLocalServer() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return "http://" + request.getServerName()
                + ((request.getServerPort() != 80) ? ":" + request.getServerPort() : "");
    }
}
