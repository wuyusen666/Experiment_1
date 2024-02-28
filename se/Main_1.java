package se;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import database.*;
import 首页.*;


public class Main_1 extends JFrame {         //首页的代码主体//
    add1 ad1;     /**add1为注册大界面的监视器类**/
    
    public Main_1(){
    	this.setIconImage(new ImageIcon("src/img/appIcon.png").getImage());
    	setTitle("迷宫游戏");
    	setLayout(null);
    	setSize(700,700);
    	setLocationRelativeTo(null);
    	setResizable(false);
    	init();
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    void init(){
    	
        ImageIcon bgIcon;
        JLabel l1=new JLabel("合理安排游戏，享受健康生活");
        JButton login=new JButton("登录");
        JButton register=new JButton("注册");
        JButton exit=new JButton("退出");
        ad1=new add1();
        ad1.setM1(this);
        bgIcon = new ImageIcon("src/img/迷宫游戏背景.png");
        JLabel bgImg = new JLabel(bgIcon);
        bgImg.setBounds(0, 0, 700, 700);
        
        l1.setFont(new Font("仿宋", Font.BOLD, 20));
        l1.setBounds(220,600,300,30);
        
        login.setBounds(250,210,200,70);
        login.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        login.setForeground(Color.WHITE);
        login.setContentAreaFilled(false);
        login.setFocusPainted(false);
        login.addActionListener(ad1);
        
        register.setBounds(250,320,200,70);
        register.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        register.setForeground(Color.WHITE);
        register.setContentAreaFilled(false);
        register.setFocusPainted(false);
        register.addActionListener(ad1);
        
        exit.setBounds(250,430,200,70);
        exit.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        exit.setForeground(Color.WHITE);
        exit.setContentAreaFilled(false);
        exit.setFocusPainted(false);
        exit.addActionListener(ad1);
        
        add(l1);
        add(login);
        add(register);
        add(exit);
        add(bgImg);
    }

}




class add1 implements ActionListener {          /**add1为注册大界面的监视器类**/
    Main_1 m1;
    public void setM1(Main_1 m1) {
        this.m1 = m1;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if("注册".equals(e.getActionCommand())){
            new Register();
            m1.dispose();
        } else if ("登录".equals(e.getActionCommand())) {
            new Login();
            m1.dispose();
        } else if ("退出".equals(e.getActionCommand())) {
            System.exit(0);

        }
    }
}



class Register extends JFrame {          //注册界面的主代码//
    JTextField temail;
    JTextField tusername;
    JTextField tpassword;
    JTextField tkeeppassword;
    JLabel lemail;
    JLabel lusername;
    JLabel lpassword;
    JLabel lkeeppassword;
    JButton determine;
    JButton cancel;
    Add_Register al1;
    
