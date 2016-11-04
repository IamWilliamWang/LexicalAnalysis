import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

class DfaSimplifyInitiateException extends Exception {
	public DfaSimplifyInitiateException() {
		// TODO Auto-generated constructor stub
		super("DfaSimplify类初始化错误。请检查参数的正确性！");
	}
}

public class DfaSimplify {

	DfaSimplify() {
		nonFinalSet = new HashSet<String>();
		finalSet = new HashSet<String>();
		edges = new ArrayList<Edge>();
		changes = new ArrayList<String>();
		allSets = new HashSet<HashSet<String>>();

		System.out.println("输入非终态(逗号隔开)：");
		Scanner scanner = new Scanner(System.in);
		for (String str : scanner.nextLine().split(",")) {
			nonFinalSet.add(str);
		}

		System.out.println("输入终态(逗号隔开)：");
		for (String str : scanner.nextLine().split(",")) {
			finalSet.add(str);
		}

		System.out.println("输入DFA(node~change~final,输入回车结束)：");

		while (true) {
			String[] strs = scanner.nextLine().split("~");// 输入
			if (strs[0] == "-1" || strs.length < 3)// 防止错误
				return;
			String node = strs[0], change = strs[1], arrive = strs[2];// 分离出来的全部原始信息
			for (String t : change.split(","))
				Nfa2Dfa.insertOrContains(changes, t);// 增加中间信息
			/* 增加边信息 */
			for (String nodeTemp : node.split(",")) {
				for (String changeTemp : change.split(",")) {
					for (String arriveTemp : arrive.split(","))
						Nfa2Dfa.insertOrContains(edges, new Edge(nodeTemp,
								changeTemp, arriveTemp));
				}
			}
		}
	}
	
	DfaSimplify(ArrayList<String> inputStrings,String fina,String unfina) {
		nonFinalSet = new HashSet<String>();
		finalSet = new HashSet<String>();
		edges = new ArrayList<Edge>();
		changes = new ArrayList<String>();
		allSets = new HashSet<HashSet<String>>();

		System.out.println("输入非终态(逗号隔开)：");
		for (String str : unfina.split(",")) {
			nonFinalSet.add(str);
		}

		System.out.println("输入终态(逗号隔开)：");
		for (String str : fina.split(",")) {
			finalSet.add(str);
		}

		System.out.println("输入DFA(node~change~final,输入回车结束)：");

		for(int i=0;i<inputStrings.size();i++) {
			String[] strs = inputStrings.get(i).split("~");// 输入
			if (strs[0] == "-1" || strs.length < 3)// 防止错误
				return;
			String node = strs[0], change = strs[1], arrive = strs[2];// 分离出来的全部原始信息
			for (String t : change.split(","))
				Nfa2Dfa.insertOrContains(changes, t);// 增加中间信息
			/* 增加边信息 */
			for (String nodeTemp : node.split(",")) {
				for (String changeTemp : change.split(",")) {
					for (String arriveTemp : arrive.split(","))
						Nfa2Dfa.insertOrContains(edges, new Edge(nodeTemp,
								changeTemp, arriveTemp));
				}
			}
		}
	}
	/**
	 * 输入非终态字符串组，终态字符串组，所有Edge类型边的信息
	 * 
	 * @param nonFinalState
	 * @param finalState
	 * @param edges
	 * @throws Initiate
	 *             Exception
	 */
	public DfaSimplify(String[] nonFinalState, String[] finalState,
			ArrayList<Edge> edgesInformation)
			throws DfaSimplifyInitiateException {
		if (nonFinalState == null || finalState == null
				|| edgesInformation.isEmpty()) {
			throw new DfaSimplifyInitiateException();
		}
		nonFinalSet = new HashSet<String>();
		finalSet = new HashSet<String>();
		edges = (ArrayList<Edge>) edgesInformation.clone();
		changes = new ArrayList<String>();
		allSets = new HashSet<HashSet<String>>();

		for (String nonFinalSetElement : nonFinalState) {
			Nfa2Dfa.insertOrContains(nonFinalSet, nonFinalSetElement);
		}
		for (String finalSetElement : finalState) {
			Nfa2Dfa.insertOrContains(finalSet, finalSetElement);
		}
		for (Edge edge : edgesInformation) {
			Nfa2Dfa.insertOrContains(changes, edge.getChange());
		}
	}

