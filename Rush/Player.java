import java.util.Vector;

public class Player {
    Player(Vector<Integer> coord)
    {
        this._my_coord = coord;
    }

    public void marioMove()
    {
        //алгоритм движения
    }

    public Vector<Integer>  getPlayerCoord()
    {
        return this._my_coord;
    }

    private Vector<Integer> _my_coord;
}
