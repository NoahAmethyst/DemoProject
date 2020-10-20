package com.boot_demo.demo1.utils;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class VideoUtil {

    /**
     * get video duration，the unit is seconds
     *
     * @param video source file
     * @return duration（s）
     */
    public static long getVideoDuration(File video) {
        long duration = 0L;
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();
            duration = ff.getLengthInTime() / (1000 * 1000);
            ff.stop();
        } catch (FrameGrabber.Exception e) {
            log.error("get video duration error:{}", e);
        }
        return duration;
    }


    /**
     * Capture the video to get a picture of the specified frame
     */
    public static List<String> getVideoPic(File video, int size) {
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        List<String> picPathList = new ArrayList<>();
        try {
            ff.start();

            // 截取中间帧图片(具体依实际情况而定)
            int i = 0;
            int length = ff.getLengthInFrames();
            for (int j = 1; j < size; j++) {
                int middleFrame = length / size * j;
                Frame frame = null;
                while (i < length) {
                    frame = ff.grabFrame();
                    if ((i > middleFrame) && (frame.image != null)) {
                        break;
                    }
                    i++;
                }

                // 截取的帧图片
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage srcImage = converter.getBufferedImage(frame);
                int srcImageWidth = srcImage.getWidth();
                int srcImageHeight = srcImage.getHeight();

                // 对截图进行等比例缩放(缩略图)
                int width = 480;
                int height = (int) (((double) width / srcImageWidth) * srcImageHeight);
                BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                thumbnailImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
                String path = "src/main/resources/temp/";
                String picName = path + GenerateUtil.duang() + ".jpg";
                picPathList.add(picName);
                File picFile = new File(picName);
                ImageIO.write(thumbnailImage, "jpg", picFile);
            }

            ff.stop();
        } catch (IOException e) {
            log.error("processing video error:{}", e);
        }
        return picPathList;
    }


}
