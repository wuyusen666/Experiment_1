package 首页;

import gameGUI.Game;
import tool.AudioPlayer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import database.*;

public class Home extends OpenGame {
    JButton levelMode; //关卡模式标签
    JButton randomMode; //随机模式标签
    JButton options; //其它选项（如设置、游戏记录）
    JButton exitGame; //退出游戏
    JLabel userInfo; //用户信息
    private ImageIcon bgIcon;
	public static AudioPlayer AP;

    public Home() {
    	super("迷宫小游戏", 700, 700);
        initial();
        setResizable(false); //不可改变大小
        bgIcon = new ImageIcon("src/img/appIcon.png");
        setIconImage(bgIcon.getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        if (AudioPlayer.isBegin) {
	        AP = new AudioPlayer();
	        AP.randomPlay();
	        AudioPlayer.isBegin = false;
        }
        
        init();
        setVisible(true);
    }

    private void init() {

        levelMode = new JButton("普通模式");
        levelMode.setBounds(250, 200, 200, 70);
        levelMode.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        levelMode.setForeground(Color.WHITE);
        setIconOfBtn(levelMode, "src/img/关卡.png", 21, 25);
        levelMode.setContentAreaFilled(false);
        levelMode.setFocusPainted(false);
//        levelMode.setBorderPainted(true);

        randomMode = new JButton("随机模式");
        randomMode.setBounds(250, 300, 200, 70);
        randomMode.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        randomMode.setForeground(Color.WHITE);
        setIconOfBtn(randomMode, "src/img/随机.png", 21, 25);
        randomMode.setContentAreaFilled(false);
        randomMode.setFocusPainted(false);

        options = new JButton("   选项    ");
        options.setBounds(250, 400, 200, 70);
        options.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        options.setForeground(Color.WHITE);
        setIconOfBtn(options, "src/img/选项.png", 21, 25);
        options.setContentAreaFilled(false);
        options.setFocusPainted(false);

        exitGame = new JButton("退出游戏");
        exitGame.setBounds(250, 500, 200, 70);
        exitGame.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        exitGame.setForeground(Color.WHITE);
        setIconOfBtn(exitGame, "src/img/退出_logout.png", 21, 25);
        exitGame.setContentAreaFilled(false);
        exitGame.setFocusPainted(false);

        userInfo = new JLabel(Main.userName); // 从数据库中获取用户名:"账户：xxx已登录"
        userInfo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        // 计算label长度
        FontMetrics fontMetrics = userInfo.getFontMetrics(userInfo.getFont());
        setIconForLabel(userInfo, "src/img/头像_avatar.png", 21, 10);
        int stringWidth = fontMetrics.stringWidth(userInfo.getText());
        userInfo.setBounds(620-stringWidth, 10, stringWidth+80, 30);
        userInfo.setForeground(Color.WHITE);

        add(levelMode);
        add(randomMode);
        add(options);
        add(exitGame);
        add(userInfo);
        add(beautifyBackground("src/img/迷宫游戏背景.png"));

//        add(bgImg);

        // 为关卡模式按钮添加点击事件
        levelMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到普通模式面板
                switchPanel(1);
            }
        });

        randomMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到随机模式面板
                switchPanel(2);
            }
        });

        // 退出游戏按钮事件
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "确定退出吗？",
                        "提示",
                        JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) { //如果选择确认退出，退出程序
                    dispose();
                    System.exit(0);
                }
            }
        });

        // 选项按钮事件
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // 隐藏首页界面
                new OptionsPage(Home.this);
            }
        });


    }

    // 设置按钮图标
    public static void setIconOfBtn(JButton btn, String filePath, int size, int gap) {
        ImageIcon Icon = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
        btn.setIcon(Icon);
        btn.setIconTextGap(gap);
//		btn.setHorizontalTextPosition(JButton.RIGHT); // 将文本放置在图标的右边
    }
    private static void setIconForLabel(JLabel label, String filePath, int size, int gap) {
        ImageIcon icon = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
        label.setIcon(icon);
        label.setIconTextGap(gap);
    }

    private void switchPanel(int mode) { // 1：普通  2：随机
        getContentPane().removeAll(); // 移除当前所有组件

        if (mode == 1) {
            NormalModePanel normalModePanel = new NormalModePanel(this); // 创建普通模式面板
            normalModePanel.getusername(Main.userName);
            normalModePanel.setBounds(0, 0, 700, 700);
            add(normalModePanel);
        } else {
            RandomModePanel randomModePanel = new RandomModePanel(this); // 创建随机模式面板
            randomModePanel.setBounds(0, 0, 700, 700);
            add(randomModePanel);
        }
        add(beautifyBackground("src/img/迷宫游戏背景.png"));
        
        revalidate(); // 重新绘制界面
        repaint();
    }

    public void switchToHomePanel() {
        getContentPane().removeAll();
        init(); // 重新添加初始组件
        revalidate();
        repaint();
    }

}




class NormalModePanel extends JPanel {

    private Home homeFrame;
    private String username;
	private AccessDatabase DB;
	
