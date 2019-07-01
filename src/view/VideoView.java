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
	JTable tableVideo; // table view 역활
	
	//추가
	VideoModel model;
	VideoTableModel tbModelVideo;
	
	//이미지작업
	JPanel picPanel;
	JLabel picLabel;
	JButton bChooseFile;
	JTextField jfPath;
	
	File f=null;
	String fName="";
	
	
	public VideoView() {
		addLayout();//화면설계
		
		//초기화 작업
		initStyle();
		//이벤트 작업
		eventProc();
		//db연결
		ConnectDB();
		
	}
	
	void initStyle() {
		tfVideoNum.setEditable(false);//비활성화
		tfInsertCount.setEditable(false);
	}
	
	public void ConnectDB() {
		try {
			model=new VideoModel();
			System.out.println("비디오연결성공");
		} catch (Exception e) {
			System.out.println("비디오연결실패");
		}
	}
	
	
	public void eventProc() {
		//수신기 부착
		cbMultiInsert.addActionListener(this);//다중입고
		bVideoDelete.addActionListener(this);
		bVideoInsert.addActionListener(this);
		bVideoModify.addActionListener(this);
		tfVideoSearch.addActionListener(this);//검색
		
		//JTable에 리스너 부착
		tableVideo.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				int row=tableVideo.getSelectedRow();
				int col=0;
				//클릭레코드 비디오 번호얻기
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
		//탭의 디자인 구성
		tfVideoNum=new JTextField();
		tfVideoTitle=new JTextField();
		tfVideoDirector=new JTextField();
		tfVideoActor=new JTextField();
		
		String[] cbJanreStr= {"멜로","액션","스릴","코미디"};
		comVideoJanre = new JComboBox(cbJanreStr);
		taVideoContent = new JTextArea();
		
		cbMultiInsert = new JCheckBox("다중입고");
		tfInsertCount = new JTextField("1",5);
		
		//버튼
		bVideoInsert=new JButton("입고");
		bVideoModify=new JButton("수정");
		bVideoDelete=new JButton("삭제");
		
		String[] cbVideoSearch= {"제목","감독"};
		comVideoSearch = new JComboBox(cbVideoSearch);
		tfVideoSearch = new JTextField(15);
		
		//나중추가 엑셀 테이블처럼
		tbModelVideo =new VideoTableModel();
		tableVideo =new JTable(tbModelVideo);
		tableVideo.setModel(tbModelVideo);
		
		picLabel=new JLabel();
		picPanel=new JPanel();
		
		
		//화면구성
		//west 판넬구성
		JPanel p_west= new JPanel();
		p_west.setLayout(new BorderLayout());
		
		//왼쪽 가운데
		JPanel p_west_center=new JPanel();
		p_west_center.setLayout(new BorderLayout());
		
		//왼쪽 가운데 위쪽
		JPanel p_west_center_north=new JPanel();
		p_west_center_north.setLayout(new GridLayout(5, 2));
		p_west_center_north.add(new JLabel("비디오번호"));
		p_west_center_north.add(tfVideoNum);
		
		p_west_center_north.add(new JLabel("장르"));
		p_west_center_north.add(comVideoJanre);
		
		p_west_center_north.add(new JLabel("제목"));
		p_west_center_north.add(tfVideoTitle);
		
		p_west_center_north.add(new JLabel("감독"));
		p_west_center_north.add(tfVideoDirector);
		
		p_west_center_north.add(new JLabel("배우"));
		p_west_center_north.add(tfVideoActor);
		
		
		//외쫀 가운데 가운데
		JPanel p_west_center_center = new JPanel();
		p_west_center_center.setLayout(new GridLayout(0, 2));
		
		p_west_center_center.add(new JLabel("설명"));
		
		p_west_center_center.add(new JLabel("그림"));
		
		
		p_west_center_center.add(taVideoContent);
		p_west_center_center.add(picLabel);
		//이미지 찾기 버튼 경로 추가
		bChooseFile = new JButton("selFile");
		jfPath=new JTextField();
		p_west_center_center.add(jfPath);
		p_west_center_center.add(bChooseFile);
		//버튼에 리스너 부착
		bChooseFile.addActionListener(this);
		
		
		
		
		
		
		
		//왼쪽 가운데 판넬에 두개의 판넬추가
		p_west_center.add(p_west_center_north,BorderLayout.NORTH);
		p_west_center.add(p_west_center_center,BorderLayout.CENTER);
		
		p_west_center.setBorder(new TitledBorder("비디오정보입력"));//경계선 만들기
		
		//왼쪽 아래
		JPanel p_west_south=new JPanel();
		p_west_south.setLayout(new GridLayout(2, 1));
		
		//왼쪽 아래에 사용될 판넬
		JPanel p_west_south_1=new JPanel();
		p_west_south_1.setLayout(new FlowLayout());
		p_west_south_1.add(cbMultiInsert);
		p_west_south_1.add(tfInsertCount);
		p_west_south_1.add(new JLabel("개"));
		p_west_south_1.setBorder(new TitledBorder("다중입력시 선택"));
		
		
		JPanel p_west_south_2=new JPanel();
		p_west_south_2.setLayout(new GridLayout(1, 3));
		p_west_south_2.add(bVideoInsert);
		p_west_south_2.add(bVideoModify);
		p_west_south_2.add(bVideoDelete);
		
		p_west_south.add(p_west_south_1);
		p_west_south.add(p_west_south_2);
		
		p_west.add(p_west_center,BorderLayout.CENTER);
		p_west.add(p_west_south,BorderLayout.SOUTH);
		
		//east 판넬구성
		JPanel p_east = new JPanel();
		p_east.setLayout(new BorderLayout());
		
		//오른쪽에 위쪽
		JPanel p_east_north =new JPanel();
		p_east_north.add(comVideoSearch);
		p_east_north.add(tfVideoSearch);
		//경계선 만들기
		p_east_north.setBorder(new TitledBorder("비디오검색"));
		p_east.add(p_east_north,BorderLayout.NORTH);
		p_east.add(new JScrollPane(tableVideo),BorderLayout.CENTER);
		
		
		setLayout(new GridLayout(1, 2));
		add(p_west);
		add(p_east);
	}
	
	class VideoTableModel extends AbstractTableModel{
//JTable 을 구성하기 위한 데이터 세팅
		ArrayList data=new ArrayList();
		String[] columnNames= {"비디오번호","제목","장르","감독","배우"};
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
		if (evt==bVideoInsert) {//입고버튼이 눌리면
			fName=System.currentTimeMillis()+f.getName();//파일이름을 가져온다
			insertVideo(fName);
			//db에 insert 완료된 상태
			//file upload 구현
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
			System.out.println("파일선택");
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
				f.mkdirs();//폴더 만들기
				
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
			JOptionPane.showMessageDialog(null, "삭제완료");
			tfVideoNum.setText(null);
			tfVideoActor.setText(null);
			tfVideoDirector.setText(null);
			tfVideoTitle.setText(null);
			taVideoContent.setText(null);
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "삭제실패");
			e1.printStackTrace();
		}//수정작업 메소드 호출
		
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
			JOptionPane.showMessageDialog(null, "수정완료");
			tfVideoNum.setText(null);
			tfVideoActor.setText(null);
			tfVideoDirector.setText(null);
			tfVideoTitle.setText(null);
			taVideoContent.setText(null);
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "수정실패");
			e1.printStackTrace();
		}//수정작업 메소드 호출
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
		//선택된 레코드를 textfield에 뿌리기
		tfVideoNum.setText(String.valueOf(vo.getVicode()));
		tfVideoTitle.setText(vo.getVideoName());
		tfVideoDirector.setText(vo.getDirector());
		tfVideoActor.setText(vo.getActor());
		taVideoContent.setText(vo.getExp());
		comVideoJanre.setSelectedItem(vo.getGenre());
		
		//이미지 뿌리기 처리
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
		
		//이미지를 vo에 셋팅
		vo.setImgfname(fName);
		int count=Integer.parseInt(tfInsertCount.getText());
		
		try {
			model.insertVideo(vo,count);
			JOptionPane.showMessageDialog(null, "입고완료");
			
			//text창 내용지우기
			tfVideoNum.setText(null);
			tfVideoActor.setText(null);
			tfVideoDirector.setText(null);
			tfVideoTitle.setText(null);
			taVideoContent.setText(null);
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "입고실패");
			e1.printStackTrace();
		}
	}

}
