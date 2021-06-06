import java.util.Vector;
import java.awt.Point;

public class Enem {
    Enem(Point coord) {
        this._coord = coord;
    }
    public GameMap enemMove(GameMap map) {
        Pathfinder  pth = new Pathfinder(map, _coord, map.getPlayer());
        Point newpoint = pth.nextPoint();
        System.out.println(_coord.equals(newpoint));
        map.updateEntity(GameMap.Tokens.ENEMY, _coord, newpoint);
        _coord = newpoint;
        return map;
    }

    public Point  getEnemCoord()
    {
        return this._coord;
    }

    private Point    _coord;
}
