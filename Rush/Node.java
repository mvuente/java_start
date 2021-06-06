import java.awt.Point;

public class Node {
	private Point _point;
	private long _gSum;
	private long _hSum;
	private long _fSum;
	private Node _previousNode;

	public Node(Point pt, Point target) {
		_previousNode = null;
		_point = pt;
		_gSum = 0;
		_hSum = (long)Math.abs(target.getX() - pt.getX()) + (long)Math.abs(target.getY() - pt.getY());
		_fSum = _gSum + _hSum;
	}

	public Node(Point pt, Point target, Node prev) {
		_point = pt;
		_gSum = prev.getGSum() + 1;
		_hSum = (long)Math.abs(target.getX() - pt.getX()) + (long)Math.abs(target.getY() - pt.getY());
		_fSum = _gSum + _hSum;
		_previousNode = prev;
	}

	public long getGSum() {
		return _gSum;
	}

	public long getFSum() {
		return _fSum;
	}

	public Node getPreviousNode() {
		return _previousNode;
	}

	public Point getPoint() {
		return _point;
	}

	public void updatePreviousNode(Node node) {
		_previousNode = node;
		_gSum = node.getGSum() + 1;
		_fSum = _gSum + _hSum;
	}
}