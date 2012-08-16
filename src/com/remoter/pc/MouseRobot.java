package com.remoter.pc;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseRobot extends Robot{
	public static int Mobilescreen_x = 1280,Mobilescreen_y = 720,motion_scale=1;
	public static String mArea = "";
	public static String LeftArea = "LEFTAREA";
	public static String RightArea = "RIGHTAREA";
	public static String WheelArea = "WHEELAREA";
	
	public MouseRobot() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized void mouseMove(int x, int y) {
		// TODO Auto-generated method stub
		super.mouseMove((int)(x*motion_scale), (int)(y*motion_scale));
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
		if(((values[0]/Mobilescreen_x)<rpx)&&((values[1]/Mobilescreen_y)<rpy))mArea = RightArea;
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
 }