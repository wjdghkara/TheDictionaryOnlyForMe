

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class AddDictionaryContent extends JDialog {
	
	JTextField contentName = new JTextField(); // JTextField타입의 contentName 선언 및 생성
	JTextField contents = new JTextField(); // JTextField타입의 contents 선언 및 생성
	
	public AddDictionaryContent() {
		
		setTitle("Add New Content"); // 타이틀 설정
		
		Container c = getContentPane(); // 컨테이너 가져오기(c)
		c.setLayout(null); // c의 레이아웃을 null로 설정
		c.setSize(500, 350); // c의 크기 설정
		
//		단어이름 관련 코드--------------------------------------------------------
		contentName.setSize(450, 30); // contentName의 크기 설정
		contentName.setLocation(20, 20); // contentName의 위치 설정
		contentName.setFont(new Font("바탕체", Font.PLAIN, 20)); // contentName의 폰트 설정
//		--------------------------------------------------------------------
		
//		단어의 내용 관련 코드-------------------------------------------------------
		contents.setSize(450, 200); // contents의 크기 설정
		contents.setLocation(20, 90); // contents의 위치 설정
		contents.setFont(new Font("바탕체", Font.PLAIN, 18)); // contents의 폰트 설정
//		--------------------------------------------------------------------
		
//		완료 버튼 관련 코드--------------------------------------------------------
		JLabel modifieFinish = new JLabel("[완료]"); // "[완료]"가 써져있는 JLabel타입의 modifieFinish 선언 및 생성
		modifieFinish.setSize(100, 30); // modifieFinish의 크기 설정
		modifieFinish.setLocation(20, 55); // modifieFinish의 위치 설정
		modifieFinish.setFont(new Font("바탕체", Font.PLAIN, 17)); // modifieFinish의 폰트 설정
		modifieFinish.setForeground(Color.BLUE); // modifieFinish의 색깔 설정
		
		ModifieFinishListener addListener = new ModifieFinishListener(); // 마우스 리스너 객체 addListener 선언 및 생성
		modifieFinish.addMouseListener(addListener); // modifieFinish에 마우스 리스너 등록
//		--------------------------------------------------------------------
		
		c.add(contentName); // 단어이름 입력칸 등록
		c.add(contents); // 단어내용 입력칸 등록
		c.add(modifieFinish); // 완료 라벨 등록
		
		Dimension frameSize = this.getSize(); // 프레임 크기 구하기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 화면 크기 구하기
		
		setLocation((screenSize.width - frameSize.width)/2 - 200, (screenSize.height - frameSize.height)/2 - 175); // 프레임을 화면 중앙에 위치 설정
		
		setSize(500, 350); // 창 크기 설정
		setResizable(false); // 창 크기 고정
		setVisible(true); // 가시성 설정
		
	}
	
	class ModifieFinishListener implements MouseListener { // 마우스 리스너 인터페이스를 상속받은 클래스 구현

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			MyOwnDictionary.addContentName(contentName.getText()); // 단어이름 벡터에 입력받은 이름 저장
			MyOwnDictionary.addContentInside(contents.getText()); // 단어내용 벡터에 입력받은 내용 저장
			MyOwnDictionary.dictionaryList.setListData(MyOwnDictionary.getContentNames()); // JList 재등록
			
			MyOwnDictionary.saveContentNames.add(contentName.getText()); // 저장용 벡터에 단어이름 추가
			MyOwnDictionary.saveContentInsides.add(contents.getText()); // 저장용 벡터에 단어내용 추가
			MyOwnDictionary.saveDictionaryList.setListData(MyOwnDictionary.saveContentNames); // 저장용 JList에 저장
			
			dispose();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) { // 마우스가 올라가 있으면 마우스 커서를 손 모양으로 변경
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0) { // 마우스가 밖으로 나가면 원래 모양으로 변경
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
