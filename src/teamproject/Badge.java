package teamproject;
public class Badge {
    //instance fields
    private String id;
    private String name;
    //constructor
    public Badge(String id, String name) {
        this.id = id;
        this.name = name;
    }
    //methods
    public String getID() {return id;}
    public String getName() {return name;}
    public void setID(String i) {
        this.id = i;
    }
    public void setName(String n) {
        this.name = n;
    }
    //toString()
    @Override
    public String toString() {
        return "#" + this.id + " " + '(' + this.name + ')';
    }
    
    
    
}
