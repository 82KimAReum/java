
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JList;

public class WeatherDAO {
	public static int insert_Schedule(String sdate, String stime, String etime, String stitle, String scontent)
			throws SQLException {
		Connection conn = new DBConnection().getConnection();

		String sql_save = "INSERT INTO schedule VALUES (SCID_SEQ.nextval,?,?,?,?,?)";
		PreparedStatement pstmt1 = conn.prepareStatement(sql_save);

		pstmt1.setString(1, sdate);
		pstmt1.setString(2, stime);
		pstmt1.setString(3, etime);
		pstmt1.setString(4, stitle);
		pstmt1.setString(5, scontent);

		int row = pstmt1.executeUpdate();
		if (pstmt1 != null)
			pstmt1.close();
		DBClose.close(conn);

		return row;
	}

	public static JList[] select_Schedule(String sdate) throws SQLException {
		Connection conn = new DBConnection().getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql_load = "select * from schedule where sdate = '" + sdate + "' ORDER BY STime, ETime, SCID";
		rs = stmt.executeQuery(sql_load);

		Vector<String> vec1 = new Vector<>();
		Vector<Integer> vec2 = new Vector<>();
		while (rs.next()) {
			String str = rs.getString("Stime") + rs.getString("etime") + rs.getString("stitle");
			vec1.addElement(str);
			vec2.addElement(rs.getInt("ScID"));
		}

		JList[] list = new JList[2];
		list[0] = new JList(vec1);
		list[1] = new JList(vec2);

		return list;
	}

	public static ResultSet load_Schedule(int scid) throws SQLException {
		Connection conn = new DBConnection().getConnection();

		String sql_load = "SELECT * FROM schedule WHERE ScID LIKE ?";
		PreparedStatement pstmt = conn.prepareStatement(sql_load);

		pstmt.setInt(1, scid);

		ResultSet rs = pstmt.executeQuery();

		return rs;
	}

	public static int delete_Schedule(int scid) throws SQLException {

		Connection conn = new DBConnection().getConnection();
		String sql_delete = "DELETE FROM schedule WHERE scid = ?";
		PreparedStatement pstmt1 = conn.prepareStatement(sql_delete);
		pstmt1.setInt(1, scid);

		int row = pstmt1.executeUpdate();
		if (pstmt1 != null)
			pstmt1.close();
		DBClose.close(conn);

		return row;

	}

	public static int update_Schedule(String sdate, String stime, String etime, String stitle, String scontent,
			int scid) throws SQLException {
		Connection conn = new DBConnection().getConnection();
		String sql_update = "UPDATE Schedule SET sdate = ? , stime = ?, etime = ?, stitle = ? , scontent = ?  WHERE scid = ?";
		PreparedStatement pstmt1 = conn.prepareStatement(sql_update);

		pstmt1.setString(1, sdate);
		pstmt1.setString(2, stime);
		pstmt1.setString(3, etime);
		pstmt1.setString(4, stitle);
		pstmt1.setString(5, scontent);
		pstmt1.setInt(6, scid);

		int row = pstmt1.executeUpdate();
		if (pstmt1 != null)
			pstmt1.close();
		DBClose.close(conn);

		return row;
	}

	public static ResultSet check_Schedule(String date) throws SQLException {
		Connection conn = new DBConnection().getConnection();

		String sql = "SELECT DISTINCT SDate FROM Schedule WHERE SDate LIKE ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, date);

		ResultSet rs = pstmt.executeQuery();

		return rs;
	}

