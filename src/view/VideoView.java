package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.VideoModel;
import model.vo.Video;

public class VideoView extends JPanel implements ActionListener{
	JTextField tfVideoNum,tfVideoTitle,tfVideoDirector,tfVideoActor;
	JComboBox comVideoJanre;
	JTextArea taVideoContent;
	
	JCheckBox cbMultiInsert;
	JTextField tfInsertCount;
	
	JButton bVideoInsert,bVideoModify,bVideoDelete;

	JComboBox comVideoSearch;
	JTextField tfVideoSearch;
	JTable tableVideo; // table view ��Ȱ
	
	//�߰�
	VideoModel model;
	VideoTableModel tbModelVideo;
	
	//�̹����۾�
	JPanel picPanel;
	JLabel picLabel;
	JButton bChooseFile;
	JTextField jfPath;
	
	File f=null;
	String fName="";
	
	
	public VideoView() {
		addLayout();//ȭ�鼳��
		
		//�ʱ�ȭ �۾�
		initStyle();
		//�̺�Ʈ �۾�
		eventProc();
		//db����
		ConnectDB();
		
	}
	
	void initStyle() {
		tfVideoNum.setEditable(false);//��Ȱ��ȭ
		tfInsertCount.setEditable(false);
	}
	
	public void ConnectDB() {
		try {
			model=new VideoModel();
			System.out.println("�������Ἲ��");
		} catch (Exception e) {
			System.out.println("�����������");
		}
	}
	
	
	public void eventProc() {
		//���ű� ����
		cbMultiInsert.addActionListener(this);//�����԰�
		bVideoDelete.addActionListener(this);
		bVideoInsert.addActionListener(this);
		bVideoModify.addActionListener(this);
		tfVideoSearch.addActionListener(this);//�˻�
		
		//JTable�� ������ ����
		tableVideo.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				int row=tableVideo.getSelectedRow();
				int col=0;
				//Ŭ�����ڵ� ���� ��ȣ���
				String data=(String) tableVideo.getValueAt(row, col);
				int no=Integer.parseInt(data);
				
				try {
					Video vo=model.selectbyPk(no);
					selectbyPk(vo);
					
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

	private void addLayout() {
		//���� ������ ����
		tfVideoNum=new JTextField();
		tfVideoTitle=new JTextField();
		tfVideoDirector=new JTextField();
		tfVideoActor=new JTextField();
		
		String[] cbJanreStr= {"���","�׼�","����","�ڹ̵�"};
		comVideoJanre = new JComboBox(cbJanreStr);
		taVideoContent = new JTextArea();
		
		cbMultiInsert = new JCheckBox("�����԰�");
		tfInsertCount = new JTextField("1",5);
		
		//��ư
		bVideoInsert=new JButton("�԰�");
		bVideoModify=new JButton("����");
		bVideoDelete=new JButton("����");
		
		String[] cbVideoSearch= {"����","����"};
		comVideoSearch = new JComboBox(cbVideoSearch);
		tfVideoSearch = new JTextField(15);
		
		//�����߰� ���� ���̺�ó��
		tbModelVideo =new VideoTableModel();
		tableVideo =new JTable(tbModelVideo);
		tableVideo.setModel(tbModelVideo);
		
		picLabel=new JLabel();
		picPanel=new JPanel();
		
		
		//ȭ�鱸��
		//west �ǳڱ���
		JPanel p_west= new JPanel();
		p_west.setLayout(new BorderLayout());
		
		//���� ���
		JPanel p_west_center=new JPanel();
		p_west_center.setLayout(new BorderLayout());
		
		//���� ��� ����
		JPanel p_west_center_north=new JPanel();
		p_west_center_north.setLayout(new GridLayout(5, 2));
		p_west_center_north.add(new JLabel("������ȣ"));
		p_west_center_north.add(tfVideoNum);
		
		p_west_center_north.add(new JLabel("�帣"));
		p_west_center_north.add(comVideoJanre);
		
		p_west_center_north.add(new JLabel("����"));
		p_west_center_north.add(tfVideoTitle);
		
		p_west_center_north.add(new JLabel("����"));
		p_west_center_north.add(tfVideoDirector);
		
		p_west_center_north.add(new JLabel("���"));
		p_west_center_north.add(tfVideoActor);
		
		
		//���� ��� ���
		JPanel p_west_center_center = new JPanel();
		p_west_center_center.setLayout(new GridLayout(0, 2));
		
		p_west_center_center.add(new JLabel("����"));
		
		p_west_center_center.add(new JLabel("�׸�"));
		
		
		p_west_center_center.add(taVideoContent);
		p_west_center_center.add(picLabel);
		//�̹��� ã�� ��ư ��� �߰�
		bChooseFile = new JButton("selFile");
		jfPath=new JTextField();
		p_west_center_center.add(jfPath);
		p_west_center_center.add(bChooseFile);
		//��ư�� ������ ����
		bChooseFile.addActionListener(this);
		
		
		
		
		
		
		
		//���� ��� �ǳڿ� �ΰ��� �ǳ��߰�
		p_west_center.add(p_west_center_north,BorderLayout.NORTH);
		p_west_center.add(p_west_center_center,BorderLayout.CENTER);
		
		p_west_center.setBorder(new TitledBorder("���������Է�"));//��輱 �����
		
		//���� �Ʒ�
		JPanel p_west_south=new JPanel();
		p_west_south.setLayout(new GridLayout(2, 1));
		
		//���� �Ʒ��� ���� �ǳ�
		JPanel p_west_south_1=new JPanel();
		p_west_south_1.setLayout(new FlowLayout());
		p_west_south_1.add(cbMultiInsert);
		p_west_south_1.add(tfInsertCount);
		p_west_south_1.add(new JLabel("��"));
		p_west_south_1.setBorder(new TitledBorder("�����Է½� ����"));
		
		
		JPanel p_west_south_2=new JPanel();
		p_west_south_2.setLayout(new GridLayout(1, 3));
		p_west_south_2.add(bVideoInsert);
		p_west_south_2.add(bVideoModify);
		p_west_south_2.add(bVideoDelete);
		
		p_west_south.add(p_west_south_1);
		p_west_south.add(p_west_south_2);
		
		p_west.add(p_west_center,BorderLayout.CENTER);
		p_west.add(p_west_south,BorderLayout.SOUTH);
		
		//east �ǳڱ���
		JPanel p_east = new JPanel();
		p_east.setLayout(new BorderLayout());
		
		//�����ʿ� ����
		JPanel p_east_north =new JPanel();
		p_east_north.add(comVideoSearch);
		p_east_north.add(tfVideoSearch);
		//��輱 �����
		p_east_north.setBorder(new TitledBorder("�����˻�"));
		p_east.add(p_east_north,BorderLayout.NORTH);
		p_east.add(new JScrollPane(tableVideo),BorderLayout.CENTER);
		
		
		setLayout(new GridLayout(1, 2));
		add(p_west);
		add(p_east);
	}
	
	class VideoTableModel extends AbstractTableModel{
//JTable �� �����ϱ� ���� ������ ����
		ArrayList data=new ArrayList();
		String[] columnNames= {"������ȣ","����","�帣","����","���"};
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			ArrayList temp=(ArrayList) data.get(row);
			return temp.get(col);
		}
		public String getColumnName(int col) {
			return columnNames[col];
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt=e.getSource();
		if (evt==bVideoInsert) {//�԰��ư�� ������
			fName=System.currentTimeMillis()+f.getName();//�����̸��� �����´�
			insertVideo(fName);
			//db�� insert �Ϸ�� ����
			//file upload ����
			System.out.println("f :"+f);
			System.out.println("fname : "+fName);
			fileSave(f,".//upload2",fName);
			
			
		}else if (evt==tfVideoSearch) {
			searchVideo();
		}else if (evt==bVideoModify) {
			modify();
		}else if (evt==bVideoDelete) {
			delete();
		}else if (evt==bChooseFile) {
			System.out.println("���ϼ���");
			JFileChooser jc=new JFileChooser();
			jc.showOpenDialog(this);
			f=jc.getSelectedFile();
			jfPath.setText(f.getPath());
			
		}
		
	}
	
	void fileSave(File file, String path, String name) {
		try {
			File f=new File(path);
			if (!f.exists()) {
				f.mkdirs();//���� �����
				
			}
			String filePath=path+"\\"+name;
			
			FileInputStream fis=new FileInputStream(file);
			FileOutputStream fos=new FileOutputStream(filePath);
			
			int i=0;
			byte[] buffer=new byte[1024];
			
			while ((i=fis.read(buffer,0,1024))!=-1) {
				fos.write(buffer,0,i);
			}
			fis.close();
			fos.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete() {
//		Video vo=new Video();
//		vo.setVicode(Integer.parseInt(tfVideoNum.getText()));
		String vico= tfVideoNum.getText();
		
		try {
//			model.deleteVideo(vo);
			model.deleteVideo(vico);
			JOptionPane.showMessageDialog(null, "�����Ϸ�");
			tfVideoNum.setText(null);
			tfVideoActor.setText(null);
			tfVideoDirector.setText(null);
			tfVideoTitle.setText(null);
			taVideoContent.setText(null);
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "��������");
			e1.printStackTrace();
		}//�����۾� �޼ҵ� ȣ��
		
	}

	private void modify() {
		Video vo=new Video();
		vo.setVicode(Integer.parseInt(tfVideoNum.getText()));
		vo.setVideoName(tfVideoTitle.getText());
		vo.setActor(tfVideoActor.getText());
		vo.setDirector(tfVideoDirector.getText());
		vo.setExp(taVideoContent.getText());
		vo.setGenre((String)comVideoJanre.getSelectedItem());
		
		try {
			model.modifyVideo(vo);
			JOptionPane.showMessageDialog(null, "�����Ϸ�");
			tfVideoNum.setText(null);
			tfVideoActor.setText(null);
			tfVideoDirector.setText(null);
			tfVideoTitle.setText(null);
			taVideoContent.setText(null);
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "��������");
			e1.printStackTrace();
		}//�����۾� �޼ҵ� ȣ��
	}

	private void searchVideo() {
		int idx=comVideoSearch.getSelectedIndex();
		String str =tfVideoSearch.getText();
		
		try {
			ArrayList data=model.searchVideo(idx,str);
			tbModelVideo.data=data;//tbModelVideo = VideoTableModel
			System.out.println(data);
			tableVideo.setModel(tbModelVideo);
			tbModelVideo.fireTableDataChanged();
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	ImageIcon icon;
	
	void selectbyPk(Video vo) {
		//���õ� ���ڵ带 textfield�� �Ѹ���
		tfVideoNum.setText(String.valueOf(vo.getVicode()));
		tfVideoTitle.setText(vo.getVideoName());
		tfVideoDirector.setText(vo.getDirector());
		tfVideoActor.setText(vo.getActor());
		taVideoContent.setText(vo.getExp());
		comVideoJanre.setSelectedItem(vo.getGenre());
		
		//�̹��� �Ѹ��� ó��
		System.out.println(vo.getImgfname());
		icon=new ImageIcon(".\\upload2\\"+vo.getImgfname());
		ImageIcon newIcon;
		Image image=icon.getImage();
		image.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(),0);
		int imgW=picLabel.getWidth();
		int imgH=picLabel.getHeight();
		Image img=icon.getImage();
		Image newimg=img.getScaledInstance(imgW, imgH, java.awt.Image.SCALE_SMOOTH);
		newIcon=new ImageIcon(newimg);
		picLabel.setIcon(newIcon);
	}
	

	private void insertVideo(String fName) {
		Video vo=new Video();
		vo.setGenre((String) comVideoJanre.getSelectedItem());
		vo.setActor(tfVideoActor.getText());
		vo.setDirector(tfVideoDirector.getText());
		vo.setExp(taVideoContent.getText());
		vo.setVideoName(tfVideoTitle.getText());
		
		//�̹����� vo�� ����
		vo.setImgfname(fName);
		int count=Integer.parseInt(tfInsertCount.getText());
		
		try {
			model.insertVideo(vo,count);
			JOptionPane.showMessageDialog(null, "�԰�Ϸ�");
			
			//textâ ���������
			tfVideoNum.setText(null);
			tfVideoActor.setText(null);
			tfVideoDirector.setText(null);
			tfVideoTitle.setText(null);
			taVideoContent.setText(null);
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "�԰����");
			e1.printStackTrace();
		}
	}

}
