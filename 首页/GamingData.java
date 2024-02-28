package 首页;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.*;

public class GamingData extends OpenGame {
	
	AccessDatabase DB = new AccessDatabase("src/playerinfo.accdb");
	
    public GamingData(OptionsPage optionsPage){
    	super("游戏数据", 700, 700);
    	initial();
    	setIconImage(new ImageIcon("src/img/appIcon.png").getImage());
    	setResizable(false); //不可改变大小
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        init();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                optionsPage.setVisible(true); // 在选项页面关闭时显示首页
            }
        });

    }

    private void init() {
    	
    	JPanel panel = new JPanel(null);
    	setContentPane(panel);
    	
    	JTable dataTable = createTable(5, 2, 100, 40); // 调整单元格尺寸
        dataTable.setBounds(120, 50, 460, 240); // 设置表格位置和大小
        dataTable.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        dataTable.setFont(new Font("楷体", Font.BOLD, 17));
        setCellFont(dataTable, 0, 0, new Font("宋体", Font.BOLD, 19));
        
        // 第一列
        dataTable.setValueAt("用户名", 0, 0);
        dataTable.setValueAt("Email", 1, 0);
        dataTable.setValueAt("最高通关数", 2, 0);
        dataTable.setValueAt("历史最高分", 3, 0);
        dataTable.setValueAt("历史最低分", 4, 0);
        // 先将游标移到第一行数据上
        try {
        	DB.builtConnection();
        	ResultSet rs = DB.executeQuery("SELECT * FROM basicinfo WHERE userName = '" + Main.userName + "'");
        	if (rs != null) {
	            while (rs.next()) {
	                
	                // 第二列
	                dataTable.setValueAt(Main.userName, 0, 1);
	                dataTable.setValueAt(rs.getString("email"), 1, 1);
	                dataTable.setValueAt(rs.getInt("maxLevel"), 2, 1);
	                dataTable.setValueAt(rs.getInt("maxScore"), 3, 1);
	                dataTable.setValueAt(rs.getInt("minScore"), 4, 1);
	            }
            } else {
            	// 没有数据时的处理
                // 将第二列均填写为 "0"
                dataTable.setValueAt("0", 0, 1);
                dataTable.setValueAt("0", 1, 1);
                dataTable.setValueAt("0", 2, 1);
                dataTable.setValueAt("0", 3, 1);
                dataTable.setValueAt("0", 4, 1);
                
                // 弹出提示框，显示 "发生错误"
                JOptionPane.showMessageDialog(panel, "数据异常，请尝试重新登录", "错误提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
// 测试           System.out.println("Error fetching data!");
            e.printStackTrace();
        }

    	panel.add(dataTable);
    	add(beautifyBackground("src/img/迷宫游戏背景副本.png"));
    }
    
    // 创建指定格式表格
    public JTable createTable(int rows, int columns, int cellWidth, int cellHeight) {
        DefaultTableModel model = new DefaultTableModel(rows, columns);

        JTable table = new JTable(model);
        table.setRowHeight(cellHeight);

        for (int col = 0; col < columns; col++) {
            table.getColumnModel().getColumn(col).setPreferredWidth(cellWidth);
        }
        
        // 设置单元格内容居中
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        return table;
    }
    
    // BUG 无法指定一个单元格
    public static void setCellFont(JTable table, int row, int column, Font font) {
        TableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                rendererComponent.setFont(font);
                setHorizontalAlignment(JLabel.CENTER);
                return rendererComponent;
            }
        };

        table.getColumnModel().getColumn(column).setCellRenderer(customRenderer);
        table.repaint();
    }
    
}
