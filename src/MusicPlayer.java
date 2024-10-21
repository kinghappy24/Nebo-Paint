
import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicPlayer {
    private List<File> musicFiles;
    private Clip currentClip;
    private int currentIndex = 0;
    private boolean playing = true;

    public MusicPlayer() {
        loadMusicFiles();
        playNextSong();
    }

    private void loadMusicFiles() {
        File musicDir = new File("assets/music/");
        File[] files = musicDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));
        musicFiles = new ArrayList<>();
        if (files != null) {
            Collections.addAll(musicFiles, files);
        }
        Collections.shuffle(musicFiles);
    }

    private void playNextSong() {
        if (musicFiles.isEmpty()) return;
        try {
            if (currentClip != null) {
                currentClip.close();
            }
            File songFile = musicFiles.get(currentIndex);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(songFile);
            currentClip = AudioSystem.getClip();
            currentClip.open(audioIn);
            currentClip.start();
            currentClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    playNextSong();
                }
            });
            currentIndex = (currentIndex + 1) % musicFiles.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (currentClip != null) {
            currentClip.start();
            playing = true;
        }
    }

    public void pause() {
        if (currentClip != null) {
            currentClip.stop();
            playing = false;
        }
    }

    public boolean isPlaying() {
        return playing;
    }
}
