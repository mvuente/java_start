import java.util.Vector;
import java.awt.Point;

public class Player {
    Player(Point coord)
    {
        this._my_coord = coord;
    }

    public GameMap marioMove(String path, GameMap map)
    {
        Point   newpoint = new Point(_my_coord);

        if (path.equals("w"))
            if (newpoint.getY() != 0)
                newpoint.translate(0, -1);
            else
                return null;
            if (path.equals("s"))
                if (newpoint.getY() != map.getSize() - 1)
                    newpoint.translate(0, 1);
                else
                    return null;
        if (path.equals("d"))
            if (newpoint.getX() != map.getSize() - 1)
                newpoint.translate(1, 0);
            else
                return null;
        if (path.equals("a"))
            if (newpoint.getX() != 0)
                newpoint.translate(-1,0);
            else
                return null;
        if (map.getObst().contains(newpoint))
            return null;
        map.updateEntity(GameMap.Tokens.PLAYER, _my_coord, newpoint);
        _my_coord = newpoint;
        return map;
    }

    public Point  getPlayerCoord()
    {
        return this._my_coord;
    }

    private Point _my_coord;
}
