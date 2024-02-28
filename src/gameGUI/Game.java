package gameGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import database.AccessDatabase;

import 首页.*;
public class Game extends JPanel implements KeyListener {
    public GameRecord GR;

    public static final int CELL_SIZE = 18; // 单元格尺寸
    public static int width;
    public static int height;

    public static Color wallsColor = Color.BLACK; // 墙的颜色
    public static Color pathColor = new Color(230, 230, 230); // 路径颜色

    private boolean[][] maze;

    public static OpenGame frame; // 此处frame为临时使用
    private int playerX;
    private int playerY;
    private boolean[][] goldBlocks;
    private boolean[][] pathBlocks;
    private int[][] TollBlocks; // 默认0，1为收费站，2为已过的收费站
    private BufferedImage playerImage; // 角色图片
    private BufferedImage goldImage; // 金币图片
    private BufferedImage tollImage; // 收费站图片
    private long startTime;
    private Timer gameTimer;
    private long stopTimeInSeconds = -1; // 初始值为-1表示计时未停止

	private BufferedImage goldIcon;



    public Game(boolean isNormalMode, int numOfLevel) {
    	setOpaque(false);
        GR = new GameRecord(isNormalMode, numOfLevel);
        width = GR.getWidth();
        height = GR.getHeight();
        try { // 用于提前加载“角色”和“金币”图片
            InputStream inputStream = getClass().getResourceAsStream("/img/character.png");
            playerImage = ImageIO.read(inputStream);
            inputStream = getClass().getResourceAsStream("/img/gold.png");
            goldImage = ImageIO.read(inputStream);
            inputStream = getClass().getResourceAsStream("/img/收费站.png");
            tollImage = ImageIO.read(inputStream);
            inputStream = getClass().getResourceAsStream("/img/金币图标.png");
            goldIcon = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        // 开始游戏
        maze = generateMaze(width, height, GR.getSeed());

        // 初始化
        goldBlocks = new boolean[width][height];
        pathBlocks = new boolean[width][height];
        TollBlocks = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                goldBlocks[x][y] = false;
                pathBlocks[x][y] = false;
                TollBlocks[x][y] = 0;
            }
        }

        Random random = new Random();
        if (GR.getSeed() != 0) { // 通过特定种子用于设置关卡模式的固定迷宫
            random = new Random(GR.getSeed());
        }

