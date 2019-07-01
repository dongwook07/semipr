package videoShop;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import view.CustomerView;
import view.VideoView;

public class VideoShop extends JFrame{
	VideoView video;
	CustomerView customer;
	
	public VideoShop() {
		video=new VideoView();//按眉积己
		customer=new CustomerView();
		
		
		//徘眠啊
		JTabbedPane pane =new JTabbedPane();
		pane.addTab("绊按包府", customer);
		pane.add("厚叼坷 包府", video);
		pane.setSelectedIndex(1);
		add("Center",pane);
		setSize(800,600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	public static void main(String[] args) {
		new VideoShop();
		
	}

	

}
