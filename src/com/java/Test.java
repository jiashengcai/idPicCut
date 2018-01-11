package com.java;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.opencv.core.Core;

import net.coobird.thumbnailator.Thumbnails;

public class Test {
	private static String url = "D:/image";
	private static String saveUrl = "D:/image/newimage/";

	public static void main(String[] args) {

		IDPicDeal test = new IDPicDeal();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		File dir = new File(url);
		/**
		 * 读取图片路径
		 */
		File[] images = dir.listFiles(new FilenameFilter() {
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

		/**
		 * 压缩 人脸识别+剪裁
		 */
		for (File file : images) {
			// smartData(file);
			/*
			 * if (file.length() > 1000000) { try {
			 * Thumbnails.of(file).scale(1f).outputQuality(0.8f).toFile(file); }
			 * catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } }
			 */
			System.out.println(file.getPath());
			// test.dealImage(file,saveUrl);
		}

		File dirNew = new File(saveUrl);
		File[] newImages = dirNew.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// 只读取jpg文件
				if (name.length() > 4 && name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".jpg")) {
					return true;
				}
				return false;
			}
		});

		for (File file : newImages) {
			//
			try {
				// 一寸
				Thumbnails.of(file).size(259, 413).keepAspectRatio(true).toFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// test.setBGC(file,saveUrl,0xCD661D);
		}

		// new BeautyFace().run()
		// IDPicDeal idPicCut=new IDPicDeal();
		// new BeautyFace().run();
	}
}
