package com.remoter.pc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class X_Frame {
	
	private static boolean DBG = true;
	
	static String t = "Android 4.1 (Jelly Bean) builds on what's great about Android with improvements to performance and user experience.  "
			+ "\n\n\n"
			+ " New APIs are also available that allow you to build richer and more interactive notifications, transfer larger payloads through NFC, discover services over Wi-Fi, and much more."
			+ "mQGiBEnnWD4RBACt9/h4v9xnnGDou13y3dvOx6/t43LPPIxeJ8eX9WB+8LLuROSVlFhpHawsVAcFlmi7f7jdSRF+OvtZL9ShPKdLfwBJMNkU66/TZmPewS4m782ndtw78tR1cXb197Ob8kOfQB3A9yk2XZ4ei4ZC3i6wVdqHLRxABdncwu5hOF9KXwCgkxMDu4PVgChaAJzTYJ1EG+UYBIUEAJmfearb0qRAN7dEoff0FeXsEaUA6U90sEoVks0ZwNj96SA8BL+a1OoEUUfpMhiHyLuQSftxisJxTh+2QclzDviDyaTrkANjdYY7p2cq/HMdOY7LJlHaqtXmZxXjjtw5Uc2QG8UY8aziU3IE9nTjSwCXeJnuyvoizl9/I1S5jU5SA/9WwIps4SC84ielIXiGWEqq6i6/sk4I9q1YemZF2XVVKnmI1F4iCMtNKsR4MGSa1gA8s4iQbsKNWPgp7M3a51JCVCu6l/8zTpA+uUGapw4tWCp4o0dpIvDPBEa9b/aF/ygcR8mh5hgUfpF9IpXdknOsbKCvM9lSSfRciETykZc4wrRCVGhlIEFuZHJvaWQgT3BlbiBTb3VyY2UgUHJvamVjdCA8aW5pdGlhbC1jb250cmlidXRpb25AYW5kcm9pZC5jb20+iGAEExECACAFAknnWD4CGwMGCwkIBwMCBBUCCAMEFgIDAQIeAQIXgAAKCRDorT+BmrEOeNr+AJ42Xy6tEW7r3KzrJxnRX8mij9z8tgCdFfQYiHpYngkI2t09Ed+9Bm4gmEO5Ag0ESedYRBAIAKVW1JcMBWvV/0Bo9WiByJ9WJ5swMN36/vAlQN4mWRhfzDOk/Rosdb0csAO/l8Kz0gKQPOfObtyYjvI8JMC3rmi+LIvSUT9806UphisyEmmHv6U8gUb/xHLIanXGxwhYzjgeuAXVCsv+EvoPIHbY4L/KvP5x+oCJIDbkC2b1TvVk9PryzmE4BPIQL/NtgR1oLWm/uWR9zRUFtBnE411aMAN3qnAHBBMZzKMXLWBGWE0znfRrnczI5p49i2YZJAjyX1P2WzmScK49CV82dzLo71MnrF6fj+Udtb5+OgTg7Cow+8PRaTkJEW5Y2JIZpnRUq0CYxAmHYX79EMKHDSThf/8AAwUIAJPWsB/MpK+KMs/s3r6nJrnYLTfdZhtmQXimpoDMJg1zxmL8UfNUKiQZ6esoAWtDgpqt7Y7sKZ8laHRARonte394hidZzM5nb6hQvpPjt2OlPRsyqVxw4c/KsjADtAuKW9/d8phbN8bTyOJo856qg4oOEzKG9eeF7oaZTYBy33BTL0408sEBxiMior6b8LrZrAhkqDjAvUXRwm/fFKgpsOysxC6xi553CxBUCH2omNV6Ka1LNMwzSp9ILz8jEGqmUtkBszwoG1S8fXgE0Lq3cdDM/GJ4QXP/p6LiwNF99faDMTV3+2SAOGvytOX6KjKVzKOSsfJQhN0DlsIw8hqJc0WISQQYEQIACQUCSedYRAIbDAAKCRDorT+BmrEOeCUOAJ9qmR0lEXzeoxcdoafxqf6gZlJZlACgkWF7wi2YLW3Oa+jv2QSTlrx4KLM==Wi5D";

	private static JFrame mFrame;
	private static JTabbedPane tabbedPane; //whole pane (include of main_tab,setup_tab,about_tab)
	private static JTextArea info;  //
	private static JTextArea about; //
	private static JButton broadcastbtn;
	private static JTextField BCip_1,BCip_2,BCip_3,BCip_4;
	private static JButton setupbtn;
	private static JButton feedbackbtn;
	private static Label systemstatus;
	private static X_Server mServer;
	
	public static void main(String[] agrs) {
		
		Frame_init();

		main_table_init();
		
		setup_table_init();
		
		about_table_init();

		status_bar_init();
		
		mFrame.setVisible(true);
		info.requestFocus();
		
		mServer = new X_Server(info,systemstatus);
		mServer.run();
	}
	
	private static void Frame_init(){
		mFrame = new JFrame();
		mFrame.setForeground(Color.GRAY);
		mFrame.setTitle("Remoter_X");
		mFrame.setIconImage(new ImageIcon(ClassLoader
				.getSystemResource("ic_launcher.png")).getImage());
		mFrame.setBounds(
				java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 250,
				java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 160,
				500, 320);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		mFrame.setResizable(false);
	}
	
	private static void main_table_init(){
		JLabel welcome = new JLabel(" Welcome !");
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		mFrame.getContentPane().add(welcome, BorderLayout.NORTH);
		welcome.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 28));

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setToolTipText("");
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		mFrame.getContentPane().add(tabbedPane);

		JPanel main_tab = new JPanel();
		tabbedPane.addTab(
				"",
				new ImageIcon(ClassLoader
						.getSystemResource("ic_menu_refresh.png")), main_tab,
				null);
		main_tab.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 268, 206);
		panel.setBorder(BorderFactory.createTitledBorder(""));
		main_tab.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		info = new JTextArea(8, 20);
		panel.add(new JScrollPane(info));
		info.setWrapStyleWord(true);
		info.setLineWrap(true);
		info.setColumns(10);
		info.requestFocus();
		info.setCaretPosition(0);

		broadcastbtn = new JButton();
		broadcastbtn.setFont(new Font("SimSun", Font.PLAIN, 12));
		broadcastbtn.setText("Stop BC");
		broadcastbtn.setBounds(288, 10, 94, 23);
		broadcastbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(X_Server.broadcatst_enable){
					X_Server.broadcatst_enable = false;
					broadcastbtn.setText("Start BC");
					if(setupbtn != null)setupbtn.setEnabled(true);
				}else{
					X_Server.broadcatst_enable = true;
					broadcastbtn.setText("Stop BC");
					if(setupbtn != null)setupbtn.setEnabled(false);
				}
				super.mouseClicked(e);
			}
			
		});
		main_tab.add(broadcastbtn);
	}
	
	private static void setup_table_init(){
		JPanel setup_tab = new JPanel();
		tabbedPane.addTab(
				"",
				new ImageIcon(ClassLoader
						.getSystemResource("ic_menu_manage.png")), setup_tab,
				null);
		setup_tab.setLayout(null);
		
		JTextField t = new JTextField("");//---¹ÊÒâµÄ
		t.setBounds(1, 1, 1, 1);
		setup_tab.add(t);
		
		Label BClabel = new Label("Setup broadcast address:");
		BClabel.setBounds(10, 10, 200, 20);
		setup_tab.add(BClabel);
		
		Label BCIplabel = new Label("Address:");
		BCIplabel.setBounds(10, 30, 60, 20);
		setup_tab.add(BCIplabel);
		
		BCip_1 = new JTextField("192");
		BCip_1.setBounds(70, 30, 40, 20);
		BCip_1.addKeyListener(new BCkeyListener());
		BCip_1.addFocusListener(new BCFocusListener());
		setup_tab.add(BCip_1);
		Label Colon_1 = new Label(".");
		Colon_1.setBounds(110, 30, 10, 20);
		setup_tab.add(Colon_1);
		
		BCip_2 = new JTextField("168");
		BCip_2.setBounds(120, 30, 40, 20);
		BCip_2.addKeyListener(new BCkeyListener());
		BCip_2.addFocusListener(new BCFocusListener());
		setup_tab.add(BCip_2);
		Label Colon_2 = new Label(".");
		Colon_2.setBounds(160, 30, 10, 20);
		setup_tab.add(Colon_2);
		
		BCip_3 = new JTextField("1");
		BCip_3.setBounds(170, 30, 40, 20);
		BCip_3.addKeyListener(new BCkeyListener());
		BCip_3.addFocusListener(new BCFocusListener());
		setup_tab.add(BCip_3);
		Label Colon_3 = new Label(".");
		Colon_3.setBounds(210, 30, 10, 20);
		setup_tab.add(Colon_3);
		
		BCip_4 = new JTextField("255");
		BCip_4.setBounds(220, 30, 40, 20);
		BCip_4.addKeyListener(new BCkeyListener());
		BCip_4.addFocusListener(new BCFocusListener());
		setup_tab.add(BCip_4);
		
		setupbtn = new JButton();
		setupbtn.setText("Setup");
		setupbtn.setFont(new Font("SimSun", Font.PLAIN, 12));
		setupbtn.setBounds(288, 30, 94, 20);
		if(X_Server.broadcatst_enable)setupbtn.setEnabled(false);
		setupbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				X_Server.BROADCASTADDR = BCip_1.getText()+"."+ BCip_2.getText()+"."+ BCip_3.getText()+"."+ BCip_4.getText();
				mServer.PacketSetup(X_Server.BROADCASTADDR);
				if(DBG) System.out.println(X_Server.BROADCASTADDR);
				super.mouseClicked(e);
			}
		});
		setup_tab.add(setupbtn);
	}
	
	static class BCkeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			JTextField tmp = (JTextField)e.getComponent();
			if(e.getKeyChar() == KeyEvent.VK_SPACE){
				tmp.nextFocus();
				tmp.setText(tmp.getText().trim());
				return;
			}
			if((e.getKeyChar() <= KeyEvent.VK_9)&&(e.getKeyChar() >= KeyEvent.VK_0)){ 
				if((Integer.valueOf(tmp.getText()).intValue()>=100)&&(Integer.valueOf(tmp.getText()).intValue()<=255)){
					tmp.nextFocus();
				}else if(Integer.valueOf(tmp.getText()).intValue()>255) tmp.setText("");
				else ;
			}else{
				tmp.setText("");
			}
			if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
				if(tmp.getText().length()<=0)
					;
			}
		}
	}
	
	static class BCFocusListener implements FocusListener{
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			JTextField tmp = (JTextField)e.getComponent();
			tmp.setText("");
		}
	}
	
	private static void about_table_init(){
		JPanel about_tab = new JPanel();
		tabbedPane
				.addTab("",
						new ImageIcon(ClassLoader
								.getSystemResource("ic_menu_home.png")),
						about_tab, null);
		about_tab.setLayout(null);

		feedbackbtn = new JButton("Feedback");
		feedbackbtn.setFont(new Font("ËÎÌå", Font.PLAIN, 12));
		feedbackbtn.setBounds(288, 10, 120, 23);
		about_tab.add(feedbackbtn);

		about = new JTextArea();
		about.setWrapStyleWord(true);
		about.setLineWrap(true);
		about.setColumns(10);
		about.setCaretPosition(0);
		about.setText(t);
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createTitledBorder(""));
		panel_1.setBounds(10, 10, 268, 206);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		panel_1.add(new JScrollPane(about));
		about_tab.add(panel_1);
	}
	
	private static void status_bar_init(){
		JPanel westpanel = new JPanel();
		mFrame.getContentPane().add(westpanel, BorderLayout.WEST);

		JPanel statuspanel = new JPanel();
		mFrame.getContentPane().add(statuspanel, BorderLayout.SOUTH);
		statuspanel.setLayout(new BorderLayout(0, 0));

		JPanel statuswestpanel = new JPanel();
		statuspanel.add(statuswestpanel, BorderLayout.WEST);

		systemstatus = new Label("waiting...");
		statuspanel.add(systemstatus, BorderLayout.CENTER);

		JPanel rightpanel = new JPanel();
		mFrame.getContentPane().add(rightpanel, BorderLayout.EAST);
	}
}
