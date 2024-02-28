package 首页;

import gameGUI.Game;
import tool.AudioPlayer;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class SettingPage extends JFrame {
    
    public SettingPage(OptionsPage optionsPage){
        setTitle("游戏设置");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setResizable(false); //不可改变大小
        setLayout(null); //空布局
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        init();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                optionsPage.setVisible(true); // 在选项页面关闭时显示首页
            }
        });

    }
    public void init(){
        ImageIcon bgIcon;
        
        bgIcon = new ImageIcon("src/img/appIcon.png");
        setIconImage(bgIcon.getImage());
        bgIcon = new ImageIcon("src/img/迷宫游戏背景副本.png");
        JLabel bgImg=new JLabel(bgIcon);
        bgImg.setBounds(0,0,700,700);

        JLabel jLabel1=new JLabel("颜色选择");
        jLabel1.setBounds(50,50,100,100);
        jLabel1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));

        JLabel jLabel2=new JLabel("墙体颜色");
        jLabel2.setBounds(100,100,200,100);
        jLabel2.setFont(new Font("宋体",Font.BOLD,22));

        String[] wallsColorOptions = {"默认", "蓝色", "灰色"};
        JComboBox<String> comBox1=new JComboBox<>(wallsColorOptions);//墙体颜色设计的下拉列表
        comBox1.setBounds(200,130,400,40);
        comBox1.setFont(new Font("宋体",Font.BOLD,22));
        if (Game.wallsColor.equals(Color.BLUE))
        	comBox1.setSelectedItem("蓝色");
        if (Game.wallsColor.equals(Color.GRAY))
        	comBox1.setSelectedItem("灰色");

        JLabel jLabel3=new JLabel("路径颜色");
        jLabel3.setBounds(100,175,200,100);
        jLabel3.setFont(new Font("宋体",Font.BOLD,22));

        String[] pathColorOptions = {"默认", "白色"};
        JComboBox<String> comBox2=new JComboBox<>(pathColorOptions);//路径颜色设计的下拉列表
        comBox2.setBounds(200,205,400,40);
        comBox2.setFont(new Font("宋体",Font.BOLD,22));
        if (Game.pathColor.equals(Color.WHITE))
        	comBox2.setSelectedItem("白色");

        JLabel jLabel4=new JLabel("音乐");
        jLabel4.setBounds(50,225,200,100);
        jLabel4.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));

        JRadioButton radioButton1=new JRadioButton("开");
        JRadioButton radioButton2=new JRadioButton("关");
        ButtonGroup buttonGroup=new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        radioButton1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
        radioButton1.setBounds(100,300,100,100);
        radioButton1.setOpaque(false);
        radioButton1.setSelected(true);
        if (!AudioPlayer.isRunning) {
        	radioButton2.setSelected(true);
        }
        radioButton2.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
        radioButton2.setBounds(400,300,100,100);
        radioButton2.setOpaque(false);

        JButton Determine=new JButton("确定");
        Determine.setBounds(100,500,200,75);
        Determine.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
        Determine.setForeground(Color.white);
        Determine.setContentAreaFilled(false);
        Determine.setFocusPainted(false);

        JButton exiting1=new JButton("返回");//返回按钮
        exiting1.setBounds(350,500,200,75);
        exiting1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
        exiting1.setForeground(Color.white);
        exiting1.setContentAreaFilled(false);
        exiting1.setFocusPainted(false);

        add(jLabel1);
        add(jLabel2);
        add(comBox1);
        add(jLabel3);
        add(comBox2);
        add(jLabel4);
        add(radioButton1);
        add(radioButton2);
        add(Determine);
//        add(jComboBox5);
        add(exiting1);
        add(bgImg);

        // 确定按钮事件
        Determine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wallcolor=comBox1.getSelectedItem().toString();//获取所选颜色
                // 墙体颜色选择
                if(wallcolor.equals("默认")){
                    Game.wallsColor=Color.BLACK;
                } else if (wallcolor.equals("蓝色")) {
                    Game.wallsColor=Color.BLUE;
                } else if (wallcolor.equals("灰色")) {
                    Game.wallsColor=Color.GRAY;
                }

                String pathcolor=comBox2.getSelectedItem().toString();//获取所选颜色
                // 路径颜色选择
                if(pathcolor.equals("默认")){
                    Game.pathColor=new Color(230,230,230);
                } else if (pathcolor.equals("白色")) {
                    Game.pathColor=Color.WHITE;
                }
                
                if (radioButton1.isSelected() && !AudioPlayer.isRunning) {
                	Home.AP.randomPlay();
                }
                if (radioButton2.isSelected() && AudioPlayer.isRunning) {
                	Home.AP.stop();
                }
                
                JOptionPane.showMessageDialog(null, "设置成功！", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        // 返回按钮事件
        exiting1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // 隐藏首页界面
                Home home1=new Home();
                new OptionsPage(home1);
                home1.setVisible(false);
            }
        });
    }

}
