import java.util.Vector;
import java.awt.Point;
public class Enemies {
    Enemies(int num, Vector<Point> coord)
    {
        this._enemies_num = num;
        _enem = new Enem[_enemies_num];
        for (int i = 0; i < _enemies_num; ++i)
        {
            this._enem[i] = new Enem(coord.elementAt(i));
        }
    }

    public GameMap enemies_attack(GameMap map) {
        for (int i = 0; i < _enemies_num; ++i)
        {
            if (i == 0)
                System.out.println(_enem[i].getEnemCoord().getX() + " - " + _enem[i].getEnemCoord().getY());
            map = _enem[i].enemMove(map);
        }
        return map;
    }
    private Enem[]  _enem;
    private int     _enemies_num;
}
