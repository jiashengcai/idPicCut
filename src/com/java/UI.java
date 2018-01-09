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
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Label;
import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;

public class UI {

	private String filePath;
	private String savePath;
	private JFrame frame;
	
	//路径选择器
	private JFileChooser chooser;

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
		tabbedPane.setBounds(10, 74, 415, 366);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("证件照处理", null, panel, null);
		panel.setLayout(null);
		
		JRadioButton radioButton = new JRadioButton("是否需要磨皮");
		radioButton.setBounds(76, 28, 97, 23);
		panel.add(radioButton);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("图片压缩", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("添加水印", null, panel_2, null);
		
		
		JButton button = new JButton("打开");
		button.setBounds(399, 10, 93, 23);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("保存");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 点击选择保存路径允许为空
			}
		});
		button_1.setBounds(399, 41, 93, 23);
		frame.getContentPane().add(button_1);
		
		JTextPane txtpnjpgpng = new JTextPane();
		txtpnjpgpng.setEditable(false);
		txtpnjpgpng.setText("请先选择图片的文件夹路径\r\n只能识别后缀为.JPG和.PNG的图片\r\n如果不选择保存路径将会覆盖原来的图片文件");
		txtpnjpgpng.setBounds(10, 10, 246, 51);
		frame.getContentPane().add(txtpnjpgpng);
		
		JLabel label = new JLabel("图片文件夹");
		label.setBounds(301, 10, 98, 23);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("保存路径");
		label_1.setBounds(301, 49, 98, 15);
		frame.getContentPane().add(label_1);
		
		JLabel lblNull = new JLabel("NULL");
		lblNull.setBounds(502, 12, 170, 19);
		frame.getContentPane().add(lblNull);
		
		JLabel lblNull_1 = new JLabel("NULL");
		lblNull_1.setBounds(502, 45, 170, 15);
		frame.getContentPane().add(lblNull_1);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser= new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				chooser.setDialogTitle("选择文件夹");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(button)==JFileChooser.APPROVE_OPTION) {
					filePath=chooser .getCurrentDirectory().toString();
				}
			}
		});
		
		
	}
}
