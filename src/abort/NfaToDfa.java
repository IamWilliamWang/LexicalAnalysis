package abort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

class Edge {
	private String from;
	private String change;
	private String to;
	public Edge(String from, String change, String to) {
		super();
		this.from = from;
		this.change = change;
		this.to = to;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (change == null) {
			if (other.change != null)
				return false;
		} else if (!change.equals(other.change))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
}


public class NfaToDfa {

	public NfaToDfa() {
		// TODO Auto-generated constructor stub
		// 构造函数输入边的属性
		// nodes = new ArrayList<String>();
		changes = new ArrayList<String>();
		// arrives = new ArrayList<String>();
		edges = new ArrayList<Edge>();
		dfaSets = new ArrayList<HashSet<String>>();

		System.out.println("输入NFA(node~change~final,输入-1结束,eps代表epsilon)：");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String[] strs = scanner.nextLine().split("~");// 输入
			if (strs[0] == "-1" || strs.length < 3)// 防止错误
				return;
			String node = strs[0], change = strs[1], arrive = strs[2];// 分离出来的全部原始信息
			/*
			 * for (String t : node.split(",")) insertOrContains(nodes,
			 * t);//增加初始节点信息 for (String t : arrive.split(","))
			 * insertOrContains(arrives, t);//增加目的节点信息
			 */
			for (String t : change.split(","))
				insertOrContains(changes, t);// 增加中间信息
			
			
			/* 增加边信息 */
			for (String nodeTemp : node.split(",")) {
				for (String changeTemp : change.split(",")) {
					for (String arriveTemp : arrive.split(","))
						insertOrContains(edges, new Edge(nodeTemp, changeTemp,
								arriveTemp));
				}
			}
		}
	}

	/**
	 * 如果有此元素则返回false，如果没有则添加后返回true
	 * 
	 * @param list
	 * @param element
	 * @return
	 */
	private <T> boolean insertOrContains(List<T> list, T element) {

		if (list.contains(element)) {
			return false;
		} else {
			list.add(element);
			return true;
		}
	}

	private void nfa2dfa() {
		HashSet<String> oneOfDfaSets = new HashSet<>();
		// int lastSizeofDnaSets = 0;
		// epsilon 闭包
		// do {
		//
		// } while (lastSizeofDnaSets != oneOfDnaSets.size());
		oneOfDfaSets = getFirstSet();
		dfaSets.add(oneOfDfaSets); // add first set
		// System.out.println(dfaSets.toString());

		HashSet<String> newSet = new HashSet<>();
		int index = 0;
		try {
			while (true) {
				for (String changeTemp : changes) {
					newSet = move(oneOfDfaSets, changeTemp);
					if (!dfaSets.contains(newSet) && !newSet.isEmpty()) {
						dfaSets.add(newSet);
					}
				}
				oneOfDfaSets = dfaSets.get(++index);
			}
		} catch (IndexOutOfBoundsException e) {  //溢出则跳出循环
			
//			System.out.println(e);
		}
	}

	// return newSet
	private HashSet<String> move(HashSet<String> originalSet, String change,
			 boolean epsilonMode) {
		HashSet<String> newSet=null;
//		for (int i = 0; i < Step; i++) {
			switch (epsilonMode == true ? 1 : 0) {
			case 1:
				newSet=(HashSet<String>) originalSet.clone();
				for (Edge edge : edges) {
					if (edge.getChange().equals("eps")) { // 如果转换条件正确
						for (String str : originalSet) {
							if (str.equals(edge.getFrom())) // 如果也存在起始相同的节点
							{
								newSet.add(edge.getTo());
								//epsilon闭包的追加
								for(int i=0;i<3;i++)//只能处理三个epsilon闭包
								for(Edge edge2:edges) {
									if(edge2.getChange().equals("eps")&& edge.getTo().equals(edge2.getFrom())){
										newSet.add(edge2.getTo());
									}
								}
							}
						}
					}
				}
				newSet.add("hhh");
				break;
			case 0:
				/*
				newSet=new HashSet<String>();
				for (Edge edge : edges) {
					if (edge.getChange().equals(change)) { // 如果转换条件正确
						
						for (String str : originalSet) {
							System.out.println("规则："+edge.getFrom()+edge.getChange()+edge.getTo());
							System.out.println("元素："+str+change);
							if (str.equals(edge.getFrom())) {
								newSet.add(edge.getTo());
								System.out.println(newSet);
							}
						}
					}
				}
				*/
				newSet = new HashSet<String>();
				for(String str:originalSet) {
					
					for(Edge edge :edges) {
						System.out.println("元素："+str+change);
						System.out.println("规则："+edge.getFrom()+edge.getChange()+edge.getTo());
						if(edge.getFrom().equals(str) && edge.getChange().equals(change)) {
							newSet.add(edge.getTo());
							System.out.println(newSet);
						}
					}
				}
				newSet.add("ttt");
				break;
			}
		
		return newSet;
	}

	private HashSet<String> move(HashSet<String> originalSet, String change) {
		return move(originalSet, change, false);
	}

	private HashSet<String> getFirstSet() {
		HashSet<String> hs = new HashSet<String>();
		hs.add(edges.get(0).getFrom());

		return move(hs, "eps", true);
	}

	// private ArrayList<String> nodes;
	private ArrayList<String> changes;
	// private ArrayList<String> arrives;
	private ArrayList<Edge> edges;
	private ArrayList<HashSet<String>> dfaSets;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NfaToDfa x = new NfaToDfa();
		x.nfa2dfa();
		System.out.println(x.dfaSets);
		return;
	}

}
