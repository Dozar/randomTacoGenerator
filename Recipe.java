/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recipesearch;

/**
 *
 * @author Dozar
 */
public class Recipe {
  private int id;
	private String title, link;

    Recipe(){
        id = 0;
        title = "";
        link = "";
    }
    Recipe(int newID, String newTitle, String newLink){
        id = newID;
        title = newTitle;
        link = newLink;
    }

    int getID(){
        return id;
    }
    String getTitle(){
        return title;
    }
    String getLink(){
        return link;
    }
    void setLink(String linkPut){
        link = linkPut;
    }
    public String toString() {
        return "Title: " + getTitle() + "\nLink: " + getLink() + "\n\n";
    }
}
