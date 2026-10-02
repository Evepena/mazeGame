package game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class GameBase extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    Image off_screen;
    Graphics off_screen_g;

    boolean[] pressing = new boolean[1024];

    public static final int UP = KeyEvent.VK_UP;
    public static final int DN = KeyEvent.VK_DOWN;
    public static final int LT = KeyEvent.VK_LEFT;
    public static final int RT = KeyEvent.VK_RIGHT;

    public static final int _A = KeyEvent.VK_A;
    public static final int _B = KeyEvent.VK_B;
    public static final int _C = KeyEvent.VK_C;
    public static final int _D = KeyEvent.VK_D;
    public static final int _E = KeyEvent.VK_E;
    public static final int _F = KeyEvent.VK_F;
    public static final int _G = KeyEvent.VK_G;
    public static final int _H = KeyEvent.VK_H;
    public static final int _I = KeyEvent.VK_I;
    public static final int _J = KeyEvent.VK_J;
    public static final int _K = KeyEvent.VK_K;
    public static final int _L = KeyEvent.VK_L;
    public static final int _M = KeyEvent.VK_M;
    public static final int _N = KeyEvent.VK_N;
    public static final int _O = KeyEvent.VK_O;
    public static final int _P = KeyEvent.VK_P;
    public static final int _Q = KeyEvent.VK_Q;
    public static final int _R = KeyEvent.VK_R;
    public static final int _S = KeyEvent.VK_S;
    public static final int _T = KeyEvent.VK_T;
    public static final int _U = KeyEvent.VK_U;
    public static final int _V = KeyEvent.VK_V;
    public static final int _W = KeyEvent.VK_W;
    public static final int _X = KeyEvent.VK_X;
    public static final int _Y = KeyEvent.VK_Y;
    public static final int _Z = KeyEvent.VK_Z;

    public static final int _1 = KeyEvent.VK_1;
    public static final int SPACE = KeyEvent.VK_SPACE;

    int mx;
    int my;

    Thread t;

    int screenW = 1280;
    int screenH = 720;

    public abstract void initialize();

    public abstract void inGameLoop();

    public final void init() {
        setSize(screenW, screenH);

        screenW = getWidth();
        screenH = getHeight();

        off_screen = createImage(screenW, screenH);
        off_screen_g = off_screen.getGraphics();

        requestFocus();

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        initialize();

        t = new Thread(this);
        t.start();
    }

    public final void run() {
        while (true) {
            inGameLoop();
            repaint();

            try {
                Thread.sleep(16);
            } catch (Exception x) {}
        }
    }

    public final void update(Graphics g) {
        if (off_screen == null || screenW != getWidth() || screenH != getHeight()) {
            screenW = getWidth();
            screenH = getHeight();

            off_screen = createImage(screenW, screenH);
            off_screen_g = off_screen.getGraphics();
        }

        off_screen_g.setColor(Color.BLACK);
        off_screen_g.fillRect(0, 0, screenW, screenH);

        paint(off_screen_g);

        g.drawImage(off_screen, 0, 0, null);
    }

    public final void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code >= 0 && code < pressing.length) {
            pressing[code] = true;
        }
    }

    public final void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code >= 0 && code < pressing.length) {
            pressing[code] = false;
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}