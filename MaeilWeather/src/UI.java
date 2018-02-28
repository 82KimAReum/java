
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class UI extends JFrame implements ItemListener {
	private JLabel lblYyyymmdd;//// 일정 panel의 yyyy-mm-dd
	private JList[] list;// 일정 리스트
	private ImageIcon im0;// 날씨 아이콘
	private ImageIcon im1;
	private ImageIcon im2;
	private ImageIcon im3;
	private ImageIcon im4;
	private ImageIcon im5;
	private ImageIcon im6;

	private JLabel temperature;// 현재 온도
	private ImageIcon gurim;// 매일웨더 글씨 그림
	private Image gurimIm;// gurim크기 수정 이미지
	private JPanel contentPane;// 기본 팬
	private JTable dayViewTable;// 달력의 일 표시
	private JLabel label;// 달력위 년 월 표시
	private JButton leftButton;// < 버튼
	private JButton rightButton;// > 버튼
	private JButton left2Button;// <<버튼
	private JButton right2Button;// >>버튼
	private JTable weekBarTable;// 달력의 요일표시
	private JButton todayButton;// 오늘로 복귀하는 버튼
	private JButton addButton;// 일정 추가 버튼
	private JButton moButton;// 일정 추가 버튼
	private JButton btnNewButton;// renew 버튼
	private JComboBox comboBox;// 지역 선택 콤보박스
	private boolean focus = false;
	private Font font = new Font("맑은 고딕", Font.PLAIN, 14);
	Calendar calendar = Calendar.getInstance();

	DefaultTableModel weekTable = new DefaultTableModel(0, 7);// 요일
	DefaultTableModel dayTable = new DefaultTableModel(6, 7);// 일

	int todayY;// 년
	int todayM;// 월
	int todayD;// 일
	private JPanel panel;// 일정 팬
	private JPanel panel_1;// 날씨 팬
	private JLabel now_weather;// 최고기온
	private JLabel wind_speed;// 최저기온
	private JLabel label_5;// 업데이트 시간
	private JLabel lblNewLabel_4;
	private JLabel day_2;
	private JLabel day_3;
	private JLabel day_4;
	private JLabel day_5;
	private JLabel day_6;
	private JLabel day_7;
	private JScrollPane scrollPane;
	private JSeparator separator;
	private JLabel day_1w;
	private JLabel day_1t;
	private JLabel day_2w;
	private JLabel day_2t;
	private JLabel day_3w;
	private JLabel day_3t;
	private JLabel day_4w;
	private JLabel day_4t;
	private JLabel day_5w;
	private JLabel day_5t;
	private JLabel day_6w;
	private JLabel day_6t;
	private JLabel day_7w;
	private JLabel day_7t;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JSeparator separator_6;
	private String PTime;
	private String Pres;
	private Double PTemp;
	private String Wind;
	private String D1;
	private Double D1MAXT;
	private Double D1MINT;
	private String D2;
	private Double D2MAXT;
	private Double D2MINT;
	private String D3;
	private Double D3MAXT;
	private Double D3MINT;
	private String D4;
	private Double D4MAXT;
	private Double D4MINT;
	private String D5;
	private Double D5MAXT;
	private Double D5MINT;
	private String D6;
	private Double D6MAXT;
	private Double D6MINT;
	private String D7;
	private Double D7MAXT;
	private Double D7MINT;

	/**
	 * Create the frame.
	 */
	public UI() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("구름아이콘.png");
		this.setIconImage(img);

		im0 = new ImageIcon("sunny.png");// 날씨 아이콘들 생성
		im1 = new ImageIcon("cloudy.png"); // 다른 아이콘으로 수정할 것
		im2 = new ImageIcon("mcloudy.png");
		im3 = new ImageIcon("overcast.png");
		im4 = new ImageIcon("rainy.png");
		im5 = new ImageIcon("rainsnow.png");
		im6 = new ImageIcon("snowy.png");

		gurim = new ImageIcon("그림1.png"); // 매일워더 우 상단 글씨
		gurimIm = gurim.getImage().getScaledInstance(190, 60, Image.SCALE_SMOOTH);
		gurim = new ImageIcon(gurimIm);

		setResizable(false);
		setFont(new Font("굴림", Font.PLAIN, 12));
		setTitle("매일웨더");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(300, 200, 801, 525);

		initial_ContentPane();// 컨텐트팬 초기화

		// 요일 표시 테이블
		String[] weekColumns = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		weekTable.addRow(weekColumns);

		// getRenderer();
		initial_panel_2();//// 달력 및 주간 날씨
		initial_Label();// 위에 년 월 표시
		initial_leftButton();// <버튼
		initial_left2Button();// <<버튼
		initial_rightButton();// > 버튼
		initial_right2Button();// >>버튼
		initial_todayButton();// 오늘 버튼
		initial_weekBarTable();// 달력위에 요일
		initial_dayViewTable();// 달력 날짜
		initial_panel_1(); // 날씨 panel초기화

		todayY = calendar.get(Calendar.YEAR);
		todayM = calendar.get(Calendar.MONTH);
		todayD = calendar.get(Calendar.DATE);

		// 실행시 날짜 뿌리면서 오늘로 포커싱
		refresh();
		todayFocus();
		initial_panel();// 일정 panel초기화
		selectWeather();// 처음 날씨 출력

		this.setVisible(true);
	}

	private void initial_panel_2() {
		JLabel icon = new JLabel(gurim);
		icon.setBounds(618, 17, 165, 60);
		contentPane.add(icon);
	}

	private void initial_panel_1() {
		// 날씨 panel
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(524, 269, 261, 198);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		comboBox = new JComboBox();// 지역선택 콤보박스
		comboBox.setBackground(SystemColor.control);
		comboBox.setFont(font);
		comboBox.setBounds(0, 0, 62, 21);
		comboBox.addItem("서울");
		comboBox.addItem("부산");
		comboBox.addItem("대전");
		comboBox.addItem("대구");
		comboBox.addItem("울산");
		comboBox.addItem("광주");
		comboBox.addItem("인천");
		comboBox.addItem("제주");
		panel_1.add(comboBox);
		comboBox.addItemListener(this);

		now_weather = new JLabel("맑음");
		now_weather.setHorizontalAlignment(SwingConstants.CENTER);
		now_weather.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		now_weather.setBounds(10, 125, 87, 21);
		panel_1.add(now_weather);

		wind_speed = new JLabel("남서 5/s");
		wind_speed.setHorizontalAlignment(SwingConstants.CENTER);
		wind_speed.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		wind_speed.setBounds(140, 125, 109, 21);
		panel_1.add(wind_speed);

		label_5 = new JLabel("업데이트 시간");
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_5.setBounds(12, 176, 140, 21);
		panel_1.add(label_5);

		// now의 날씨 표시
		lblNewLabel_4 = new JLabel();
		lblNewLabel_4.setBounds(10, 31, 92, 92);
		lblNewLabel_4.setIcon(im0);// 해당 시간의 날씨에따라 바꿀 것
		panel_1.add(lblNewLabel_4);

		btnNewButton = new JButton("refresh");// 날씨 갱신
		btnNewButton.setBounds(175, 175, 85, 23);
		btnNewButton.setBackground(SystemColor.control);
		btnNewButton.setFont(font);
		btnNewButton.setOpaque(false);
		btnNewButton.setFocusable(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.addActionListener(new ActionListener() { // refresh 버튼 클릭 시 선택된 지역으로 업데이트 후 DB에 저장
			public void actionPerformed(ActionEvent e) {
				try {
					WeatherDAO.refreshWeather(new XML(comboBox.getSelectedIndex() + 1));
					selectWeather();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(btnNewButton);

		temperature = new JLabel("8", SwingConstants.CENTER);// 현재 온도
		temperature.setFont(new Font("맑은 고딕", Font.PLAIN, 50));
		temperature.setBounds(120, 35, 135, 83);
		panel_1.add(temperature);

		JLabel day_1 = new JLabel("내일");
		day_1.setHorizontalAlignment(SwingConstants.CENTER);
		day_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		day_1.setBounds(17, 408, 57, 15);
		contentPane.add(day_1);

		day_2 = new JLabel("2일 후 ");
		day_2.setHorizontalAlignment(SwingConstants.CENTER);
		day_2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		day_2.setBounds(92, 408, 57, 15);
		contentPane.add(day_2);

		day_3 = new JLabel("3일 후");
		day_3.setHorizontalAlignment(SwingConstants.CENTER);
		day_3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		day_3.setBounds(162, 408, 57, 15);
		contentPane.add(day_3);

		day_4 = new JLabel("4일 후");
		day_4.setHorizontalAlignment(SwingConstants.CENTER);
		day_4.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		day_4.setBounds(234, 408, 57, 15);
		contentPane.add(day_4);

		day_5 = new JLabel("5일 후");
		day_5.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		day_5.setBounds(314, 408, 57, 15);
		contentPane.add(day_5);

		day_6 = new JLabel("6일 후 ");
		day_6.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		day_6.setBounds(387, 408, 57, 15);
		contentPane.add(day_6);

		day_7 = new JLabel("7일 후");
		day_7.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		day_7.setBounds(458, 408, 57, 15);
		contentPane.add(day_7);

		separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setForeground(Color.GRAY);
		separator.setBounds(12, 428, 502, 8);
		contentPane.add(separator);

		day_1w = new JLabel("맑음");// 내일 날씨
		day_1w.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_1w.setHorizontalAlignment(SwingConstants.CENTER);
		day_1w.setBounds(17, 440, 57, 15);
		contentPane.add(day_1w);

		day_1t = new JLabel("-1/8");// 내일 온도
		day_1t.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_1t.setHorizontalAlignment(SwingConstants.CENTER);
		day_1t.setBounds(17, 458, 57, 15);
		contentPane.add(day_1t);

		day_2w = new JLabel("맑음");// 2일 후 날씨
		day_2w.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_2w.setHorizontalAlignment(SwingConstants.CENTER);
		day_2w.setBounds(92, 440, 57, 15);
		contentPane.add(day_2w);

		day_2t = new JLabel("-1/8");// 2일 후 온도
		day_2t.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_2t.setHorizontalAlignment(SwingConstants.CENTER);
		day_2t.setBounds(92, 458, 57, 15);
		contentPane.add(day_2t);

		day_3w = new JLabel("맑음");// 3일 후 날씨
		day_3w.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_3w.setHorizontalAlignment(SwingConstants.CENTER);
		day_3w.setBounds(162, 440, 57, 15);
		contentPane.add(day_3w);

		day_3t = new JLabel("-1/8");// 3일 후 온도
		day_3t.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_3t.setHorizontalAlignment(SwingConstants.CENTER);
		day_3t.setBounds(162, 457, 57, 15);
		contentPane.add(day_3t);

		day_4w = new JLabel("맑음");// 4일 후 날씨
		day_4w.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_4w.setHorizontalAlignment(SwingConstants.CENTER);
		day_4w.setBounds(234, 440, 57, 15);
		contentPane.add(day_4w);

		day_4t = new JLabel("-1/8");// 4일 후 온도
		day_4t.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_4t.setHorizontalAlignment(SwingConstants.CENTER);
		day_4t.setBounds(234, 457, 57, 15);
		contentPane.add(day_4t);

		day_5w = new JLabel("맑음");// 5일 후 날씨
		day_5w.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_5w.setHorizontalAlignment(SwingConstants.CENTER);
		day_5w.setBounds(305, 440, 57, 15);
		contentPane.add(day_5w);

		day_5t = new JLabel("-1/8");//// 5일 후 온도
		day_5t.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_5t.setHorizontalAlignment(SwingConstants.CENTER);
		day_5t.setBounds(305, 457, 57, 15);
		contentPane.add(day_5t);

		day_6w = new JLabel("맑음");// 6일 후 날씨
		day_6w.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_6w.setHorizontalAlignment(SwingConstants.CENTER);
		day_6w.setBounds(376, 440, 57, 15);
		contentPane.add(day_6w);

		day_6t = new JLabel("-1/8");// 6일 후 온도
		day_6t.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_6t.setHorizontalAlignment(SwingConstants.CENTER);
		day_6t.setBounds(376, 458, 57, 15);
		contentPane.add(day_6t);

		day_7w = new JLabel("맑음");// 7일 후 날씨
		day_7w.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_7w.setHorizontalAlignment(SwingConstants.CENTER);
		day_7w.setBounds(447, 440, 57, 15);
		contentPane.add(day_7w);

		day_7t = new JLabel("-1/8");// 7일 후 온도
		day_7t.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		day_7t.setHorizontalAlignment(SwingConstants.CENTER);
		day_7t.setBounds(447, 457, 57, 15);
		contentPane.add(day_7t);

		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(Color.GRAY);
		separator_1.setBackground(Color.BLACK);
		separator_1.setBounds(82, 408, 1, 79);
		contentPane.add(separator_1);

		separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setForeground(Color.GRAY);
		separator_2.setBackground(Color.BLACK);
		separator_2.setBounds(154, 408, 1, 79);
		contentPane.add(separator_2);

		separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setForeground(Color.GRAY);
		separator_3.setBackground(Color.BLACK);
		separator_3.setBounds(226, 408, 1, 79);
		contentPane.add(separator_3);

		separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setForeground(Color.GRAY);
		separator_4.setBackground(Color.BLACK);
		separator_4.setBounds(297, 408, 1, 79);
		contentPane.add(separator_4);

		separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setForeground(Color.GRAY);
		separator_5.setBackground(Color.BLACK);
		separator_5.setBounds(368, 408, 1, 79);
		contentPane.add(separator_5);

		separator_6 = new JSeparator();
		separator_6.setOrientation(SwingConstants.VERTICAL);
		separator_6.setForeground(Color.GRAY);
		separator_6.setBackground(Color.BLACK);
		separator_6.setBounds(440, 408, 1, 79);
		contentPane.add(separator_6);

	}

	private void initial_panel() {
		// 일정 panel
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(524, 62, 261, 198);
		contentPane.add(panel);
		panel.setLayout(null);

		lblYyyymmdd = new JLabel(getFocusedDate(todayY, todayM, todayD));
		lblYyyymmdd.setBounds(12, 0, 85, 14);
		panel.add(lblYyyymmdd);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 21, 261, 144);
		panel.add(scrollPane);

		list = new JList[2];
		try {
			list = WeatherDAO.select_Schedule(lblYyyymmdd.getText());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		scrollPane.setViewportView(list[0]);

		addButton = new JButton("일정추가");
		addButton.setBounds(160, 175, 101, 23);
		panel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_addButton_actionPerformed(e);
			}
		});
		addButton.setBackground(SystemColor.control);
		addButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		addButton.setOpaque(false);
		addButton.setFocusable(false);

		moButton = new JButton("조회/수정");
		moButton.setBounds(0, 175, 101, 23);
		panel.add(moButton);
		moButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list[0].getSelectedValue() != null)
					do_addButton_actionPerformed(e);
			}
		});
		moButton.setBackground(SystemColor.control);
		moButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		moButton.setOpaque(false);
		moButton.setFocusable(false);
	}

	private void initial_dayViewTable() {
		// 날짜 표시 테이블
		dayViewTable = new JTable(dayTable) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};

		};
		dayViewTable.setCellSelectionEnabled(true);// 셀선택
		dayViewTable.setColumnSelectionAllowed(true);// 열선택
		dayViewTable.setBackground(null);
		dayViewTable.addMouseListener(new MouseAdapter() {
			// 마우스 클릭 될 때 마다 일정 창의 날짜 변경
			@Override
			public void mouseClicked(MouseEvent e) {
				String day = dayViewTable.getValueAt(dayViewTable.getSelectedRow(), dayViewTable.getSelectedColumn())
						.toString().trim();
				if (day.length() > 2) {
					lblYyyymmdd.setText(getFocusedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
							Integer.parseInt(day.substring(0, 2).trim())));
				} else if (day.length() > 0)
					lblYyyymmdd.setText(getFocusedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
							Integer.parseInt(day.trim())));

				try {
					list = WeatherDAO.select_Schedule(lblYyyymmdd.getText());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				scrollPane.setViewportView(list[0]);
			}
		});
		dayViewTable.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		dayViewTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		dayViewTable.setRowHeight(49);
		dayViewTable.setFillsViewportHeight(true);
		dayViewTable.setBounds(12, 107, 500, 291);
		dayViewTable.getColumnModel().getColumn(0).setCellRenderer(getRenderer("top"));
		dayViewTable.getColumnModel().getColumn(1).setCellRenderer(getRenderer("top"));
		dayViewTable.getColumnModel().getColumn(2).setCellRenderer(getRenderer("top"));
		dayViewTable.getColumnModel().getColumn(3).setCellRenderer(getRenderer("top"));
		dayViewTable.getColumnModel().getColumn(4).setCellRenderer(getRenderer("top"));
		dayViewTable.getColumnModel().getColumn(5).setCellRenderer(getRenderer("top"));
		dayViewTable.getColumnModel().getColumn(6).setCellRenderer(getRenderer("top"));
		dayViewTable.getColumnModel().getColumn(0).setCellRenderer(getRenderer("sunDateRed"));
		dayViewTable.getColumnModel().getColumn(6).setCellRenderer(getRenderer("satDateBlue"));
		contentPane.add(dayViewTable);
	}

	private void initial_weekBarTable() {
		// 달력 위에 요일 테이블
		weekBarTable = new JTable(weekTable) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};

		};

		weekBarTable.setEnabled(false);
		weekBarTable.setRowSelectionAllowed(false);
		weekBarTable.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		weekBarTable.setBackground(new Color(220, 220, 220));
		weekBarTable.setRowHeight(25);
		weekBarTable.setFillsViewportHeight(true);
		weekBarTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		weekBarTable.setBounds(12, 85, 500, 23);

		weekBarTable.getColumnModel().getColumn(1).setCellRenderer(getRenderer("center"));
		weekBarTable.getColumnModel().getColumn(2).setCellRenderer(getRenderer("center"));
		weekBarTable.getColumnModel().getColumn(3).setCellRenderer(getRenderer("center"));
		weekBarTable.getColumnModel().getColumn(4).setCellRenderer(getRenderer("center"));
		weekBarTable.getColumnModel().getColumn(5).setCellRenderer(getRenderer("center"));
		weekBarTable.getColumnModel().getColumn(0).setCellRenderer(getRenderer("sunRed"));
		weekBarTable.getColumnModel().getColumn(6).setCellRenderer(getRenderer("satBlue"));
		contentPane.add(weekBarTable);
	}

	private void initial_todayButton() {
		// 오늘 버튼
		todayButton = new JButton("오늘");
		todayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_todayButton_actionPerformed(e);
			}
		});
		todayButton.setBackground(SystemColor.control);
		todayButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		todayButton.setBounds(433, 62, 79, 23);
		todayButton.setOpaque(false);
		todayButton.setBorderPainted(false);
		todayButton.setFocusable(false);
		contentPane.add(todayButton);
	}

	private void initial_right2Button() {
		// >>버튼
		right2Button = new JButton(">>");
		right2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_right2Button_actionPerformed(e);
			}
		});
		right2Button.setOpaque(false);
		right2Button.setFont(new Font("굴림", Font.PLAIN, 16));
		right2Button.setFocusable(false);
		right2Button.setContentAreaFilled(false);
		right2Button.setBorderPainted(false);
		right2Button.setBounds(443, 17, 69, 46);
		contentPane.add(right2Button);
	}

	private void initial_rightButton() {
		// >버튼
		rightButton = new JButton(">");
		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_rightButton_actionPerformed(e);
			}
		});
		rightButton.setFont(new Font("굴림", Font.PLAIN, 16));
		rightButton.setBounds(391, 17, 62, 46);
		rightButton.setOpaque(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setBorderPainted(false);
		rightButton.setFocusable(false);
		contentPane.add(rightButton);
	}

	private void initial_left2Button() {
		// <<버튼
		left2Button = new JButton("<<");
		left2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_left2Button_actionPerformed(e);
			}
		});
		left2Button.setOpaque(false);
		left2Button.setFont(new Font("굴림", Font.PLAIN, 16));
		left2Button.setFocusable(false);
		left2Button.setContentAreaFilled(false);
		left2Button.setBorderPainted(false);
		left2Button.setBounds(12, 17, 62, 46);
		contentPane.add(left2Button);
	}

	private void initial_leftButton() {
		// <버튼
		leftButton = new JButton("<");
		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				do_leftButton_actionPerformed(e);
			}
		});

		leftButton.setOpaque(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setBorderPainted(false);
		leftButton.setFocusable(false);
		leftButton.setFont(new Font("굴림", Font.PLAIN, 16));
		leftButton.setBounds(70, 17, 62, 46);
		contentPane.add(leftButton);
	}

	private void initial_Label() {
		// 달력 위에 년 월
		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setBounds(70, 10, 384, 54);
		contentPane.add(label);

	}

	private DefaultTableCellRenderer getRenderer(String str) {
		// 랜더러
		switch (str) {
		case "center":
			// 가운데 정렬 renderer
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(JLabel.CENTER);
			return center;
		case "sunRed":
			// 일요일 빨간색
			DefaultTableCellRenderer sunRed = new DefaultTableCellRenderer();
			sunRed.setForeground(Color.RED);
			sunRed.setHorizontalAlignment(JLabel.CENTER);
			return sunRed;
		case "satBlue":
			// 토요일 파란색
			DefaultTableCellRenderer satBlue = new DefaultTableCellRenderer();
			satBlue.setForeground(Color.BLUE);
			satBlue.setHorizontalAlignment(JLabel.CENTER);
			return satBlue;
		case "top":
			DefaultTableCellRenderer top = new DefaultTableCellRenderer();
			top.setVerticalAlignment(SwingConstants.TOP);
			return top;

		case "bottom_right":
			DefaultTableCellRenderer bottom_right = new DefaultTableCellRenderer();
			bottom_right.setHorizontalAlignment(SwingConstants.RIGHT);
			bottom_right.setVerticalAlignment(SwingConstants.BOTTOM);
			return bottom_right;
		// ----------------------------------
		case "sunDateRed":
			// 날짜 일요일 빨간색
			DefaultTableCellRenderer sunDateRed = new DefaultTableCellRenderer();
			sunDateRed.setForeground(Color.RED);
			sunDateRed.setVerticalAlignment(SwingConstants.TOP);
			return sunDateRed;
		case "satDateBlue":
			// 날짜 토요일 파란색
			DefaultTableCellRenderer satDateBlue = new DefaultTableCellRenderer();
			satDateBlue.setForeground(Color.BLUE);
			satDateBlue.setVerticalAlignment(SwingConstants.TOP);
			return satDateBlue;
		}
		return null;
	}

	private void initial_ContentPane() {
		// 기본 컨텐트 패널
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				do_contentPane_mouseWheelMoved(arg0);// 마우스휠의 움직임으로 월 조정
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	}

	// 달력 소스
	protected void refresh() {

		label.setText((calendar.get(Calendar.YEAR) + "년  ") + (calendar.get(Calendar.MONTH) + 1) + "월");
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int endDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		boolean[] ScheduleCheck = new boolean[32];
		ResultSet rs = null;
		String date = null;
		if ((calendar.get(Calendar.MONTH) + 1) < 10)
			date = (String.valueOf(calendar.get(Calendar.YEAR))).substring(2, 4) + "/" + "0"
					+ (calendar.get(Calendar.MONTH) + 1) + "%";
		else
			date = (String.valueOf(calendar.get(Calendar.YEAR))).substring(2, 4) + "/"
					+ (calendar.get(Calendar.MONTH) + 1) + "%";

		try {
			rs = WeatherDAO.check_Schedule(date);
			while (rs.next()) {
				ScheduleCheck[Integer.parseInt(rs.getString(1).substring(8, 10))] = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // 한 달 중 일정이 있는 날을 반환

		for (int i = 0; i < 6; i++) {// 모든 셀에 공백
			for (int j = 0; j < 7; j++) {
				dayViewTable.setValueAt("", i, j);
			}
		}

		for (int day = 1, row = 0, col = dayWeek - 1; day < endDay + 1; day++, col++) {
			if (col % 7 == 0) {
				col = 0;
				row += 1;
			}
			if (ScheduleCheck[day])
				dayViewTable.setValueAt(" " + day + "         ♥", row, col);
			else
				dayViewTable.setValueAt(" " + day, row, col); // 일정이 있는 날에 ♥ 표시
		}
	}

	// < 버튼 입력시
	protected void do_leftButton_actionPerformed(ActionEvent e) {
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, calendar.get(Calendar.DATE));
		refresh();
		String day = dayViewTable.getValueAt(dayViewTable.getSelectedRow(), dayViewTable.getSelectedColumn()).toString()
				.trim();
		if (day.length() > 2) {
			lblYyyymmdd.setText(getFocusedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					Integer.parseInt(day.substring(0, 2).trim())));
		} else if (day.length() > 0)
			lblYyyymmdd.setText(getFocusedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					Integer.parseInt(day.trim())));
		try {
			list = WeatherDAO.select_Schedule(lblYyyymmdd.getText());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		scrollPane.setViewportView(list[0]);
	}

	// << 버튼 입력시
	protected void do_left2Button_actionPerformed(ActionEvent e) {
		calendar.set(calendar.get(Calendar.YEAR) - 1, calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		refresh();
		String day = dayViewTable.getValueAt(dayViewTable.getSelectedRow(), dayViewTable.getSelectedColumn()).toString()
				.trim();
		if (day.length() > 2) {
			lblYyyymmdd.setText(getFocusedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					Integer.parseInt(day.substring(0, 2).trim())));
		} else if (day.length() > 0)
			lblYyyymmdd.setText(getFocusedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					Integer.parseInt(day.trim())));
		try {
			list = WeatherDAO.select_Schedule(lblYyyymmdd.getText());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		scrollPane.setViewportView(list[0]);
	}

	// > 버튼 입력시
	protected void do_rightButton_actionPerformed(ActionEvent e) {
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
		refresh();
		String day = dayViewTable.getValueAt(dayViewTable.getSelectedRow(), dayViewTable.getSelectedColumn()).toString()
				.trim();
		if (day.length() > 2) {
			lblYyyymmdd.setText(getFocusedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					Integer.parseInt(day.substring(0, 2).trim())));
		} else if (day.length() > 0)
			lblYyyymmdd.setText(getFocusedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					Integer.parseInt(day.trim())));
		try {
			list = WeatherDAO.select_Schedule(lblYyyymmdd.getText());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		scrollPane.setViewportView(list[0]);
	}

	// >> 버튼 입력시
	protected void do_right2Button_actionPerformed(ActionEvent e) {
		calendar.set(calendar.get(Calendar.YEAR) + 1, calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		refresh();
		String day = dayViewTable.getValueAt(dayViewTable.getSelectedRow(), dayViewTable.getSelectedColumn()).toString()
				.trim();
		if (day.length() > 2) {
			lblYyyymmdd.setText(getFocusedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					Integer.parseInt(day.substring(0, 2).trim())));
		} else if (day.length() > 0)
			lblYyyymmdd.setText(getFocusedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					Integer.parseInt(day.trim())));
		try {
			list = WeatherDAO.select_Schedule(lblYyyymmdd.getText());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		scrollPane.setViewportView(list[0]);
	}

	// 마우스 휠 구현
	protected void do_contentPane_mouseWheelMoved(MouseWheelEvent arg0) {
		int notches = arg0.getWheelRotation();
		if (notches < 0) {
			do_leftButton_actionPerformed(null);
		} else {
			do_rightButton_actionPerformed(null);
		}
	}

	// 오늘 버튼 클릭
	protected void do_todayButton_actionPerformed(ActionEvent e) {
		calendar.set(todayY, todayM, todayD);
		refresh();
		todayFocus();
		lblYyyymmdd.setText(getFocusedDate(todayY, todayM, todayD));
		try {
			list = WeatherDAO.select_Schedule(lblYyyymmdd.getText());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		scrollPane.setViewportView(list[0]);
	}

	// 오늘로 포커싱 하기
	protected void todayFocus() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (dayViewTable.getValueAt(i, j).equals(" " + todayD)
						|| dayViewTable.getValueAt(i, j).equals(" " + todayD + "         ♥")) {
					dayViewTable.changeSelection(i, j, false, false);
					dayViewTable.requestFocus();
				}
			}
		}
	}

	// 일정추가 버튼
	protected void do_addButton_actionPerformed(ActionEvent e) {
		String day = dayViewTable.getValueAt(dayViewTable.getSelectedRow(), dayViewTable.getSelectedColumn()).toString()
				.trim();
		if (e.getSource() == addButton) {
			if (day.length() > 2) {
				Dialog dialog = new Dialog(false, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						Integer.parseInt(day.substring(0, 2).trim()), -1, this);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} else if (day.length() > 0) {
				Dialog dialog = new Dialog(false, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						Integer.parseInt(day.trim()), -1, this);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} else {
			}
		}
		if (e.getSource() == moButton) {
			list[1].setSelectedIndex(list[0].getSelectedIndex());
			if (day.length() > 2) {
				Dialog dialog = new Dialog(true, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						Integer.parseInt(day.substring(0, 2).trim()), (int) list[1].getSelectedValue(), this);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} else if (day.length() > 0) {
				Dialog dialog = new Dialog(true, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						Integer.parseInt(day.trim()), (int) list[1].getSelectedValue(), this);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} else {
			}
		}
	}

	// 년,월,일 받아서 YYYY-MM-DD 로 반환
	protected String getFocusedDate(int Y, int M, int D) {
		String Day = (D + "").trim();
		String Year = Y + "";
		String Month = (M + 1) + "";

		if (Month.length() == 1) {
			Month = "0" + Month;
		}
		if (Day.length() == 1) {
			Day = "0" + Day;
		}
		String fDate = Year + " - " + Month + " - " + Day;

		return fDate;
	}

	protected void selectWeather() {
		try {
			ResultSet rs = WeatherDAO.selectWeather(comboBox.getSelectedIndex() + 1);
			while (rs.next()) {
				PTime = rs.getString(1);
				Pres = rs.getString(2);
				PTemp = rs.getDouble(3);
				Wind = rs.getString(4);
				D1 = rs.getString(5);
				D1MAXT = rs.getDouble(6);
				D1MINT = rs.getDouble(7);
				D2 = rs.getString(8);
				D2MAXT = rs.getDouble(9);
				D2MINT = rs.getDouble(10);
				D3 = rs.getString(11);
				D3MAXT = rs.getDouble(12);
				D3MINT = rs.getDouble(13);
				D4 = rs.getString(14);
				D4MAXT = rs.getDouble(15);
				D4MINT = rs.getDouble(16);
				D5 = rs.getString(17);
				D5MAXT = rs.getDouble(18);
				D5MINT = rs.getDouble(19);
				D6 = rs.getString(20);
				D6MAXT = rs.getDouble(21);
				D6MINT = rs.getDouble(22);
				D7 = rs.getString(23);
				D7MAXT = rs.getDouble(24);
				D7MINT = rs.getDouble(25);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		now_weather.setText(Pres);
		wind_speed.setText(Wind);
		label_5.setText("업데이트 시간 : " + PTime);
		temperature.setText(String.format("%.0f%c", PTemp, '℃'));
		switch (Pres) {
		case "맑음":
			lblNewLabel_4.setIcon(im0);
			break;
		case "구름 조금":
			lblNewLabel_4.setIcon(im1);
			break;
		case "구름 많음":
			lblNewLabel_4.setIcon(im2);
			break;
		case "흐림":
			lblNewLabel_4.setIcon(im3);
			break;
		case "비":
			lblNewLabel_4.setIcon(im4);
			break;
		case "눈/비":
			lblNewLabel_4.setIcon(im5);
			break;
		case "눈":
			lblNewLabel_4.setIcon(im6);
			break;
		}

		day_1w.setText(D1);
		day_1t.setText(String.format("%.0f/%.0f", D1MINT, D1MAXT));
		day_2w.setText(D2);
		day_2t.setText(String.format("%.0f/%.0f", D2MINT, D2MAXT));
		day_3w.setText(D3);
		day_3t.setText(String.format("%.0f/%.0f", D3MINT, D3MAXT));
		day_4w.setText(D4);
		day_4t.setText(String.format("%.0f/%.0f", D4MINT, D4MAXT));
		day_5w.setText(D5);
		day_5t.setText(String.format("%.0f/%.0f", D5MINT, D5MAXT));
		day_6w.setText(D6);
		day_6t.setText(String.format("%.0f/%.0f", D6MINT, D6MAXT));
		day_7w.setText(D7);
		day_7t.setText(String.format("%.0f/%.0f", D7MINT, D7MAXT));
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			try {
				WeatherDAO.refreshWeather(new XML(comboBox.getSelectedIndex() + 1));
				selectWeather();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void listrefresh() {
		try {
			list = WeatherDAO.select_Schedule(lblYyyymmdd.getText());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scrollPane.setViewportView(list[0]);
	}
}