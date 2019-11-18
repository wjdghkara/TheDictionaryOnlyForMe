

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class DictionaryContent extends JDialog {
	
	JTextField contentName = new JTextField(); // JTextFieldŸ���� contentName ���� �� ����
	JTextField contents = new JTextField(); // JTextFieldŸ���� contents ���� �� ����
	JLabel modifieFinish = new JLabel("[�Ϸ�]"); // "[�Ϸ�]"�� �����ִ� JLabelŸ���� modifieFinish ���� �� ����
	JLabel modifie = new JLabel("[����]"); // "[����]"�� �����ִ� JLabelŸ���� modifie ���� �� ����
	
	int selectedIndex;
	
	public DictionaryContent(int index) {
		
		selectedIndex = index;
		
		setTitle(MyOwnDictionary.getContentName(selectedIndex));
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setSize(500, 350);
		
//		�ܾ��̸� ���� �ڵ�--------------------------------------------------------
		contentName.setSize(450, 30); // contentName�� ũ�� ����
		contentName.setLocation(20, 20); // contentName�� ��ġ ����
		contentName.setFont(new Font("����ü", Font.PLAIN, 20)); // contentName�� ��Ʈ ����
		contentName.setText(MyOwnDictionary.getContentName(selectedIndex)); // �ε������� �ܾ� �̸� ��������
		contentName.setDisabledTextColor(Color.BLACK); // ���� ����
		contentName.setEnabled(false); // ��Ȱ��ȭ
//		--------------------------------------------------------------------
		
//		�ܾ��� ���� ���� �ڵ�-------------------------------------------------------
		contents.setSize(450, 200); // contents�� ũ�� ����
		contents.setLocation(20, 90); // contents�� ��ġ ����
		contents.setFont(new Font("����ü", Font.PLAIN, 18)); // contents�� ��Ʈ ����
		contents.setText(MyOwnDictionary.getContentInside(selectedIndex)); // �ε������� �ܾ� ���� ��������
		contents.setDisabledTextColor(Color.BLACK); // ���� ����
		contents.setEnabled(false); // contents ��Ȱ��ȭ
//		--------------------------------------------------------------------
		
//		�Ϸ� ��ư ���� �ڵ�--------------------------------------------------------
		modifieFinish.setSize(100, 30); // modifieFinish�� ũ�� ����
		modifieFinish.setLocation(20, 55); // modifieFinish�� ��ġ ����
		modifieFinish.setFont(new Font("����ü", Font.PLAIN, 17)); // modifieFinish�� ��Ʈ ����
		modifieFinish.setForeground(Color.BLUE); // modifieFinish�� ���� ����
		modifieFinish.setVisible(false); // �� ���̰� �ϱ�
		
		ModifieFinishMouseListener modifieFinishListener = new ModifieFinishMouseListener(); // ���콺 ������ ���� �� ����
		modifieFinish.addMouseListener(modifieFinishListener); // ���콺 ������ ���
//		--------------------------------------------------------------------
		
//		���� ��ư ���� �ڵ�--------------------------------------------------------
		modifie.setSize(100, 30); // modifie�� ũ�� ����
		modifie.setLocation(420, 55); // modifie�� ��ġ ����
		modifie.setFont(new Font("����ü", Font.PLAIN, 17)); // modifie�� ��Ʈ ����
		modifie.setForeground(Color.BLUE); // modifie�� ���� ����
		
		ModifieContentMouseListener modifieListener = new ModifieContentMouseListener(); // ���콺 ������ ���� �� ����
		modifie.addMouseListener(modifieListener); // ���콺 ������ ���
//		--------------------------------------------------------------------
		
		c.add(contentName); // �ܾ��̸� ĭ ���
		c.add(contents); // �ܾ�� ĭ ���
		c.add(modifieFinish); // �Ϸ� �� ���
		c.add(modifie);
		
		Dimension frameSize = this.getSize(); // ������ ũ�� ���ϱ�
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ȭ�� ũ�� ���ϱ�
		
		setLocation((screenSize.width - frameSize.width)/2 - 200, (screenSize.height - frameSize.height)/2 - 175); // �������� ȭ�� �߾ӿ� ��ġ ����
		
		setSize(500, 350); // â ũ�� ����
		setResizable(false); // â ũ�� ����
		setVisible(true); // ���ü� ����
		
	}
	
	class ModifieContentMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			contentName.setEnabled(true); // contentName Ȱ��ȭ
			contents.setEnabled(true); // contents Ȱ��ȭ
			modifieFinish.setVisible(true); // modifieFinish Ȱ��ȭ
			modifie.setVisible(false); // modifie �Ⱥ��̰� �ϱ�
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö� ������ ���콺 Ŀ���� �� ������� ����
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // ���콺�� ������ ������ ���� ������� ����
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
			MyOwnDictionary.setContentName(selectedIndex, contentName.getText()); // contentNames�� index��°�� �ܾ� �̸� ����
			MyOwnDictionary.setContentInside(selectedIndex, contents.getText()); // contentInsides�� index��°�� �ܾ� ���� ����
			MyOwnDictionary.dictionaryList.setListData(MyOwnDictionary.getContentNames()); // JList ����
			
			MyOwnDictionary.saveContentNames.set(selectedIndex, contentName.getText()); // ����� ���Ϳ� �ٲ� �ܾ��̸��� ����
			MyOwnDictionary.saveContentInsides.set(selectedIndex, contents.getText()); // ����� ���Ϳ� �ٲ� ������ ����
			MyOwnDictionary.saveDictionaryList.setListData(MyOwnDictionary.saveContentNames); // ����� ����Ʈ�� �ٲ� ����Ʈ�� ����
			
			contentName.setEnabled(false); // �̸����� ��Ȱ��ȭ
			contents.setEnabled(false); // ������� ��Ȱ��ȭ
			modifie.setVisible(true); // [����] �� Ȱ��ȭ
			modifieFinish.setVisible(false); // [�ϼ�] �� ��Ȱ��ȭ
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� �ö� ������ ���콺 Ŀ���� �� ������� ����
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // ���콺�� ������ ������ ���� ������� ����
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
