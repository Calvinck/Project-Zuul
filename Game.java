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
 * @version 2020.01.22
 */

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private HashMap<String, Item> closet;
    private ArrayList<Item> inventory;
    private Item currentOutfit;
    private Stack<Room> prevRoom;
    private PlayMusic musicplayer;

    private Room outside, centralhall, parkinglot, restroom, hall, safe, meetingroom, controlroom, ceoroom, basement, northbasement, eastbasement, southbasement, westbasement, moneyroom, winningparkinglot, losingcentralhall;

    /**
     * Create the game and initialise its internal map.
     */
    public Game(){
        fillCloset();
        createRooms();
        parser = new Parser();
        inventory = new ArrayList<>();
        prevRoom = new Stack<>();
        musicplayer = new PlayMusic();

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

    private void fillCloset(){
        closet = new HashMap<>();
        //initialize all items in the game
        closet.put("casual_clothes", new Item("casual_clothes", "Your normal everyday clothing", "outfit", 2));
        //closet.put("escapecar", new Item("escapecar", "You will use this 1988 Chevrolet C/K to escape after you robbed the money.", "pickup", 900));
        closet.put("guard_clothes", new Item("guard_clothes", "Your henchman left these clothes out here for you.", "outfit", 3));
        closet.put("randombook", new Item("randombook", "This is a random book about some love issues", "nonpickup", 1));
        closet.put("bank_book", new Item("bank_book", "This is a book about running a bank'", "book", 1));
        closet.put("flyer", new Item("flyer", "A random flyer for startup company's", "nonpickup", 1));
        closet.put("door_remote", new Item("door_remote", "this remote can open all doors you like.", "dooropener",99));
        closet.put("safekey", new Item("safekey", "This is a nice, shining, big, golden key. It must be the key that is used for the safe", "key", 1));
        closet.put("burglar_clothes", new Item("burglar_clothes", "Another robber used this to rob this bank, but he was caught. Contains duffle bag", "outfit", 3));
        closet.put("book_of_knowlage", new Item("book_of_knowlage", "This book knows all", "book", 1));
        closet.put("bills", new Item("bills", "A briefcase full of $10 bills, probably worth about $10000", "money", 2));
        closet.put("drill", new Item("drill", "This is a big jackhammer, maybe you can use this to escape.", "drill", 5));
        closet.put("lockpicks", new Item("lockpicks", "Why would there be a lockpicking set down here? might as well take it with me.", "lockpick", 1));
        closet.put("nimbus_2000", new Item("nimbus_2000", "This is a weird broom. Better leave it here.", "nonpickup", 2));
        closet.put("expensive_wine", new Item("expensive_wine", "The label of this wine says: Screaming Eagle Cabernet 1992", "nonpickup", 1));
        closet.put("lots_of_gold", new Item("lots_of_gold", "Tables with lots and lots of gold.", "money", 12));

        //write books here
        Writer writer = new Writer();
        closet.get("book_of_knowlage").setText(writer.writeBook("book_of_knowlage"));
        closet.get("bank_book").setText(writer.writeBook("bank_book"));
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        outside = new Room("You are standing outside the main entrance of the bank");
        centralhall = new Room("You are standing inside the central hall. It's a big room");
        parkinglot = new Room("You are standing in the parking lot, you might want to investigate");
        hall = new Room("You are now in a long hallway. There are multiple rooms.");
        safe = new Room("You are standing inside the safe, but the money is behind bars. Use your key.");
        meetingroom = new Room("You are standing inside the meeting room");
        controlroom = new Room("Your are standing inside the controlroom.");
        basement = new Room("You're in the basement. This is the place where you fell down.");
        northbasement = new Room("You are in the north side of the basement");
        eastbasement = new Room("You are in the east side of the basement");
        southbasement = new Room("You are int the south side of the basement");
        westbasement = new Room("You are in the west side of the basement");
        ceoroom = new Room("You are in the office of the CEO. The CEO appears to be asleep, you have to be very quiet");
        restroom = new Room("You are standing in the banks gender equality restroom, quite stinky");
        moneyroom = new Room("The moneyroom, finally, grab as much money as you can! There is gold locked behind bars.");
        winningparkinglot = new Room("This is where you escape and win the game.");
        losingcentralhall = new Room("This is where you get caught and los the game.");
        
        // initialise room exits
        outside.setExit("north", centralhall);
        outside.setExit("east", parkinglot);

        centralhall.setExit("south", outside);
        centralhall.setExit("west", restroom);
        centralhall.setExit("north", hall);

        restroom.setExit("east", centralhall);

        parkinglot.setExit("west", outside);

        hall.setExit("north", safe);
        hall.setExit("south", centralhall);
        hall.setExit("east", meetingroom);
        hall.setExit("west", controlroom);

        meetingroom.setExit("west", hall);

        controlroom.setExit("east", hall);
        controlroom.setExit("up", ceoroom);

        ceoroom.setExit("down", controlroom);

        safe.setExit("south", hall);

        basement.setExit("north", northbasement);
        basement.setExit("east", eastbasement);
        basement.setExit("south", southbasement);
        basement.setExit("west", westbasement);

        northbasement.setExit("south", basement);
        eastbasement.setExit("west", basement);
        southbasement.setExit("north", basement);
        westbasement.setExit("east", basement);

        //lock doors
        meetingroom.lockDoor();
        ceoroom.lockDoor();
        safe.lockDoor();

        //placing objects in rooms
        parkinglot.setObject("dumpster", closet.get("guard_clothes"));
        meetingroom.setObject("cupboard", closet.get("burglar_clothes"));
        controlroom.setObject("doorunlocker", closet.get("door_remote"));
        ceoroom.setObject("keycabinet", closet.get("safekey"));
        ceoroom.setObject("coffeetable", closet.get("bank_book"));
        safe.setObject("briefcase", closet.get("bills"));
        basement.setObject("bookcase", closet.get("book_of_knowlage"));
        westbasement.setObject("toolwall", closet.get("drill"));
        westbasement.setObject("toolbox", closet.get("lockpicks"));

        //random items in rooms
        //parkinglot.setObject("parkinglot", closet.get("escapecar"));
        centralhall.setObject("frontdesk", closet.get("flyer"));
        controlroom.setObject("bookcase", closet.get("randombook"));
        northbasement.setObject("broom_closet", closet.get("nimbus_2000"));
        southbasement.setObject("wine_cellar", closet.get("expensive_wine"));

        //set requirements for rooms
        hall.setRequiredOutfit(closet.get("guard_clothes"));
        meetingroom.setRequiredOutfit(closet.get("guard_clothes"));
        controlroom.setRequiredOutfit(closet.get("guard_clothes"));
        ceoroom.setRequiredOutfit(closet.get("guard_clothes"));
        safe.setRequiredOutfit(closet.get("guard_clothes"));

        currentOutfit = closet.get("casual_clothes"); // start game in casual clothes

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
        String welcome = "welcome.wav";

        musicplayer.playMusic(welcome);
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
            wantToQuit = goRoom(command);
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
        else if (commandWord.equals("drop")) {
            dropObject(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("inventory")) {
            inventory(command);
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
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private boolean goRoom(Command command){
        boolean wantToQuit = false;
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return wantToQuit;
        }

        String door = "deur.wav";
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else{
            if(nextRoom.getIfLocked()){
                System.out.println("This room is locked, you can not go here.");
                return wantToQuit;
            }
            else if(nextRoom.equals(winningparkinglot)){
                int totalScore = 0;
                if(inventory.size() >= 1){
                    for(Item item : inventory){
                        if(item.getType().equals("money")){
                            int score = item.getWeight() * 5000;
                            totalScore += score;
                        }
                    }
                }
                System.out.println("Congratulations! You did it! you escaped the bank without getting caught!\n");
                System.out.println("You have collected $" + totalScore + " along the way.\n");
                wantToQuit = true;
            }
            else if(nextRoom.equals(losingcentralhall)){
                System.out.println("Game over!");
                System.out.println("You drilled right into te central hall. You are caught immediately. Mission failed.");
                wantToQuit = true;
            }
            else if(nextRoom.getRequiredOutfit() == null || nextRoom.getRequiredOutfit() == currentOutfit){
                if(nextRoom.getShortDescription().contains("room")){
                    musicplayer.playMusic(door);
                }
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
        return wantToQuit;
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

        String object = command.getSecondWord().toLowerCase();
        Item item = currentRoom.getItem(object);

        if(!currentRoom.getObjectsArray().contains(object)){
            System.out.println("There is no " + object + " here.");
        }

        else if(item == null){
            System.out.println(object + " is empty.");
        }

        else{
            String desc = item.getDescription();
            String name = item.getName();
            if(item.getType() == "nonpickup") {
                musicplayer.playMusic("checken.wav");
                System.out.println("You found a " + name + ".");
                System.out.println(desc);
                System.out.println("The " + item.getName() + " is not important. So you decided to put it back.");
            }
            else if(item.getType() == "dooropener"){
                meetingroom.unlockDoor();
                ceoroom.unlockDoor();
                safe.unlockDoor();
                System.out.println("You have found the room lock remote and decided to unlock the meetingroom, ceoroom and the safe.");
            }

            else if(item.getWeight() + inventoryWeight() <= 10){
                musicplayer.playMusic("checken.wav");
                inventory.add(item);
                currentRoom.removeObject(object, null);
                System.out.println("You have found " + name + ".");
                System.out.println(desc);
                System.out.println(name + " added to inventory");
            }
            else if(item.getType().equals("money") && currentOutfit.getName().equals("burglar_clothes")){
                inventory.add(item);
                currentRoom.removeObject(object, null);
                System.out.println("You have found " + name + ".");
                System.out.println(desc);
                System.out.println(name + " added to inventory");
            }
            else{
                System.out.println("You have found " + name + ".");
                System.out.println("But your inventory is full so you have to put it back");
            }
        }
    }

    private void useObject(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to use...
            System.out.println("Use What?");
            return;
        }

        String objectToUse = command.getSecondWord().toLowerCase();
        String omkleden = "Omkleden.wav";
        String valgeluid = "valgeluid.wav";
        Item itemToUse = null;

        for(Item item : inventory) {
            if(item.getName().equals(objectToUse)){
                itemToUse = item;
            }
        }

        if(itemToUse == null){
            System.out.println("You don't have '" + objectToUse + "'");
        }
        else{
            if(itemToUse.getName().equals("safekey") && currentRoom.equals(safe)){
                musicplayer.playMusic(valgeluid);
                currentRoom = basement;
                System.out.println("It's a trap!");
                System.out.println(currentRoom.getLongDescription());
            }
            else if(itemToUse.getType().equals("outfit")){
                if(currentRoom.getRequiredOutfit() != null){
                    System.out.println("You can not change you outfit now.");
                }
                else{
                    musicplayer.playMusic(omkleden);
                    inventory.add(currentOutfit);
                    currentOutfit = itemToUse;
                    inventory.remove(itemToUse);
                    System.out.println("Outfit changed to " + objectToUse);
                }
            }
            else if(itemToUse.getType().equals("book")){
                System.out.println(itemToUse.getContent());
            }
            else if(itemToUse.getType().equals("drill")){
                if(currentRoom.getExit("up") == null){
                    if(currentRoom.equals(northbasement)){
                        musicplayer.playMusic("boren.wav");
                        northbasement.setExit("up", moneyroom);
                        moneyroom.setExit("down", northbasement);
                        System.out.println("You have drilled a hole in the ceiling. Use 'go up' to check it out.");
                    }
                    else if(currentRoom.equals(eastbasement)){
                        musicplayer.playMusic("boren.wav");
                        eastbasement.setExit("up", winningparkinglot);
                        System.out.println("You have drilled a hole in the ceiling. Use 'go up' to check it out.");
                    }
                    else if(currentRoom.equals(southbasement)){
                        musicplayer.playMusic("boren.wav");
                        southbasement.setExit("up", losingcentralhall);
                        System.out.println("You have drilled a hole in the ceiling. Use 'go up' to check it out.");
                    }
                    else if(currentRoom.equals(westbasement)){
                        musicplayer.playMusic("boren.wav");
                        System.out.println("You have drilled into the gender equality sewer. Nasty.");
                    }
                    else{
                        System.out.println("You can not drill here now.");
                    }
                }
                else{
                    System.out.println("You can not drill here now.");
                }
            }
            else if(itemToUse.getType().equals("lockpick") && currentRoom.equals(moneyroom)){
                moneyroom.setObject("tables", closet.get("lots_of_gold"));
                System.out.println("There were tables with gold behind bars.");
                System.out.println("You were able to unlock those bars with your lock picking set.");
                System.out.println("You however need a duffle bag to carry the gold");
            }
            else{
                System.out.println("You can not use the " + itemToUse + " now.");
            }
        }
    }

    private void dropObject (Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to use...
            System.out.println("Drop What?");
            return;
        }

        String objectToDrop = command.getSecondWord();
        Item itemToDrop = null;

        for(Item item : inventory) {
            if(item.getName().equals(objectToDrop)){
                itemToDrop = item;
            }
        }

        if(itemToDrop == null){
            System.out.println("You don't have '" + objectToDrop + "'");
        }
        else {
            String trash = "trashcan_" + objectToDrop;
            System.out.println("You just threw " + objectToDrop + " in the trashcan. You can pick it up later if needed.");
            currentRoom.setObject(trash, closet.get(objectToDrop));
            inventory.remove(itemToDrop);
        }
    }

    private void inventory(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Inventory what?");
        }
        else if(inventory.size() >= 1){
            for(int i = 0; i < inventory.size(); i++){
                System.out.println(inventory.get(i).getName() + " weight: " + inventory.get(i).getWeight());
            }
        }
        else {System.out.println("You have nothing in your inventory");}
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

        else if(prevRoom.size() <= 0) {
            System.out.println("Go to another room first before you use this command.");
        }

        else if(currentRoom.equals(basement)){
            prevRoom.clear();
            System.out.println("You are stuck in the basement. You can't get back up!");
        }
        else {
            Room nextRoom = prevRoom.peek();
            String door = "deur.wav";

            if(nextRoom.getRequiredOutfit() == null || nextRoom.getRequiredOutfit() == currentOutfit){
                if(nextRoom.getShortDescription().contains("room")){
                    musicplayer.playMusic(door);
                }
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

    private int inventoryWeight(){
        if(inventory.size() >= 1){
            int total = 0;
            if(currentOutfit.getName().equals("burglar_clothes")){
                for(Item item : inventory){
                    if(!item.getType().equals("money")){
                        int weight = item.getWeight();
                        total += weight;
                    }
                }
            }
            else{
                for(Item item : inventory){
                    int weight = item.getWeight();
                    total += weight;
                }
            }
            return total;
        }
        else{
            return 0;
        }
    }
}
