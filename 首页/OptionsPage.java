package 首页;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;
public class OptionsPage extends JFrame{
	public OptionsPage(Home home) {
		setTitle("游戏选项");
		setSize(700, 700);
		setLocationRelativeTo(null);
		setResizable(false); //不可改变大小
		setLayout(null); //空布局
		setIconImage(new ImageIcon("src/img/appIcon.png").getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
                home.setVisible(true); // 在选项页面关闭时显示首页
            }
		});
	}

	private void init() {
		ImageIcon bgIcon = new ImageIcon("src/img/迷宫游戏背景.png");
		JLabel bgImg = new JLabel(bgIcon);
		bgImg.setBounds(0, 0, 700, 700);
		
		JButton setting = new JButton("   设置    "); // 设置按钮
		setting.setBounds(250, 200, 200, 70);
		setting.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		setting.setForeground(Color.white);
		Home.setIconOfBtn(setting, "src/img/设置_setting.png", 21, 25);
		setting.setContentAreaFilled(false);
		setting.setFocusPainted(false);

		JButton gameDate = new JButton("游戏数据"); // 设置按钮
		gameDate.setBounds(250, 300, 200, 70);
		gameDate.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		gameDate.setForeground(Color.white);
		Home.setIconOfBtn(gameDate, "src/img/数据_data.png", 21, 25);
		gameDate.setContentAreaFilled(false);
		gameDate.setFocusPainted(false);


		JButton exitting=new JButton("   返回    ");
		exitting.setBounds(250, 400, 200, 70);
		exitting.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		exitting.setForeground(Color.white);
		Home.setIconOfBtn(exitting, "src/img/上一步_back.png", 21, 25);
		exitting.setContentAreaFilled(false);
		exitting.setFocusPainted(false);

		add(setting);
		add(gameDate);
		add(exitting);
		add(bgImg);

		// 设置按钮事件
		setting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new SettingPage(OptionsPage.this);
			}
		});

		// 游戏数据按钮事件
		gameDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new GamingData(OptionsPage.this);
			}
		});

		// 退出按钮事件
		exitting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false); // 隐藏首页界面
				new Home();
			}
		});
	}
}