        // 在“路”的位置随机生成金币方块和收费站
        int n = 0; // 已生成收费站数量
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ((x < width/4 || x > 3*width/4) && (y < height/4 || y > 3*height/4)) {
                    if (!maze[x][y] && random.nextDouble() < 0.04 && !hasAdjacentTrue(goldBlocks, x, y)) { // 边缘区域1%的概率生成金币
                        goldBlocks[x][y] = true;
                    }
                } else {
                    if (!maze[x][y] && random.nextDouble() > 0.92 && !hasAdjacentTrue(goldBlocks, x, y)) { // 中心区域8%的概率生成金币
                        goldBlocks[x][y] = true;
                    }
                }
                if (x > width/2 && y > height/2 && !goldBlocks[x][y] && !maze[x][y]
                        && random.nextDouble() < 0.1 && !hasAdjacentOne(TollBlocks, x, y)
                        &&  (n < GR.getNumOfToll())) { // 控制数量
                        TollBlocks[x][y] = 1;
                        n++;
                }
            }
        }

        startTime = System.currentTimeMillis();
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint(); // 每秒钟触发一次绘制更新
            }
        });
        gameTimer.start();

        playerX = 1;
        playerY = 1; // 角色初始位置
        setPreferredSize(new Dimension(width * CELL_SIZE, height * CELL_SIZE));
        addKeyListener(this);
        setFocusable(true);
    }

    // 每当窗口需要更新显示时，比如窗口刚刚创建、改变大小、重新展示，或者调用了repaint()方法，paintComponent方法就会被自动调用
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (maze[x][y]) { // true为墙
                    g.setColor(wallsColor);
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else {
                    if (goldBlocks[x][y]){ // true为金币
                        g.drawImage(goldImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                    } else {
                        g.setColor(Color.WHITE);
                        if (pathBlocks[x][y]) {
                            g.setColor(pathColor);
                        }
                        g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        if (TollBlocks[x][y] > 0){
                            g.drawImage(tollImage, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                        }
                    }
                }
            }
        }
        // 起点终点绘制
        g.setColor(Color.RED);
        g.fillRect(CELL_SIZE, CELL_SIZE, CELL_SIZE, CELL_SIZE);
        g.fillRect((width-2)*CELL_SIZE, (height-2)*CELL_SIZE, CELL_SIZE, CELL_SIZE);

        //设置“角色”形象、位置及大小
        g.drawImage(playerImage, playerX * CELL_SIZE, playerY * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);

        // 绘制游戏开始的时间
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        seconds %= 60;

        String timeString = String.format("%02d:%02d", minutes, seconds);
        g.setColor(Color.CYAN);
        g.setFont(new Font("楷体", Font.BOLD, 20));
        g.drawString("游戏时间: " + timeString, (width-7)*CELL_SIZE/2, (height+1)*CELL_SIZE+5);
        
        // 绘制金币数
        g.drawImage(goldIcon, 0, (height)*CELL_SIZE+5, 21, 21, null);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("楷体", Font.BOLD, 20));
        g.drawString(GR.numOfGold+"", 30, (height+1)*CELL_SIZE+5);
        
    }

    private boolean[][] generateMaze(int width, int height, long seed) {
        // 生成迷宫

        boolean[][] maze = new boolean[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                maze[x][y] = true; // 初始化所有单元格为墙
            }
        }

        // 使用Prim算法生成迷宫
        Random random = new Random(); // 创建一个随机数生成器对象，用来随机选择迷宫中的边界单元格
        if (seed != 0) { // 通过特定种子用于设置关卡模式的固定迷宫
            random = new Random(seed);
        }
        List<Point> frontier = new ArrayList<>();
        Point initialCell = new Point(1, 1); // 初始单元格对象
        frontier.add(initialCell);
        maze[initialCell.x][initialCell.y] = false;

        while (!frontier.isEmpty()) {
            Point current = frontier.remove(random.nextInt(frontier.size()));
            List<Point> neighbors = getUnvisitedNeighbors(current, maze);
            if (!neighbors.isEmpty()) {
                frontier.add(current);
                Point chosenNeighbor = neighbors.get(random.nextInt(neighbors.size()));
                int midX = (current.x + chosenNeighbor.x) / 2;
                int midY = (current.y + chosenNeighbor.y) / 2;
                maze[midX][midY] = false;
                maze[chosenNeighbor.x][chosenNeighbor.y] = false;
                frontier.add(chosenNeighbor);
            }
        }

        return maze;
    }

    // 获取当前单元格附近的未访问邻居单元格
    private List<Point> getUnvisitedNeighbors(Point cell, boolean[][] maze) {
        int x = cell.x;
        int y = cell.y;
        List<Point> neighbors = new ArrayList<>();
        if (x >= 2 && maze[x - 2][y]) neighbors.add(new Point(x - 2, y));
        if (x <= width - 3 && maze[x + 2][y]) neighbors.add(new Point(x + 2, y));
        if (y >= 2 && maze[x][y - 2]) neighbors.add(new Point(x, y - 2));
        if (y <= height - 3 && maze[x][y + 2]) neighbors.add(new Point(x, y + 2));
        return neighbors;
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // 如果计时已经停止，不处理键盘输入
        if (stopTimeInSeconds != -1) {
        	GR.getHomeFrame().setEnabled(false);
            return;
        }

        int lastX = playerX;
        int lastY = playerY;
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (playerY > 0 && !maze[playerX][playerY - 1]) {
                    playerY--;
                    GR.addStep();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (playerY < height - 1 && !maze[playerX][playerY + 1]) {
                    playerY++;
                    GR.addStep();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (playerX > 0 && !maze[playerX - 1][playerY]) {
                    playerX--;
                    GR.addStep();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (playerX < width - 1 && !maze[playerX + 1][playerY]) {
                    playerX++;
                    GR.addStep();
                }
                break;
            default:
            	break;
        }
        // 遇到收费站
        if (TollBlocks[playerX][playerY] > 0) {
            if (GR.numOfGold < 5 && TollBlocks[playerX][playerY] == 1) { // 无法通过收费站
                playerX = lastX;
                playerY = lastY;
                GR.reduceStep();
            } else if (GR.numOfGold > 5 && TollBlocks[playerX][playerY] == 1) {
                GR.numOfGold = GR.numOfGold - 5;
                GR.numOfTollPassed++;
                TollBlocks[playerX][playerY] = 2;
            }
        }

        // 角色“吃”金币
        if (goldBlocks[playerX][playerY]) {
            goldBlocks[playerX][playerY] = false;
            GR.numOfGold++;
        }
        pathBlocks[playerX][playerY] = true;

        if (playerX == width - 2 && playerY == height - 2 && stopTimeInSeconds == -1) {
                stopTimeInSeconds = (System.currentTimeMillis() - startTime) / 1000;
                gameTimer.stop(); // 停止计时器
                GR.setSeconds((int) stopTimeInSeconds);
                enterNewPage();
                repaint(); // 重新绘制以显示停止计时的秒数
            
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    // 下面两个方法用于控制金币和收费站的密集程度
    public boolean hasAdjacentTrue(boolean[][] matrix, int row, int col) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int minRow = Math.max(0, row - 3);
        int maxRow = Math.min(rows - 1, row + 3);
        int minCol = Math.max(0, col - 3);
        int maxCol = Math.min(cols - 1, col + 3);

        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (matrix[i][j] && (i != row || j != col)) {
                    return true;
                }
            }
        }

        return false;
    }
    public static boolean hasAdjacentOne(int[][] matrix, int row, int col) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int minRow = Math.max(0, row - 3);
        int maxRow = Math.min(rows - 1, row + 3);
        int minCol = Math.max(0, col - 3);
        int maxCol = Math.min(cols - 1, col + 3);

        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (matrix[i][j] > 0 && (i != row || j != col)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void enterNewPage() {
        JDialog settlementDialog = new JDialog(frame, "Settlement", true); // Set modal to true
        settlementDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        settlementDialog.setSize(900, 650);

        Settlements settlePanel = new Settlements(GR);
        settlePanel.setDialog(settlementDialog);

        settlementDialog.getContentPane().add(settlePanel);
        settlementDialog.setLocationRelativeTo(null);
        settlementDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				AccessDatabase DB = new AccessDatabase("src/playerinfo.accdb");
				DB.builtConnection();
				if (GR.getScore() > settlePanel.getMaxScore()) {
    				DB.updateData("basicinfo", "maxScore", String.valueOf(GR.getScore()), "userName = '"+Main.userName+"'");
    			}
    			if (GR.getScore() < settlePanel.getMinScore()) {
    				DB.updateData("basicinfo", "minScore", String.valueOf(GR.getScore()), "userName = '"+Main.userName+"'");
    			}
    			if (GR.numOfLevel > settlePanel.getMaxLevel()) {
    				DB.updateData("basicinfo", "maxLevel", String.valueOf(GR.numOfLevel), "userName = '"+Main.userName+"'");
    			}
    			if (!Settlements.isNormallyDispose)
    				frame.dispose();
            }
		});

        settlementDialog.setVisible(true);


    }


}