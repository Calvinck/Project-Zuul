import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author Calvin
 * @author Jorian
 * @version 2019.12.20
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private HashMap<String, Item> closet;
    private ArrayList<Item> inventory;
    private Item currentOutfit;
    private Stack<Room> prevRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game(){
        fillCloset();
        createRooms();
        parser = new Parser();
        inventory = new ArrayList<>();
        prevRoom = new Stack<>();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

    private void fillCloset(){
        closet = new HashMap<>();
        closet.put("casualClothes", new Item("Casual_Clothes", "Your normal everyday clothing", "outfit", 1));
        closet.put("guardClothes", new Item("cuard_clothes", "Your henchman left these clothes out here for you.", "outfit", 1));
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, centralhall, parkinglot, hall, safe, meetingroom, controlroom, basement, room1, room2, room3;

        // create the rooms
        outside = new Room("You are standing outside the main entrance of the bank");
        centralhall = new Room("You are standing inside the central hall");
        parkinglot = new Room("You are standing in the parking lot, you might want to investigate");
        hall = new Room("You are now in a long hallway");
        safe = new Room("You are standing in front of the safe");
        meetingroom = new Room("You are standing inside the meeting room");
        controlroom = new Room("Your are standing inside the controlroom, there is a golden key on the table");
        basement = new Room("In front of you are 3 rooms. pick the west, north or east door.");

        // initialise room exits
        outside.setExit("north", centralhall);
        outside.setExit("east", parkinglot);

        centralhall.setExit("south", outside);
        centralhall.setExit("north", hall);

        //Parking lot
        parkinglot.setExit("west", outside);

        //The hall inside the bank
        hall.setExit("north", safe);
        hall.setExit("south", centralhall);
        hall.setExit("east", meetingroom);
        hall.setExit("west", controlroom);

        meetingroom.setExit("south", hall);

        controlroom.setExit("south", hall);

        //placing objects in rooms
        parkinglot.setObject("dumpster", closet.get("guardClothes"));

        //set requirements for rooms
        hall.setRequiredOutfit(closet.get("guardClothes"));

        currentOutfit = closet.get("casualClothes"); // start game in casual clothes

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Hello, do you want to play a game?");
        System.out.println();
        System.out.println("You are dreaming of that nice Maserati");
        System.out.println("That nice Granturismo Sport V8");
        System.out.println("But your Bank account says '1.5 frikadelbroodje'");
        System.out.println();
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command){
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("investigate")) {
            investigate();
        }
        else if (commandWord.equals("check")) {
            checkObject(command);
        }
        else if (commandWord.equals("use")) {
            useObject(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp(){
        System.out.println("You are at the 'Cash Money Bank' in South Africa");
        System.out.println("It is the biggest bank in Africa.");
        System.out.println("And you are trying to rob it. Good Luck!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            if(nextRoom.getRequiredOutfit() == null || nextRoom.getRequiredOutfit() == currentOutfit){
                addPrevRoom();
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());

            }
            else{
                System.out.println();
                System.out.println("з=( ͠° ͟ʖ ͡°)=ε");
                System.out.println("This is private access sir. You cannot go here.");
                System.out.println();
                System.out.println(currentRoom.getLongDescription());

            }
        }
    }

    private void investigate(){
        String string = currentRoom.getObjectsString();
        System.out.println(string);
    }

    private void checkObject(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to check...
            System.out.println("Check What?");
            return;
        }

        String object = command.getSecondWord();

        Item item = currentRoom.getItem(object);

        if(item == null){
            System.out.println("There ain't no " + object + " here.");
        }
        else{
            inventory.add(item);
            String desc = item.getDescription();
            String name = item.getName();
            System.out.println("You have found " + name + ".");
            System.out.println(desc);
            System.out.println(name + " added to inventory");
        }
    }

    private void useObject(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to use...
            System.out.println("Use What?");
            return;
        }

        String objectToUse = command.getSecondWord();

        Item itemToUse = null;

        for(Item item : inventory) {
            if(item.getName().equals(objectToUse)){
                itemToUse = item;
            }
        }

        if(itemToUse.getType().equals("outfit")){
            inventory.add(currentOutfit);
            currentOutfit = itemToUse;
            System.out.println("Outfit changed to " + objectToUse);
        }

        if(itemToUse.equals(null)){
            System.out.println("There ain't no " + objectToUse + " in your inventory.");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void addPrevRoom() {
        prevRoom.push(currentRoom);
    }

    private void back(Command command) {

        if (command.hasSecondWord()) {
            System.out.println("back what?");   
        }

        else {
            Room nextRoom = prevRoom.peek();

            if(nextRoom.getRequiredOutfit() == null || nextRoom.getRequiredOutfit() == currentOutfit){
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
                prevRoom.pop();
            }
            else{
                System.out.println();
                System.out.println("з=( ͠° ͟ʖ ͡°)=ε");
                System.out.println("This is private access sir. You cannot go here.");
                System.out.println();
                System.out.println(currentRoom.getLongDescription());
            }

        }

    }
}
