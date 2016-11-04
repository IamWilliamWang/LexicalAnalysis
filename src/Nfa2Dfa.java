import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

public class Nfa2Dfa {

	public Nfa2Dfa() {
		// TODO Auto-generated constructor stub
		// 构造函数输入边的属性
		changes = new ArrayList<String>();
		edges = new ArrayList<Edge>();
		EdgesWithSet = new ArrayList<Edge_Set>();
		dfaSets = new ArrayList<HashSet<String>>();

		System.out.println("输入NFA(node~change~final,输入回车结束,eps代表epsilon)：");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String[] strs = scanner.nextLine().split("~");// 输入
			if (strs[0] == "-1" || strs.length < 3)// 防止错误
				return;
			String node = strs[0], change = strs[1], arrive = strs[2];// 分离出来的全部原始信息
			for (String t : change.split(","))
				insertOrContains(changes, t);// 增加中间信息
			changes.remove("eps");// 去掉epsilon
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

	public Nfa2Dfa(ArrayList<String> inputStrings) {
		// TODO Auto-generated constructor stub
		// 构造函数输入边的属性
		changes = new ArrayList<String>();
		edges = new ArrayList<Edge>();
		EdgesWithSet = new ArrayList<Edge_Set>();
		dfaSets = new ArrayList<HashSet<String>>();
		
		for(int i=0;i<inputStrings.size();i++) {
			String[] strs = inputStrings.get(i).split("~");// 输入
			if (strs[0] == "-1" || strs.length < 3)// 防止错误
				return;
			String node = strs[0], change = strs[1], arrive = strs[2];// 分离出来的全部原始信息
			for (String t : change.split(","))
				insertOrContains(changes, t);// 增加中间信息
			changes.remove("eps");// 去掉epsilon
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
	
	public static <T> boolean insertOrContains(Collection<T> collection,
			T element) {
		if (collection.contains(element)) {
			return false;
		} else {
			collection.add(element);
			return true;
		}
	}

	private HashSet<String> getFitstElement() {
		HashSet<String> firstSet = new HashSet<String>();
		firstSet.add(edges.get(0).getFrom());
		return firstSet;

	}

	private HashSet<String> epsilon(HashSet<String> old_Set) {
		HashSet<String> oldSet = (HashSet<String>) old_Set.clone();
		HashSet<String> newSet = (HashSet<String>) old_Set.clone();
		boolean existNewSet = false;
		do {
			existNewSet = false;
			for (Edge edge : edges) { // 遍历一遍
				if (edge.getChange().equals("eps")) {
					for (String fromWhere : oldSet) {
						if (edge.getFrom().equals(fromWhere)) {
							newSet.add(edge.getTo());
							if (!newSet.equals(oldSet)) {
								existNewSet = true;
							}
						}
					}
				}
			}
			oldSet = newSet;
		} while (existNewSet);
		return newSet;
	}

	private HashSet<String> move(HashSet<String> old_Set, String change) {
		HashSet<String> newSet = new HashSet<String>();
		for (String element : old_Set) {
			for (Edge edge : edges) {
				// System.out.println("Rule:" + edge.getFrom() +
				// edge.getChange()
				// + edge.getTo());
				if (edge.getFrom().equals(element)) {
					// System.out.println("Element:" + element + change);
					if (edge.getChange().equals(change)) {
						newSet.add(edge.getTo());
						// System.out.println(newSet);
					}
				}
			}
		}
		return newSet;
	}

	public void nfa2Dfa() {
		HashSet<String> hashset, newSet;
		// 得到初态
		hashset = getFitstElement();
		hashset = epsilon(hashset);
		dfaSets.add(hashset);// 加入首元素
		// 开始循环
		int i = 0;
		try {
			while (true) {
				hashset = dfaSets.get(i++);
				for (String cha : changes) { // 每次遍历即生成一行 原集合-Ia-Ib
					newSet = this.move(hashset, cha);
					newSet = this.epsilon(newSet);
					if (!newSet.equals(hashset) && !newSet.isEmpty()) {
						insertOrContains(dfaSets, newSet);
						EdgesWithSet.add(new Edge_Set(hashset, cha, newSet));
						// System.out.println(dfaSets);
					}
				}
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			// System.out.println(e);
		}
	}

	public ArrayList<Edge_Set> getNewEdges() {
		return EdgesWithSet;
	}

	public ArrayList<HashSet<String>> getDfaSets() {
		return dfaSets;
	}

	public static void main(String[] args) {
		Nfa2Dfa x = new Nfa2Dfa();
		x.nfa2Dfa();
		System.out.println(x.getDfaSets());
		return;
	}

	private ArrayList<String> changes;
	private ArrayList<Edge> edges;
	private ArrayList<Edge_Set> EdgesWithSet;
	private ArrayList<HashSet<String>> dfaSets;
}
