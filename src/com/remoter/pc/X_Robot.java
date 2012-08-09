package com.remoter.pc;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;


public class X_Robot extends Thread{ //这个线程理论上可以省略掉
	
	private static boolean DBG = true;
	
	private String KEY_FLAG = "k";
	private String MOUSE_FLAG = "m";
	private String MOUSE_UP_FLAG = "u";
	private String MOUSE_DOWN_FLAG = "d";
	private String GRYO_FLAG = "g";
	private String ACCE_FLAG = "a";
	private KeyThread keyThread;
	private MouseThread mouseThread;

	
	public X_Robot(){
		super();
		if(DBG) System.out.println("X_Robot is booting.");
		keyThread = new KeyThread();
		mouseThread = new MouseThread();
		keyThread.start();
		mouseThread.start();
	}
	
	public void WorkingWith(String command){
		
		String cmd_flag = command.substring(0, 1);
		String cmd_data = command.substring(1, command.length());
		
		if(cmd_flag.equals(KEY_FLAG)){
			
			keyThread.handlewith(cmd_data);
			
		}else if(cmd_flag.equals(MOUSE_FLAG)){
			
			mouseThread.handlewith(cmd_data);
			
		}
	}
    

//---------------------------------------------
	
	class KeyThread extends Thread{
		
		private KeyRobot kRobot;
		private boolean receiveDataFlag = false;
		private String mCmd_data = "";
		
		public KeyThread(){
			super();
			try {
				kRobot = new KeyRobot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(DBG) System.out.println("KeyRobot is booting.");
		}
		
		public void handlewith(String cmd_data){
			// 
			receiveDataFlag = true;
			mCmd_data = cmd_data;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			super.run();

			//----------------------------
			while(true){
				if(receiveDataFlag){
					receiveDataFlag = false;
					if(DBG) System.out.println("I receive cmd:\""+ mCmd_data + "\",I must work hard.");

					//Now,let kRobot to finish the job.
//					kRobot.keyPress(KeyEvent.VK_0);
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
//---------------------------------------------
	
	class MouseThread extends Thread{
		
		private MouseRobot mRobot;
		private boolean receiveDataFlag = false;
		private String mCmd_data = "";
		float[] valuescurr = new float[2];
		float[] valuestmp = new float[2];
		float[] valuesdelta = new float[2];
		int clickcount = 0,clickcountthreshold = 5;
		String Area = "";
		String LeftArea = "LEFTAREA";
		String RightArea = "RIGHTAREA";
		String WheelArea = "WHEELAREA";
		int wheelscale = 3;
		
		public MouseThread(){
			super();
			valuescurr[0] = MouseInfo.getPointerInfo().getLocation().x;
			valuescurr[1] = MouseInfo.getPointerInfo().getLocation().y;
			for(int i=0;i<2;i++){
				valuestmp[i] = 0;valuesdelta[i]=0;
			}
			try {
				mRobot = new MouseRobot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(DBG) System.out.println("MouseRobot is booting.");
		}
		
		public void handlewith(String cmd_data){
			// 
			receiveDataFlag = true;
			mCmd_data = cmd_data;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			super.run();
			while(true){
				if(receiveDataFlag){
					if(DBG) System.out.println("I receive cmd:\""+ mCmd_data + "\",I must work hard.");
					//Now,let mRobot to finish the job.
					
					String cmd_flag = separateFlagandData(mCmd_data)[0];
					String cmd_data = separateFlagandData(mCmd_data)[1];

					if(cmd_flag.equals(ACCE_FLAG)){ //acce mode 
					
						handleAcceCmdData(cmd_data);
				
					}else if(cmd_flag.equals(GRYO_FLAG)){//gryo mode
					
						handleGryoCmdData(cmd_data);
					
					}else{ // touch mode
					
						handleTouchCmdData(mCmd_data);
						
					}
					receiveDataFlag = false;
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			    }
			}
		}
		
		private void handleTouchCmdData(String data){
			
			float[] values = {0,0};
			
			String cmd_flag = separateFlagandData(data)[0];
			String cmd_data = separateFlagandData(data)[1];
			
			if(DBG) System.out.println(cmd_flag+" "+cmd_data);
			
			if(cmd_flag.equals(MOUSE_DOWN_FLAG)){
				clickcount = 0;
				values = parseCmdData(cmd_data);
				ConfirmAreaBy(values);
				for(int i=0;i<2;i++){
					valuestmp[i] = 0;
					valuestmp[i] = values[i];
				}
			}else if(cmd_flag.equals(MOUSE_UP_FLAG)){
				values = parseCmdData(cmd_data);
				if(clickcount < clickcountthreshold) SelectClickOrWheelInAreaBy(values);
			}else{
				clickcount++;
				values = parseCmdData(data);
			}
			
			for(int i=0;i<2;i++){
				valuesdelta[i] = values[i]-valuestmp[i];
				valuescurr[i] += valuesdelta[i];
				valuestmp[i] = values[i];
			}
			if(!Area.equals(WheelArea))mRobot.mouseMove((int)valuescurr[0], (int)valuescurr[1]);
			else mRobot.mouseWheel((int)(valuesdelta[1]/wheelscale));
		}
		
		private void handleGryoCmdData(String data){
			float[] values = parseCmdData(data);
			for(int i=0;i<2;i++){
				valuesdelta[i] = values[i]-valuestmp[i];
				valuescurr[i] += 10*valuesdelta[i];
				valuestmp[i] = values[i];
			}
		}
		
		private void handleAcceCmdData(String data){
			float[] values = parseCmdData(data);
			for(int i=0;i<2;i++){
				valuesdelta[i] = values[i]-valuestmp[i];
				valuescurr[i] += valuesdelta[i];
				valuestmp[i] = values[i];
			}
		}
		
		private float[] parseCmdData(String data){ //values[0]->x values[1]->y 
			float[] values = {0,0};
			int xindex = data.indexOf("x") + 1;
			int yindex = data.indexOf("y") + 1;
			String xstr = data.substring(xindex, data.indexOf("y"));
			String ystr = data.substring(yindex, data.length());
			values[0] = (float) new Float(xstr).floatValue();
			values[1] = (float) new Float(ystr).floatValue();
			return values;
		}
		
		private String[] separateFlagandData(String data){
			String[] s = new String[2];
			s[0] = data.substring(0, 1);
			s[1] = data.substring(1, data.length());
			return s;
		}
		
		private void ConfirmAreaBy(float[] values){
			float rpx = (float) 0.3,rpy = (float) 0.3,wpx = (float) 0.85; //r is "right",p is "percent"
			float mscreenx = 1280,mscreeny = 720;

			if(((values[0]/mscreenx)<rpx)&&((values[1]/mscreeny)<rpy))Area = RightArea;
			else if(((values[0]/mscreenx)>wpx)) Area = WheelArea;
			else Area = LeftArea;
		}			
		
		private void SelectClickOrWheelInAreaBy(float[] values){
			if(Area.equals(LeftArea)){			
				mRobot.mouseleftclick();			
			}else if(Area.equals(RightArea)){			
				mRobot.mouserightclick();			
			}else if(Area.equals(WheelArea)){
				mRobot.mousewheelclick();
			}						
		}
	}			
}	
