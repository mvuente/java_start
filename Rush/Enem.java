import java.util.Vector;

public class Enem {
    Enem(Vector<Integer> coord) {
        this._coord = coord;
    }
    public void enemMove() {
        //меняем текущие координаты этого скалоеда
    }

    public Vector<Integer>  getEnemCoord()
    {
        return this._coord;
    }

    private Vector<Integer>    _coord;
}
