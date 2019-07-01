package videoShop;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import view.CustomerView;
import view.VideoView;

public class VideoShop extends JFrame{
	VideoView video;
	CustomerView customer;
	
	public VideoShop() {
		video=new VideoView();//��ü����
		customer=new CustomerView();
		
		
		//���߰�
		JTabbedPane pane =new JTabbedPane();
		pane.addTab("������", customer);
		pane.add("���� ����", video);
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
