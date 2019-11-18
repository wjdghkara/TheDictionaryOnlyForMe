

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyOwnDictionary extends JFrame {
	
	private static Vector<String> contentNames = new Vector<String>(); // 사전에 추가되는 내용물의 이름들을 담을 String 벡터
	public static Vector<String> saveContentNames = new Vector<String>(); // 단어이름을 백업할 벡터
	public static JList<String> dictionaryList = new JList<String>(saveContentNames); // contentNames로 JList타입의 dictionaryList 선언
	public static JList<String> saveDictionaryList = new JList<String>(contentNames); // 단어이름들을 적는 리스트를 백업할 리스트
	private JScrollPane dictionaryScrollPane = new JScrollPane(dictionaryList); // dictionaryList로 JScrollPane타입의 dictionaryScrollPane 선언
	private static Vector<String> contentInsides = new Vector<String>(); // 사전에 추가되는 단어의 뜻(내용)을 담을 String 벡터
	public static Vector<String> saveContentInsides = new Vector<String>(); // 단어 내용을 백업할 벡터
	boolean reside;
	static boolean isEnd;
	
	AddDictionaryContent newWindow_1; // 단어 등록 창
	DictionaryContent newWindow_2; // 단어 확인 창
	
	public static void addContentName(String contentName) { // contentNames의 add메서드
		contentNames.add(contentName);
	}
	
	public static String getContentName(int index) { // contentNames의 get메서드
		return contentNames.get(index);
	}
	
	public static void setContentName(int index, String name) { // contentNames의 set메서드
		contentNames.set(index, name);
	}
	
	public static Vector<String> getContentNames() { // contentNames의 get메서드
		return contentNames;
	}
	
	public void removeContentName(int index) { // contentNames의 remove메서드
		contentNames.remove(index);
	}
	
	public void removeAllContentNames() { // contentNames안에 모든 요소를 삭제하는 메서드
		contentNames.removeAllElements();
	}
	
	public int getContentNamesLength() { // contentNames의 길이를 구하는 메서드
		return contentNames.size();
	}
	
	public static void addContentInside(String contentInside) { // contentInsides의 add메서드
		contentInsides.add(contentInside);
	}
	
	public static String getContentInside(int index) { // contentInsides의 get메서드
		return contentInsides.get(index);
	}
	
	public static void setContentInside(int index, String content) { // contentInside의 set메서드
		contentInsides.set(index, content);
	}
	
	public void removeContentInside(int index) { // contentInsides의 remove메서드
		contentInsides.remove(index);
	}
	
	public MyOwnDictionary() {
		
		File file = new File("vector.txt");
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"euc-kr"));
			String line;
			while((line = bufferedReader.readLine()) != null) {
				String [] array = line.split(":");
				addContentName(array[0]);
				addContentInside(array[1]);
				saveContentNames.add(array[0]);
				saveContentInsides.add(array[1]);
			}
			dictionaryList.setListData(getContentNames());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setTitle("The Dictionary only for me"); // 제목 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창이 닫히면 동시에 프로그램도 꺼지게 설정
		
		Container c = getContentPane();
		
		c.setLayout(null);
		c.setSize(800, 650);
		
//      검색창 관련 코드--------------------------------------------------
		JTextField searchBox = new JTextField(); // JTextField타입의 searchBox 선언
		searchBox.setSize(600, 50); // searchBox의 크기 설정
		searchBox.setLocation(c.getWidth()-700, c.getHeight()-600); // searchBox의 위치 설정
		searchBox.setFont(new Font("바탕체", Font.PLAIN, 23)); // searchBox의 폰트 설정
		
		searchBox.addActionListener(new ActionListener() { // searchBox에 액션리스너 등록
			public void actionPerformed(ActionEvent e) {
				reside = true; // 내용이 있는지 없는지 판별해주는 boolean형 함수
				JTextField t = (JTextField)e.getSource(); // 텍스트 박스에 입력된 내용을 저장
				
				dictionaryList.removeAll(); // 보여지고 있는 JList의 내용을 모두 삭제
				removeAllContentNames(); // 단어이름이 저장된 벡터의 내용을 모두 삭제
				contentInsides.removeAllElements(); // 단어내용이 저장된 벡터의 내용을 모두 삭제
				for(int i=0; i<saveContentNames.size(); i++) { // 반복문을 저장된 단어의 개수만큼 돌림

					if(saveContentNames.get(i).indexOf(t.getText()) != -1) {
						addContentName(saveContentNames.get(i));
						contentInsides.add(saveContentInsides.get(i));
						dictionaryList.setListData(getContentNames());
					}
					
				}
				
				t.setText(""); // 텍스트 박스 문자열 초기화
				reside = false; // 내용이 없으므로 reside는 false
			}
		});
		
		searchBox.addKeyListener(new MyKeyListener()); // 검색창에 키 리스너 등록
//		------------------------------------------------------------
		
//		사전목록 관련 코드------------------------------------------------
		dictionaryList.setFont(new Font("바탕체", Font.PLAIN, 20)); // dictionaryList의 폰트 설정
		dictionaryScrollPane.setSize(600, 400); // dictionaryScrollPane의 크기 설정
		dictionaryScrollPane.setLocation(c.getWidth()-700, c.getHeight()-475); // dictionaryScrollPane의 위치 설정
		
		ConfirmContentMouseListener confirmListener = new ConfirmContentMouseListener(); // 마우스 리스너 선언 및 생성
		dictionaryList.addMouseListener(confirmListener); // 마우스 리스너 등록
