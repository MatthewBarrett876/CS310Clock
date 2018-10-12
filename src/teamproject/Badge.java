package teamproject;
public class Badge {
    //instance fields
    private String id;
    private String description;
    //constructor
    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }
    //methods

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  
    @Override
    public String toString() {
        return "#" + this.id + " " + '(' + this.description + ')';
    }
    
    
    
}
