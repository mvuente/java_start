import java.util.Vector;
public class Enemies {
    Enemies(int num, Vector<Vector<Integer>> coord)
    {
        this._enemies_num = num;
        for (int i = 0; i < _enemies_num; ++i)
        {
            this._enem[i] = new Enem(coord.elementAt(i));
        }
    }

    public void enemies_attack() {
        for (int i = 0; i < _enemies_num; ++i)
        {
            _enem[i].enemMove();
        }
    }
    private Enem[]  _enem;
    private int     _enemies_num;
}
