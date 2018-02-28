
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Dialog extends JDialog implements ItemListener {
	Calendar calendar = Calendar.getInstance();
	private final JPanel contentPanel = new JPanel();

	JComboBox time_start;// 시작시간 선택 콤보 박스
	JComboBox time_end;// 끝시간 선택 콤보박스
	JComboBox year_box;// 년 선택
	JComboBox month_box;// 웘선택
	JComboBox day_box;// 일 선택
	JTextArea textArea;// 내용 텍스트 에리어
	JTextPane title;// 제목 택스트 필드
	JLabel day_of_week;// 요일
	Calendar c;
	final String[] week = { "일", "월", "화", "수", "목", "금", "토" };

	public Dialog(boolean check, int getyear, int getmonth, int getday, int scid, UI ui) {
		Border border = BorderFactory.createLineBorder(Color.black, 1);

		setBounds(1105, 200, 379, 318);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		// *************
		JLabel label = new JLabel("년");
		label.setBounds(141, 13, 57, 15);

		contentPanel.add(label);
		// *************
		JLabel label_1 = new JLabel("월");
		label_1.setBounds(224, 13, 57, 15);
		contentPanel.add(label_1);
		// *************
		JLabel label_2 = new JLabel("제목");
		label_2.setBounds(12, 72, 57, 15);
		contentPanel.add(label_2);
		// *************
		JLabel lblNewLabel = new JLabel("내용");
		lblNewLabel.setBounds(12, 111, 57, 15);
		contentPanel.add(lblNewLabel);
		String str = null;
		JLabel lblNewLabel_1 = new JLabel("~");
		lblNewLabel_1.setBounds(202, 47, 57, 15);
		contentPanel.add(lblNewLabel_1);
		// *************
		JLabel label_3 = new JLabel("일");
		label_3.setBounds(305, 13, 57, 15);
		contentPanel.add(label_3);
		// *************

		/////////////////////////////////////////////////////////////
		title = new JTextPane();// 제목 텍스트 필드
		title.setBounds(63, 72, 288, 29);
		title.setBorder(border);
		contentPanel.add(title);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(63, 107, 288, 118);
		contentPanel.add(scrollPane);
		// *************
		textArea = new JTextArea();// 내용 텍스트 에리어
		scrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setRows(5);
		textArea.setBorder(border);

		// *************

		time_start = new JComboBox();// 시작 시간 선택 콤보박스
		time_start.setBounds(63, 41, 127, 21);
		for (int i = 0; i < 24; i++) {
			if (i < 10)
				str = "0" + i;
			else
				str = Integer.toString(i);
			time_start.addItem(str + ":00 ");
		}
		time_start.addItemListener(this);

		contentPanel.add(time_start);
		// *************
		time_end = new JComboBox();// 끝시간 선택 콤보 박스
		time_end.setBounds(224, 41, 127, 21);
		for (int i = 1; i <= 24; i++) {
			if (i < 10)
				str = "0" + i;
			else
				str = Integer.toString(i);
			time_end.addItem(str + ":00 ");
		}
		contentPanel.add(time_end);
		// *************

		year_box = new JComboBox();// 년 선택

		year_box.setBounds(63, 10, 74, 21);
		int year = calendar.get(Calendar.YEAR);

		for (int i = year - 50; i < year + 50; i++) {
			year_box.addItem(i);
		}
		year_box.setSelectedIndex(getyear - 1968);
		contentPanel.add(year_box);
		year_box.addItemListener(this);
		// *************

		month_box = new JComboBox();// 월

		month_box.setBounds(168, 10, 51, 21);
		// int month = calendar.get(Calendar.MONTH);

		for (int i = 1; i <= 12; i++) {
			month_box.addItem(i);
		}
		month_box.setSelectedIndex(getmonth);
		month_box.addItemListener(this);
		contentPanel.add(month_box);

		// *************

		day_box = new JComboBox();// 일 //현재 달의 일 범위만 들어가 있으므로 년, 월이 바뀌었을 때 day_box 내부 날짜들 범위 바꿔줄 것

		// int day = calendar.get(Calendar.DATE);// 오늘 날짜
		int month_day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);// 달의 마지막 날
		for (int i = 1; i <= month_day; i++) {
			day_box.addItem(i);
		}
		day_box.setSelectedIndex(getday - 1);
		day_box.setBounds(248, 10, 51, 21);
		day_box.addItemListener(this);
		contentPanel.add(day_box);

		// *************

		JButton del = new JButton("삭제");
		del.setEnabled(check);// 일정이 있는 경우 true로
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = 0;
				int yn = JOptionPane.showConfirmDialog(del, "정말 삭제하시겠습니까?", "삭제 확인창", JOptionPane.YES_NO_OPTION);
				if(yn == 0) {
					try {
						row = WeatherDAO.delete_Schedule(scid);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					switch (row) {
					case 0:
						JOptionPane.showMessageDialog(del, "삭제실패!", "실패 알림창", JOptionPane.WARNING_MESSAGE);
						break;
					case 1:
						JOptionPane.showMessageDialog(del, "삭제성공!", "성공 알림창", JOptionPane.PLAIN_MESSAGE);
						ui.refresh();
						ui.listrefresh();
						break;
					}
					dispose();
				}
			}
		});
		del.setBounds(12, 247, 97, 23);
		contentPanel.add(del);
		del.setVisible(check);
		// *************
		JButton save = new JButton("저장");// 저장버튼
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sdate = year_box.getSelectedItem() + "-" + month_box.getSelectedItem() + "-"
						+ day_box.getSelectedItem();
				String stime = time_start.getSelectedItem() + "";
				String etime = time_end.getSelectedItem() + "";
				String stitle = title.getText();
				String scontent = textArea.getText();
				int row = 0;
				if (check == false) {
					try {
						row = WeatherDAO.insert_Schedule(sdate, stime, etime, stitle, scontent);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					switch (row) {
					case 0:
						JOptionPane.showMessageDialog(save, "저장실패!", "오류", JOptionPane.WARNING_MESSAGE);
						break;
					case 1:
						JOptionPane.showMessageDialog(save, "저장성공!", "성공", JOptionPane.PLAIN_MESSAGE);
						ui.refresh();
						ui.listrefresh();
						break;
					}
					dispose();
				}

				else if (check == true) {
					int yn = JOptionPane.showConfirmDialog(save, "정말 수정하시겠습니까?", "수정 확인창", JOptionPane.YES_NO_OPTION);
					if(yn == 0) {
						try {
							row = WeatherDAO.update_Schedule(sdate, stime, etime, stitle, scontent, scid);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						switch (row) {
						case 0:
							JOptionPane.showMessageDialog(save, "수정실패!", "오류", JOptionPane.WARNING_MESSAGE);
							break;
						case 1:
							JOptionPane.showMessageDialog(save, "수정성공!", "성공", JOptionPane.PLAIN_MESSAGE);
							ui.refresh();
							ui.listrefresh();
							break;
						}
						dispose();
					}
				}
				
			}
		});
		save.setBounds(254, 247, 97, 23);
		contentPanel.add(save);

		c = Calendar.getInstance();
		c.set(getyear, getmonth, getday);
		day_of_week = new JLabel(("(" + week[c.get(Calendar.DAY_OF_WEEK) - 1] + ")"));// 요일
		day_of_week.setBounds(327, 13, 57, 15);
		contentPanel.add(day_of_week);
		// *************

		if (check) {
			String stime = null;
			String etime = null;
			String stitle = null;
			String scontent = null;
			try {
				ResultSet rs = WeatherDAO.load_Schedule(scid);
				rs.next();
				stime = rs.getString("stime");
				etime = rs.getString("etime");
				stitle = rs.getString("stitle");
				scontent = rs.getString("scontent");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			time_start.setSelectedIndex(Integer.parseInt(stime.substring(0, 2)));
			time_end.setSelectedIndex(
					Integer.parseInt(etime.substring(0, 2)) - 1 - Integer.parseInt(stime.substring(0, 2)));
			title.setText(stitle);
			textArea.setText(scontent);

		}
	}

	@Override // 년,월 변경 시 일 변경
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == year_box || e.getSource() == month_box) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				calendar.set((int) year_box.getSelectedItem(), (int) month_box.getSelectedItem() - 1,
						calendar.get(Calendar.DATE));
				day_box.removeAllItems();
				for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
					day_box.addItem(i);
				}
			}
		}

		if (e.getSource() == day_box) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				c.set((int) year_box.getSelectedItem(), month_box.getSelectedIndex(), (int) day_box.getSelectedItem());
				day_of_week.setText(("(" + week[c.get(Calendar.DAY_OF_WEEK) - 1] + ")"));
			}
		}

		if (e.getSource() == time_start) {
			time_end.removeAllItems();
			String str = null;
			int i = time_start.getSelectedIndex();
			for (i = i + 1; i <= 24; i++) {
				if (i < 10)
					str = "0" + i;
				else
					str = Integer.toString(i);
				time_end.addItem(str + ":00 ");
			}
		}
	}
}
