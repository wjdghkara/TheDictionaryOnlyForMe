

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
	
	private static Vector<String> contentNames = new Vector<String>(); // ������ �߰��Ǵ� ���빰�� �̸����� ���� String ����
	public static Vector<String> saveContentNames = new Vector<String>(); // �ܾ��̸��� ����� ����
	public static JList<String> dictionaryList = new JList<String>(saveContentNames); // contentNames�� JListŸ���� dictionaryList ����
	public static JList<String> saveDictionaryList = new JList<String>(contentNames); // �ܾ��̸����� ���� ����Ʈ�� ����� ����Ʈ
	private JScrollPane dictionaryScrollPane = new JScrollPane(dictionaryList); // dictionaryList�� JScrollPaneŸ���� dictionaryScrollPane ����
	private static Vector<String> contentInsides = new Vector<String>(); // ������ �߰��Ǵ� �ܾ��� ��(����)�� ���� String ����
	public static Vector<String> saveContentInsides = new Vector<String>(); // �ܾ� ������ ����� ����
	boolean reside;
	static boolean isEnd;
	
	AddDictionaryContent newWindow_1; // �ܾ� ��� â
	DictionaryContent newWindow_2; // �ܾ� Ȯ�� â
	
	public static void addContentName(String contentName) { // contentNames�� add�޼���
		contentNames.add(contentName);
	}
	
	public static String getContentName(int index) { // contentNames�� get�޼���
		return contentNames.get(index);
	}
	
	public static void setContentName(int index, String name) { // contentNames�� set�޼���
		contentNames.set(index, name);
	}
	
	public static Vector<String> getContentNames() { // contentNames�� get�޼���
		return contentNames;
	}
	
	public void removeContentName(int index) { // contentNames�� remove�޼���
		contentNames.remove(index);
	}
	
	public void removeAllContentNames() { // contentNames�ȿ� ��� ��Ҹ� �����ϴ� �޼���
		contentNames.removeAllElements();
	}
	
	public int getContentNamesLength() { // contentNames�� ���̸� ���ϴ� �޼���
		return contentNames.size();
	}
	
	public static void addContentInside(String contentInside) { // contentInsides�� add�޼���
		contentInsides.add(contentInside);
	}
	
	public static String getContentInside(int index) { // contentInsides�� get�޼���
		return contentInsides.get(index);
	}
	
	public static void setContentInside(int index, String content) { // contentInside�� set�޼���
		contentInsides.set(index, content);
	}
	
	public void removeContentInside(int index) { // contentInsides�� remove�޼���
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
		
		setTitle("The Dictionary only for me"); // ���� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // â�� ������ ���ÿ� ���α׷��� ������ ����
		
		Container c = getContentPane();
		
		c.setLayout(null);
		c.setSize(800, 650);
		
//      �˻�â ���� �ڵ�--------------------------------------------------
		JTextField searchBox = new JTextField(); // JTextFieldŸ���� searchBox ����
		searchBox.setSize(600, 50); // searchBox�� ũ�� ����
		searchBox.setLocation(c.getWidth()-700, c.getHeight()-600); // searchBox�� ��ġ ����
		searchBox.setFont(new Font("����ü", Font.PLAIN, 23)); // searchBox�� ��Ʈ ����
		
		searchBox.addActionListener(new ActionListener() { // searchBox�� �׼Ǹ����� ���
			public void actionPerformed(ActionEvent e) {
				reside = true; // ������ �ִ��� ������ �Ǻ����ִ� boolean�� �Լ�
				JTextField t = (JTextField)e.getSource(); // �ؽ�Ʈ �ڽ��� �Էµ� ������ ����
				
				dictionaryList.removeAll(); // �������� �ִ� JList�� ������ ��� ����
				removeAllContentNames(); // �ܾ��̸��� ����� ������ ������ ��� ����
				contentInsides.removeAllElements(); // �ܾ���� ����� ������ ������ ��� ����
				for(int i=0; i<saveContentNames.size(); i++) { // �ݺ����� ����� �ܾ��� ������ŭ ����

					if(saveContentNames.get(i).indexOf(t.getText()) != -1) {
						addContentName(saveContentNames.get(i));
						contentInsides.add(saveContentInsides.get(i));
						dictionaryList.setListData(getContentNames());
					}
					
				}
				
				t.setText(""); // �ؽ�Ʈ �ڽ� ���ڿ� �ʱ�ȭ
				reside = false; // ������ �����Ƿ� reside�� false
			}
		});
		
		searchBox.addKeyListener(new MyKeyListener()); // �˻�â�� Ű ������ ���
//		------------------------------------------------------------
		
//		������� ���� �ڵ�------------------------------------------------
		dictionaryList.setFont(new Font("����ü", Font.PLAIN, 20)); // dictionaryList�� ��Ʈ ����
		dictionaryScrollPane.setSize(600, 400); // dictionaryScrollPane�� ũ�� ����
		dictionaryScrollPane.setLocation(c.getWidth()-700, c.getHeight()-475); // dictionaryScrollPane�� ��ġ ����
		
		ConfirmContentMouseListener confirmListener = new ConfirmContentMouseListener(); // ���콺 ������ ���� �� ����
		dictionaryList.addMouseListener(confirmListener); // ���콺 ������ ���
//		------------------------------------------------------------
		
//		�߰� ���� �ڵ�---------------------------------------------------
		JLabel addContent = new JLabel("[�߰�]"); // JLabelŸ���� addContent ����
		addContent.setLocation(c.getWidth()-700, c.getHeight()-525); // addContent��  ��ġ ����
		addContent.setFont(new Font("����ü", Font.PLAIN, 20)); // addContent�� ��Ʈ ����
		addContent.setForeground(Color.BLUE); // ������ �Ķ������� ����
		addContent.setSize(60, 18); // ũ�� ����
		
		AddContentMouseListener addListener = new AddContentMouseListener(); // �߰� ���콺 ������ ���� �� ����
		addContent.addMouseListener(addListener); // �߰� ���콺 ������ ���
//		------------------------------------------------------------
		
//		���� ���� �ڵ�---------------------------------------------------
		JLabel removeContent = new JLabel("[����]"); // JLabelŸ���� removeContent ����
		removeContent.setLocation(c.getWidth()-160 , c.getHeight()-525); // removeContent�� ��ġ ����
		removeContent.setFont(new Font("����ü", Font.PLAIN, 20)); // removeContent�� ��Ʈ ����
		removeContent.setForeground(Color.BLUE); // removeContent ���� �Ķ������� ����
		removeContent.setSize(60, 18); // removeContent�� ũ�� ����
		
		RemoveContentMouseListener removeListener = new RemoveContentMouseListener(); // ���� ���콺 ������ ���� �� ����
		removeContent.addMouseListener(removeListener); // ���� ���콺 ������ ���
//		------------------------------------------------------------
		
//		���� ���� �ڵ�---------------------------------------------------
		JButton saveButton = new JButton("����"); // "����"�� ���ִ� ��ư ���� �� ����
		saveButton.setLocation(c.getWidth()-450, c.getHeight()-535); // �����ư ��ġ ����
		saveButton.setFont(new Font("����ü", Font.BOLD, 20)); // �����ư ��Ʈ ����
		saveButton.setForeground(Color.RED); // �����ư �۾� ���� ����
		saveButton.setBackground(Color.WHITE); // �����ư ��� ���� ����
		saveButton.setSize(80, 40); // �����ư ������ ����
		
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
		
		c.add(searchBox); // �˻�â �߰�
		c.add(dictionaryScrollPane); // ������� �߰�
		c.add(addContent); // �߰� �� �߰�
		c.add(removeContent); // ���� �� �߰�
		c.add(saveButton); // ���� ��ư �߰�
		
		Dimension frameSize = this.getSize(); // ������ ������ �١ڶ�
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ȭ�� ������ �١ڶ�
		
		this.setLocation((screenSize.width - frameSize.width)/2 - 350, (screenSize.height - frameSize.height)/2 - 350); // ȭ�� �߾ӿ� ������ ��ġ ����
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				isEnd = true;
			}
		});
		
		setSize(800, 650); // â ũ�� ����
		setResizable(false); // â ũ�� ����
		setVisible(true); // ���ü� ����
		
	}
	
	class AddContentMouseListener implements MouseListener { // ���콺 ������ �������̽��� ��ӹ��� Ŭ���� ����

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			newWindow_1 = new AddDictionaryContent(); // [�߰�]��ư�� Ŭ���Ǹ� ���ο� â�� ����
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� [�߰�] ���� �ö� ������ �� ������� �ٲ�
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // ���콺�� [�߰�] ������ ������ �ٽ� ���� ������� �ٲ�
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
			if(e.getClickCount() > 1) { // ���콺�� �� �� �̻�(����Ŭ��) �Ǿ��� ���
				newWindow_2 = new DictionaryContent(dictionaryList.getSelectedIndex()); // ���ο� â�� ����
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
			removeContentName(dictionaryList.getSelectedIndex()); // ���õ� �ε������� �ܾ��̸��� ����
			removeContentInside(dictionaryList.getSelectedIndex()); // ���õ� �ε������� �ܾ���� ����
			saveContentNames.remove(dictionaryList.getSelectedIndex()); // ���õ� �ε������� ����� �ܾ��̸��� ����
			saveContentInsides.remove(dictionaryList.getSelectedIndex()); // ���õ� �ε������� ����� �ܾ���� ����
			dictionaryList.setListData(contentNames); // ����Ʈ �����͸� �缳��
			saveDictionaryList.setListData(saveContentNames); // ����� ����Ʈ �����͸� �� ����
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.HAND_CURSOR)); // ���콺�� [����]���� �ö� ������ �� ������� �ٲ�
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // ���콺�� [����] ������ ������ �ٽ� ���� ������� �ٲ�
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
			if(e.getKeyCode() == 10 && reside == false) { // �ƹ��͵� �Է� ���ϰ� ���͸� �Է����� ���
				dictionaryList.setListData(saveContentNames); // ����� ��� �ܾ���� List�����Ϳ� �� ���ش�
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new MyOwnDictionary();
		
	}

}
