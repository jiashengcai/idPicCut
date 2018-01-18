package com.java;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class IDPicDeal {
	private static final int MAXCOLOR = 255;

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}


	/**
	 * 人脸磨皮
	 */
	public void beautyFace(File image) {

		Mat src2 = Highgui.imread(image.getPath());
		Mat src3 = face(src2);
		Highgui.imwrite(image.getPath(), src3);
	}

	public Mat face(Mat image) {
		//Dest =(Src * (100 - Opacity) + (Src + 2 * GuassBlur(EPFFilter(Src) - Src + 128) - 256) * Opacity) /100 
		Mat dst = new Mat();
		// 磨皮程度与细节程度的确定
		int value1 = 3, value2 = 3;
		int dx = value1 * 5; // 双边滤波参数之一
		double fc = value1 * 12.5; // 双边滤波参数之一
		double opacity = 0.1f; // 透明度
		Mat temp1 = new Mat(), temp2 = new Mat(), temp3 = new Mat(), temp4 = new Mat();
		// 双边滤波
		Imgproc.bilateralFilter(image, temp1, dx, fc, fc);
		// temp2 = (temp1 - image + 128);
		Mat temp22 = new Mat();
		Core.subtract(temp1, image, temp22);
		
		Core.add(temp22, new Scalar(128, 128, 128, 128), temp2);
		// 高斯模糊
		Imgproc.GaussianBlur(temp2, temp3, new Size(2 * value2 - 1, 2 * value2 - 1), 0, 0);
		
		// temp4 = image + 2 * temp3 - 256;
		Mat temp44 = new Mat();
		temp3.convertTo(temp44, temp3.type(), 2, -256);
		Core.add(image, temp44, temp4);
		/**
		 * dst = (image*(100 - opacity) + temp4*opacity) / 100;
		 * dst = (image*(1 - opacity) + temp4*opacity) / 1;
		 */
		Core.addWeighted(image, opacity, temp4, 1 - opacity, 0.0, dst);
		Core.add(dst, new Scalar(10, 10, 10), dst);
		return dst;
	}

	/**
	 * 压缩图片
	 * 
	 * @param file 图片文件
	 * @param size 压缩比例
	 * @param w 宽度
	 * @param h 高度 
	 */
	public void smartData(File file, Float size, int w, int h) {
		try {//修改图片尺寸
			if (w != 0 && h != 0) {
				Thumbnails.of(file).size(w, h).keepAspectRatio(true).toFile(file);
			}
			//压缩
			Thumbnails.of(file).scale(1f).outputQuality(size).toFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param file
	 *            传入照片文件进行图像换底色
	 * @param url
	 *            保存路径
	 */
	public void setBGC(File image, int color) {
		BufferedImage bi = null;
		try {
			/**
			 * 用ImageIO将图片读入到缓冲中
			 */
			bi = ImageIO.read(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 得到图片的长宽
		 */
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		int rightHeight = 0; // 右边最低点
		int leftHeight = 0; // 左边最低点
		int flage;
		int temp = 0;
		int bfHeiht = 0;
		boolean f = false;
		// System.out.println("正在处理："+image.getName());
		for (int i = 0; i < height; i++) {
			if (isWhite(bi, 0, i)) {
				leftHeight = i;
			}
		}
		for (int i = 0; i < height; i++) {
			if (isWhite(bi, width - 1, i)) {
				rightHeight = i;
			}
		}
		// 从左往右
		for (int i = miny; i < height; i++) {
			temp = 0;
			flage = 0;
			for (int j = minx; j <= width / 2; j++) {
				if (j - temp > 3 && flage != 0) {
					break;
				}
				if (isWhite(bi, j, i) && i < leftHeight) {
					flage++;
					bi.setRGB(j, i, color);
					temp = j;
				}

			}
		}

		// 从右往左
		for (int i = miny; i < height; i++) {
			temp = 0;
			flage = 0;
			for (int j = width - 1; j >= width / 2; j--) {
				if (temp - j > 3 && flage != 0) {
					break;
				}
				if (isWhite(bi, j, i) && i < rightHeight) {
					flage++;
					bi.setRGB(j, i, color);
					temp = j;
				}

			}
		}

		/**
		 * 将缓冲对象保存到新文件中
		 */
		FileOutputStream ops = null;
		try {
			ops = new FileOutputStream(new File(image.getPath()));
			ImageIO.write(bi, "jpg", ops);
			ops.flush();
			ops.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 底色判断
	 * 
	 * @param file
	 */
	private boolean isWhite(BufferedImage image, int i, int j) {
		int pixel = image.getRGB(i, j);
		/**
		 * 分别进行位操作得到 r g b上的值
		 */
		int[] rgb = new int[3];
		rgb[0] = (pixel & 0xff0000) >> 16;
		rgb[1] = (pixel & 0xff00) >> 8;
		rgb[2] = (pixel & 0xff);

		/**
		 * 进行换色操作，我这里是要把蓝底换成白底，那么就判断图片中rgb值是否在蓝色范围的像素
		 */
		if (rgb[0] < MAXCOLOR && rgb[0] >= 160 && rgb[1] < MAXCOLOR && rgb[1] >= 160 && rgb[2] < MAXCOLOR
				&& rgb[2] >= 160) {
			return true;
		}
		return false;
	}

	/**
	 * 传入照片文件 对图像进行人脸识别剪切
	 * @param file
	 */
	public void dealImage(File file, String savUrl) {
		Rect face = detectFace(file);
		try {
			Thumbnails.of(file)
					.sourceRegion((int) (face.x - face.width * 0.4), (int) (face.y - face.height * 0.6),
							(int) (face.width * 1.8), (int) (face.height * 2.4))
					.scale(1f).toFile(savUrl + "/" + file.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 人脸识别 ，并返回最大的人脸
	 * @param image
	 * @return
	 */
	private Rect detectFace(File image) {
		Rect MaxRect = null;
		Mat face = Highgui.imread(image.getPath());
		MatOfRect faceDetections = new MatOfRect();
		CascadeClassifier faceDetector = createFaceDetector();//构造人脸识别器
		faceDetector.detectMultiScale(face, faceDetections);
		// 找最大的脸
		for (Rect rect : faceDetections.toArray()) {
			if (MaxRect == null) {
				MaxRect = rect;
			}
			if (MaxRect.height < rect.height) {
				MaxRect = rect;
			}
		}
		return MaxRect;
	}
	/**
	 * 构造人脸识别器
	 * 
	 * @return
	 */
	private CascadeClassifier createFaceDetector() {
		String xmlfilePath = getClass().getResource("lbpcascade_frontalface.xml").getPath().substring(1);
		CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
		return faceDetector;
	}
}
