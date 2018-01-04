package com.java;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

import net.coobird.thumbnailator.Thumbnails;

//
// Detects faces in an image, draws boxes around them, and writes the results
// to "faceDetection.png".
//
public class DetectFaceDemo {
	private static String url="D:/image";
	private static String saveUrl="D:/image/newimage/";
	
	public void run() {
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
		setBGC(file);
//		try {
//			//一寸
//			Thumbnails.of(file).size(259, 341).keepAspectRatio(false).toFile(file);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
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
				Thumbnails.of(file).scale(1f).outputQuality(0.25f).toFile(file);
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
		 /** 
         * 定义一个RGB的数组，因为图片的RGB模式是由三个 0-255来表示的 比如白色就是(255,255,255) 
         */  
        int[] rgb = new int[3];  
        /** 
         * 用来处理图片的缓冲流 
         */  
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
        System.out.println("正在处理："+image.getName());  
        /** 
         * 这里是遍历图片的像素，因为要处理图片的背色，所以要把指定像素上的颜色换成目标颜色 
         * 这里 是一个二层循环，遍历长和宽上的每个像素 
         */  
        /**
         * 水平1/4 3/4
         * 垂直1/2
         */
        int widthOneToFor=(int)width/5;
        int widthThreeToFor=4*widthOneToFor;
        //左边
        int tempHeight = 0;
        for (int i = minx; i <=width/2; i++) {  
            for (int j = miny; j < height; j++) {  
                // System.out.print(bi.getRGB(jw, ih));  
                /** 
                 * 得到指定像素（i,j)上的RGB值， 
                 */  
                int pixel = bi.getRGB(i, j);  
                /** 
                 * 分别进行位操作得到 r g b上的值 
                 */  
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                
                /** 
                 * 进行换色操作，我这里是要把蓝底换成白底，那么就判断图片中rgb值是否在蓝色范围的像素 
                 */  
                if(rgb[0]<240&&rgb[0]>=160 && rgb[1]<240&&rgb[1]>=160 && rgb[2]<240&&rgb[2]>=160){
                	if (i==minx) {
						tempHeight=j;//找到左边最低
					}else if(j>tempHeight) {
						continue;
					}
                    /** 
                     * 这里是判断通过，则把该像素换成白色 
                     */ 
                    bi.setRGB(i, j, 0x0066CC);  
                }else if((j<(width/3)*2)&&(i<widthOneToFor||i>widthThreeToFor)){
                	bi.setRGB(i, j, 0x0066CC);
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
			ops = new FileOutputStream(new File("D:/newImage/"+image.getName()+".jpg"));
			ImageIO.write(bi,"jpg", ops);
			ops.flush();
			ops.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	
	
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
			System.out.println((int) (face.x - face.width * 0.4)+" "+
					(int) (face.y - face.height * 0.6)+" "+
					(int) (face.width * 1.8)+" "+(int) (face.height * 2.4));
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
