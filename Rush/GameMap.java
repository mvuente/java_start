

import java.util.Vector;
import java.util.Scanner;
import java.util.Random;
import java.awt.Point;


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
        this._enem_num = enem_num; // кол-во скалоедов
        this._obst_num = obst_num; // кол-во препятствий
        this._enemies = new Vector<>(_enem_num); // начальные координаты скалоедов
        for (int i = 0; i < _enem_num; ++i)
        {
            Point enem_coord = new Point();
            this.randomizer(enem_coord);
            _enemies.add(enem_coord);
        }

        this._obst = new Vector<>(_obst_num); // координаты препятствий
        for (int i = 0; i < _obst_num; ++i)
        {
            Point obst_coord = new Point();
            this.randomizer(obst_coord);
            _obst.add(obst_coord);
        }

        this._player = new Point(); // начальные координаты игрока
        this.randomizer(_player);

        this._target = new Point(); // коордтинаты цели
        this.randomizer(_target);

        this.drawMap();
    }

    public void drawMap()
    {
        for (int i = 0; i < this._size; ++i)
        {
            String  drawString = new String("");
            for (int j = 0; j < this._size; ++j)
            {
                Point coord = new Point(j, i);
                if (_enemies.contains(coord))
                    drawString = drawString + this._enemySmpl;
                else if (_obst.contains(coord))
                    drawString = drawString + this._wallSmpl;
                else if (_player.equals(coord))
                    drawString = drawString + this._playerSmpl;
                else if (_target.equals(coord))
                    drawString = drawString + this._targetSmpl;
                else
                    drawString = drawString + this._spaceSmpl;
            }
            System.out.println(drawString);
        }
    }

    public void    randomizer(Point myvect)
    {
        Random  myrand = new Random();
        int     tmp;
        while (true)
        {
            tmp = Math.abs(myrand.nextInt() % this._size);
            myvect.x = tmp;
            tmp = Math.abs(myrand.nextInt() % this._size);
            myvect.y = tmp;

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

    public Tokens   getAt(Point point)
    {
        if (this._player.equals(point))
            return Tokens.PLAYER;
        else if (this._target.equals(point))
            return Tokens.TARGET;
        else if (this._obst.contains(point))
            return Tokens.WALL;
        else if (this._enemies.contains(point))
            return Tokens.ENEMY;
        else
            return Tokens.SPACE;
    }

    public Vector<Point>  getEnemies()
    {
        return this._enemies;
    }

    public Point          getPlayer()
    {
        return this._player;
    }

    public Point          getTarget()
    {
        return this._target;
    }

    public Vector<Point>  getObst()
    {
        return this._obst;
    }

    public GameMap updateEntity(Tokens t, Point oldPoint, Point newPoint) // когда использовать этот метод
    {
        if (t == Tokens.PLAYER)
        {
            this._player = newPoint;
        }
        else
        {
            for (Point p : this._enemies)
            {
                if (p.equals(oldPoint)) {
                    System.out.println("Finded");
                    p.setLocation(newPoint);
                }
            }
        }
        return this;
    }

    public static GameMap pather(String path, Player mario, GameMap map)
    {
        System.out.println(path + " " + path.length());
        if (!path.equals("a") && !path.equals("s") && !path.equals("d") && !path.equals("w"))
            return null;
        System.out.println("Test");
        return mario.marioMove(path, map);
    }

    private GameMap() {};
    private int             _size;
    private int             _enem_num;
    private int             _obst_num;
    private Vector<Point>   _enemies;
    private Vector<Point>   _obst;
    private Vector<Point>   _all_coords;
    private Point           _player;
    private Point           _target;
    private String          _spaceSmpl;
    private String          _wallSmpl;
    private String          _enemySmpl;
    private String          _playerSmpl;
    private String          _targetSmpl;

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
        GameMap newmap;
        System.out.println("Hi, Mario! You're in green cell. Your target is a blue cell. Red cells - are enemies. Move to your target and avoid enemies. ");
        Enemies enem_proup = new Enemies(enem_num, map.getEnemies());
        Player  mario = new Player(map.getPlayer());
        scanner.nextLine();
        while (true)
        {
            System.out.println("Please, tell me, where you're going to (W, A, S, D)");
            while ((newmap = pather(scanner.nextLine(), mario, map)) == null)
                System.out.println("Wrong choice! Change, please");
            map = newmap;
            if (!map.getPlayer().equals(map.getTarget()))
            {
                map = enem_proup.enemies_attack(map);
                if (map.getEnemies().contains(map.getPlayer()))
                {
                    System.out.println("You lose! It's pitty!");
                    System.exit(0);
                }
            }
            else
                break;
            map.drawMap();
        }
        System.out.println("You won! Congratulations!");

    }
}
