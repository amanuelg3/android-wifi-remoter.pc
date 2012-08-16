package com.remoter.pc;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyRobot extends Robot{
	
	public KeyRobot() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void parseKey(String cmd){
		if(cmd.length() == 1){  //¼´  k + ?
			 char[] cmdtemp = cmd.toCharArray();
				 this.pressKey(cmdtemp[0]);
				 this.MultipressKey(cmd);
		  }else{  				  
			  if(cmd .equals(new String( "Enter"))){
				  this.pressKey(KeyEvent.VK_ENTER);
			  }else if(cmd .equals(new String( "Back"))){
				  this.pressKey(KeyEvent.VK_BACK_SPACE);
			  }else if(cmd .equals(new String( "Space"))){
				  this.pressKey(KeyEvent.VK_SPACE);
			  }else if(cmd .equals(new String( "Tab"))){
				  this.pressKey(KeyEvent.VK_TAB);
			  }else if(cmd .equals(new String( "Delete"))){
				  this.pressKey(KeyEvent.VK_DELETE);
			  }else if(cmd .equals(new String( "Esc"))){
				  this.pressKey(KeyEvent.VK_ESCAPE);
			  }else if(cmd .equals(new String( "Shift"))){
					  this.keyPress(KeyEvent.VK_SHIFT);
			  }else if(cmd .equals(new String( "Ctrl"))){
					  this.keyPress(KeyEvent.VK_CONTROL);
			  }else if(cmd .equals(new String( "Alt"))){
					  this.keyPress(KeyEvent.VK_ALT);
			  }else if(cmd .equals(new String( "Up"))){
				  this.pressKey(KeyEvent.VK_UP);
			  }else if(cmd .equals(new String( "Dn"))){
				  this.pressKey(KeyEvent.VK_DOWN);
			  }else if(cmd .equals(new String( "Lf"))){
				  this.pressKey(KeyEvent.VK_LEFT);
			  }else if(cmd .equals(new String( "Rt"))){
				  this.pressKey(KeyEvent.VK_RIGHT);
			  }else if(cmd .equals(new String( "PgU"))){
				  this.pressKey(KeyEvent.VK_PAGE_UP);
			  }else if(cmd .equals(new String( "PgD"))){
				  this.pressKey(KeyEvent.VK_PAGE_DOWN);
			  }else if(cmd .equals(new String( "HM"))){
				  this.pressKey(KeyEvent.VK_HOME);
			  }else if(cmd .equals(new String( "End"))){
				  this.pressKey(KeyEvent.VK_END);
			  }else if(cmd .equals(new String( "WS"))){
				  this.pressKey(KeyEvent.VK_WINDOWS);
			  }else if(cmd .equals(new String( "MN"))){
				  this.pressKey(KeyEvent.VK_CONTEXT_MENU);
			  }else if(cmd .equals(new String( "CapsLK"))){
				  this.pressKey(KeyEvent.VK_CAPS_LOCK);
			  }else if(cmd .equals(new String( "F1"))){
				  this.pressKey(KeyEvent.VK_F1);
			  }else if(cmd .equals(new String( "F2"))){
				  this.pressKey(KeyEvent.VK_F2);
			  }else if(cmd .equals(new String( "F3"))){
				  this.pressKey(KeyEvent.VK_F3);
			  }else if(cmd .equals(new String( "F4"))){
				  this.pressKey(KeyEvent.VK_F4);
			  }else if(cmd .equals(new String( "F5"))){
				  this.pressKey(KeyEvent.VK_F5);
			  }else if(cmd .equals(new String( "F6"))){
				  this.pressKey(KeyEvent.VK_F6);
			  }else if(cmd .equals(new String( "F7"))){
				  this.pressKey(KeyEvent.VK_F7);
			  }else if(cmd .equals(new String( "F8"))){
				  this.pressKey(KeyEvent.VK_F8);
			  }else if(cmd .equals(new String( "F9"))){
				  this.pressKey(KeyEvent.VK_F9);
			  }else if(cmd .equals(new String( "F10"))){
				  this.pressKey(KeyEvent.VK_F10);
			  }else if(cmd .equals(new String( "F11"))){
				  this.pressKey(KeyEvent.VK_F11);
			  }else if(cmd .equals(new String( "F12"))){
				  this.pressKey(KeyEvent.VK_F12);
			  }else if(cmd .equals(new String( "PrtSc"))){
				  this.pressKey(KeyEvent.VK_PRINTSCREEN);
			  }else if(cmd .equals(new String( "Fn"))){
//				  this.pressKey(KeyEvent.VK_m);//-----------------------------------------------------------------
			  }
			  this.MultipressKey(cmd);
		  }
	}
	
	public void pressKey(int keyvalue) {
			this.keyPress(keyvalue);
			this.keyRelease(keyvalue);
	}
			
	public void MultipressKey(String cmd) {

	}
	
	
}
