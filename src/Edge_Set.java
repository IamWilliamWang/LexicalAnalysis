import java.util.HashSet;

public class Edge_Set {

	private HashSet<String> from;
	private String change;
	private HashSet<String> to;

	public Edge_Set(HashSet<String> from, String change, HashSet<String> to) {
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
		Edge_Set other = (Edge_Set) obj;
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

	@Override
	public String toString() {
		return "(Edge_Set)[" + from + "," + change + "," + to + "]";
	}

	public HashSet<String> getFrom() {
		return from;
	}

	public String getChange() {
		return change;
	}

	public HashSet<String> getTo() {
		return to;
	}

	public void setFrom(HashSet<String> from) {
		this.from = from;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public void setTo(HashSet<String> to) {
		this.to = to;
	}

}
