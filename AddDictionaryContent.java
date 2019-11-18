

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class AddDictionaryContent extends JDialog {
	
	JTextField contentName = new JTextField(); // JTextFieldŸ���� contentName ���� �� ����
	JTextField contents = new JTextField(); // JTextFieldŸ���� contents ���� �� ����
	
	public AddDictionaryContent() {
		
		setTitle("Add New Content"); // Ÿ��Ʋ ����
		
		Container c = getContentPane(); // �����̳� ��������(c)
		c.setLayout(null); // c�� ���̾ƿ��� null�� ����
		c.setSize(500, 350); // c�� ũ�� ����
		
//		�ܾ��̸� ���� �ڵ�--------------------------------------------------------
		contentName.setSize(450, 30); // contentName�� ũ�� ����
		contentName.setLocation(20, 20); // contentName�� ��ġ ����
		contentName.setFont(new Font("����ü", Font.PLAIN, 20)); // contentName�� ��Ʈ ����
//		--------------------------------------------------------------------
		
//		�ܾ��� ���� ���� �ڵ�-------------------------------------------------------
		contents.setSize(450, 200); // contents�� ũ�� ����
		contents.setLocation(20, 90); // contents�� ��ġ ����
		contents.setFont(new Font("����ü", Font.PLAIN, 18)); // contents�� ��Ʈ ����
//		--------------------------------------------------------------------
		
//		�Ϸ� ��ư ���� �ڵ�--------------------------------------------------------
		JLabel modifieFinish = new JLabel("[�Ϸ�]"); // "[�Ϸ�]"�� �����ִ� JLabelŸ���� modifieFinish ���� �� ����
		modifieFinish.setSize(100, 30); // modifieFinish�� ũ�� ����
		modifieFinish.setLocation(20, 55); // modifieFinish�� ��ġ ����
		modifieFinish.setFont(new Font("����ü", Font.PLAIN, 17)); // modifieFinish�� ��Ʈ ����
		modifieFinish.setForeground(Color.BLUE); // modifieFinish�� ���� ����
		
		ModifieFinishListener addListener = new ModifieFinishListener(); // ���콺 ������ ��ü addListener ���� �� ����
		modifieFinish.addMouseListener(addListener); // modifieFinish�� ���콺 ������ ���
//		--------------------------------------------------------------------
		
		c.add(contentName); // �ܾ��̸� �Է�ĭ ���
		c.add(contents); // �ܾ�� �Է�ĭ ���
		c.add(modifieFinish); // �Ϸ� �� ���
		
		Dimension frameSize = this.getSize(); // ������ ũ�� ���ϱ�
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ȭ�� ũ�� ���ϱ�
		
		setLocation((screenSize.width - frameSize.width)/2 - 200, (screenSize.height - frameSize.height)/2 - 175); // �������� ȭ�� �߾ӿ� ��ġ ����
		
		setSize(500, 350); // â ũ�� ����
		setResizable(false); // â ũ�� ����
		setVisible(true); // ���ü� ����
		
	}
	
	class ModifieFinishListener implements MouseListener { // ���콺 ������ �������̽��� ��ӹ��� Ŭ���� ����

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			MyOwnDictionary.addContentName(contentName.getText()); // �ܾ��̸� ���Ϳ� �Է¹��� �̸� ����
			MyOwnDictionary.addContentInside(contents.getText()); // �ܾ�� ���Ϳ� �Է¹��� ���� ����
			MyOwnDictionary.dictionaryList.setListData(MyOwnDictionary.getContentNames()); // JList ����
			
			MyOwnDictionary.saveContentNames.add(contentName.getText()); // ����� ���Ϳ� �ܾ��̸� �߰�
			MyOwnDictionary.saveContentInsides.add(contents.getText()); // ����� ���Ϳ� �ܾ�� �߰�
			MyOwnDictionary.saveDictionaryList.setListData(MyOwnDictionary.saveContentNames); // ����� JList�� ����
			
			dispose();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) { // ���콺�� �ö� ������ ���콺 Ŀ���� �� ������� ����
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0) { // ���콺�� ������ ������ ���� ������� ����
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
