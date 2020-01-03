package settings;

public class Settings {

	public static enum Directions { N, E, S, W};
	
	public static final double SCENE_WIDTH = 1600;
    public static final double SCENE_HEIGHT = 1000;
    public static final double SIZE_LAND = 200;
    public static final double SIZE_CASTLE = 80;
	public static final double HUD_HEIGHT = SCENE_HEIGHT/5;
	public static final double HUD_WIDTH =  SCENE_WIDTH;
	public static final double DISTANCE_BETWEEN_CASTLES_WIDTH = 100;
	public static final double DISTANCE_BETWEEN_CASTLES_HEIGHT = 50;

	
	public static final double WINDOW_WIDTH = 790;
    public static final double WINDOW_HEIGHT = 790;
	
	public static final int LEVEL_1 = 1;
	public static final int LEVEL_2 = 2;
	public static final int LEVEL_3 = 3;
	public static final int LEVEL_4 = 4;
	public static final int LEVEL_5 = 5;
	
	public static final int NB_TYPE_SOLDIERS = 3;
	
	// Size of soldiers
	public static final double SIZE_PIKER = 10;
	public static final double SIZE_KNIGHT = 12;
	public static final double SIZE_CATAPULT = 16;
	
	public static final int COST_PIKER = 100;
	public static final int COST_KNIGHT = 500;
	public static final int COST_CATAPULT = 1000;
	public static final int COST_UPGRADE_LEVEL = 1000;
	
	// Indice of sodiers
	public static final int PIKER = 0;
	public static final int KNIGHT = 1;
	public static final int CATAPULT = 2;
	public static final int UPGRADE_LEVEL = 3;
	
	
	public static final double TIME_ROUND_SECOND = 5;
	public static final int NB_FRAMES = 60;
	
	
	public static final int NB_ROUNDS_INCOME = 2;	
	public static final int NB_ROUNDS_PRODUCTION_PIKER = 5;
	public static final int NB_ROUNDS_PRODUCTION_KNIGHT = 20;
	public static final int NB_ROUNDS_PRODUCTION_CATAPULT = 50;
	public static final int NB_ROUNDS_PRODUCTION_UPGRADE_LEVEL = 10;
	
	
	public static final double TIME_PIKER_SECOND = TIME_ROUND_SECOND * NB_ROUNDS_PRODUCTION_PIKER;
	public static final double TIME_KNIGHT_SECOND = TIME_ROUND_SECOND * NB_ROUNDS_PRODUCTION_KNIGHT;
	public static final double TIME_CATAPULT_SECOND = TIME_ROUND_SECOND * NB_ROUNDS_PRODUCTION_CATAPULT;
	public static final double TIME_UPGRADE_LEVEL_SECOND = TIME_ROUND_SECOND * NB_ROUNDS_PRODUCTION_UPGRADE_LEVEL;
	
	
	public static final short EXIT_ATTACK = 0;
	public static final short EXIT_TRAIN = 1;
	public static final short EXIT_CANCEL_ONE_QUEUE = 2;
	public static final short EXIT_CANCEL_ALL_QUEUE = 3;
	public static final short EXIT_UPGRADE_LEVEL = 4;
	public static final short EXIT_TRANSFER = 5;
	
	public static final short EXIT_ECHAP = 404;
	
	
}