    public NormalModePanel(Home homeFrame) {
        this.homeFrame = homeFrame;
        DB=new AccessDatabase("src/playerinfo.accdb");
        DB.builtConnection();
        init();
        setOpaque(false); // 设置透明，显示背景图片
    }
    public void getusername(String username){
        this.username=username;
    }
    private void init() {
        // 创建并添加返回按钮
        setLayout(null);
        JButton newgame=new JButton("新的游戏");
        newgame.setBounds(250, 200, 200, 70);
        newgame.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        newgame.setForeground(Color.WHITE);
        Home.setIconOfBtn(newgame, "src/img/关卡.png", 21, 25);
        newgame.setContentAreaFilled(false);
        newgame.setFocusPainted(false);
        
        JButton keepgame=new JButton("继续游戏");
        keepgame.setBounds(250, 300, 200, 70);
        keepgame.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        keepgame.setForeground(Color.WHITE);
        Home.setIconOfBtn(keepgame, "src/img/继续_go-on.png", 21, 25);
        keepgame.setContentAreaFilled(false);
        keepgame.setFocusPainted(false);
        
        JButton backButton = new JButton("   返回    ");
        backButton.setBounds(250, 400, 200, 70);
        backButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        backButton.setForeground(Color.WHITE);
        Home.setIconOfBtn(backButton, "src/img/上一步_back.png", 21, 25);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 当返回按钮点击时，重新绘制回原始状态
                homeFrame.switchToHomePanel();
            }
        });
        newgame.addActionListener(new ActionListener() {
            //** 当点击新的游戏按钮时，重置游戏进度并开始新游戏 **//
            @Override
            public void actionPerformed(ActionEvent e) {
            	OpenGame frame=new OpenGame("迷宫游戏", 950, 850);
                frame.initial();
                // 更新关卡数
                DB.updateData("basicinfo", "numOfLevel", "1", "userName = '" + Main.userName + "'");
                Game mazeGame = new Game(true, 1); // 这句设置模式及关卡数
                mazeGame.GR.setHomeFrame(homeFrame); // GR记录首页面板
                mazeGame.startGame();
                mazeGame.setBounds((950-Game.width*Game.CELL_SIZE-Game.CELL_SIZE)/2, (850-Game.height*Game.CELL_SIZE-Game.CELL_SIZE)/2, Game.width*Game.CELL_SIZE, Game.height*Game.CELL_SIZE+30);
                Game.frame = frame;
                frame.add(mazeGame);
                frame.add(frame.beautifyBackground("src/img/blue_big.png"));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                try {
                	ResultSet rs = DB.executeQuery("SELECT maxLevel FROM basicinfo WHERE userName = '"+Main.userName+"'");
                	while (rs.next()) {
                		if (rs.getInt("maxLevel") <= 1) {
                			OpenGame tipFrame = new OpenGame("玩法说明", 500, 500); // 新手提示窗口
                			tipFrame.initial();
                			tipFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                			tipFrame.setResizable(false);
                			tipFrame.add(tipFrame.beautifyBackground("src/img/新手提示.png"));
                			tipFrame.setVisible(true);
                		}
                	}
                }
                catch (Exception e1) {
                }
            }
        });
        keepgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String qureysql="select numOfLevel from basicinfo where userName='"+username+"'";
                ResultSet rs=DB.executeQuery(qureysql);
                int numOfLevel = 1;
                try {
                    while (rs.next()) {
                        numOfLevel = rs.getInt(1);
                    }
                OpenGame frame=new OpenGame("迷宫游戏", 950, 850);
                frame.initial();
                Game mazeGame = new Game(true, numOfLevel); // 这句设置模式及关卡数
                mazeGame.GR.setHomeFrame(homeFrame); // GR记录首页面板
                mazeGame.startGame();
                mazeGame.setBounds((950-Game.width*Game.CELL_SIZE-Game.CELL_SIZE)/2, (850-Game.height*Game.CELL_SIZE-Game.CELL_SIZE)/2, Game.width*Game.CELL_SIZE, Game.height*Game.CELL_SIZE+30);
                Game.frame = frame;
                frame.add(mazeGame);
                frame.add(frame.beautifyBackground("src/img/blue_big.png"));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            }
        });
        add(backButton);add(newgame);add(keepgame);
    }
}

class RandomModePanel extends JPanel {

    private Home homeFrame;

    public RandomModePanel(Home homeFrame) {
        this.homeFrame = homeFrame;
        init();
        setOpaque(false); // 设置透明，显示背景图片
    }

    private void init() {

        setLayout(null);
        JButton startButton = new JButton("开始游戏");
        startButton.setBounds(250,200,200,70);
        startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        startButton.setForeground(Color.WHITE);
        Home.setIconOfBtn(startButton, "src/img/关卡.png", 21, 25);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        
        JButton backButton = new JButton("   返回    ");
        backButton.setBounds(250, 300, 200, 70);
        backButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        backButton.setForeground(Color.WHITE);
        Home.setIconOfBtn(backButton, "src/img/上一步_back.png", 21, 25);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 当返回按钮点击时，重新绘制回原始状态
                homeFrame.switchToHomePanel();
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenGame frame=new OpenGame("迷宫游戏", 950, 850);
                frame.initial();
                Game mazeGame = new Game(false, 1);
                mazeGame.GR.setHomeFrame(homeFrame); // GR记录首页面板
                mazeGame.startGame();
                mazeGame.setBounds((950-Game.width*Game.CELL_SIZE-Game.CELL_SIZE)/2, (850-Game.height*Game.CELL_SIZE-Game.CELL_SIZE)/2, Game.width*Game.CELL_SIZE, Game.height*Game.CELL_SIZE+30);
                Game.frame = frame;
                frame.add(mazeGame);
                frame.add(frame.beautifyBackground("src/img/blue_big.png"));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        add(backButton);add(startButton);
    }
}



