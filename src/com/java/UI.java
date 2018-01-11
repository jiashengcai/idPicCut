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
import java.text.SimpleDateFormat;
import java.util.Date;
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

	private String filePath = null;
	private String savePath = null;
	private JFrame frame;
	private File[] images;
	private SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");// 设置日期格式
	// 路径选择器
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
		IDPicDeal idPicDeal = new IDPicDeal();//

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 76, 478, 364);
		frame.getContentPane().add(tabbedPane);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("图片剪裁", null, panel_3, null);
		panel_3.setLayout(null);
		/**
		 * 图片剪裁
		 */
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setEditable(false);
		textPane_2.setBounds(39, 170, 366, 155);
		panel_3.add(textPane_2);
		
		JButton button_5 = new JButton("开始");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = 0;
				images = getFile(filePath);
				if (images == null) {
					output("没有选择图片", textPane_2);
				} else {
					output("剪裁开始", textPane_2);
					for (File file : images) {
						count++;
						output(file.getName()+"开始剪裁", textPane_2);
						idPicDeal.dealImage(file, savePath);
						output(file.getName() + "剪裁完成", textPane_2);
					}
				} // end if
				output("全部照片剪裁完成，共" + count + "张，" + df.format(new Date()), textPane_2);
			}
		});
		button_5.setBounds(228, 132, 93, 23);
		panel_3.add(button_5);

		JTextPane textPane_4 = new JTextPane();
		textPane_4.setText("说明：\r\n把图片文件夹里的图片进行人脸识别，裁剪后放到选定的保存路径下");
		textPane_4.setBounds(28, 26, 393, 56);
		panel_3.add(textPane_4);
		

		JPanel panel = new JPanel();
		tabbedPane.addTab("证件照处理", null, panel, null);
		panel.setLayout(null);

		JRadioButton radioButton = new JRadioButton("");
		radioButton.setSelected(true);
		radioButton.setBounds(108, 116, 183, 23);
		panel.add(radioButton);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "", "蓝", "红", "白" }));
		comboBox.setBounds(108, 72, 46, 23);
		panel.add(comboBox);

		JLabel label_2 = new JLabel("修改底色");
		label_2.setBounds(41, 76, 54, 15);
		panel.add(label_2);

		JLabel label_3 = new JLabel("人脸磨皮");
		label_3.setBounds(41, 116, 54, 15);
		panel.add(label_3);

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(29, 178, 327, 149);
		panel.add(textPane);
		JButton button_2 = new JButton("开始");

		button_2.setBounds(261, 145, 93, 23);
		panel.add(button_2);

		JTextPane textPane_5 = new JTextPane();
		textPane_5.setText("说明：\r\n操作的对象为保存路径下的图片，修改完后覆盖原来的图片");
		textPane_5.setBounds(29, 25, 368, 41);
		panel.add(textPane_5);
		// 证件照处理
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bgColor = comboBox.getSelectedItem().toString();// 需要更换的底色
				int color;
				int count = 0;
				if (bgColor == "红") {
					color = 0xFF0000;
				} else if (bgColor == "蓝") {
					color = 0x438EDB;
				} else if (bgColor == "白") {
					color = 0xFFFFFF;
				} else {
					color = 0;
				}
				boolean isBeauty = radioButton.isSelected();// 是否需要磨皮
				File[] saveFiles = getFile(savePath);
				if (saveFiles == null) {
					output("没有选择图片", textPane);
				} else {
					output("开始处理", textPane);
					for (File file : saveFiles) {
						count++;
						if (isBeauty) {
							output("对图片" + file.getName() + "进行磨皮", textPane);
							idPicDeal.beautFace(file);
							output("图片" + file.getName() + "磨皮完成", textPane);
						}
						if (color != 0) {
							output("对图片" + file.getName() + "进行修改底色", textPane);
							idPicDeal.setBGC(file, color);
							output("对图片" + file.getName() + "修改完成", textPane);
						}
					}
					output("处理完成，共" + count + "张图片" + df.format(new Date()), textPane);
				}

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
		slider.setValue(100);
		slider.setMinimum(10);
		slider.setMajorTickSpacing(10);
		slider.setToolTipText("");
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setBounds(30, 89, 189, 46);
		panel_1.add(slider);

		JLabel label_4 = new JLabel("压缩比例");
		label_4.setBounds(30, 67, 189, 23);
		panel_1.add(label_4);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "", "一寸", "二寸" }));
		comboBox_1.setBounds(273, 89, 70, 22);
		panel_1.add(comboBox_1);

		JButton button_3 = new JButton("开始");
		// 图片压缩
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float size = (float) (slider.getValue() / 100.0);
				int count = 0;
				int width;
				int height;
				if (comboBox_1.getSelectedItem().toString() == "一寸") {
					width = 295;
					height = 413;
				} else if (comboBox_1.getSelectedItem().toString() == "二寸") {
					width = 413;
					height = 626;
				} else {
					width = 0;
					height = 0;
				}
				
				images = getFile(savePath);
				if (images == null) {
					output("没有选择图片", textPane_1);
				} else {
					output("压缩开始", textPane_1);
					for (File file : images) {
						count++;
						output("对图片" + file.getName() + "进行修改大小", textPane_1);
						idPicDeal.smartData(file, size, width, height);
						output("对图片" + file.getName() + "修改完成", textPane_1);
					}
				} // end if
				output("全部照片压缩完成，共" + count + "张，" + df.format(new Date()), textPane_1);
			}
		});
		button_3.setBounds(30, 145, 93, 23);
		panel_1.add(button_3);

		JLabel label_7 = new JLabel("尺寸");
		label_7.setBounds(273, 71, 54, 15);
		panel_1.add(label_7);

		JTextPane textPane_6 = new JTextPane();
		textPane_6.setText("说明：\r\n压缩比例为100表示不压缩，这是 对文件大小进行压缩\r\n修改尺寸为修改图片的长宽，一寸 295×413(px) 二寸413×626(px) ");
		textPane_6.setBounds(30, 10, 359, 52);
		panel_1.add(textPane_6);

		JButton button = new JButton("打开");
		button.setBounds(332, 10, 93, 23);
		frame.getContentPane().add(button);

		JLabel lblNull_1 = new JLabel("NULL");
		lblNull_1.setBounds(435, 45, 227, 21);
		frame.getContentPane().add(lblNull_1);

		// 文件保存路径
		JButton button_1 = new JButton("保存");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				chooser.setDialogTitle("选择文件夹");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(button_1) == JFileChooser.APPROVE_OPTION) {
					savePath = chooser.getSelectedFile().getAbsolutePath();
					lblNull_1.setText(savePath);
				} else {
					savePath = filePath;
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
		textPane_3.setBounds(508, 142, 154, 288);
		frame.getContentPane().add(textPane_3);

		JLabel label_6 = new JLabel("文件名称");
		label_6.setBounds(498, 109, 109, 23);
		frame.getContentPane().add(label_6);

		// 打开图片路径
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				chooser.setDialogTitle("选择文件夹");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(button) == JFileChooser.APPROVE_OPTION) {
					filePath = chooser.getSelectedFile().getAbsolutePath();
					if (savePath == null) {
						savePath = filePath;
					}
					lblNull.setText(filePath);
					images = getFile(filePath);
					// 把文件名称输出到界面上
					String temp = "";
					for (File file : images) {
						temp += file.getName() + "\n";
					}
					textPane_3.setText(temp);
				} // endIF

			}
		});

	}

	private File[] getFile(String url) {
		if (url == null) {
			return null;
		}
		File[] files = null;
		File dir = new File(url);
		/**
		 * 读取图片路径
		 */
		files = dir.listFiles(new FilenameFilter() {
			@Override
			// 重写文件判断方法 只读取后缀为.jpg的照片
			public boolean accept(File dir, String name) {
				if ((name.length() > 4 && name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".jpg"))
						|| (name.length() > 4
								&& name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".png"))) {
					return true;
				}
				return false;
			}
		});
		return files;
	}

	// 把文件处理信息输出到界面
	private void output(String messge, JTextComponent textPane) {
		StyleConstants.setFontSize(attrset, 12);
		Document docsPanel = textPane.getDocument();// 输出修改信息
		try {
			docsPanel.insertString(0, messge + "\n", attrset);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
