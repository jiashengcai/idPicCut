package com.java;

import java.awt.image.BufferedImage;
import java.awt.*;
import com.sun.image.codec.jpeg.*;
import java.io.*;

public class test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			FileOutputStream t = new FileOutputStream("d:1.jpg");
			BufferedImage bi = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bi.createGraphics();
			// g.setBackground(Color.BLUE);
			// g.clearRect(0, 0, 200, 200);
			g.drawString("Graph TEST!!", 15, 15);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(t);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
			param.setQuality(1.0f, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(bi);
			t.close();
			System.out.println("ok");
		} catch (Exception e) {
			System.out.println("error");
		}

	}
}