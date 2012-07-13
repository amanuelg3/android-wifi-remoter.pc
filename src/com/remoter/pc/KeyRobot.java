package com.remoter.pc;

import java.awt.AWTException;
import java.awt.Robot;

public class KeyRobot extends Robot{
	
	public KeyRobot() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void pressKey(int keyvalue) {
			this.keyPress(keyvalue);
			this.keyRelease(keyvalue);
	}
			
	public void MultipressKey(String cmd) {

	}
}
