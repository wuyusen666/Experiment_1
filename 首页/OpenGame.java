package 首页;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class OpenGame extends JFrame{
	
	public int width;
	public int height;
	public String title;
	
	public OpenGame(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	public void initial() {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setLayout(null);
		ImageIcon bgIcon = new ImageIcon("src/img/appIcon.png");
        setIconImage(bgIcon.getImage());
	}
	
	public JLabel beautifyBackground(String imgPath) { // 美化背景
		ImageIcon bgIcon = new ImageIcon(imgPath);
        JLabel bgImg = new JLabel(bgIcon);
        bgImg.setBounds(0, 0, width, height);
        return bgImg;
	}
	
}