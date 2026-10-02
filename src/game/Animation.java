package game;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Animation {

    private Image[] images;
    private int current = 0;
    private int duration;
    private int delay;

    public Animation(String name, int count, int duration, String filetype) {
        if (count <= 0) {
            throw new IllegalArgumentException("count must be greater than 0");
        }

        if (duration <= 0) {
            throw new IllegalArgumentException("duration must be greater than 0");
        }

        images = new Image[count];
        this.duration = duration;
        this.delay = duration;

        for (int i = 0; i < images.length; i++) {
            images[i] = loadImage(name + "_" + i + "." + filetype);
        }
    }

    public Image stillImage() {
        return images[0];
    }

    public Image nextImage() {
        if (images.length == 1) {
            return images[0];
        }

        delay--;

        if (delay <= 0) {
            current = (current + 1) % images.length;
            delay = duration;
        }

        return images[current];
    }

    private Image loadImage(String filename) {
        return new ImageIcon(filename).getImage();
    }
}