    Register(){
    	init();
    	setTitle("注册新用户");
    	setSize(450,450);
    	setResizable(false);
    	setLocationRelativeTo(null);
    	setLayout(null);
    	setVisible(true);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    void init(){
        ImageIcon bgIcon;
        lusername=new JLabel("用  户  名");
        lpassword=new JLabel("密       码");
        lkeeppassword=new JLabel("确认密码");
        lemail=new JLabel("用户邮箱");
        JLabel lgame = new JLabel("迷宫游戏");
    	lgame.setFont(new Font("仿宋",Font.BOLD,32));
        
        temail=new JTextField(200);
        temail.setForeground(Color.gray);
        temail.setText("请输入邮箱地址");
        tusername=new JTextField(200);
        tusername.setForeground(Color.gray);
        tusername.setText("请设置用户名");
        tpassword=new JTextField(200);
        tpassword.setForeground(Color.gray);
    	tpassword.setText("请设置密码");
        tkeeppassword=new JTextField(200);
        tkeeppassword.setForeground(Color.gray);
        tkeeppassword.setText("请确认您的密码");
    	
        determine=new JButton("确定");
        determine.setContentAreaFilled(false);
        determine.setForeground(Color.WHITE);
        cancel=new JButton("取消");
        cancel.setContentAreaFilled(false);
        cancel.setForeground(Color.WHITE);
        
        al1=new Add_Register();
        al1.setadd_login(this);
        bgIcon = new ImageIcon("src/img/迷宫游戏背景副本.png");
        JLabel bgImg = new JLabel(bgIcon);
        bgImg.setBounds(0, 0, 450, 450);
        lemail.setBounds(75,240,50,30);
        temail.setBounds(140,240,200,30);
        lusername.setBounds(75,90,50,30);
        tusername.setBounds(140,90,200,30);
        lpassword.setBounds(75,140,50,30);
        tpassword.setBounds(140,140,200,30);
        lkeeppassword.setBounds(75,190,50,30);
        tkeeppassword.setBounds(140,190,200,30);
        determine.setBounds(130,300,80,40);
        determine.setFocusPainted(false);
        cancel.setBounds(250,300,80,40);
        cancel.setFocusPainted(false);
        lgame.setBounds(150,30,200,50);
        
        add(lemail);
        add(lusername);
        add(lpassword);
        add(lkeeppassword);
        add(temail);
        add(tusername);
        add(tpassword);
        add(tkeeppassword);
        add(determine);
        add(cancel);
        add(lgame);
        add(bgImg);
        
        temail.addActionListener(al1);
        tusername.addActionListener(al1);
        tpassword.addActionListener(al1);
        tkeeppassword.addActionListener(al1);
        determine.addActionListener(al1);
        cancel.addActionListener(al1);
        
        tusername.addFocusListener(new focusAdapterWithReminderTextAdapter(tusername, "请设置用户名"));
        tpassword.addFocusListener(new focusAdapterWithReminderTextAdapter(tpassword, "请设置密码"));
        temail.addFocusListener(new focusAdapterWithReminderTextAdapter(temail, "请输入邮箱地址"));
        tkeeppassword.addFocusListener(new focusAdapterWithReminderTextAdapter(tkeeppassword, "请确认您的密码"));
        
        SwingUtilities.invokeLater(new Runnable() {
    	    public void run() {
    	        lemail.requestFocusInWindow();
    	    }
    	});
    }
}



class Add_Register implements ActionListener {       /**注册界面的监视器**/
    Register l1;
    boolean bl1;
    public void setadd_login(Register l1){
        this.l1=l1;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        AccessDatabase DB=new AccessDatabase("src/playerinfo.accdb");
        DB.builtConnection();
        if(e.getActionCommand().equals("确定")) {        // 添加用户数据 //
            if(!(l1.tpassword.getText().equals(l1.tkeeppassword.getText()))){
                JOptionPane.showMessageDialog(null, "前后密码不一致！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
            	if ( ! l1.temail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$") ) {
            		JOptionPane.showMessageDialog(null, "邮箱账号格式有误！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
            	}
                bl1 = DB.insertData("basicinfo", l1.tusername.getText(),l1.tpassword.getText(),l1.tusername.getText(), 1);
                if(bl1){
                    JOptionPane.showMessageDialog(null, "注册成功！", "", JOptionPane.INFORMATION_MESSAGE);
                    l1.dispose();
                }
            }
        }
        if(e.getActionCommand().equals("取消")){
           l1.dispose();
           new Main_1();
        }

    }
}



class Login extends JFrame {           //登录界面代码主体//
    JTextField tusername;
    JTextField tpassword;
    JLabel lusername;
    JLabel lpassword;
    JLabel lgame;
    JButton login;
    JButton cancel;
    Add_Login ar1;
    
    Login(){
    	setTitle("登录");
        init();
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    void init(){
     ImageIcon bgIcon;
    	setLayout(null);
    	lusername=new JLabel("用户名");
    	lpassword=new JLabel("密    码");
    	tusername=new JTextField(180);
    	tpassword=new JTextField(180);
    	lgame=new JLabel("迷宫游戏");
    	Font f=new Font("仿宋",Font.BOLD,32);
    	lgame.setFont(f);
    	
    	login=new JButton("登录");
    	login.setContentAreaFilled(false);
    	login.setForeground(Color.WHITE);
    	cancel=new JButton("取消");
    	cancel.setContentAreaFilled(false);
    	cancel.setForeground(Color.WHITE);
    	
    	bgIcon = new ImageIcon("src/img/迷宫游戏背景副本.png");
    	JLabel bgImg = new JLabel(bgIcon);
    	bgImg.setBounds(0, 0, 450, 450);
    	
    	ar1=new Add_Login();
    	ar1.setadd_register(this);
    	
    	lusername.setBounds(100,100,50,30);
    	tusername.setBounds(145, 100, 180, 32);
    	tusername.setForeground(Color.gray);
    	tusername.setText("请输入用户名");
    	
    	lpassword.setBounds(100,150,50,30);
    	tpassword.setBounds(145, 150, 180, 32);
    	tpassword.setForeground(Color.gray);
    	tpassword.setText("请输入密码");
    	login.setBounds(140,200,80,40);
    	cancel.setBounds(240,200,80,40);
    	lgame.setBounds(150,30,200,50);
    	
    	add(lusername);
    	add(lpassword);
    	add(lgame);
    	add(tusername);
    	add(tpassword);
    	add(login);
    	add(cancel);
    	add(bgImg);
    	
    	tusername.addActionListener(ar1);
    	tpassword.addActionListener(ar1);
    	login.addActionListener(ar1);
    	cancel.addActionListener(ar1);
    	
    	tusername.addFocusListener(new focusAdapterWithReminderTextAdapter(tusername, "请输入用户名"));
    	tpassword.addFocusListener(new focusAdapterWithReminderTextAdapter(tpassword, "请输入密码"));
    	
    	SwingUtilities.invokeLater(new Runnable() {
    	    public void run() {
    	        lusername.requestFocusInWindow();
    	    }
    	});


    }
}




class Add_Login implements ActionListener {          //**登录界面的监视器**//
    Login r1;
    public void setadd_register(Login r1){
        this.r1=r1;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        AccessDatabase DB=new AccessDatabase("src/playerinfo.accdb");
        DB.builtConnection();

        if(e.getActionCommand().equals("登录")){      //**查询用户数据**//
            String name=r1.tusername.getText();
            String password=r1.tpassword.getText();
            String qureysql="select password from basicinfo where userName='"+name+"'";
            ResultSet rs=DB.executeQuery(qureysql);
            try {
                String password_true = null;
                while (rs.next()) {
                    password_true = rs.getString(1);
                }if(name.equals("")||password.equals("")){

                    JOptionPane.showMessageDialog(null, "用户名或密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                }
                else if(password_true==null||!(password_true.equals(password))){

                    JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }

                else {

                    JOptionPane.showMessageDialog(null, "登录成功！", "登录", JOptionPane.INFORMATION_MESSAGE);
                    Main.userName = name;
                    new Home();
                    r1.dispose();

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        if(e.getActionCommand().equals("取消")){
            new Main_1();
            r1.dispose();
        }
    }

}


class focusAdapterWithReminderTextAdapter extends FocusAdapter{  //JTextField提示文字通用方法
	JTextField txt;     
	String remindertxtString;
	//初始化
	public focusAdapterWithReminderTextAdapter(JTextField txt_,String reminderString_) {
		txt = txt_;
		remindertxtString = reminderString_;
	}
	//焦点获得
	@Override
	public void focusGained(FocusEvent e) {
		String tempString = txt.getText();
		//String tempString = this.getText();
		if (tempString.equals(remindertxtString)){
			txt.setText("");
			txt.setForeground(Color.BLACK);
		}
	}
	//焦点失去
	@Override
	public void focusLost(FocusEvent e) {
		String tempString = txt.getText();
		if(tempString.equals("")) {
			txt.setForeground(Color.GRAY);
			txt.setText(remindertxtString);
		}
	}
}