package gameGUI;

import javax.swing.*;

import database.AccessDatabase;
import 首页.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Settlements extends JPanel {
	AccessDatabase DB = new AccessDatabase("src/playerinfo.accdb");
	
	JLabel background_defeat;
	JLabel background_victory;
	JLabel background_rog;
	JLabel coins;
	JLabel time;
	JLabel difficulty;
	JButton returns;
	JButton restart;
	JButton next;
	GameRecord GR;
	
	int currentLevels;
	int currentScores;
    //test 1.0
    public JDialog dialog;
    // 临时记录用于比较
    private int maxScore;
	private int numOfLevel;
	private int maxLevel;
	private int minScore;
	
	public static boolean isNormallyDispose = true;

    Settlements(GameRecord GR){
    	numOfLevel = GR.numOfLevel;
    	maxLevel = 1;
    	maxScore = 1;
    	minScore = 1;
    	isNormallyDispose = false;
    	
    	this.GR=GR;
    	init();
    	setVisible(true);
    	setBounds(350,200,900,650);
    	returns.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			Game.frame.dispose();
    			GR.getHomeFrame().switchToHomePanel();
    			dialog.dispose();
    		}
    	});
    	restart.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			int level=GR.numOfLevel;
    			boolean isnormalmode= GR.isNormalMode;
    			
    			Game mazeGame = new Game(isnormalmode, level);// 这句设置模式及关卡数
    			Game.frame.getContentPane().removeAll();
    			mazeGame.startGame();
    			mazeGame.setBounds((950-Game.width*Game.CELL_SIZE-Game.CELL_SIZE)/2, (850-Game.height*Game.CELL_SIZE-Game.CELL_SIZE)/2, Game.width*Game.CELL_SIZE, Game.height*Game.CELL_SIZE+30);
    			
    			Game.frame.add(mazeGame);
    			Game.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    			Game.frame.add(Game.frame.beautifyBackground("src/img/blue_big.png"));
    			
    			Game.frame.revalidate();
    			Game.frame.repaint();
    			Game.frame.setVisible(true);
    			isNormallyDispose = true;
    			dialog.dispose();
    			
    		}
    	});
    	next.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			GR.numOfLevel++;
    			int level=GR.numOfLevel;
    			boolean isnormalmode= GR.isNormalMode;
    			
    			DB.builtConnection();
    			DB.updateData("basicinfo", "numOfLevel", String.valueOf(GR.numOfLevel), "userName = '"+Main.userName+"'");
    			
    			Game mazeGame = new Game(isnormalmode, level);// 这句设置模式及关卡数
    			Game.frame.getContentPane().removeAll();
    			mazeGame.startGame();
    			mazeGame.setBounds((950-Game.width*Game.CELL_SIZE-Game.CELL_SIZE)/2, (850-Game.height*Game.CELL_SIZE-Game.CELL_SIZE)/2, Game.width*Game.CELL_SIZE, Game.height*Game.CELL_SIZE+30);
    			
    			Game.frame.add(mazeGame);
    			Game.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    			Game.frame.add(Game.frame.beautifyBackground("src/img/blue_big.png"));
    			
    			Game.frame.revalidate();
    			Game.frame.repaint();
    			Game.frame.setVisible(true);
    			isNormallyDispose = true;
    			dialog.dispose();
    			
    		}
    	});
    	
    }
	void init() {
        ImageIcon bgIcon1;
        ImageIcon bgIcon2;
        ImageIcon bgIcon3;
        setLayout(null);
        returns = new JButton("返回首页");
        restart = new JButton("重新开始");
        next = new JButton("下一关");
        this.setIconOfBtn(returns,"src/img/首页_home.png",30,0);
        this.setIconOfBtn(restart,"src/img/重新_redo.png",30,0);
        this.setIconOfBtn(next,"src/img/下一步_next.png",30,0);
        bgIcon1 = new ImageIcon("src/img/结算背景—胜利2.png");
        bgIcon2 = new ImageIcon("src/img/结算背景—失败2.png");
        bgIcon3=new ImageIcon("src/img/结算背景—随机.png");
        coins = new JLabel("分数:" + GR.getScore());
        int time1 = GR.getSeconds();
        int minute = time1 / 60;
        int second = time1 % 60;
        time = new JLabel("通关时间:     " + minute + ":" + second);
        difficulty = new JLabel("难度:" + GR.getDifficulty());
        background_victory = new JLabel(bgIcon1);
        background_defeat = new JLabel(bgIcon2);
        background_rog=new JLabel(bgIcon3);
        boolean isnormalmode= GR.isNormalMode;
        
	    if(isnormalmode) {
	        if (GR.getScore() >= 20) {
	            background_victory.setBounds(-10, -100, 900, 750);
	            coins.setBounds(200, 295, 200, 40);
	            difficulty.setBounds(450, 295, 200, 40);
	            time.setBounds(200, 180, 400, 50);
	            returns.setBounds(75, 400, 200, 80);
	            returns.setContentAreaFilled(false);
	            returns.setFocusPainted(false);
	            restart.setBounds(335, 400, 200, 80);
	            restart.setContentAreaFilled(false);
	            restart.setFocusPainted(false);
	            next.setBounds(600, 400, 200, 80);
	            next.setContentAreaFilled(false);
	            next.setFocusPainted(false);
	            Font f = new Font("华文彩云", Font.BOLD, 32);
	            returns.setFont(f);
	            restart.setFont(f);
	            next.setFont(f);
	            coins.setFont(f);
	            difficulty.setFont(f);
	            time.setFont(f);
	            add(coins);
	            add(returns);
	            add(restart);
	            add(next);
	            add(difficulty);
	            add(time);
	            add(background_victory);
	        } else {
	            background_defeat.setBounds(-10, -100, 900, 750);
	            coins.setBounds(200, 295, 200, 40);
	            difficulty.setBounds(450, 295, 200, 40);
	            time.setBounds(200, 180, 400, 50);
	            returns.setBounds(190, 405, 200, 80);
	            returns.setContentAreaFilled(false);
	            returns.setFocusPainted(false);
	            restart.setBounds(470, 405, 200, 80);
	            restart.setContentAreaFilled(false);
	            restart.setFocusPainted(false);
	            Font f = new Font("华文彩云", Font.BOLD, 32);
	            returns.setFont(f);
	            restart.setFont(f);
	            coins.setFont(f);
	            difficulty.setFont(f);
	            time.setFont(f);
	            add(coins);
	            add(returns);
	            add(restart);
	            add(difficulty);
	            add(time);
	            add(background_defeat);
	        }
	    } else {
	        background_rog.setBounds(-10, -100, 900, 750);
	        coins.setBounds(200, 295, 200, 40);
	        difficulty.setBounds(450, 295, 200, 40);
	        time.setBounds(200, 180, 400, 50);
	        returns.setBounds(190, 405, 200, 80);
	        returns.setContentAreaFilled(false);
	        returns.setFocusPainted(false);
	        next.setBounds(470, 405, 200, 80);
	        next.setContentAreaFilled(false);
	        next.setFocusPainted(false);
	        Font f = new Font("华文彩云", Font.BOLD, 32);
	        returns.setFont(f);
	        next.setFont(f);
	        coins.setFont(f);
	        difficulty.setFont(f);
	        time.setFont(f);
	        add(coins);
	        add(returns);
	        add(next);
	        add(difficulty);
	        add(time);
	        add(background_rog);
	    }


    }

	private void setIconOfBtn(JButton btn, String filePath, int size, int gap) {
		ImageIcon Icon = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
		btn.setIcon(Icon);
		btn.setIconTextGap(gap);
	}

    public void setDialog(JDialog dialog){
        this.dialog=dialog;
    }
	public int getMaxScore() {
		return maxScore;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public int getMinScore() {
		return minScore;
	}
}
	
