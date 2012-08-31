package com.remoter.pc;

import java.awt.Label;
import java.awt.TextArea;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

public class X_Server {

	private static boolean DBG = true;

	private static final long serialVersionUID = 1L;

	private static X_Robot mRobot;

	private int SocketTimeout = 400;
	private static DatagramSocket mSocket;
	private static DatagramPacket mPacket;
	private static DatagramPacket mPacket_R;

	private static boolean SocketIsOpen = false;

	public static String BROADCASTADDR = "192.168.1.255";
	public static boolean broadcatst_enable = true;
	private static String targetIP_str;
	private static InetAddress targetIP;
	private static int targetPort;
	private static int locatePort;

	private static byte[] mDataTemp;
	private static String mData;
	private static int mData_lenth;
	private JTextArea minfo; //
	private Label msystemstatus; //
	private static String mDisplayerTemp = "";
	private int DISPLAY_MAX_LENGTH = 500;

	private static String CheckSignal = "Hi,I am here.";
	private static String ClientCloseSignal = "clientisclose.";

	public X_Server() {
		super();
	}

	public X_Server(JTextArea info, Label systemstatus) {
		super();
		minfo = info;
		msystemstatus = systemstatus;
	}

	public void run() {

		mRobot = new X_Robot();

		locatePort = 40001;
		targetIP_str = BROADCASTADDR;
		targetPort = 40002;

		mData_lenth = 1024;
		mDataTemp = new byte[mData_lenth];
		mPacket = new DatagramPacket(mDataTemp, mData_lenth);
		mPacket_R = new DatagramPacket(mDataTemp, mData_lenth);

		this.SocketInit();

		this.Working();
	}

	public void Working() {
		String command = "";

		BroadcastSignal();

		while (SocketIsOpen) {
			command = receiveData();
			if (isClientCloseSignal(command))
				BroadcastSignal(); // client is close,so broadcast signal to all
									// now.
			mRobot.WorkingWith(command);
		}
	}

	public void BroadcastSignal() {

		refresh_Frame("Waiting...", "Waiting...");

		try {
			mSocket.setSoTimeout(SocketTimeout);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PacketSetup(BROADCASTADDR + ":" + targetPort); // setup the packet ip
														// with 192.168.1.255

		while (!isCheckSignal(receiveData())) {
			if (broadcatst_enable)
				sendData(CheckSignal);
		}

		// catch mobile client.
		PacketSetup(mPacket_R.getAddress().toString().substring(1) + ":"
				+ mPacket_R.getPort()); // setup the packet ip with mobile ip

		try {
			mSocket.setSoTimeout(0);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		refresh_Frame("Connected succeed.", "Connected succeed.");
	}

	public void SocketInit() {
		try {
			if (!SocketIsOpen) {
				mSocket = new DatagramSocket(locatePort);
				SocketIsOpen = true;
				refresh_Frame("Socket created.", "Socket created.");
				if (DBG) {
					System.out.println(mDisplayerTemp);
				}
			} else {
				refresh_Frame(
						"mSocket has opened yet.\nPlease check your app.",
						"mSocket has opened yet.");
				if (DBG) {
					System.out.println(mDisplayerTemp);
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			refresh_Frame("Socket create fail.", "Socket create fail.");
			if (DBG) {
				System.out.println(mDisplayerTemp);
			}
			e.printStackTrace();
		}
	}

	public void PacketSetup(String addr) {
		if (addr.contains(":")) {
			try {
				targetIP_str = addr.substring(0, addr.indexOf(':'));
				targetIP = InetAddress.getByName(targetIP_str);
				mPacket.setAddress(targetIP);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			targetPort = new Integer(addr.substring(addr.indexOf(':') + 1,
					addr.length())).intValue();
			mPacket.setPort(targetPort);
		} else {
			try {
				targetIP_str = addr;
				targetIP = InetAddress.getByName(targetIP_str);
				mPacket.setAddress(targetIP);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String receiveData() {
		try {

			mSocket.receive(mPacket_R);
			mDataTemp = mPacket_R.getData();
			mData = new String(mDataTemp, 0, mPacket_R.getLength());

			if (DBG) {
				refresh_Frame(mData, null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// if(DBG) e.printStackTrace();
		}
		return mData;
	}

	public void sendData(String data) {
		mDataTemp = data.getBytes();
		mPacket.setData(mDataTemp, 0, mDataTemp.length);
		try {
			mSocket.send(mPacket);
			if (DBG)
				System.out.println("sendData: " + data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// if(DBG) e.printStackTrace();
		}
	}

	public boolean isCheckSignal(String data) {
		if (data == null)
			return false;
		else if (data.equals(CheckSignal))
			return true;
		return false;
	}

	public boolean isClientCloseSignal(String data) {
		if (data == null)
			return false;
		else if (data.equals(ClientCloseSignal)) {
			refresh_Frame("Client is closed.", null);
			return true;
		}
		return false;
	}

	private String displaytemp_add(String str) {
		mDisplayerTemp += str + "\n";
		if (mDisplayerTemp.length() > DISPLAY_MAX_LENGTH)
			mDisplayerTemp = mDisplayerTemp.substring(
					mDisplayerTemp.indexOf("\n") + 1, mDisplayerTemp.length());
		return mDisplayerTemp;
	}

	private void refresh_Frame(String info, String systemstatus) {
		if (info != null) {
			displaytemp_add(info);
			this.minfo.setText(mDisplayerTemp);
		}
		if (systemstatus != null) {
			this.msystemstatus.setText(systemstatus);
		}
	}
}
