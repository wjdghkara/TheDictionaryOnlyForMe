

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class DictionaryContent extends JDialog {
	
	JTextField contentName = new JTextField(); // JTextField타입의 contentName 선언 및 생성
	JTextField contents = new JTextField(); // JTextField타입의 contents 선언 및 생성
	JLabel modifieFinish = new JLabel("[완료]"); // "[완료]"가 써져있는 JLabel타입의 modifieFinish 선언 및 생성
	JLabel modifie = new JLabel("[수정]"); // "[수정]"이 써져있는 JLabel타입의 modifie 선언 및 생성
	
	int selectedIndex;
	
	public DictionaryContent(int index) {
		
		selectedIndex = index;
		
		setTitle(MyOwnDictionary.getContentName(selectedIndex));
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setSize(500, 350);
		
//		단어이름 관련 코드--------------------------------------------------------
		contentName.setSize(450, 30); // contentName의 크기 설정
		contentName.setLocation(20, 20); // contentName의 위치 설정
		contentName.setFont(new Font("바탕체", Font.PLAIN, 20)); // contentName의 폰트 설정
		contentName.setText(MyOwnDictionary.getContentName(selectedIndex)); // 인덱스값의 단어 이름 가져오기
		contentName.setDisabledTextColor(Color.BLACK); // 색깔 설정
		contentName.setEnabled(false); // 비활성화
//		--------------------------------------------------------------------
		
//		단어의 내용 관련 코드-------------------------------------------------------
		contents.setSize(450, 200); // contents의 크기 설정
		contents.setLocation(20, 90); // contents의 위치 설정
		contents.setFont(new Font("바탕체", Font.PLAIN, 18)); // contents의 폰트 설정
		contents.setText(MyOwnDictionary.getContentInside(selectedIndex)); // 인덱스값의 단어 내용 가져오기
		contents.setDisabledTextColor(Color.BLACK); // 색깔 설정
		contents.setEnabled(false); // contents 비활성화
//		--------------------------------------------------------------------
		
//		완료 버튼 관련 코드--------------------------------------------------------
		modifieFinish.setSize(100, 30); // modifieFinish의 크기 설정
		modifieFinish.setLocation(20, 55); // modifieFinish의 위치 설정
		modifieFinish.setFont(new Font("바탕체", Font.PLAIN, 17)); // modifieFinish의 폰트 설정
		modifieFinish.setForeground(Color.BLUE); // modifieFinish의 색깔 설정
		modifieFinish.setVisible(false); // 안 보이게 하기
		
		ModifieFinishMouseListener modifieFinishListener = new ModifieFinishMouseListener(); // 마우스 리스너 선언 및 생성
		modifieFinish.addMouseListener(modifieFinishListener); // 마우스 리스너 등록
//		--------------------------------------------------------------------
		
//		수정 버튼 관련 코드--------------------------------------------------------
		modifie.setSize(100, 30); // modifie의 크기 설정
		modifie.setLocation(420, 55); // modifie의 위치 설정
		modifie.setFont(new Font("바탕체", Font.PLAIN, 17)); // modifie의 폰트 설정
		modifie.setForeground(Color.BLUE); // modifie의 색깔 설정
		
		ModifieContentMouseListener modifieListener = new ModifieContentMouseListener(); // 마우스 리스너 선언 및 생성
		modifie.addMouseListener(modifieListener); // 마우스 리스너 등록
//		--------------------------------------------------------------------
		
		c.add(contentName); // 단어이름 칸 등록
		c.add(contents); // 단어내용 칸 등록
		c.add(modifieFinish); // 완료 라벨 등록
		c.add(modifie);
		
		Dimension frameSize = this.getSize(); // 프레임 크기 구하기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 화면 크기 구하기
		
		setLocation((screenSize.width - frameSize.width)/2 - 200, (screenSize.height - frameSize.height)/2 - 175); // 프레임을 화면 중앙에 위치 설정
		
		setSize(500, 350); // 창 크기 설정
		setResizable(false); // 창 크기 고정
		setVisible(true); // 가시성 설정
		
	}
	
	class ModifieContentMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			contentName.setEnabled(true); // contentName 활성화
			contents.setEnabled(true); // contents 활성화
			modifieFinish.setVisible(true); // modifieFinish 활성화
			modifie.setVisible(false); // modifie 안보이게 하기
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 올라가 있으면 마우스 커서를 손 모양으로 변경
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스가 밖으로 나가면 원래 모양으로 변경
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
	
	class ModifieFinishMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			MyOwnDictionary.setContentName(selectedIndex, contentName.getText()); // contentNames의 index번째에 단어 이름 저장
			MyOwnDictionary.setContentInside(selectedIndex, contents.getText()); // contentInsides의 index번째에 단어 내용 저장
			MyOwnDictionary.dictionaryList.setListData(MyOwnDictionary.getContentNames()); // JList 재등록
			
			MyOwnDictionary.saveContentNames.set(selectedIndex, contentName.getText()); // 저장용 벡터에 바뀐 단어이름을 저장
			MyOwnDictionary.saveContentInsides.set(selectedIndex, contents.getText()); // 저장용 벡터에 바뀐 내용을 저장
			MyOwnDictionary.saveDictionaryList.setListData(MyOwnDictionary.saveContentNames); // 저장용 리스트에 바뀐 리스트를 저장
			
			contentName.setEnabled(false); // 이름수정 비활성화
			contents.setEnabled(false); // 내용수정 비활성화
			modifie.setVisible(true); // [수정] 라벨 활성화
			modifieFinish.setVisible(false); // [완성] 라벨 비활성화
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스가 올라가 있으면 마우스 커서를 손 모양으로 변경
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스가 밖으로 나가면 원래 모양으로 변경
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
	
}
