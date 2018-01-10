package com.java;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;

import com.sun.xml.internal.bind.v2.TODO;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.Label;
import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.JList;
import javax.management.BadStringOperationException;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class UI {

	private String filePath=null;
	private String savePath=null;
	private JFrame frame;
	private File[] images;
	
	//路径选择器
	private JFileChooser chooser;
	
	SimpleAttributeSet attrset = new SimpleAttributeSet();
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
		frame.setSize(688, 479);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("图片批量处理软件");
		frame.getContentPane().setLayout(null);
		
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 76, 436, 364);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("证件照处理", null, panel, null);
		panel.setLayout(null);
		
		JRadioButton radioButton = new JRadioButton("");
		radioButton.setSelected(true);
		radioButton.setBounds(110, 77, 183, 23);
		panel.add(radioButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"蓝", "红", "白"}));
		comboBox.setBounds(110, 33, 46, 23);
		panel.add(comboBox);
		
		JLabel label_2 = new JLabel("底色");
		label_2.setBounds(43, 37, 54, 15);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("磨皮");
		label_3.setBounds(43, 77, 54, 15);
		panel.add(label_3);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(29, 178, 327, 149);
		panel.add(textPane);
		JButton button_2 = new JButton("开始");
		
		button_2.setBounds(263, 106, 93, 23);
		panel.add(button_2);
		//证件照处理
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String bgColor=comboBox.getSelectedItem().toString();//需要更换的底色
				boolean isBeauty=radioButton.isSelected();//是否需要磨皮
				
				
				
				
			}
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("图片压缩", null, panel_1, null);
		panel_1.setLayout(null);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		textPane_1.setBounds(30, 178, 359, 149);
		panel_1.add(textPane_1);
		
		JSlider slider = new JSlider();
		slider.setMinimum(10);
		slider.setMajorTickSpacing(10);
		slider.setToolTipText("");
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setBounds(30, 70, 189, 46);
		panel_1.add(slider);
		
		JLabel label_4 = new JLabel("压缩比例");
		label_4.setBounds(30, 37, 189, 23);
		panel_1.add(label_4);
		
		JButton button_3 = new JButton("开始");
		//图片压缩
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Document docsPanel_1=textPane_1.getDocument();
				try {
					docsPanel_1.insertString(0, slider.getValue()+"\n", attrset);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_3.setBounds(273, 115, 93, 23);
		panel_1.add(button_3);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("添加水印", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel label_5 = new JLabel("水印");
		label_5.setBounds(27, 36, 54, 15);
		panel_2.add(label_5);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(85, 32, 126, 19);
		panel_2.add(textArea);
		
		JButton button_4 = new JButton("开始");
		//添加图片水印
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_4.setBounds(210, 85, 93, 23);
		panel_2.add(button_4);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setEditable(false);
		textPane_2.setBounds(10, 165, 390, 150);
		panel_2.add(textPane_2);
		
		
		JButton button = new JButton("打开");
		button.setBounds(332, 10, 93, 23);
		frame.getContentPane().add(button);
		
		JLabel lblNull_1 = new JLabel("NULL");
		lblNull_1.setBounds(435, 45, 227, 21);
		frame.getContentPane().add(lblNull_1);

		//文件保存路径
		JButton button_1 = new JButton("保存");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 点击选择保存路径允许为空
				chooser= new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				chooser.setDialogTitle("选择文件夹");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(button_1)==JFileChooser.APPROVE_OPTION) {
					savePath=chooser .getSelectedFile().getAbsolutePath();
					lblNull_1.setText(savePath);
				}else {
					savePath=filePath;
				}
				
			}
		});
		button_1.setBounds(332, 41, 93, 23);
		frame.getContentPane().add(button_1);
		
		JTextPane txtpnjpgpng = new JTextPane();
		txtpnjpgpng.setEditable(false);
		txtpnjpgpng.setText("请先选择图片的文件夹路径\r\n只能识别后缀为.JPG和.PNG的图片\r\n如果不选择保存路径将会覆盖原来的图片文件");
		txtpnjpgpng.setBounds(10, 10, 246, 51);
		frame.getContentPane().add(txtpnjpgpng);
		
		JLabel label = new JLabel("图片文件夹");
		label.setBounds(266, 8, 66, 23);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("保存路径");
		label_1.setBounds(266, 49, 60, 19);
		frame.getContentPane().add(label_1);
		
		JLabel lblNull = new JLabel("NULL");
		lblNull.setBounds(435, 10, 227, 21);
		frame.getContentPane().add(lblNull);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setEditable(false);
		textPane_3.setBounds(474, 142, 188, 288);
		frame.getContentPane().add(textPane_3);
		
		JLabel label_6 = new JLabel("文件名称");
		label_6.setBounds(498, 109, 109, 23);
		frame.getContentPane().add(label_6);
		
		//打开图片路径
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser= new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				chooser.setDialogTitle("选择文件夹");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(button)==JFileChooser.APPROVE_OPTION) {
					filePath=chooser.getSelectedFile().getAbsolutePath();
					if (savePath==null) {
						savePath=filePath;
					}
					lblNull.setText(filePath);
					File dir = new File(filePath);
				    /**
				     * 读取图片路径
				     */
					images=dir.listFiles(new FilenameFilter() {
						@Override
						//重写文件判断方法 只读取后缀为.jpg的照片
						public boolean accept(File dir, String name) {
							if ((name.length()>4&&name.substring(name.length()-4, name.length()).equalsIgnoreCase(".jpg"))||(name.length()>4&&name.substring(name.length()-4, name.length()).equalsIgnoreCase(".png"))) {
								return true;
							}
							return false;
						}
					});
				}//endIF
				//把文件名称输出到界面上
				String temp = "";
				for (File file : images) {
					temp+=file.getName()+"\n";
				}
				textPane_3.setText(temp);
				
				
			}
		});
		
		
	}
	
	//把文件处理信息输出到界面
	private void output(String messge, JTextComponent textPane) {
		StyleConstants.setFontSize(attrset,18);
		Document docsPanel=textPane.getDocument();//输出修改信息
		try {
			docsPanel.insertString(0, messge+"\n", attrset);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
