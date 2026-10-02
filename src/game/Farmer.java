package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;

public class Farmer extends GameBase {

    private static final long serialVersionUID = 1L;

    private int x;
    private int y;

    private final int tileSize = 40;
    private final int playerSize = 30;
    private final int speed = 4;

    private boolean hasWon = false;
    private boolean gameStarted = false;
    private boolean gameOver = false;

    private final int startX = 40;
    private final int startY = 40;

    private int score = 0;
    private int requiredCorn = 3;
    private int lives = 3;

    private int level = 1;

    private int timerFrames = 0;
    private int finalTimeSeconds = 0;
    private int hurtCooldown = 0;

    private Image farmerImg, cornImg, groundhogImg, boyImg,
    seagullImg, bushImg, goalImg, dirtTileImg,
    barnImg, treatImg, gardenImg, cornGardenImg;

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Treat> treats = new ArrayList<>();

    private Rect emptyGarden = new Rect(240, 280, 240, 160);
    private Rect cornGarden = new Rect(620, 280, 240, 160);

    private int treatCooldown = 0;
    private int gardenCornHealth = 10;

    private int[][] maze = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,1,1,0,1,0,1,1,1,1,0,1,0,1,1,1,0,1,0,1,1,1,1,1,1,1,0,1},
        {1,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1},
        {1,0,1,0,1,1,1,0,1,0,0,1,1,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1,0,1},
        {1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,1},
        {1,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1},
        {1,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,1},
        {1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,0,1,1,1,1,1,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,1},
        {1,1,1,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1},
        {1,0,1,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1},
        {1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,1},
        {1,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,0,1},
        {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
        {1,0,1,1,0,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1},
        {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,1,0,1,1,1,1,1,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1},
        {1,1,1,1,0,1,0,1,0,0,0,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1},
        {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,1},
        {1,0,1,1,1,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,0,1,1,1,0,1,0,1},
        {1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1},
        {1,1,1,1,1,1,0,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,0,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1},
        {1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,1,1,0,1},
        {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    private int[][] crops;

    private final int goalRow = 26;
    private final int goalCol = 28;

    public Farmer() {
        farmerImg = loadImage("farmer.png");
        cornImg = loadImage("corn.png");
        groundhogImg = loadImage("groundhog.png");
        boyImg = loadImage("boy.png");
        seagullImg = loadImage("seagull.png");
        bushImg = loadImage("bush.png");
        goalImg = loadImage("goal.png");
        dirtTileImg = loadImage("dirt_tile.png");
        barnImg = loadImage("barn.png");
        treatImg = loadImage("treat.png");
        gardenImg = loadImage("garden.png");
        cornGardenImg = loadImage("corn_garden.png");
        
        
        
        resetLevel(1);
    }
 
    public void initialize() { 
        setSize(1280, 720);
    }

    public void inGameLoop() {
        if (!gameStarted) {
            if (pressing[_1]) {
                resetLevel(1);
                gameStarted = true;
            }
            return;
        }

        if (pressing[_R]) {
            resetLevel(1);
            gameStarted = true;
            return;
        }

        if (!hasWon && !gameOver) timerFrames++;
        if (hurtCooldown > 0) hurtCooldown--;

        if (hasWon || gameOver) return;

        int nextX = x;
        int nextY = y;

        if (pressing[LT] || pressing[_A]) nextX -= speed;
        if (pressing[RT] || pressing[_D]) nextX += speed;
        if (pressing[UP] || pressing[_W]) nextY -= speed;
        if (pressing[DN] || pressing[_S]) nextY += speed;

        if (canPlayerMoveTo(nextX, y)) x = nextX;
        if (canPlayerMoveTo(x, nextY)) y = nextY;

        updateCamera();

        if (level == 4) {
            if (treatCooldown > 0) treatCooldown--;

            if (pressing[SPACE] && treatCooldown == 0) {
                treats.add(new Treat(x + playerSize / 2, y + playerSize / 2));
                treatCooldown = 45;
            }

            updateTreats();

            for (Enemy e : enemies) {
                moveEnemyTowardGardenOrTreat(e);
                enemyDamageGarden(e);
            }

            checkEnemyTouch();
            checkLevelFourWin();
        } else {
            collectCrop();

            for (Enemy e : enemies) {
                moveEnemy(e);
                enemyStealCorn(e);
            }

            checkEnemyTouch();
            checkLose();
            checkWin();
        }
    }

    private void resetLevel(int newLevel) {
        level = newLevel;
        x = startX;
        y = startY;
        Camera.setLocation(0, 0, 0);

        hasWon = false;
        gameOver = false;
        score = 0;
        timerFrames = 0;
        finalTimeSeconds = 0;
        hurtCooldown = 0;
        lives = 3;

        enemies.clear();
        treats.clear();

        treatCooldown = 0;
        gardenCornHealth = 10;

        crops = new int[maze.length][maze[0].length];

        if (level == 1) {
            requiredCorn = 8;
            addRandomCorn(14);

            addEnemyOnTile("groundhog", 1, 4, 2, 5);
            addEnemyOnTile("groundhog", 3, 9, 2, 5);
            addEnemyOnTile("groundhog", 5, 21, 2, 5);
            addEnemyOnTile("groundhog", 7, 3, 3, 5);
            addEnemyOnTile("groundhog", 9, 12, 3, 5);
            addEnemyOnTile("groundhog", 13, 18, 3, 5);
            addEnemyOnTile("groundhog", 17, 7, 3, 5);
            addEnemyOnTile("groundhog", 22, 14, 3, 5);
            addEnemyOnTile("groundhog", 26, 24, 3, 5);
        }

        if (level == 2) {
            requiredCorn = 12;
            addRandomCorn(20);

            
            addEnemyOnTile("boy", 1, 8, 3, 4);
            addEnemyOnTile("boy", 3, 22, 4, 4);
            addEnemyOnTile("boy", 7, 26, 4, 4);
            addEnemyOnTile("boy", 11, 20, 4, 4);
            addEnemyOnTile("boy", 17, 16, 4, 4);
            addEnemyOnTile("boy", 22, 23, 4, 4);
            addEnemyOnTile("boy", 26, 27, 4, 4);

            
            addEnemyOnTile("groundhog", 5, 5, 3, 4);
            addEnemyOnTile("groundhog", 15, 9, 3, 4);
            addEnemyOnTile("groundhog", 20, 2, 3, 4);
        }

        if (level == 3) {
            requiredCorn = 16;
            addRandomCorn(28);
            enemies.add(new Enemy("seagull", 680, 360, 4, 4));
            enemies.add(new Enemy("seagull", 560, 80, 4, 5));
            enemies.add(new Enemy("seagull", 120, 360, 4, 6));
            enemies.add(new Enemy("boy", 880, 520, 4, 4));
            enemies.add(new Enemy("groundhog", 320, 640, 2, 3));
            enemies.add(new Enemy("seagull", 1040, 120, 4, 3));
            enemies.add(new Enemy("boy", 880, 880, 4, 4));
            enemies.add(new Enemy("groundhog", 240, 920, 4, 4));
        }

        if (level == 4) {
            requiredCorn = 1;
            score = 0;

            enemies.add(new Enemy("groundhog", 120, 120, 3, 3));
            enemies.add(new Enemy("boy", 980, 120, 3, 3));
            enemies.add(new Enemy("seagull", 160, 760, 4, 3));
            enemies.add(new Enemy("groundhog", 980, 760, 3, 3));
        }
    }

    private void checkWin() {
        int playerCol = (x + playerSize / 2) / tileSize;
        int playerRow = (y + playerSize / 2) / tileSize;

        if (playerRow == goalRow && playerCol == goalCol && score >= requiredCorn) {
            if (level < 4) {
                resetLevel(level + 1);
            } else {
                hasWon = true;
                finalTimeSeconds = timerFrames / 60;
            }
        }
    }

    private void checkLevelFourWin() {
        if (timerFrames / 60 >= 45 && gardenCornHealth > 0) {
            hasWon = true;
            finalTimeSeconds = timerFrames / 60;
        }
    }

    private boolean overlaps(int ax, int ay, int aw, int ah, int bx, int by, int bw, int bh) {
        return ax < bx + bw && ax + aw > bx && ay < by + bh && ay + ah > by;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(190, 230, 190));
        g.fillRect(0, 0, getWidth(), getHeight());

        if (!gameStarted) {
            drawTitleScreen(g);
            return;
        }

        drawMaze(g);

        if (level == 4) drawLevelFourObjects(g);
        else drawGoal(g);

        drawFarmer(g, x - Camera.x, y - Camera.y);

        for (Enemy e : enemies) drawEnemy(g, e);

        drawHUD(g);

        if (hasWon) drawWinScreen(g);
        if (gameOver) drawGameOverScreen(g);
    }

    private void drawLevelFourObjects(Graphics g) {
        drawBarn(g, 80 - Camera.x, 80 - Camera.y);
        drawBarn(g, 960 - Camera.x, 80 - Camera.y);

        drawGarden(g, emptyGarden, false);
        drawGarden(g, cornGarden, true);

        for (Treat t : treats) {
            int drawX = t.x - Camera.x;
            int drawY = t.y - Camera.y;

            if (treatImg != null) {
                g.drawImage(treatImg, drawX, drawY, 24, 24, null);
            } else {
                g.setColor(new Color(255, 180, 90));
                g.fillOval(drawX, drawY, 24, 24);

                g.setColor(Color.BLACK);
                g.drawOval(drawX, drawY, 24, 24);
            }
        }
    }

    private void drawBarn(Graphics g, int x, int y) {
        if (barnImg != null) {
            g.drawImage(barnImg, x, y, 140, 130, null);
            return;
        }

        g.setColor(new Color(130, 40, 30));
        g.fillRect(x, y + 40, 130, 90);

        g.setColor(new Color(90, 30, 20));
        int[] roofX = {x - 10, x + 65, x + 140};
        int[] roofY = {y + 45, y, y + 45};
        g.fillPolygon(roofX, roofY, 3);

        g.setColor(Color.WHITE);
        g.drawRect(x + 45, y + 75, 40, 55);
        g.drawLine(x + 45, y + 75, x + 85, y + 130);
        g.drawLine(x + 85, y + 75, x + 45, y + 130);
    }

    private void drawGarden(Graphics g, Rect garden, boolean hasCorn) {
        int gx = (int) garden.x - Camera.x;
        int gy = (int) garden.y - Camera.y;

        if (hasCorn && cornGardenImg != null) {
            g.drawImage(cornGardenImg, gx, gy, garden.w, garden.h, null);
            return;
        }

        if (!hasCorn && gardenImg != null) {
            g.drawImage(gardenImg, gx, gy, garden.w, garden.h, null);
            return;
        }

        g.setColor(new Color(120, 75, 35));
        g.fillRect(gx, gy, garden.w, garden.h);

        g.setColor(new Color(85, 50, 25));
        g.drawRect(gx, gy, garden.w, garden.h);

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 6; col++) {
                int px = gx + 25 + col * 35;
                int py = gy + 25 + row * 30;

                if (hasCorn) {
                    drawCrop(g, px, py);
                } else {
                    g.setColor(new Color(90, 55, 25));
                    g.fillOval(px + 8, py + 12, 14, 8);
                }
            }
        }
    }

    private void drawHUD(Graphics g) {

        g.setColor(new Color(0, 0, 0, 170));
        g.fillRoundRect(15, 15, 250, 150, 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Level " + level, 30, 45);

        if (level == 4) {

            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Protect the corn garden!", 30, 75);
            g.drawString("SPACE = Drop treats", 30, 100);
            g.drawString("Garden Health: " + gardenCornHealth, 30, 125);
            g.drawString("Lives: " + lives, 30, 150);

            return;
        }

        
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Corn left: " + countCorn(), 30, 80);
        g.drawString("Stolen: " + stolenCornTotal(), 30, 110);
        g.drawString("Lives: " + lives, 30, 140);

        
        int iconX = getWidth() - 170;
        int iconY = 18;

        if (cornImg != null) {
            g.drawImage(cornImg, iconX, iconY, 40, 40, null);
        } else {
            g.setColor(Color.YELLOW);
            g.fillOval(iconX, iconY, 40, 40);
        }

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(score + "/" + requiredCorn, iconX + 50, iconY + 30);
    }

    private boolean canPlayerMoveTo(int newX, int newY) {
        int left = newX;
        int right = newX + playerSize - 1;
        int top = newY;
        int bottom = newY + playerSize - 1;

        int leftCol = left / tileSize;
        int rightCol = right / tileSize;
        int topRow = top / tileSize;
        int bottomRow = bottom / tileSize;

        if (leftCol < 0 || rightCol >= maze[0].length || topRow < 0 || bottomRow >= maze.length) return false;

        if (level == 4) return true;

        return maze[topRow][leftCol] == 0 &&
               maze[topRow][rightCol] == 0 &&
               maze[bottomRow][leftCol] == 0 &&
               maze[bottomRow][rightCol] == 0;
    }

    private boolean canEnemyMoveTo(Enemy e, int newX, int newY) {
        int leftCol = newX / tileSize;
        int rightCol = (newX + e.size - 1) / tileSize;
        int topRow = newY / tileSize;
        int bottomRow = (newY + e.size - 1) / tileSize;

        if (leftCol < 0 || rightCol >= maze[0].length || topRow < 0 || bottomRow >= maze.length) return false;

        if (level == 4) return true;

        return maze[topRow][leftCol] == 0 &&
               maze[topRow][rightCol] == 0 &&
               maze[bottomRow][leftCol] == 0 &&
               maze[bottomRow][rightCol] == 0;
    }

    private void updateCamera() {
        int camX = x + playerSize / 2 - getWidth() / 2;
        int camY = y + playerSize / 2 - getHeight() / 2;

        int worldW = maze[0].length * tileSize;
        int worldH = maze.length * tileSize;

        int maxCamX = worldW - getWidth();
        int maxCamY = worldH - getHeight();

        if (maxCamX < 0) maxCamX = 0;
        if (maxCamY < 0) maxCamY = 0;
        if (camX < 0) camX = 0;
        if (camY < 0) camY = 0;
        if (camX > maxCamX) camX = maxCamX;
        if (camY > maxCamY) camY = maxCamY;

        Camera.setLocation(camX, camY, 0); 
    }

    private Image loadImage(String filename) {
        java.net.URL url = getClass().getResource("/images/" + filename);

        if (url == null) {
            System.out.println("Missing image: " + filename);
            return null;
        }

        return new ImageIcon(url).getImage();
    }

    private void addEnemyOnTile(String type, int row, int col, int speed, int delay) {
        if (maze[row][col] == 0) {
            enemies.add(new Enemy(
                    type,
                    col * tileSize + 5,
                    row * tileSize + 5,
                    speed,
                    delay));
        }
    }
    
    private void addRandomCorn(int amount) {
        ArrayList<Point> openTiles = new ArrayList<>();

        for (int row = 1; row < maze.length - 1; row++) {
            for (int col = 1; col < maze[row].length - 1; col++) {
                if (maze[row][col] == 0) {
                    boolean tooCloseToStart = row <= 2 && col <= 2;
                    boolean isGoal = row == goalRow && col == goalCol;

                    if (!tooCloseToStart && !isGoal) {
                        openTiles.add(new Point(col, row));
                    }
                }
            }
        }

        Collections.shuffle(openTiles);

        for (int i = 0; i < amount && i < openTiles.size(); i++) {
            Point p = openTiles.get(i);
            crops[p.y][p.x] = 1;
        }
    }

    private void collectCrop() {
        int col = (x + playerSize / 2) / tileSize;
        int row = (y + playerSize / 2) / tileSize;

        if (row >= 0 && row < crops.length && col >= 0 && col < crops[0].length) {
            if (crops[row][col] == 1) {
                crops[row][col] = 0;
                score++;
            }
        }
    }

    private void updateTreats() {
        for (int i = treats.size() - 1; i >= 0; i--) {
            Treat t = treats.get(i);
            t.life--;

            if (t.life <= 0) {
                treats.remove(i);
            }
        }
    }

    private Treat getClosestTreat(Enemy e) {
        Treat closest = null;
        int best = 999999;

        for (Treat t : treats) {
            int d = Math.abs(e.x - t.x) + Math.abs(e.y - t.y);

            if (d < best) {
                best = d;
                closest = t;
            }
        }

        return closest;
    }

    private void moveEnemyTowardGardenOrTreat(Enemy e) {
        e.moveDelay++;

        if (e.moveDelay < e.delayLimit) return;

        e.moveDelay = 0;

        Treat treat = getClosestTreat(e);

        int targetX;
        int targetY;

        if (treat != null) {
            targetX = treat.x;
            targetY = treat.y;
        } else {
            targetX = (int) cornGarden.x + cornGarden.w / 2;
            targetY = (int) cornGarden.y + cornGarden.h / 2;
        }

        int nextX = e.x;
        int nextY = e.y;

        if (Math.abs(targetX - e.x) > Math.abs(targetY - e.y)) {
            if (targetX < e.x) nextX -= e.speed;
            else nextX += e.speed;
        } else {
            if (targetY < e.y) nextY -= e.speed;
            else nextY += e.speed;
        }

        if (canEnemyMoveTo(e, nextX, nextY)) {
            e.x = nextX;
            e.y = nextY;
        }

        if (treat != null && overlaps(e.x, e.y, e.size, e.size, treat.x, treat.y, 24, 24)) {
            treats.remove(treat);
        }
    }

    private void enemyDamageGarden(Enemy e) {
        if (overlaps(e.x, e.y, e.size, e.size,
                (int) cornGarden.x, (int) cornGarden.y, cornGarden.w, cornGarden.h)) {

            gardenCornHealth--;

            e.x += 80;
            e.y += 80;

            if (gardenCornHealth <= 0) {
                gameOver = true;
            }
        }
    }

    private void moveEnemy(Enemy e) {
        e.moveDelay++;

        if (e.moveDelay < e.delayLimit) return;

        e.moveDelay = 0;

        if (e.type.equals("boy") || e.type.equals("seagull")) {
            if (chasePlayer(e)) return;
        }

        if (!tryMoveEnemy(e, e.direction)) {
            chooseNewEnemyDirection(e);
            tryMoveEnemy(e, e.direction);
        }
    }

    private boolean chasePlayer(Enemy e) {
        int distanceX = x - e.x;
        int distanceY = y - e.y;
        int distance = Math.abs(distanceX) + Math.abs(distanceY);

        if (distance > 800) return false;

        int bestDirection = findPathDirection(e);   

        if (bestDirection != -1) {
            return tryMoveEnemy(e, bestDirection);
        }

        chooseNewEnemyDirection(e);
        return tryMoveEnemy(e, e.direction);
    }

    private int findPathDirection(Enemy e) {
        int rows = maze.length;
        int cols = maze[0].length;

        int startRow = (e.y + e.size / 2) / tileSize;
        int startCol = (e.x + e.size / 2) / tileSize;

        int targetRow = (y + playerSize / 2) / tileSize;
        int targetCol = (x + playerSize / 2) / tileSize;

        if (startRow < 0 || startRow >= rows || startCol < 0 || startCol >= cols) return -1;
        if (targetRow < 0 || targetRow >= rows || targetCol < 0 || targetCol >= cols) return -1;

        boolean[][] visited = new boolean[rows][cols];
        int[][] firstDirection = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                firstDirection[r][c] = -1;
            }
        }

        ArrayList<Point> queue = new ArrayList<>();
        queue.add(new Point(startCol, startRow));
        visited[startRow][startCol] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int i = 0; i < queue.size(); i++) {
            Point p = queue.get(i);

            if (p.y == targetRow && p.x == targetCol) {
                return firstDirection[p.y][p.x];
            }

            for (int d = 0; d < 4; d++) {
                int nr = p.y + dr[d];
                int nc = p.x + dc[d];

                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                if (visited[nr][nc]) continue;
                if (maze[nr][nc] != 0) continue;

                visited[nr][nc] = true;

                if (p.y == startRow && p.x == startCol) {
                    firstDirection[nr][nc] = d;
                } else {
                    firstDirection[nr][nc] = firstDirection[p.y][p.x];
                }

                queue.add(new Point(nc, nr));
            }
        }

        return -1;
    }

    private boolean tryMoveEnemy(Enemy e, int direction) {
        int nextX = e.x;
        int nextY = e.y;

        if (direction == 0) nextY -= e.speed;
        if (direction == 1) nextY += e.speed;
        if (direction == 2) nextX -= e.speed;
        if (direction == 3) nextX += e.speed;

        if (canEnemyMoveTo(e, nextX, nextY)) {
            e.x = nextX;
            e.y = nextY;
            e.direction = direction;
            return true;
        }

        return false;
    }

    private void chooseNewEnemyDirection(Enemy e) {
        ArrayList<Integer> possibleDirections = new ArrayList<>();

        for (int direction = 0; direction < 4; direction++) {
            int nextX = e.x;
            int nextY = e.y;

            if (direction == 0) nextY -= e.speed;
            if (direction == 1) nextY += e.speed;
            if (direction == 2) nextX -= e.speed;
            if (direction == 3) nextX += e.speed;

            if (canEnemyMoveTo(e, nextX, nextY)) {
                possibleDirections.add(direction);
            }
        }

        if (!possibleDirections.isEmpty()) {
            int randomIndex = (int)(Math.random() * possibleDirections.size());
            e.direction = possibleDirections.get(randomIndex);
        }
    }

    private void enemyStealCorn(Enemy e) {
        int col = (e.x + e.size / 2) / tileSize;
        int row = (e.y + e.size / 2) / tileSize;

        if (row >= 0 && row < crops.length && col >= 0 && col < crops[0].length) {
            if (crops[row][col] == 1) {
                crops[row][col] = 0;
                e.stolenCorn++;
            }
        }
    }

    private void checkEnemyTouch() {
        if (hurtCooldown > 0) return;

        for (Enemy e : enemies) {
            if (overlaps(x, y, playerSize, playerSize, e.x, e.y, e.size, e.size)) {
                lives--;
                hurtCooldown = 60;
                x = startX;
                y = startY;
                updateCamera();

                if (lives <= 0) gameOver = true;

                return;
            }
        }
    }

    private int countCorn() {
        int cornLeft = 0;

        for (int row = 0; row < crops.length; row++) {
            for (int col = 0; col < crops[row].length; col++) {
                if (crops[row][col] == 1) cornLeft++;
            }
        }

        return cornLeft;
    }

    private int stolenCornTotal() {
        int total = 0;

        for (Enemy e : enemies) total += e.stolenCorn;

        return total;
    }

    private void checkLose() {
        if (score + countCorn() < requiredCorn) gameOver = true;
    }

    private void drawMaze(Graphics g) {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                int tileX = col * tileSize - Camera.x;
                int tileY = row * tileSize - Camera.y;

                if (tileX + tileSize < 0 || tileX > getWidth() || tileY + tileSize < 0 || tileY > getHeight()) continue;

                if (level == 4 || maze[row][col] == 0) {
                    if (dirtTileImg != null) {
                        g.drawImage(dirtTileImg, tileX, tileY, tileSize, tileSize, null);
                    } else {
                        g.setColor(new Color(150, 100, 60));
                        g.fillRect(tileX, tileY, tileSize, tileSize);
                    }
                } else {
                    if (bushImg != null) {
                        g.drawImage(bushImg, tileX, tileY, tileSize, tileSize, null);
                    } else {
                        drawBush(g, tileX, tileY);
                    }
                }

                g.setColor(new Color(100, 70, 40));
                g.drawRect(tileX, tileY, tileSize, tileSize);

                if (level != 4 && crops[row][col] == 1) {
                    drawCrop(g, tileX, tileY);
                }
            }
        }
    }

    private void drawGoal(Graphics g) {
        int goalX = goalCol * tileSize - Camera.x;
        int goalY = goalRow * tileSize - Camera.y;

        if (goalImg != null) {
            g.drawImage(goalImg, goalX, goalY, tileSize, tileSize, null);
        } else {
            g.setColor(new Color(40, 200, 70));
            g.fillRect(goalX, goalY, tileSize, tileSize);

            g.setColor(Color.WHITE);
            g.drawRect(goalX + 4, goalY + 4, tileSize - 8, tileSize - 8);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("GOAL", goalX + 4, goalY + 24);
        }
    }

    private void drawTitleScreen(Graphics g) {
        g.setColor(new Color(170, 220, 170));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(new Color(85, 135, 85));
        g.fillRoundRect(310, 100, 660, 500, 30, 30);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("Farmer Maze Game", 425, 185);

        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Use arrow keys or WASD to move", 430, 250);
        g.drawString("Collect corn before enemies steal it", 430, 300);
        g.drawString("Level 4: protect the corn garden", 430, 350);
        g.drawString("Drop treats to distract enemies", 430, 400);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.drawString("Press 1 to Start", 510, 560);
    }

    private void drawFarmer(Graphics g, int x, int y) {
        if (hurtCooldown > 0 && hurtCooldown % 10 < 5) return;

        if (farmerImg != null) {
            g.drawImage(farmerImg, x, y, playerSize, playerSize, null);
            return;
        }

        g.setColor(new Color(139, 69, 19));
        g.fillRect(x + 5, y + 2, 20, 6);
        g.fillRect(x + 9, y, 12, 4);

        g.setColor(new Color(255, 224, 189));
        g.fillOval(x + 8, y + 6, 14, 14);

        g.setColor(Color.RED);
        g.fillRect(x + 9, y + 20, 12, 10);
    }

    private void drawEnemy(Graphics g, Enemy e) {
        int drawX = e.x - Camera.x;
        int drawY = e.y - Camera.y;

        Image img = null;

        if (e.type.equals("groundhog")) img = groundhogImg;
        if (e.type.equals("boy")) img = boyImg;
        if (e.type.equals("seagull")) img = seagullImg;

        if (img != null) {
            g.drawImage(img, drawX, drawY, playerSize, playerSize, null);
            return;
        }

        g.setColor(Color.BLACK);
        g.fillOval(drawX, drawY, playerSize, playerSize);
    }

    private void drawCrop(Graphics g, int tileX, int tileY) {
        if (cornImg != null) {
            g.drawImage(cornImg, tileX + 5, tileY + 5, 30, 30, null);
            return;
        }

        g.setColor(new Color(30, 150, 40));
        g.fillRect(tileX + 16, tileY + 10, 8, 24);

        g.setColor(new Color(255, 215, 0));
        g.fillOval(tileX + 14, tileY + 18, 12, 16);
    }

    private void drawBush(Graphics g, int tileX, int tileY) {
        g.setColor(new Color(40, 120, 45));
        g.fillOval(tileX + 2, tileY + 8, 18, 22);
        g.fillOval(tileX + 12, tileY + 2, 20, 26);
        g.fillOval(tileX + 22, tileY + 10, 16, 22);
    }

    private void drawWinScreen(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRoundRect(420, 220, 440, 210, 25, 25);

        g.setColor(new Color(255, 215, 0));
        g.setFont(new Font("Arial", Font.BOLD, 44));

        if (level == 4) g.drawString("Garden Saved!", 485, 285);
        else g.drawString("You Win!", 555, 285);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Final Time: " + finalTimeSeconds + " sec", 505, 335);

        g.setFont(new Font("Arial", Font.PLAIN, 22));
        g.drawString("Press R to play again", 530, 385);
    }

    private void drawGameOverScreen(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRoundRect(400, 220, 480, 230, 25, 25);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 42));
        g.drawString("Game Over!", 520, 285);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 22));

        if (level == 4) {
            g.drawString("The enemies ruined the garden!", 465, 340);
        } else if (lives <= 0) {
            g.drawString("You lost all your lives!", 510, 340);
        } else {
            g.drawString("You did not collect enough corn!", 465, 340);
        }

        g.drawString("Press R to try again", 535, 395);
    }

    private class Treat {
        int x;
        int y;
        int life = 300;

        Treat(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private class Enemy {
        String type;
        int x;
        int y;
        int speed;
        int delayLimit;
        int moveDelay = 0;
        int size = playerSize;
        int stolenCorn = 0;
        int direction = 0;

        Enemy(String type, int x, int y, int speed, int delayLimit) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.delayLimit = delayLimit;
            this.direction = (int)(Math.random() * 4);
        }
    }
}


