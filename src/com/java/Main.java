package com.java;

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
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

import net.coobird.thumbnailator.Thumbnails;
public class Main {
	private static String url="D:/image";
	private static String saveUrl="D:/image/newimage/";
	
	public void run() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
    File dir = new File(url);
    /**
     * 读取图片路径
     */
	File[] images=dir.listFiles(new FilenameFilter() {
		@Override
		//重写文件判断方法 只读取后缀为.jpg的照片
		public boolean accept(File dir, String name) {
			if (name.length()>4&&name.substring(name.length()-4, name.length()).equalsIgnoreCase(".jpg")) {
				return true;
			}
			return false;
		}
	});
	
	
	
	/**
	 * 压缩
	 * 人脸识别+剪裁
	 */
	for (File file : images) {
		//smartData(file);
		if (file.length() > 1000000) {
			try {
				Thumbnails.of(file).scale(1f).outputQuality(0.8f).toFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//dealImage(file);
	}
	
	
	File dirNew=new File(saveUrl);
	File[] newImages=dirNew.listFiles(new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			//只读取jpg文件
			if (name.length()>4&&name.substring(name.length()-4, name.length()).equalsIgnoreCase(".jpg")) {
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
			setBGC(file);
		}

	}

/**
 * 压缩图片
 * 如果文件大于1m就进行压缩
 * 1f表示尺寸不变 0.25f表示质量进行压缩
 * @param file
 */
	private void smartData(File file) {
		
		if (file.length() > 1000000) {
			try {
				Thumbnails.of(file).scale(1f).outputQuality(0.8f).toFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	/**
	 * 换底色
	 * @param file
	 */
	private void setBGC(File image) {
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
        int rightHeight=0;  //右边最低点
        int leftHeight=0;   //左边最低点
        int flage;
        int temp=0;
        int bfHeiht = 0;
        boolean f=false;
        System.out.println("正在处理："+image.getName());  
        for (int i = 0; i < height; i++) {
            if(isWhite(bi,0,i)){
            	leftHeight=i;
            }
		}
        for (int i = 0; i < height; i++) {
            if(isWhite(bi,width-1,i)){
            	rightHeight=i;
            }
		}
        /**
         * 从上到下
         * 中间-》左
         */
        
        for (int i = (int)width/2; i>=minx; i--) {
        	temp=0;
            for (int j = miny; j < height; j++) { 
            	if (j-bfHeiht>100) {//当前的j比之前的j相差范围过大就表示走到了脸的最左侧
					f=true;
				}
            	if (j-temp>5) { //如果当前的j和同一竖线下的前一个j不连续而且相差大于5就表示以及到达边缘了
            		bfHeiht=j;
					break;
				}
            	//&&(((i<width/2&&j<leftHeight)||(i>width/2&&j>rightHeight)))
                if(isWhite(bi,i,j)){
                		bi.setRGB(i, j, 0x0066CC);
                		temp=j;
                }
            }
            if (f) {//如果脸的最右侧就退出
            	f=false;
				break;
			}
        } 
        /**
         * 上-》下
         * 中-》右
         */
        bfHeiht=0;
        for (int i = (int)width/2; i<width; i++) {
        	temp=0;
            for (int j = miny; j < height; j++) { 
            	if (j-bfHeiht>100) {//当前的j比之前的j相差范围过大就表示走到了脸的最左侧
					f=true;
				}
            	if (j-temp>5) { //如果当前的j和同一竖线下的前一个j不连续而且相差大于5就表示以及到达边缘了
            		bfHeiht=j;
					break;
				}
            	//&&(((i<width/2&&j<leftHeight)||(i>width/2&&j>rightHeight)))
                if(isWhite(bi,i,j)){
                		bi.setRGB(i, j, 0x0066CC);
                		temp=j;
                }
            }
            if (f) {//如果脸的最右侧就退出
            	f=false;
				break;
			}
        }
        //从左往右
        for (int i = miny; i <height; i++) {  
        	temp=0;
        	flage=0;
            for (int j =minx ; j <=width/2 ; j++) {  
            	if (j-temp>3&&flage!=0) {
					break;
				}
                if(isWhite(bi,j,i)&&i<leftHeight){
                	flage++;
                	bi.setRGB(j, i, 0x0066CC);
                	temp=j;
                }
                                  
            }  
        }
        
        //从右往左
        for (int i = miny; i <height; i++) {  
        	temp=0;
        	flage=0;
            for (int j =width-1 ; j >=width/2 ; j--) {  
            	if (temp-j>3&&flage!=0) {
					break;
				} 
                if(isWhite(bi,j,i)&&i<rightHeight){
                	flage++;
                	bi.setRGB(j, i, 0x0066CC);
                	temp=j;
                }
                                 
            }  
        }  
        
        System.out.println("\t处理完毕："+image.getName());  
        System.out.println();  
        /** 
         * 将缓冲对象保存到新文件中 
         */  
        FileOutputStream ops = null;
		try {
			ops = new FileOutputStream(new File("D:/newImage/"+image.getName()));
			ImageIO.write(bi,"jpg", ops);
			ops.flush();
			ops.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	
	
	}
	
	/**
	 * 颜色判断
	 * @param file
	 */
	private boolean isWhite(BufferedImage image,int i,int j) {
		int pixel = image.getRGB(i, j);  
        /** 
         * 分别进行位操作得到 r g b上的值 
         */  
		int[] rgb=new int[3];
        rgb[0] = (pixel & 0xff0000) >> 16;  
        rgb[1] = (pixel & 0xff00) >> 8;  
        rgb[2] = (pixel & 0xff);  
        
        /** 
         * 进行换色操作，我这里是要把蓝底换成白底，那么就判断图片中rgb值是否在蓝色范围的像素 
         */  
        if(rgb[0]<240&&rgb[0]>=160 && rgb[1]<240&&rgb[1]>=160 && rgb[2]<240&&rgb[2]>=160){
        	return true;
        }
		return false;
	}
	//图片处理
	private void dealImage(File file) {
		Rect face=detectFace(file);
		
		//Core.rectangle(face, new Point(MaxRect.x, MaxRect.y), new Point(MaxRect.x + MaxRect.width, MaxRect.y + MaxRect.height), new Scalar(0, 255, 0));
		//图片剪裁    
	    try {
			Thumbnails.of(file).sourceRegion((int) (face.x - face.width * 0.4),
					(int) (face.y - face.height * 0.6),
					(int) (face.width * 1.8), (int) (face.height * 2.4)).scale(1f).toFile(saveUrl+file.getName());
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
		Rect MaxRect=null;
		Mat face=Highgui.imread(image.getPath());
	    MatOfRect faceDetections = new MatOfRect();
	    CascadeClassifier faceDetector = createFaceDetector();
	    faceDetector.detectMultiScale(face, faceDetections);
	    // 找最大的脸
	    for (Rect rect : faceDetections.toArray()) {
	    	if(MaxRect==null){
	    		MaxRect=rect;
	    	}
	    	if (MaxRect.height<rect.height) {
				MaxRect=rect;
			}
	    }
		return MaxRect;
	}
	
	/**
	 * 构造人脸识别器
	 * @return
	 */
	private CascadeClassifier createFaceDetector() {
		String xmlfilePath = getClass()
				.getResource("lbpcascade_frontalface.xml").getPath()
				.substring(1);
		CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
		return faceDetector;
	}
}
