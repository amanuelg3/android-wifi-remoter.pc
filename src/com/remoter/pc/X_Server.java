package com.remoter.pc;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class X_Server extends Frame {

	private static boolean DBG = true;
	
	private static final long serialVersionUID = 1L;
	
	private static X_Server mServer;
	private static X_Robot mRobot;
	
	private static DatagramSocket mSocket;
	private static DatagramPacket mPacket;
	private static boolean SocketIsOpen = false;
	
	private static String targetIP_str;
	private static InetAddress targetIP;
	private static int targetPort;
	private static String locateIP_str;
	private static int locatePort;
	
	private static byte[] mDataTemp;
	private static String mData;
	private static int mData_lenth;
	private TextArea mDisplayer;
	private static String mDisplayerTemp = "";

	private static String CheckOk = "checkconnection";

	

	public X_Server() {
		super();
		super.setTitle("PC_x");
		super.setSize(200, 300);
		Point p = MouseInfo.getPointerInfo().getLocation();
		super.setLocation(p.x, p.y);
		BorderLayout b = new BorderLayout();
		super.setLayout(b);
		mDisplayer = new TextArea();
		super.add(mDisplayer, BorderLayout.CENTER);
		mDisplayerTemp = "Welcome !\n";
		mDisplayer.setText(mDisplayerTemp);
		super.setVisible(true);
	}

	public static void main(String[] agrs) {

		mServer = new X_Server();
		mRobot = new X_Robot();
		
		locatePort = 40001;
		mData_lenth = 1024;
		mDataTemp = new byte[mData_lenth];
		mPacket = new DatagramPacket(mDataTemp, mData_lenth);

		mServer.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				if (SocketIsOpen) {
					// mServer.mSocket.close();
					if (DBG)
						System.out.println("mSocket is closed.");
				}
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		mServer.SocketInit();
		
		mServer.Working();
	}

	public void Working(){
		String command;
		while (SocketIsOpen) {
			command = receiveData();
			if(isCheckSignal(command)){
				PacketSetup(receiveData());
				sendData(CheckOk);
			}
			mRobot.WorkingWith(command);
		}
		
	}
	
	public void SocketInit(){
		try {
			if (!SocketIsOpen) {
				mSocket = new DatagramSocket(locatePort);
				SocketIsOpen = true;
				if (DBG) {
					mDisplayerTemp += "Socket created.\n";
					System.out.println(mDisplayerTemp);
					mServer.mDisplayer.setText(mDisplayerTemp);
				}
			} else {
				if (DBG) {
					mDisplayerTemp = "mSocket has opened yet.\n";
					System.out.println(mDisplayerTemp);
					mServer.mDisplayer.setText(mDisplayerTemp);
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			if (DBG) {
			mDisplayerTemp = "Socket create fail.\n";
			mServer.mDisplayer.setText(mDisplayerTemp);
				System.out.println(mDisplayerTemp);
			}
			e.printStackTrace();
		}
	}
	
	public void PacketSetup(String addr){
		try {
			targetIP_str = addr.substring(0, addr.indexOf(':'));
			targetIP = InetAddress.getByName(targetIP_str);
			mPacket.setAddress(targetIP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		targetPort = new Integer(addr.substring(addr.indexOf(':')+1, addr.length()));
		mPacket.setPort(targetPort);
	}
	
	public String receiveData() {
		try {
			mSocket.receive(mPacket);
			mDataTemp = mPacket.getData();
			mData = new String(mDataTemp, 0,
					mPacket.getLength());
//			mDisplayerTemp = mDisplayerTemp.substring(mDisplayerTemp.indexOf('\n')+1, mDisplayerTemp.lastIndexOf('\n')+1);
			mDisplayerTemp = mData;
			
			if (DBG) {
				//System.out.println(mDisplayerTemp);
				mServer.mDisplayer.setText(mDisplayerTemp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mData;
	}
	
	public void sendData(String data){
		mDataTemp = data.getBytes();
		mPacket.setData(mDataTemp, 0, mDataTemp.length);
		try {
			mSocket.send(mPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isCheckSignal(String data){
		if(data.equals(CheckOk )) return true;
		return false;
	}
}
