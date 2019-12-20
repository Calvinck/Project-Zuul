/**
 * In deze class wordt gedefineerd wat items zijn.
 *
 * @author Jorian
 * @version 1.0
 */
public class Item{
    private String name;
    private String description;
    private int weight;
    
    public Item(String name, String description, int weight){
        this.name = name;
        this.description = description;
        this.weight = weight;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setWeight(int weight){
        this.weight = weight;
    }
    
    public String getName(){
      return  this.name;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public int getWeight(){
        return this.weight;
    }
}
