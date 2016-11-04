package abort;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JEditorPane;
import java.awt.FlowLayout;


public class LexicalAnalysisGUI {

	private JFrame frame;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LexicalAnalysisGUI window = new LexicalAnalysisGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LexicalAnalysisGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("转换器");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu 菜单_JMenu = new JMenu("菜单");
		menuBar.add(菜单_JMenu);
		
		JMenuItem Nfadfa_MenuItem = new JMenuItem("NFA转换为DFA");
		Nfadfa_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		菜单_JMenu.add(Nfadfa_MenuItem);
		
		JMenuItem Dfa化简_MenuItem = new JMenuItem("DFA的化简");
		Dfa化简_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		菜单_JMenu.add(Dfa化简_MenuItem);
		
		JMenuItem Combination_MenuItem = new JMenuItem("NFA-DFA-化简");
		Combination_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		菜单_JMenu.add(Combination_MenuItem);
		
		JMenuItem 退出_MenuItem = new JMenuItem("退出");
		退出_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		菜单_JMenu.add(退出_MenuItem);
		
		JMenu 帮助_JMenu = new JMenu("帮助");
		menuBar.add(帮助_JMenu);
		
		JMenuItem 帮助_MenuItem = new JMenuItem("帮助");
		帮助_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		帮助_JMenu.add(帮助_MenuItem);
		
		JMenuItem 关于_MenuItem = new JMenuItem("关于");
		关于_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "2143521 王劲翔");
			}
		});
		帮助_JMenu.add(关于_MenuItem);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0};
		
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel TopPanel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) TopPanel_1.getLayout();
		GridBagConstraints gridBagConstraints_TopPanel_1 = new GridBagConstraints();
		gridBagConstraints_TopPanel_1.insets = new Insets(0, 0, 5, 5);
		gridBagConstraints_TopPanel_1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_TopPanel_1.gridx = 0;
		gridBagConstraints_TopPanel_1.gridy = 0;
		frame.getContentPane().add(TopPanel_1, gridBagConstraints_TopPanel_1);
		
		JLabel From_Label = new JLabel("起始");
		TopPanel_1.add(From_Label);
		
		JPanel TopPanel_2 = new JPanel();
		GridBagConstraints gridBagConstraints_TopPanel_2 = new GridBagConstraints();
		gridBagConstraints_TopPanel_2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_TopPanel_2.insets = new Insets(0, 0, 5, 5);
		gridBagConstraints_TopPanel_2.gridx = 1;
		gridBagConstraints_TopPanel_2.gridy = 0;
		gridBagConstraints_TopPanel_2.weightx = 0;
		gridBagConstraints_TopPanel_2.weighty = 0;
		frame.getContentPane().add(TopPanel_2, gridBagConstraints_TopPanel_2);
		
		JLabel Change_Label = new JLabel("变换条件");
		TopPanel_2.add(Change_Label);
		
		JPanel TopPanel_3 = new JPanel();
		GridBagConstraints gridBagConstraints_TopPanel_3 = new GridBagConstraints();
		gridBagConstraints_TopPanel_3.gridwidth = 0;
		gridBagConstraints_TopPanel_3.insets = new Insets(0, 0, 5, 0);
		gridBagConstraints_TopPanel_3.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_TopPanel_3.gridx = 2;
		gridBagConstraints_TopPanel_3.gridy = 0;
		gridBagConstraints_TopPanel_3.weightx = 0;
		gridBagConstraints_TopPanel_3.weighty = 0;
		frame.getContentPane().add(TopPanel_3, gridBagConstraints_TopPanel_3);
		
		JPanel MiddlePanel_1 = new JPanel();
		GridBagConstraints gbc_MiddlePanel_1 = new GridBagConstraints();
		gbc_MiddlePanel_1.insets = new Insets(0, 0, 0, 5);
		gbc_MiddlePanel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_MiddlePanel_1.gridx = 0;
		gbc_MiddlePanel_1.gridy = 1;
		gbc_MiddlePanel_1.weightx = 0;
		gbc_MiddlePanel_1.weighty = 0;
		frame.getContentPane().add(MiddlePanel_1, gbc_MiddlePanel_1);
		
		textField_1 = new JTextField();
		MiddlePanel_1.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel To_Label = new JLabel("终点");
		TopPanel_3.add(To_Label);
		
		JPanel MiddlePanel_2 = new JPanel();
		GridBagConstraints gbc_MiddlePanel_2 = new GridBagConstraints();
		gbc_MiddlePanel_2.insets = new Insets(0, 0, 0, 5);
		gbc_MiddlePanel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_MiddlePanel_2.gridx = 1;
		gbc_MiddlePanel_2.gridy = 1;
		gbc_MiddlePanel_2.weightx = 0;
		gbc_MiddlePanel_2.weighty = 0;
		frame.getContentPane().add(MiddlePanel_2, gbc_MiddlePanel_2);
		
		textField_2 = new JTextField();
		MiddlePanel_2.add(textField_2);
		textField_2.setColumns(10);
		
		JPanel MiddlePanel_3 = new JPanel();
		GridBagConstraints gbc_MiddlePanel_3 = new GridBagConstraints();
		gbc_MiddlePanel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_MiddlePanel_3.gridwidth=0;
		gbc_MiddlePanel_3.gridx = 2;
		gbc_MiddlePanel_3.gridy = 1;
		gbc_MiddlePanel_3.weightx = 0;
		gbc_MiddlePanel_3.weighty = 0;
		frame.getContentPane().add(MiddlePanel_3, gbc_MiddlePanel_3);
		
		textField_3 = new JTextField();
		MiddlePanel_3.add(textField_3);
		textField_3.setColumns(10);
		
		JPanel ResultPanel = new JPanel();
		GridBagConstraints gbc_ResultPanel = new GridBagConstraints();
		gbc_ResultPanel.gridwidth = 0;
		gbc_ResultPanel.insets = new Insets(0, 0, 0, 5);
		gbc_ResultPanel.gridx = 0;
		gbc_ResultPanel.gridy = 2;
		gbc_ResultPanel.weightx = 1.0;
		gbc_ResultPanel.weighty = 1;
		frame.getContentPane().add(ResultPanel, gbc_ResultPanel);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("0");
		ResultPanel.add(textArea);
	}
}

//http://www.cnblogs.com/taoweiji/archive/2012/12/14/2818787.html