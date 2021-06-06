import java.awt.Point;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Pathfinder {
	private GameMap _map;
	private Point _target;
	private Point _start;

	private boolean _finded;
	private boolean[][] _not_visited;
	private Node[][] _nodes;
	private Queue<Node> _que;
	private Point _end;

	public Pathfinder(GameMap map, Point start, Point target) {
		_map = map;
		_target = target;
		_start = start;

		_finded = false;
		_not_visited = new boolean[_map.getSize()][_map.getSize()];
		_nodes = new Node[_map.getSize()][_map.getSize()];
		_que = new PriorityQueue<>(1, comp);
		_end = null;
	}

	public void changeStart(Point start) {
		_start = start;
	}

	public void changeTarget(Point target) {
		_target = target;
	}

	public void updateMap(GameMap map) {
		_map = map;
	}

	private boolean checkPoint(Point point, Point pt, Node nd) {
		GameMap.Tokens tk;
		
		if ((tk = _map.getAt(point)) == GameMap.Tokens.PLAYER) {
			_finded = true;
			_end = pt;
		}
		if (tk == GameMap.Tokens.SPACE) {
			Node new_node = new Node(point, _target, nd);
			if (_nodes[(int)point.getY()][(int)point.getX()] != null) {
				if (new_node.getFSum() < _nodes[(int)point.getY()][(int)point.getX()].getFSum()) {
					_nodes[(int)point.getY()][(int)point.getX()] = new_node;
					_que.add(new_node);
				}
			}
			else {
				_nodes[(int)point.getY()][(int)point.getX()] = new_node;
				_que.add(new_node);
			}
		}
		return _finded;
	}

	public Point nextPoint() {
		_finded = false;
		for (int i = 0; i < _map.getSize(); i++)
			for (int j = 0; j < _map.getSize(); j++)
			{
				_nodes[i][j] = null;
				_not_visited[i][j] = true;
			}

		_nodes[(int)_start.getY()][(int)_start.getX()] = new Node(_start, _target);
		_que.add(_nodes[(int)_start.getY()][(int)_start.getX()]);
		while (_que.size() > 0) {
			Node nd = _que.poll();
			Point pt = nd.getPoint();

			Point up = new Point((int)pt.getX(), (int)pt.getY() + 1);
			Point down = new Point((int)pt.getX(), (int)pt.getY() - 1);
			Point left = new Point((int)pt.getX() - 1, (int)pt.getY());
			Point right = new Point((int)pt.getX() + 1, (int)pt.getY());

			if ((int)up.getY() > -1 && (int)up.getY() < _map.getSize() && (int)up.getX() > -1 && (int)up.getX() < _map.getSize())
				if (_not_visited[(int)up.getY()][(int)up.getX()] && checkPoint(up, pt, nd))
					break;
			if ((int)down.getY() > -1 && (int)down.getY() < _map.getSize() && (int)down.getX() > -1 && (int)down.getX() < _map.getSize())
				if (_not_visited[(int)down.getY()][(int)down.getX()] && checkPoint(down, pt, nd))
					break;
			if ((int)left.getY() > -1 && (int)left.getY() < _map.getSize() && (int)left.getX() > -1 && (int)left.getX() < _map.getSize())
				if (_not_visited[(int)left.getY()][(int)left.getX()] && checkPoint(left, pt, nd))
					break;
			if ((int)right.getY() > -1 && (int)right.getY() < _map.getSize() && (int)right.getX() > -1 && (int)right.getX() < _map.getSize())
				if (_not_visited[(int)right.getY()][(int)right.getX()] && checkPoint(right, pt, nd))
					break;

			_not_visited[(int)pt.getY()][(int)pt.getX()] = false;
		}
		if (_finded) {
			if (_end == _start)
				return _target;
			Node old = _nodes[(int)_end.getY()][(int)_end.getX()];
			while (old.getPreviousNode().getPoint() != _start)
				old = old.getPreviousNode();
			_que.clear();
			return old.getPoint();
		}
		_que.clear();
		return _start;
	}

	public static Comparator<Node> comp = new Comparator<Node>() {
		public int compare(Node c1, Node c2) {
			return (int)(c1.getFSum() - c2.getFSum());
		}
	};
}