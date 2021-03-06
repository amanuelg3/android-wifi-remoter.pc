package com.remoter.pc;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class X_Robot extends Thread { // 这个线程理论上可以省略掉

	private static boolean DBG = true;

	private String KEY_FLAG = "k";
	private String MOUSE_FLAG = "m";
	private String MOUSE_CLICK = "c";
	private String MOUSE_CLICKDOWN = "d";
	private String MOUSE_CLICKUP = "u";
	private String MOUSE_UP_FLAG = "u";
	private String MOUSE_DOWN_FLAG = "d";
	private String GRYO_FLAG = "g";
	private String GRYO_REG = "r";
	private String GRYO_UNREG = "u";
	private String ACCE_FLAG = "a";
	private String SETUP_FLAG = "s";
	private String SENSOR_PRESS = "prs";
	private String SENSOR_RELEASE = "rls";
	private String SENSOR_MID = "mid";
	private String SENSOR_LFET = "lef";
	private String SENSOR_RIGHT = "rig";
	private KeyThread keyThread;
	private MouseThread mouseThread;

	public X_Robot() {
		super();
		if (DBG)
			System.out.println("X_Robot is booting.");
		keyThread = new KeyThread();
		mouseThread = new MouseThread();
		keyThread.start();
		mouseThread.start();
	}

	public void WorkingWith(String command) {

		String cmd_flag = separateFlagandData(command)[0];
		String cmd_data = separateFlagandData(command)[1];

		if (cmd_flag.equals(KEY_FLAG)) {

			keyThread.handlewith(cmd_data);

		} else if (cmd_flag.equals(MOUSE_FLAG)) {

			mouseThread.handlewith(cmd_data);

		} else if (cmd_flag.equals(SETUP_FLAG)) {

			robot_setup_with(cmd_data);

		}
	}

	private void robot_setup_with(String parameter) { // e.g. x1280y720s1.0
		int x_index = parameter.indexOf("x"), y_index = parameter.indexOf("y"), s_index = parameter
				.indexOf("s");
		MouseRobot.Mobilescreen_x = new Float(parameter.substring(x_index + 1,
				y_index)).floatValue();
		MouseRobot.Mobilescreen_y = new Float(parameter.substring(y_index + 1,
				s_index)).floatValue();
		MouseRobot.motion_scale = new Float(parameter.substring(s_index + 1))
				.floatValue();
		MouseRobot.motion_scale = 1 + (MouseRobot.motion_scale - 25) / 25;
	}

	public String[] separateFlagandData(String data) {
		String[] s = new String[2];
		s[0] = data.substring(0, 1);
		s[1] = data.substring(1, data.length());
		return s;
	}

	// ---------------------------------------------

	class KeyThread extends Thread {

		private KeyRobot kRobot;
		private boolean receiveDataFlag = false;
		private String mCmd_data = "";

		public KeyThread() {
			super();
			try {
				kRobot = new KeyRobot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (DBG)
				System.out.println("KeyRobot is booting.");
		}

		public void handlewith(String cmd_data) {
			//
			receiveDataFlag = true;
			mCmd_data = cmd_data;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			super.run();

			// ----------------------------
			while (true) {
				if (receiveDataFlag) {
					receiveDataFlag = false;
					if (DBG)
						System.out.println("I receive cmd:\"" + mCmd_data
								+ "\",I must work hard.");

					// Now,let kRobot to finish the job.
					kRobot.parseKey(mCmd_data);
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

	// ---------------------------------------------

	class MouseThread extends Thread {

		private MouseRobot mRobot;
		private boolean receiveDataFlag = false;
		private String mCmd_data = "";
		float[] valuescurr = new float[2];
		float[] valuestmp = new float[2];
		float[] valuesdelta = new float[2];
		int clickcount = 0, clickcountthreshold = 5; // click time
//		private float mouseerase = (float) 0.3;

		public MouseThread() {
			super();
			valuescurr[0] = MouseInfo.getPointerInfo().getLocation().x;
			valuescurr[1] = MouseInfo.getPointerInfo().getLocation().y;
			for (int i = 0; i < 2; i++) {
				valuestmp[i] = 0;
				valuesdelta[i] = 0;
			}
			try {
				mRobot = new MouseRobot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mRobot.getPCScreenSize();
			
			if (DBG)
				System.out.println("MouseRobot is booting.");
		}

		public void handlewith(String cmd_data) {
			//
			receiveDataFlag = true;
			mCmd_data = cmd_data;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			super.run();
			while (true) {
				if (receiveDataFlag) {
					if (DBG)
						System.out.println("I receive cmd:\"" + mCmd_data
								+ "\",I must work hard.");
					// Now,let mRobot to finish the job.

					String cmd_flag = separateFlagandData(mCmd_data)[0];
					String cmd_data = separateFlagandData(mCmd_data)[1];

					if (cmd_flag.equals(ACCE_FLAG)) { // acce mode

						handleAcceCmdData(cmd_data);

					} else if (cmd_flag.equals(GRYO_FLAG)) { // gryo mode

						handleGryoCmdData(cmd_data);

					} else if (cmd_flag.equals(MOUSE_CLICK)) {

						handleClickCmdData(cmd_data);

					} else { // touch mode

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

		private void handleTouchCmdData(String data) {

			float[] values = { 0, 0 };

			String cmd_flag = separateFlagandData(data)[0];
			String cmd_data = separateFlagandData(data)[1];

			if (cmd_flag.equals(MOUSE_DOWN_FLAG)) {
				clickcount = 0;
				values = mRobot.parseCmdData(cmd_data);
				mRobot.ConfirmAreaBy(values);
				for (int i = 0; i < 2; i++) {
					valuestmp[i] = values[i];
				}
			} else if (cmd_flag.equals(MOUSE_UP_FLAG)) {
				values = mRobot.parseCmdData(cmd_data);
				if (clickcount < clickcountthreshold)
					mRobot.SelectClickOrWheelInAreaBy(values);
			} else {
				clickcount++;
				values = mRobot.parseCmdData(data);
			}

			for (int i = 0; i < 2; i++) {
				valuesdelta[i] = values[i] - valuestmp[i];
				// add some fiter
				// valuesdelta[i] += mouseerase * (values[i]-valuestmp[i]);
				valuescurr[i] += (valuesdelta[i]) * MouseRobot.motion_scale;
				valuestmp[i] = values[i];
			}

			if (!mRobot.mArea.equals(MouseRobot.WheelArea))
				valuescurr = mRobot.mmouseMove(valuescurr);
			else {
				if ((Math.abs(valuesdelta[1]) > 2))
					mRobot.mouseWheel(valuesdelta[1] > 0 ? 2 : -2);
			}
		}

		private void handleClickCmdData(String data) {
			if (data.equals(MOUSE_CLICKDOWN)) {
				mRobot.mousePress(InputEvent.BUTTON1_MASK);
			} else if (data.equals(MOUSE_CLICKUP)) {
				mRobot.mouseRelease(InputEvent.BUTTON1_MASK);
			}
		}

		private void handleGryoCmdData(String data) {// mgx?y?    mau    mgr ->
													 // gx?y?     gu     gr -> 
													 // x?y?      u      r
			if (data.equals(GRYO_REG)) {
				mRobot.isGryoUp = true;
			} else if (data.equals(GRYO_UNREG)) {
				
			} else if (data.equals(SENSOR_LFET + SENSOR_PRESS)) {
				clickcount = 0;
				mRobot.mousePress(InputEvent.BUTTON1_MASK);
			} else if (data.equals(SENSOR_LFET + SENSOR_RELEASE)) {
				mRobot.mouseRelease(InputEvent.BUTTON1_MASK);
			} else if (data.equals(SENSOR_MID)) {
				mRobot.mousewheelclick();
			} else if (data.equals(SENSOR_RIGHT)) {
				mRobot.mouserightclick();
			} else{
				float[] values = mRobot.parseCmdData(data);
				if(mRobot.isGryoUp){
					mRobot.isGryoUp = false;
					valuestmp[0] = values[0];
					valuestmp[1] = values[1];
				}
				for (int i = 0; i < 2; i++) {
					valuesdelta[i] = values[i] - valuestmp[i];
					valuescurr[i] += valuesdelta[i];
				}
				valuescurr = mRobot.mmouseMove(valuescurr);
			}
		}

		private void handleAcceCmdData(String data) {
			float[] values = mRobot.parseCmdData(data);
			for (int i = 0; i < 2; i++) {
				valuesdelta[i] = values[i] - valuestmp[i];
				valuescurr[i] += valuesdelta[i];
				valuestmp[i] = values[i];
			}
		}
	}
}
