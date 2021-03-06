package game.level;
 
import java.util.ArrayList;

import game.Game;
import game.character.Character;
import game.character.Player;
import game.gui.hud.InGameHud;
import game.level.tile.AirTile;
import game.level.tile.LadderTile;
import game.level.tile.SolidTile;
import game.level.tile.Tile;
import game.state.LevelState;
import game.state.StaticResources;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;
 
public class Level {
    private TiledMap map;
    private ArrayList<Character> characters;
    private Tile[][] tiles;
    private Tile[][] interactiveTiles;
    private Player player;
    private Image background;
    private InGameHud hud;
 
    public Level(String level) throws SlickException{
        map = new TiledMap("data/levels/" + level + ".tmx","data/img");
        characters = new ArrayList<Character>();
        
        background = new Image("data/img/backgrounds/" + map.getMapProperty("background", "scifi_platform_BG1.jpg"));
        
        hud = new InGameHud(player);
        
        loadTileMap();
    }
 
    public int getXOffset(){
        int offset_x = 0;
 
        //the first thing we are going to need is the half-width of the screen, to calculate if the player is in the middle of our screen
        int half_width = (int) (Game.WINDOW_WIDTH/Game.SCALE/2);
 
        //next up is the maximum offset, this is the most right side of the map, minus half of the screen offcourse
        int maxX = (int) (map.getWidth()*32)-half_width;
 
        //now we have 3 cases here
        if(player.getX() < half_width){
            //the player is between the most left side of the map, which is zero and half a screen size which is 0+half_screen
            offset_x = 0;
        }else if(player.getX() > maxX){
            //the player is between the maximum point of scrolling and the maximum width of the map
            //the reason why we substract half the screen again is because we need to set our offset to the topleft position of our screen
            offset_x = maxX-half_width;
        }else{
            //the player is in between the 2 spots, so we set the offset to the player, minus the half-width of the screen
            offset_x = (int)(player.getX()-half_width);
        }
 
        return offset_x;
    }
    
    public int getYOffset(){
        int offset_y = 0;
 
        int half_heigth = (int) (Game.WINDOW_HEIGTH/Game.SCALE/2);
 
        int maxY = (int) (map.getHeight()*32)-half_heigth;
 
        if (player.getY() < half_heigth) {
            offset_y = 0;
        } else if(player.getY() > maxY) {
            offset_y = maxY-half_heigth;
        } else {
            offset_y = (int) (player.getY()-half_heigth);
        }
 
        return offset_y;
    }
    
    public void addCharacter(Character c) {
    	characters.add(c);
    }
    
    public void render(Graphics graphics) {
    	int offset_x = getXOffset();
        int offset_y = getYOffset();
 
        //render background
        renderBackground();
        
        //render the map first
        map.render(-(offset_x%32), -(offset_y%32), offset_x/32, offset_y/32, 33, 19);
 
        //render the characters on top of the map
        for(Character c : characters){
            c.render(offset_x,offset_y);
        }
    }
    
    private void renderBackground(){
        //first calculate the maximum amount we can "scroll" the background image before we have the rightmore or bottom most pixel on the screen
        float backgroundXScrollValue = (background.getWidth()-Game.WINDOW_WIDTH/Game.SCALE);
        float backgroundYScrollValue = (background.getHeight()-Game.WINDOW_HEIGTH/Game.SCALE);
 
        //we do the same for the map
        float mapXScrollValue = ((float)map.getWidth()*32-Game.WINDOW_WIDTH/Game.SCALE);
        float mapYScrollValue = ((float)map.getHeight()*32-Game.WINDOW_HEIGTH/Game.SCALE);
 
        //and now calculate the factor we have to multiply the offset with, making sure we multiply the offset by -1 to get it to negative
        float scrollXFactor = backgroundXScrollValue/mapXScrollValue * -1;
        float scrollYFactor = backgroundYScrollValue/mapYScrollValue * -1;
 
        //and now draw it using the factor and the offset to see where we start drawing
        background.draw(this.getXOffset()*scrollXFactor,this.getYOffset()*scrollYFactor);
    }
    
    private void loadTileMap() {
        //create an array to hold all the tiles in the map
        tiles = new Tile[map.getWidth()][map.getHeight()];
        interactiveTiles = new Tile[map.getWidth()][map.getHeight()];
 
        int layerIndex = map.getLayerIndex("CollisionLayer");
        int iLayerIndex = map.getLayerIndex("InteractionLayer");
 
        if(layerIndex == -1){
            //TODO we can clean this up later with an exception if we want, but because we make the maps ourselfs this will suffice for now
            System.err.println("Map does not have the layer \"CollisionLayer\"");
            System.exit(0);
        }
 
        //loop through the whole map
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
 
                //get the tile
                int tileID = map.getTileId(x, y, layerIndex);
 
                Tile tile = null;
 
                //and check what kind of tile it is
                switch (map.getTileProperty(tileID, "tileType", "solid")){
                    case "air":
                        tile = new AirTile(x,y);
                        break;
                    default:
                        tile = new SolidTile(x,y);
                        break;
                }
                tiles[x][y] = tile;
            }
        }
        
        if (iLayerIndex != -1) {
        	for (int x = 0; x < map.getWidth(); x++) {
            	for (int y = 0; y < map.getHeight(); y++) {
            		int tileID = map.getTileId(x, y, iLayerIndex);
            		Tile tile = null;
            		
            		switch (map.getTileProperty(tileID, "tileType", "solid")) {
            		case "ladder":
            			tile = new LadderTile(x, y);
            			break;
            		default:
            			tile = new AirTile(x, y);
            			break;
            		}
            		interactiveTiles[x][y] = tile;
            	}
            }
        }
    }
    
    public TiledMap getMap() {
    	return map;
    }
    
    public void setPlayer(Player player) {
    	this.player = player;
    	addCharacter(player);
    }
    
    public ArrayList<Character> getCharacters(){
        return characters;
    }
 
    public Tile[][] getTiles(){
        return tiles;
    }
    
    public Tile[][] getInteractiveTiles() {
    	return interactiveTiles;
    }
}