//		------------------------------------------------------------
		
//		추가 관련 코드---------------------------------------------------
		JLabel addContent = new JLabel("[추가]"); // JLabel타입의 addContent 선언
		addContent.setLocation(c.getWidth()-700, c.getHeight()-525); // addContent의  위치 설정
		addContent.setFont(new Font("바탕체", Font.PLAIN, 20)); // addContent의 폰트 설정
		addContent.setForeground(Color.BLUE); // 색깔을 파랑색으로 설정
		addContent.setSize(60, 18); // 크기 설정
		
		AddContentMouseListener addListener = new AddContentMouseListener(); // 추가 마우스 리스너 선언 및 생성
		addContent.addMouseListener(addListener); // 추가 마우스 리스너 등록
//		------------------------------------------------------------
		
//		삭제 관련 코드---------------------------------------------------
		JLabel removeContent = new JLabel("[삭제]"); // JLabel타입의 removeContent 선언
		removeContent.setLocation(c.getWidth()-160 , c.getHeight()-525); // removeContent의 위치 설정
		removeContent.setFont(new Font("바탕체", Font.PLAIN, 20)); // removeContent의 폰트 설정
		removeContent.setForeground(Color.BLUE); // removeContent 색깔 파랑색으로 설정
		removeContent.setSize(60, 18); // removeContent의 크기 설정
		
		RemoveContentMouseListener removeListener = new RemoveContentMouseListener(); // 삭제 마우스 리스너 선언 및 생성
		removeContent.addMouseListener(removeListener); // 삭제 마우스 리스너 등록
//		------------------------------------------------------------
		
//		저장 관련 코드---------------------------------------------------
		JButton saveButton = new JButton("저장"); // "저장"이 써있는 버튼 생성 및 선언
		saveButton.setLocation(c.getWidth()-450, c.getHeight()-535); // 저장버튼 위치 설정
		saveButton.setFont(new Font("바탕체", Font.BOLD, 20)); // 저장버튼 폰트 설정
		saveButton.setForeground(Color.RED); // 저장버튼 글씨 색깔 설정
		saveButton.setBackground(Color.WHITE); // 저장버튼 배경 색깔 설정
		saveButton.setSize(80, 40); // 저장버튼 사이즈 설정
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				URL textURL = getClass().getClassLoader().getResource("./vector.txt");
				File file = new File("./vector.txt");
				try {
					BufferedWriter bufferdWriter = new BufferedWriter(new FileWriter(file));
					
					if(file.isFile() && file.canWrite()) {
						for(int i=0; i<saveContentNames.size(); i++) {
							bufferdWriter.write(saveContentNames.get(i) + ":" + saveContentInsides.get(i));
							
							bufferdWriter.newLine();
						}
						bufferdWriter.flush();
						bufferdWriter.close();
					}
				} catch(IOException er) {
					System.out.println(er);
				}
			}
		});
//		------------------------------------------------------------
		
		c.add(searchBox); // 검색창 추가
		c.add(dictionaryScrollPane); // 사전목록 추가
		c.add(addContent); // 추가 라벨 추가
		c.add(removeContent); // 삭제 라벨 추가
		c.add(saveButton); // 저장 버튼 추가
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈 겟★또
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 화면 사이즈 겟★또
		
		this.setLocation((screenSize.width - frameSize.width)/2 - 350, (screenSize.height - frameSize.height)/2 - 350); // 화면 중앙에 프레임 위치 설정
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				isEnd = true;
			}
		});
		
		setSize(800, 650); // 창 크기 설정
		setResizable(false); // 창 크기 고정
		setVisible(true); // 가시성 설정
		
	}
	
	class AddContentMouseListener implements MouseListener { // 마우스 리스너 인터페이스를 상속받은 클래스 구현

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			newWindow_1 = new AddDictionaryContent(); // [추가]버튼이 클릭되면 새로운 창을 연다
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 [추가] 위에 올라가 있으면 손 모양으로 바뀜
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스가 [추가] 밖으로 나가면 다시 원래 모양으로 바뀜
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	class ConfirmContentMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getClickCount() > 1) { // 마우스가 두 번 이상(더블클릭) 되었을 경우
				newWindow_2 = new DictionaryContent(dictionaryList.getSelectedIndex()); // 새로운 창을 연다
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class RemoveContentMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			removeContentName(dictionaryList.getSelectedIndex()); // 선택된 인덱스값의 단어이름을 삭제
			removeContentInside(dictionaryList.getSelectedIndex()); // 선택된 인덱스값의 단어내용을 삭제
			saveContentNames.remove(dictionaryList.getSelectedIndex()); // 선택된 인덱스값의 저장된 단어이름을 삭제
			saveContentInsides.remove(dictionaryList.getSelectedIndex()); // 선택된 인덱스값의 저장된 단어내용을 삭제
			dictionaryList.setListData(contentNames); // 리스트 데이터를 재설정
			saveDictionaryList.setListData(saveContentNames); // 저장된 리스트 데이터를 재 설정
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 [삭제]위에 올라가 있으면 손 모양으로 바뀜
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스가 [삭제] 밖으로 나가면 다시 원래 모양으로 바뀜
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class MyKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == 10 && reside == false) { // 아무것도 입력 안하고 엔터를 입력했을 경우
				dictionaryList.setListData(saveContentNames); // 저장된 모든 단어들을 List데이터에 셋 해준다
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new MyOwnDictionary();
		
	}

}
