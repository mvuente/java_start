import java.util.Vector;
import java.awt.Point;

public class Player {
    Player(Point coord)
    {
        this._my_coord = coord;
    }

    public void marioMove()
    {
        //алгоритм движения
    }

    public Point  getPlayerCoord()
    {
        return this._my_coord;
    }

    private Point _my_coord;
}
