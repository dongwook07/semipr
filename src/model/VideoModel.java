package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.Video;

public class VideoModel {
	Connection con;
	
	public VideoModel() throws Exception {
	    con=DBcon.getConnection();
	}

	public void insertVideo(Video vo, int count) throws Exception {
		
		con.setAutoCommit(false);//자동커밋 해제
		
		//DB에 insert 시키기
		String sql1="insert into vinfo(vicode, title, genre, director, actor, detail,imgfname) "
		+"values(SEQ_VI_CODE.nextval,?,?,?,?,?,?)";
		
		String sql2="insert into video(vcode,vicode) "
				+" values (seq_v_code.nextval,seq_vi_code.currval)";
		
		PreparedStatement ps1=con.prepareStatement(sql1);
		ps1.setString(1, vo.getVideoName());
		ps1.setString(2, vo.getGenre());
		ps1.setString(3, vo.getDirector());
		ps1.setString(4, vo.getActor());
		ps1.setString(5, vo.getExp());
		ps1.setString(6, vo.getImgfname());
		
		
		PreparedStatement ps2=con.prepareStatement(sql2);
		
		
		int r1=ps1.executeUpdate();//sql1 실행
		int r2=ps2.executeUpdate();//sql2 실행
		
		if (r1!=1 || r2!=1) {//두개의 sql 문장중 하나라도 실패하면
			con.rollback();
			System.out.println("롤백");
		}
		System.out.println("입고");
		con.commit();
		ps1.close();
		ps2.close();
		con.setAutoCommit(true);//자동커밋 작동
		
	}

	public ArrayList searchVideo(int idx, String str) throws Exception {
		//검색
		String[] key= {"TITLE","DIRECTOR"};
		String sql="select vicode, title,genre,director,actor "
				+ " from vinfo "
				+ " where "+key[idx]+" like '%"+str+"%'";
		
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		ArrayList data=new ArrayList();
		
		while (rs.next()) {
			ArrayList temp=new ArrayList();
			temp.add(rs.getString("VICODE"));
			temp.add(rs.getString("TITLE"));
			temp.add(rs.getString("GENRE"));
			temp.add(rs.getString("DIRECTOR"));
			temp.add(rs.getString("ACTOR"));
			data.add(temp);//ArrayList에 ArrayList를 추가
		}
		rs.close();
		ps.close();
		
		
		return data;
	}

	public Video selectbyPk(int no) throws Exception {
		//Jtable에서 클릭한 레코드의 정보를 video 타입으로 저장해서 return하는 과정
		Video vo=new Video();
		String sql="select * from vinfo where vicode="+no;
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while (rs.next()) {
			vo.setVicode(Integer.parseInt(rs.getString("vicode")));
			vo.setActor(rs.getString("actor"));
			vo.setDirector(rs.getString("director"));
			vo.setGenre(rs.getString("genre"));
			vo.setVideoName(rs.getString("title"));
			vo.setExp(rs.getString("detail"));
			//이미지 세팅
			vo.setImgfname(rs.getString("imgfname"));
			
		}
		rs.close();
		ps.close();
		
		return vo;
	}

	public void modifyVideo(Video vo) throws Exception {
		//데이터 수정작업
		String sql="UPDATE vinfo set title=?, "
				+" genre=? ,"+
				" director=? ,"+
				" actor=? ,"+
				" detail=? "+
				 " where vicode=?";
		
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, vo.getVideoName());
		ps.setString(2, vo.getGenre());
		ps.setString(3, vo.getDirector());
		ps.setString(4, vo.getActor());
		ps.setString(5, vo.getExp());
		ps.setInt(6, vo.getVicode());
		
		ps.executeUpdate();
		ps.close();
	}

	public void deleteVideo(String vico) throws Exception {
		String sql1 = "delete from vinfo where vicode = ?";
		String sql2 = "delete from video where vicode = ?";
		PreparedStatement ps1=con.prepareStatement(sql1);
		PreparedStatement ps2=con.prepareStatement(sql2);
		
		ps1.setInt(1, Integer.parseInt(vico));
		ps2.setInt(1, Integer.parseInt(vico));
		
		int r1= ps1.executeUpdate();
		ps1.close();
		int r2= ps2.executeUpdate();
		ps2.close();
		
		if (r1!=1 || r2!=1) {
			System.out.println();
			con.rollback();
		}
		con.commit();
	}
	

}
