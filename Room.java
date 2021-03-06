import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Item> objects;
    private Item requiredOutfit;
    private boolean locked;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        exits = new HashMap<>();
        objects = new HashMap<>();
        this.description = description;
        this.requiredOutfit = null;
        this.locked = false;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    public void setObject(String object, Item item){
        objects.put(object, item);
    }

    public void setRequiredOutfit(Item outfit){
        this.requiredOutfit = outfit;
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public String getObjectsString(){
        String returnString = "Interesting objects:";
        Set<String> keys = objects.keySet();
        for(String object : keys) {
            returnString += " " + object;
        }
        if(returnString == "Interesting objects:"){
            returnString = "There are no objects to check here";
        }
        return returnString;
    }

    public ArrayList getObjectsArray(){
        Set<String> keySet = objects.keySet(); 
        ArrayList<String> objects = new ArrayList<String>(keySet);
        return objects;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    public Item getItem(String object){
        return objects.get(object);
    }

    public Item getRequiredOutfit(){
        return requiredOutfit;
    }

    public boolean getIfLocked(){
        return this.locked;
    }

    public void removeObject(String from, Item to){
        objects.replace(from, to);
    }

    public void lockDoor(){
        this.locked = true;
    }

public void unlockDoor(){
        this.locked = false;
    }
}
