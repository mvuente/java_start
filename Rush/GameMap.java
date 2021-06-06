
import com.sun.media.jfxmedia.events.PlayerEvent;

import java.util.Vector;
import java.util.Scanner;
import java.util.Random;


public class GameMap {


    static public enum Tokens {SPACE, WALL, TARGET, PLAYER, ENEMY}

    public GameMap(int size, int enem_num, int obst_num)
    {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED_BACKGROUND = "\u001B[41m";
        final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
        final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
        final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";

        this._spaceSmpl = new String();
        this._spaceSmpl += ANSI_YELLOW_BACKGROUND + " " + ANSI_RESET;

        this._wallSmpl = new String();
        this._wallSmpl += ANSI_PURPLE_BACKGROUND + "#" + ANSI_RESET;

        this._enemySmpl = new String();
        this._enemySmpl += ANSI_RED_BACKGROUND + "X" + ANSI_RESET;

        this._playerSmpl = new String();
        this._playerSmpl += ANSI_GREEN_BACKGROUND + "o" + ANSI_RESET;

        this._targetSmpl = new String();
        this._targetSmpl += ANSI_BLUE_BACKGROUND + "0" + ANSI_RESET;

        this._all_coords = new Vector<>(); //хранит все структурные элементы карты кроме пустых полей
        this._size = size;
        this._map = new String[this._size];
        this._enem_num = enem_num; // кол-во скалоедов
        this._obst_num = obst_num; // кол-во препятствий
        this._enemies = new Vector<>(_enem_num); // начальные координаты скалоедов
        for (int i = 0; i < _enem_num; ++i)
        {
            Vector<Integer> enem_coord = new Vector<>(2);
            this.randomizer(enem_coord);
            _enemies.add(enem_coord);
        }

        this._obst = new Vector<>(_obst_num); // координаты препятствий
        for (int i = 0; i < _obst_num; ++i)
        {
            Vector<Integer> obst_coord = new Vector<>(2);
            this.randomizer(obst_coord);
            _obst.add(obst_coord);
        }

        this._player = new Vector<>(2); // начальные координаты игрока
        this.randomizer(_player);

        this._target = new Vector<>(2); // коордтинаты цели
        this.randomizer(_target);

        this.mapRecorder();
    }

    public void mapRecorder()
    {


        for (int i = 0; i < this._size; ++i)
        {
            this._map[i] = new String();
            for (int j = 0; j < this._size; ++j)
            {
                Vector<Integer> coord = new Vector<>(2);
                coord.add(j);
                coord.add(i);
                if (_enemies.contains(coord))
                    this._map[i] = this._map[i] + this._enemySmpl;
                else if (_obst.contains(coord))
                    this._map[i] = this._map[i] + this._wallSmpl;
                else if (_player.elementAt(0) == j && _player.elementAt(1) == i)
                    this._map[i] = this._map[i] + this._playerSmpl;
                else if (_target.elementAt(0) == j && _target.elementAt(1) == i)
                    this._map[i] = this._map[i] + this._targetSmpl;
                else
                    this._map[i] = this._map[i] + this._spaceSmpl;
            }
        }
    }

    public void drawMap()
    {
        for (int i = 0; i < this._size; ++i)
        {
            System.out.println(this._map[i]);
        }
    }

    public void    randomizer(Vector<Integer> myvect)
    {
        Random  myrand = new Random();
        int     tmp;
        while (true)
        {
            for (int i = 0; i < 2; ++i)
            {
                tmp = Math.abs(myrand.nextInt() % this._size);
                myvect.add(tmp);
            }
            if (this._all_coords.size() == 0 || (this._all_coords.size() > 0 && !this._all_coords.contains(myvect)))
            {
                this._all_coords.add(myvect); //собират все точки карты, чтобы не было наложений
                break;
            }
        }
    }

    public int  getSize()
    {
        return this._size;
    }

    public Tokens   getAt(int x, int y)
    {
        if (this._map[y].charAt((x * 10) + 5) == ' ')
            return Tokens.SPACE;
        else if (this._map[y].charAt((x * 10) + 5) == 'X')
            return Tokens.ENEMY;
        else if (this._map[y].charAt((x * 10) + 5) == '#')
            return Tokens.WALL;
        else if (this._map[y].charAt((x * 10) + 5) == 'o')
            return Tokens.PLAYER;
        else
            return Tokens.TARGET;
    }

    public Vector<Vector<Integer>>  getEnemies()
    {
        return this._enemies;
    }

    public Vector<Integer>          getPlayer()
    {
        return this._player;
    }

    public void updatePlayer(Vector<Integer> newPoint) // когда использовать этот метод
    {
        if (newPoint != this._player)
        {
            String  substr, newsubstr;
            substr = this._map[this._player.elementAt(1)].substring(this._player.elementAt(1) * 10); // выделил в новую строку кусок строки карты, начинающийся с цвета старого положения игрока. СТРОКА1
            newsubstr = substr.replaceFirst(this._playerSmpl, this._spaceSmpl); // замена поля игрока на поле пробела с цветом. СТРОКА2
            this._map[this._player.elementAt(1)] = this._map[this._player.elementAt(1)].replaceFirst(substr, newsubstr);// В строке карты заменил кусок СТРОКА1 на кусок СТРОКА2
            //теперь та же операция для новой точки
            substr = this._map[newPoint.elementAt(1)].substring(newPoint.elementAt(1) * 10); // выделил в новую строку кусок строки карты, начинающийся с цвета нового положения игрока (сейчас это пробел). СТРОКА1
            newsubstr = substr.replaceFirst(this._spaceSmpl, this._playerSmpl); // замена поля пробела на поле игрока с цветом. СТРОКА2
            this._map[newPoint.elementAt(1)] = this._map[newPoint.elementAt(1)].replaceFirst(substr, newsubstr);// В строке карты заменил кусок СТРОКА1 на кусок СТРОКА2
        }
    }

    // пока не пишу передвижение скалоедов: они могут ходить вместе на одну клетку или нет?

    private GameMap() {};
    private int _size;
    private int _enem_num;
    private int _obst_num;
    private Vector<Vector<Integer>>     _enemies;
    private Vector<Vector<Integer>>     _obst;
    private Vector<Vector<Integer>>     _all_coords;
    private Vector<Integer>             _player;
    private Vector<Integer>             _target;
    private String[]                    _map;
    private String                      _spaceSmpl;
    private String                      _wallSmpl;
    private String                      _enemySmpl;
    private String                      _playerSmpl;
    private String                      _targetSmpl;

    public static void  main(String[] args) {

        Scanner                         scanner = new Scanner(System.in);
        int                             size, enem_num, obst_num;

        System.out.println("Insert map size: ");
        size = scanner.nextInt();
        System.out.println("Insert number of enemies: ");
        enem_num = scanner.nextInt();
        System.out.println("Insert number of obstacles: ");
        obst_num = scanner.nextInt();

        GameMap map = new GameMap(size, enem_num, obst_num);
        map.drawMap();
        Enemies enem_proup = new Enemies(enem_num, map.getEnemies());
        Player  mario = new Player(map.getPlayer());

        System.out.println(map.getAt(1,1));
    }
}
