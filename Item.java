/**
 * In deze class wordt gedefineerd wat items zijn.
 *
 * @author Jorian
 * @version 1.0
 */
public class Item{
    private String name;
    private String description;
    private String type;
    private int weight;
    private String content;
    
    public Item(String name, String description, String type, int weight){
        this.name = name;
        this.description = description;
        this.type = type;
        this.weight = weight;
    }
    public String getName(){
         return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public String getType(){
        return this.type;
    }
    public int getWeight(){
        return this.weight;
    }
    public String getContent(){
        return this.content;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }
    public void setText(String content){
        this.content = content;
    }
}