	private void putStartAndEnd() {
		allSets.add(nonFinalSet);
		allSets.add(finalSet);
	}

	/**
	 * 将分离的元素进行分组
	 */
	private ArrayList<HashSet<String>> divideSetToChips(
			HashSet<String> divideSet, HashSet<String> thisSet) {
		ArrayList<HashSet<String>> dividedSets = new ArrayList<HashSet<String>>(); // 结果集合链表

		for (HashSet<String> oneOfAllSets : allSets) {
			if (oneOfAllSets.containsAll(thisSet)) // 如果遍历的是此集合
				continue;
			HashSet<String> tempSet = new HashSet<String>();
			for (String element : divideSet) {
				for (Edge edge : edges) {
					String to = null;
					if (edge.getFrom().equals(element))
						to = edge.getTo();
					if (oneOfAllSets.contains(to)) {
						tempSet.add(element);
					}
				}
			}
			if (!tempSet.isEmpty())
				dividedSets.add(tempSet);
		}
		return dividedSets;
	}

	public void dfaSimplify() {
		int length = 0;
		do {
			length = allSets.size();
			HashSet<String> getOutSet = new HashSet<String>();
			HashSet<HashSet<String>> newAllSets = new HashSet<HashSet<String>>();

			this.putStartAndEnd();
			for (String change : changes) {
				for (HashSet<String> thisSet : allSets) { // 得到每一个现存集合
					HashSet<String> oneOfAllSet = (HashSet<String>) thisSet
							.clone(); // 因为removeAll操作会改变原有allSets，所以需要clone一个副本
					for (String element : oneOfAllSet) { // 得到集合里每一个元素
						for (Edge edge : edges) { // 拿每一条边和元素进行规则对照
							if (edge.getFrom().equals(element)
									&& edge.getChange().equals(change)) { // 找到元素对应的规则
								if (!oneOfAllSet.contains(edge.getTo())) { // 如果能推到其他集合
									getOutSet.add(element);
								}
							}
						}
					}
					if (oneOfAllSet.size() > 1)
						if (oneOfAllSet.equals(getOutSet))
							Nfa2Dfa.insertOrContains(newAllSets, oneOfAllSet);
						else
							oneOfAllSet.removeAll(getOutSet);
					if (oneOfAllSet.isEmpty()) {
						getOutSet.clear();
						continue;
					}
					Nfa2Dfa.insertOrContains(newAllSets,
							(HashSet<String>) oneOfAllSet.clone());
					// System.out.println(newAllSets);
					ArrayList<HashSet<String>> getOutSetsGoBack = divideSetToChips(
							getOutSet, oneOfAllSet);
					if (!getOutSetsGoBack.isEmpty())
						for (HashSet<String> hash : getOutSetsGoBack)
							Nfa2Dfa.insertOrContains(newAllSets,
									(HashSet<String>) hash.clone());
					getOutSet.clear();
				}
			}
			allSets = newAllSets;
			// System.out.println(allSets);

		} while (allSets.size() != length);
	}

	/* 修复了存在赘余集合的bug */
	private void removeExtraSets() {
		HashSet<HashSet<String>> removeSets = new HashSet<HashSet<String>>();
		for (HashSet<String> fatherHash : allSets) {
			for (HashSet<String> hash : allSets) {
				if (!fatherHash.equals(hash) && fatherHash.containsAll(hash))
					removeSets.add(fatherHash);
			}
		}
		allSets.removeAll(removeSets);
	}

	public HashSet<HashSet<String>> getAllSets() {
		removeExtraSets();
		return allSets;
	}

	public void setNonFinalSet(HashSet<String> nonFinalSet) {
		this.nonFinalSet = nonFinalSet;
	}

	public void setFinalSet(HashSet<String> finalSet) {
		this.finalSet = finalSet;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DfaSimplify x = new DfaSimplify();
		x.dfaSimplify();
		System.out.println(x.getAllSets());
	}

	private HashSet<String> nonFinalSet;
	private HashSet<String> finalSet;
	private HashSet<HashSet<String>> allSets;
	private ArrayList<Edge> edges;
	private ArrayList<String> changes;
}
