
public class Recipe {
	private int id;
	private String name, link;

    Recipe(){
        id = 0;
        name = "";
        link = "";
    }
    Recipe(int newID, String newName, String newLink){
        id = newID;
        name = newName;
        link = newLink;
    }

    int getID(){
        return id;
    }
    String getName(){
        return name;
    }
    String getLink(){
        return link;
    }
    public String toString() {
        return "Name: " + getName() + "\nLink: " + getLink() + "\n\n";
    }
}
