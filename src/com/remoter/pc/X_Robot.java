package com.remoter.pc;

import java.awt.AWTException;
import java.awt.event.KeyEvent;


public class X_Robot extends Thread{ //这个线程理论上可以省略掉
	
	private static boolean DBG = true;
	
	private String KEY_FLAG = "k";
	private String MOUSE_FLAG = "m";
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
					if(DBG) System.out.println("I receive cmd:\" "+ mCmd_data + "\",I must work hard.");

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
		
		public MouseThread(){
			super();
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
					receiveDataFlag = false;
					if(DBG) System.out.println("I receive cmd:\" "+ mCmd_data + "\",I must work hard.");

					//Now,let mRobot to finish the job.
					
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
}	