	public static int refreshWeather(XML x) throws SQLException {
		Connection conn = new DBConnection().getConnection();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
		String date = sdf.format(new Date());
		String time = sdf1.format(new Date());

		if (check() == 0) // 오늘 날씨가 없을 경우 INSERT
		{
			String sql1 = "INSERT INTO PWeather VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);

			pstmt1.setString(1, date);
			pstmt1.setString(2, time);
			pstmt1.setInt(3, x.LocID);
			pstmt1.setString(4, x.d0_wfKor);
			pstmt1.setDouble(5, Double.parseDouble(x.d0_temp));
			pstmt1.setString(6, x.d0_wdKor + " " + x.d0_ws + "m/s");
			pstmt1.setString(7, x.d1_wf);
			pstmt1.setDouble(8, Double.parseDouble(x.d1_tmx));
			pstmt1.setDouble(9, Double.parseDouble(x.d1_tmn));
			pstmt1.setString(10, x.d2_wf);
			pstmt1.setDouble(11, Double.parseDouble(x.d2_tmx));
			pstmt1.setDouble(12, Double.parseDouble(x.d2_tmn));

			String sql2 = "INSERT INTO Weather VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);

			pstmt2.setString(1, date);
			pstmt2.setString(2, x.d3_wf);
			pstmt2.setDouble(3, Double.parseDouble(x.d3_tmx));
			pstmt2.setDouble(4, Double.parseDouble(x.d3_tmn));
			pstmt2.setString(5, x.d4_wf);
			pstmt2.setDouble(6, Double.parseDouble(x.d4_tmx));
			pstmt2.setDouble(7, Double.parseDouble(x.d4_tmn));
			pstmt2.setString(8, x.d5_wf);
			pstmt2.setDouble(9, Double.parseDouble(x.d5_tmx));
			pstmt2.setDouble(10, Double.parseDouble(x.d5_tmn));
			pstmt2.setString(11, x.d6_wf);
			pstmt2.setDouble(12, Double.parseDouble(x.d6_tmx));
			pstmt2.setDouble(13, Double.parseDouble(x.d6_tmn));
			pstmt2.setString(14, x.d7_wf);
			pstmt2.setDouble(15, Double.parseDouble(x.d7_tmx));
			pstmt2.setDouble(16, Double.parseDouble(x.d7_tmn));

			int row1 = pstmt1.executeUpdate();
			int row2 = pstmt2.executeUpdate();

			int result = 0;
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();

			if (row1 == 1 && row2 == 1)
				result = 1;
			DBClose.close(conn);
			return result;
		} else // 이미 오늘 날씨정보가 있을 경우 UPDATE
		{
			String sql1 = "UPDATE PWeather SET PTime = ?, LocID = ?, Pres = ?, PTemp = ?, Wind = ?, D1 = ?, D1MAXT = ? , D1MINT = ?, "
					+ "D2 = ?, D2MAXT = ?, D2MINT = ? WHERE PDate = ?";
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);

			pstmt1.setString(1, time);
			pstmt1.setInt(2, x.LocID);
			pstmt1.setString(3, x.d0_wfKor);
			pstmt1.setDouble(4, Double.parseDouble(x.d0_temp));
			pstmt1.setString(5, x.d0_wdKor + " " + x.d0_ws + "m/s");
			pstmt1.setString(6, x.d1_wf);
			pstmt1.setDouble(7, Double.parseDouble(x.d1_tmx));
			pstmt1.setDouble(8, Double.parseDouble(x.d1_tmn));
			pstmt1.setString(9, x.d2_wf);
			pstmt1.setDouble(10, Double.parseDouble(x.d2_tmx));
			pstmt1.setDouble(11, Double.parseDouble(x.d2_tmn));
			pstmt1.setString(12, date);

			String sql2 = "UPDATE Weather SET D3 = ?, D3MAXT = ?, D3MINT = ?, D4 = ?, D4MAXT = ?, D4MINT = ?, "
					+ "D5 = ?, D5MAXT = ?, D5MINT = ?, D6 = ?, D6MAXT = ?, D6MINT =?, D7 = ?, D7MAXT = ?, D7MINT = ? "
					+ "WHERE WDate = ?";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);

			pstmt2.setString(1, x.d3_wf);
			pstmt2.setDouble(2, Double.parseDouble(x.d3_tmx));
			pstmt2.setDouble(3, Double.parseDouble(x.d3_tmn));
			pstmt2.setString(4, x.d4_wf);
			pstmt2.setDouble(5, Double.parseDouble(x.d4_tmx));
			pstmt2.setDouble(6, Double.parseDouble(x.d4_tmn));
			pstmt2.setString(7, x.d5_wf);
			pstmt2.setDouble(8, Double.parseDouble(x.d5_tmx));
			pstmt2.setDouble(9, Double.parseDouble(x.d5_tmn));
			pstmt2.setString(10, x.d6_wf);
			pstmt2.setDouble(11, Double.parseDouble(x.d6_tmx));
			pstmt2.setDouble(12, Double.parseDouble(x.d6_tmn));
			pstmt2.setString(13, x.d7_wf);
			pstmt2.setDouble(14, Double.parseDouble(x.d7_tmx));
			pstmt2.setDouble(15, Double.parseDouble(x.d7_tmn));
			pstmt2.setString(16, date);

			int row1 = pstmt1.executeUpdate();
			int row2 = pstmt2.executeUpdate();

			int result = 0;
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();

			if (row1 == 1 && row2 == 1)
				result = 1;
			DBClose.close(conn);
			return result;
		}
	}

	public static int check() throws SQLException {
		int check = 0;
		Connection conn = new DBConnection().getConnection();

		String sql = "SELECT PDate FROM PWeather WHERE PDate = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());

		pstmt.setString(1, date);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next())
			check = 1;

		if (pstmt != null)
			pstmt.close();
		DBClose.close(conn);
		return check;
	}

	public static ResultSet selectWeather(int LocID) throws SQLException {
		Connection conn = new DBConnection().getConnection();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());

		String sql = "SELECT PTime, Pres, PTemp, Wind, D1, D1MAXT, D1MINT, D2, D2MAXT, D2MINT, D3, D3MAXT, D3MINT, "
				+ "D4, D4MAXT, D4MINT, D5, D5MAXT, D5MINT, D6, D6MAXT, D6MINT, D7, D7MAXT, D7MINT "
				+ "FROM BOT_DIS WHERE PDate = ? AND LocID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, date);
		pstmt.setInt(2, LocID);

		ResultSet rs = pstmt.executeQuery();

		return rs;
	}
}
