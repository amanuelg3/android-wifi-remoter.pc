package com.remoter.pc;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;


public class MouseRobot extends Robot{
	public static float Mobilescreen_x = 1280,Mobilescreen_y = 720,motion_scale=1,Pcscreen_x,Pcscreen_y;
	public String mArea = "";
	public static String LeftArea = "LEFTAREA";
	public static String RightArea = "RIGHTAREA";
	public static String WheelArea = "WHEELAREA";
	public boolean isGryoUp = false;
	
	public MouseRobot() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public void mouseleftclick(){
		 this.setAutoDelay(0);
		 mousePress(InputEvent.BUTTON1_MASK);
		 mouseRelease(InputEvent.BUTTON1_MASK);
	}
		
	public void mousewheelclick(){
		 this.setAutoDelay(0);
		 mousePress(InputEvent.BUTTON2_MASK);
		 mouseRelease(InputEvent.BUTTON2_MASK);
	}
		
	public void mouserightclick(){
		 this.setAutoDelay(0);
		 mousePress(InputEvent.BUTTON3_MASK);
		 mouseRelease(InputEvent.BUTTON3_MASK);
	}
		
	@Override
	public synchronized void mouseWheel(int arg0) {
		// TODO Auto-generated method stub
		super.mouseWheel(arg0);
	}
	
	
	public float[] mmouseMove(float values[]) {
		// TODO Auto-generated method stub
		values[0] = values[0] < 0?0:values[0];
		values[0] = values[0] > Pcscreen_x?Pcscreen_x:values[0];
		values[1] = values[1] < 0?0:values[1];
		values[1] = values[1] > Pcscreen_y?Pcscreen_y:values[1];
		
		this.mouseMove((int)values[0],(int)values[1]);
		return values;
	}


	public float[] parseCmdData(String data){ //values[0]->x values[1]->y 
		float[] values = {0,0};
		int xindex = data.indexOf("x") + 1;
		int yindex = data.indexOf("y") + 1;
		String xstr = data.substring(xindex, data.indexOf("y"));
		String ystr = data.substring(yindex, data.length());
		values[0] = (float) new Float(xstr).floatValue();
		values[1] = (float) new Float(ystr).floatValue();
		return values;
	}
	
	public void ConfirmAreaBy(float[] values){
		float rpx = (float) 0.3,rpy = (float) 0.3,wpx = (float) 0.85; //r is "right",p is "percent"
//		if(((values[0]/Mobilescreen_x)<rpx)&&((values[1]/Mobilescreen_y)<rpy))mArea = RightArea;
		if((values[1]/Mobilescreen_y)<rpy)mArea = RightArea;
		else if(((values[0]/Mobilescreen_x)>wpx)) mArea = WheelArea;
		else mArea = LeftArea;
	}			
	
	public  void SelectClickOrWheelInAreaBy(float[] values){
		if(mArea.equals(LeftArea)){			
			this.mouseleftclick();			
		}else if(mArea.equals(RightArea)){			
			this.mouserightclick();			
		}else if(mArea.equals(WheelArea)){
			this.mousewheelclick();
		}						
	}
	
	public void getPCScreenSize(){
		Dimension PCsize = Toolkit.getDefaultToolkit().getScreenSize();
		Pcscreen_x = PCsize.width;Pcscreen_y = PCsize.height;
	}
 }