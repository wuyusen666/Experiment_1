package tool;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class AudioPlayer {//音乐部分
    public Clip clip;
    public static boolean isBegin = true;
    public static boolean isRunning = false;
    private long clipTimePosition = 0; // 用于保存音频剪辑的播放位置
    
    // 播放音频文件
    public void play(String audioFilePath) {
        try {
            // 创建一个音频输入流
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
            // 获取音频格式
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            // 通过信息获取音频剪辑
            clip = (Clip) AudioSystem.getLine(info);
            // 打开音频剪辑
            clip.open(audioInputStream);
            // 开始播放音频
            clip.start();
            isRunning = true;
            // 音频播放完毕后循环播放
            clip.addLineListener(new LineListener() {
                public void update(LineEvent evt) {
                    if (evt.getType() == LineEvent.Type.STOP) {
                        clip.setFramePosition(0); // 将音频剪辑的位置设置为起始位置
                        clip.start(); // 重新播放
                    }
                }
            });
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public void pause() {
        if (clip != null && clip.isRunning()) {
            clipTimePosition = clip.getMicrosecondPosition(); // 保存当前播放位置
            clip.stop();
        }
    }

    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(clipTimePosition); // 设置播放位置
            clip.start();
        }
    }
    
    // 停止播放音频
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.close();
            isRunning = false;
        }
    }
    // 随机播放音频
    public void randomPlay(){
        Random random = new Random(System.currentTimeMillis());
        int a = random.nextInt(11)+1;
        switch (a) {
		case 1:
			play("src/music/FakeLove.wav");
			break;
		case 2:
			play("src/music/Ferrari.wav");
			break;
		case 3:
			play("src/music/Komorebi.wav");
			break;
		case 4:
			play("src/music/LettingGo.wav");
			break;
		case 5:
			play("src/music/TheWayIStillLoveYou.wav");
			break;
		case 6:
			play("src/music/打上花火钢琴版.wav");
			break;
		case 7:
			play("src/music/黄昏之时.wav");
			break;
		case 8:
			play("src/music/卡农.wav");
			break;
		case 9:
			play("src/music/青空.wav");
			break;
		case 10:
			play("src/music/星茶会.wav");
			break;
		case 11:
			play("src/music/无感.wav");
			break;

		default:
			break;
		}
    }
}
