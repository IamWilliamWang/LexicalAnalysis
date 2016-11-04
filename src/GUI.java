import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JSeparator;


public class GUI {

	private JFrame frame;
	private JTextField 输入textField_前;
	private JTextField 输入textField_转换;
	private JTextField 输入textField_后;

	private enum MODE{
		NFA转DFA,
		DFA化简,
		NFADFA化简,
		NULL
	};
	private MODE mode=MODE.NULL;
	private final String noNeedToInput = "无需输入";
	
	private ArrayList<String> inputStrings;
	private JTextField 非终态textField;
	private JTextField 终态textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		inputStrings = new ArrayList<String>();
		
		frame = new JFrame();
		frame.setFont(new Font("华文仿宋", Font.PLAIN, 15));
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setTitle("转换器");
		frame.setBounds(100, 100, 550, 380);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel 转换前Label = new JLabel("转换前状态");
		转换前Label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		转换前Label.setHorizontalAlignment(SwingConstants.CENTER);
		转换前Label.setBounds(101, 54, 79, 15);
		frame.getContentPane().add(转换前Label);
		
		JLabel 转换条件Label = new JLabel("转换条件");
		转换条件Label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		转换条件Label.setHorizontalAlignment(SwingConstants.CENTER);
		转换条件Label.setBounds(256, 54, 54, 15);
		frame.getContentPane().add(转换条件Label);
		
		JLabel 转换后Label = new JLabel("转换后状态");
		转换后Label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		转换后Label.setHorizontalAlignment(SwingConstants.CENTER);
		转换后Label.setBounds(413, 54, 71, 15);
		frame.getContentPane().add(转换后Label);
		
		JLabel 说明Label0 = new JLabel("说明：");
		说明Label0.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		说明Label0.setBounds(10, 32, 41, 15);
		frame.getContentPane().add(说明Label0);
		
		JLabel 说明Label1 = new JLabel("");
		说明Label1.setHorizontalAlignment(SwingConstants.CENTER);
		说明Label1.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		说明Label1.setBounds(80, 32, 123, 15);
		frame.getContentPane().add(说明Label1);
		
		JLabel 说明Label2 = new JLabel("");
		说明Label2.setHorizontalAlignment(SwingConstants.CENTER);
		说明Label2.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		说明Label2.setBounds(201, 32, 179, 15);
		frame.getContentPane().add(说明Label2);
		
		JLabel 说明Label3 = new JLabel("");
		说明Label3.setHorizontalAlignment(SwingConstants.CENTER);
		说明Label3.setFont(new Font("微软雅黑", Font.PLAIN, 10));
		说明Label3.setBounds(379, 32, 132, 15);
		frame.getContentPane().add(说明Label3);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 534, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu 文件menu = new JMenu("文件");
		文件menu.setFont(new Font("华文新魏", Font.PLAIN, 15));
		文件menu.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(文件menu);
		
		JTextArea 输出结果textArea = new JTextArea();
		JScrollPane jScrollPane = new JScrollPane(输出结果textArea);
		输出结果textArea.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		输出结果textArea.setToolTipText("输出结果");
		jScrollPane.setBounds(10, 174, 524, 167);
		frame.getContentPane().add(jScrollPane);
		
