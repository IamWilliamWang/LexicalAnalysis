import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

	final static String nonNamesKey = "nonNames";
	final static String finalNamesKey = "finalNames";

	private enum State {
		终态, 非终态
	};

	private static String[] FinalStates;
	static HashMap<String, HashSet<String>> name2SetMap;
	static ArrayList<Edge> newEdges = new ArrayList<Edge>();
	private static HashSet<String> nonFinalSet = new HashSet<String>();
	private static HashSet<String> finalSet = new HashSet<String>();

	private static void getNonFinalStateAndFinalState() {
		System.out.println("输入终态(逗号隔开)：");
		FinalStates = new Scanner(System.in).nextLine().split(",");
	}

	static void getNonFinalStateAndFinalState(String fina) {
		FinalStates = fina.split(",");
	}
	
	private static boolean contains(String[] strs, String str) {
		boolean have = false;
		for (String s : strs) {
			if (s.equals(str))
				have = true;
		}
		return have;
	}

	static HashMap<String, String[]> renameAllSets(
			ArrayList<HashSet<String>> dfaSets,
			ArrayList<Edge_Set> oldEdgesWithSet) {
		/* 把终态与非终态分成两个集合 */
		HashSet<HashSet<String>> nonFinalSets = new HashSet<HashSet<String>>();
		HashSet<HashSet<String>> finalSets = new HashSet<HashSet<String>>();
		for (HashSet<String> everyDfaSet : dfaSets) {
			State state = State.非终态;
			for (String element : everyDfaSet) {
				if (contains(FinalStates, element)) { // 找到这个集合包含终态元素
					state = State.终态;
				}
			}
			if (state == State.终态) {
				finalSets.add(everyDfaSet);
			} else {
				nonFinalSets.add(everyDfaSet);
			}
		}

		/* 给这些集合命名 */
		String[] newSetNames = new String[26];
		char ch = 'A';
		for (int i = 0; i < 26; i++)
			newSetNames[i] = new String("集合" + ch++);

		/* 将集合的名字储存起来 */
		name2SetMap = new HashMap<String, HashSet<String>>();
		int setIndex = 0;
		for (HashSet<String> nonSet : nonFinalSets) {
			name2SetMap.put(newSetNames[setIndex++], nonSet);
		}
		for (HashSet<String> finalSet : finalSets) {
			name2SetMap.put(newSetNames[setIndex++], finalSet);
		}

		/* 将名字重新排列成一个字符串，以便给DFA化简的构造函数使用 */
		String[] nonNames = new String[nonFinalSets.size()];
		String[] finalNames = new String[finalSets.size()];

		ch = 'A';
		for (int i = 0; i < nonFinalSets.size(); i++)
			nonNames[i] = new String("集合" + ch++);
		for (int i = 0; i < finalSets.size(); i++)
			finalNames[i] = new String("集合" + ch++);

		/* 将非终态和终态扔给DfaSimplify类 */
		// for(String str:nonNames) {
		// nonFinalSet.add(str);
		// }
		// for(String str:finalNames) {
		// finalSet.add(str);
		// }

		/* 把最终结果全部扔回主函数 */
		HashMap<String, String[]> returns = new HashMap<String, String[]>();
		returns.put(nonNamesKey, nonNames);
		returns.put(finalNamesKey, finalNames);
		changeSetEdgesName(oldEdgesWithSet);
		return returns;
	}

	private static void changeSetEdgesName(ArrayList<Edge_Set> oldEdgesWithSet) {
		// System.out.println(oldEdgesWithSet);
		// System.out.println(name2SetMap);
		newEdges.clear();
		for (Edge_Set edgeWithSet : oldEdgesWithSet) { // 遍历原来的信息
			String startName = null, endName = null;
			for (String keyName : name2SetMap.keySet()) { // 遍历key
				if (edgeWithSet.getFrom().equals(name2SetMap.get(keyName))) { // 如果边属性内出现了这个集合
					startName = keyName;// 则把这个集合改名字
				}
				if (edgeWithSet.getTo().equals(name2SetMap.get(keyName))) {
					endName = keyName;
				}
			}
			newEdges.add(new Edge(startName, edgeWithSet.getChange(), endName));
		}
	}

	static void clearAll() {
		name2SetMap.clear();
		newEdges.clear();
		nonFinalSet.clear();
		finalSet.clear();
	}
	
	public static void main(String[] args) {
		/* NFA到DFA */
		getNonFinalStateAndFinalState();
		Nfa2Dfa x = new Nfa2Dfa();
		x.nfa2Dfa();
		ArrayList<HashSet<String>> dfaSets = x.getDfaSets();

		/* 集合重命名 */
		HashMap<String, String[]> recieveReturns = renameAllSets(dfaSets,
				x.getNewEdges());
		String[] nonNames = recieveReturns.get(nonNamesKey);
		String[] finalNames = recieveReturns.get(finalNamesKey);

		/* DFA的化简 */
		try {
			DfaSimplify y = new DfaSimplify(nonNames, finalNames, newEdges);
			// y.setNonFinalSet(nonFinalSet);
			// y.setFinalSet(finalSet);
			y.dfaSimplify();
			System.out.println(y.getAllSets());
		} catch (DfaSimplifyInitiateException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		System.out.println(name2SetMap);
	}
}