		JMenuItem 查看输入内容menuItem = new JMenuItem("查看输入内容");
		查看输入内容menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StringBuffer sb = new StringBuffer("边：\n");
				for(String str : inputStrings) {
					sb.append(str+"\n");
				}
				if(!非终态textField.getText().equals(noNeedToInput) && !非终态textField.getText().equals(""))
					sb.append("非终：\n"+非终态textField.getText());
				if(!终态textField.getText().equals(noNeedToInput) && !终态textField.getText().equals(""))
					sb.append("终：\n"+终态textField.getText());
				JOptionPane.showMessageDialog(frame, sb);
			}
		});
		查看输入内容menuItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		查看输入内容menuItem.setHorizontalAlignment(SwingConstants.CENTER);
		文件menu.add(查看输入内容menuItem);
		
		JMenuItem 清空输出menuItem = new JMenuItem("清空输出框");
		清空输出menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				输出结果textArea.setText("");
			}
		});
		清空输出menuItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		文件menu.add(清空输出menuItem);
		
		JMenuItem 退出_MenuItem = new JMenuItem("退出");
		文件menu.add(退出_MenuItem);
		退出_MenuItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		退出_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenu menu = new JMenu("模式选择");
		menu.setFont(new Font("华文新魏", Font.PLAIN, 15));
		menuBar.add(menu);
		
		JMenuItem NFA转DFA_MenuItem = new JMenuItem("NFA转DFA");
		NFA转DFA_MenuItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		NFA转DFA_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mode=MODE.NFA转DFA;
				frame.setTitle("转换器-NFA转DFA");
				非终态textField.setEnabled(false);
				终态textField.setEnabled(false);
				非终态textField.setText(noNeedToInput);
				终态textField.setText(noNeedToInput);
				说明Label1.setText("NFA每条边开始结点名称");
				说明Label2.setText("NFA每条边转换条件名称(ε用eps代替)");
				说明Label3.setText("NFA每条边到达结点名称");
			}
		});
		menu.add(NFA转DFA_MenuItem);
		
		JMenuItem DFA化简_MenuItem = new JMenuItem("DFA化简");
		DFA化简_MenuItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		DFA化简_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode=MODE.DFA化简;
				frame.setTitle("转换器-DFA化简");
				非终态textField.setText("");
				终态textField.setText("");
				非终态textField.setEnabled(true);
				终态textField.setEnabled(true);
				说明Label1.setText("开始结点名称(DFA)");
				说明Label2.setText("转换条件名称(DFA)");
				说明Label3.setText("到达结点名称(DFA)");
			}
		});
		menu.add(DFA化简_MenuItem);
		
		JMenuItem NFADFA化简_MenuItem = new JMenuItem("NFA-DFA-化简");
		NFADFA化简_MenuItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		NFADFA化简_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode=MODE.NFADFA化简;
				frame.setTitle("转换器-NFA-DFA-化简");
				非终态textField.setEnabled(false);
				非终态textField.setText(noNeedToInput);
				终态textField.setEnabled(true);
				终态textField.setText("");
				说明Label1.setText("开始结点名称(NFA)");
				说明Label2.setText("转换条件名称(NFA)");
				说明Label3.setText("到达结点名称(NFA)");
			}
		});
		menu.add(NFADFA化简_MenuItem);
		
		JMenu 帮助_MenuItem = new JMenu("帮助");
		帮助_MenuItem.setFont(new Font("华文新魏", Font.PLAIN, 15));
		menuBar.add(帮助_MenuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("关于开发者");
		menuItem_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "2143521 王劲翔");
			}
		});
		
		JMenuItem 如何使用menuItem = new JMenuItem("如何使用");
		如何使用menuItem.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		如何使用menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, 
						"以该DFA简化为例。此图片为终\n"+
								"端输入形式，在此UI内需要在非\n"+
								"终态输入栏内输入S,A,B，在终\n"+
								"态栏输入C,D,E,F，然后输入在\n"+
								"输入栏单行输入（无需加~），\n"+
								"每行输入完成需要点击继续输\n"+
								"入。最后输入完成请点击执行\n"+
								"计算。","如何使用",
								JOptionPane.OK_OPTION,new ImageIcon("helppict.jpg"));
			}
		});
		帮助_MenuItem.add(如何使用menuItem);
		帮助_MenuItem.add(menuItem_1);
		
		输入textField_前 = new JTextField();
		输入textField_前.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		输入textField_前.setBounds(86, 79, 106, 21);
		frame.getContentPane().add(输入textField_前);
		输入textField_前.setColumns(10);
		
		输入textField_转换 = new JTextField();
		输入textField_转换.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		输入textField_转换.setBounds(222, 79, 133, 21);
		frame.getContentPane().add(输入textField_转换);
		输入textField_转换.setColumns(10);
		
		输入textField_后 = new JTextField();
		输入textField_后.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		输入textField_后.setBounds(388, 79, 123, 21);
		frame.getContentPane().add(输入textField_后);
		输入textField_后.setColumns(10);
		
		JLabel 输入_Label = new JLabel("输入：");
		输入_Label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		输入_Label.setBounds(10, 82, 54, 15);
		frame.getContentPane().add(输入_Label);
		
		JButton 继续_Button = new JButton("继续输入");
		继续_Button.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		继续_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch(mode) {
					case NFA转DFA:case DFA化简:case NFADFA化简:
						inputStrings.add(输入textField_前.getText()+
								"~"+输入textField_转换.getText()+"~"+输入textField_后.getText());
						输入textField_前.setText("");
						输入textField_转换.setText("");
						输入textField_后.setText("");
						break;
					default:
						JOptionPane.showMessageDialog(frame, "请先选择模式。");
						break;
				}
			}
		});
		继续_Button.setToolTipText("将输入的内容储存，并清空框内内容。");
		继续_Button.setBounds(158, 141, 93, 23);
		frame.getContentPane().add(继续_Button);
		
		JLabel 结果_Label = new JLabel("输出结果：");
		结果_Label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		结果_Label.setBounds(10, 159, 71, 15);
		frame.getContentPane().add(结果_Label);
		
		JButton 运算_Button = new JButton("执行运算");
		运算_Button.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		运算_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!输入textField_前.getText().equals("") || 
						!输入textField_转换.getText().equals("") || !输入textField_后.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "请先按下继续输入按钮。");
					return ;
				}
				if(inputStrings.isEmpty())	{
					JOptionPane.showMessageDialog(frame, "错误！找不到输入的数据。");
					return ;
				}
				
				switch(mode) {
					case NFA转DFA:
						Nfa2Dfa x = new Nfa2Dfa(inputStrings);
						x.nfa2Dfa();
						String result = "";
						result+='[';
						for(HashSet<String>hashset : x.getDfaSets()) {
							result+='[';
							for(String str : hashset) {
								result+=str+',';
							}
							if(result.charAt(result.length()-1) == ',') {
								result = result.substring(0, result.length()-1);
							}
							result+=']';
						}
						result+=']';
						输出结果textArea.setText(result);
						break;
					case DFA化简:
						DfaSimplify y = new DfaSimplify(
								inputStrings,终态textField.getText(),非终态textField.getText());
						y.dfaSimplify();
						System.out.println(y.getAllSets());
						输出结果textArea.setText(y.getAllSets().toString());
						break;
					case NFADFA化简:
						/* NFA到DFA */
						Main.getNonFinalStateAndFinalState(终态textField.getText());
						Nfa2Dfa z = new Nfa2Dfa(inputStrings);
						z.nfa2Dfa();
						ArrayList<HashSet<String>> dfaSets = z.getDfaSets();

						/* 集合重命名 */
						HashMap<String, String[]> recieveReturns = Main.renameAllSets(dfaSets,
								z.getNewEdges());
						String[] nonNames = recieveReturns.get(Main.nonNamesKey);
						String[] finalNames = recieveReturns.get(Main.finalNamesKey);

						DfaSimplify z_2 = null;
						/* DFA的化简 */
						try {
							z_2 = new DfaSimplify(nonNames, finalNames, Main.newEdges);
							// y.setNonFinalSet(nonFinalSet);
							// y.setFinalSet(finalSet);
							z_2.dfaSimplify();
							System.out.println(z_2.getAllSets());
						} catch (DfaSimplifyInitiateException e_2) {
							// TODO Auto-generated catch block
							System.out.println(e_2);
						}
						System.out.println(Main.name2SetMap);
						输出结果textArea.setText(z_2.getAllSets()+"\n\n"+Main.name2SetMap.toString());
						break;
					default:
						JOptionPane.showMessageDialog(frame, "请先选择模式。");
						break;
				}
				
				inputStrings.clear();
				if(mode==MODE.NFADFA化简) {
					Main.clearAll();
				}
			}
		});
		运算_Button.setBounds(329, 141, 93, 23);
		frame.getContentPane().add(运算_Button);
		
		非终态textField = new JTextField();
		非终态textField.setToolTipText("填写非终态的结点，用英文逗号隔开");
		非终态textField.setBounds(96, 110, 155, 21);
		frame.getContentPane().add(非终态textField);
		非终态textField.setColumns(10);
		
		JLabel 非终态label = new JLabel("非终态：");
		非终态label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		非终态label.setBounds(27, 113, 54, 15);
		frame.getContentPane().add(非终态label);
		
		JLabel 终态label = new JLabel("终态：");
		终态label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		终态label.setBounds(274, 113, 41, 15);
		frame.getContentPane().add(终态label);
		
		终态textField = new JTextField();
		终态textField.setToolTipText("填写终态的结点，用英文逗号隔开");
		终态textField.setColumns(10);
		终态textField.setBounds(318, 110, 166, 21);
		frame.getContentPane().add(终态textField);
	}
}
