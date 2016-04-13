/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package recipesearch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.ParseException;

public class RecipeServer {

  public static void main(String[] args) {

    System.out.println("Recipe Server ...");
    Socket sSocket = null;

    try {
    ServerSocket serverSocket = new ServerSocket(9000);
    System.out.println("Waiting for connection.....");
    sSocket = serverSocket.accept();
    System.out.println("Connected to client");
    serverSocket.close();
    } catch (IOException ex) {
    System.out.println(ex.getMessage());
    }
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(sSocket.getInputStream()));
      PrintWriter out = new PrintWriter(sSocket.getOutputStream(), true);

      String inputLine;

      int numRecipes = 0, offset = 0;
      String foodQuery;

      while ((inputLine = br.readLine()) != null) {

        System.out.println("Client request: " + inputLine);
        String[] request = inputLine.split(",");

        foodQuery = request[0];

        DecimalFormat decimalFormat = new DecimalFormat("#");
        try {
          numRecipes = decimalFormat.parse(request[1]).intValue();
        }
        catch (ParseException e) {
          System.out.println("Not a number");
        }
        try {
          offset = decimalFormat.parse(request[2]).intValue(); //me rn
        }
        catch (ParseException e) {
          System.out.println("Not a number");
        }

        String recipeJsonStr = fetchData(foodQuery, numRecipes, offset);

        Recipe[] recipes = new Recipe[numRecipes];
        recipes = parseData(recipeJsonStr, numRecipes);

        String outputLine = "";
        for (Recipe recipe : recipes) {
          outputLine += recipe.toString();
        }
        out.println(outputLine);
      }
    }
    catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public static String fetchData(String foodQuery, int numRecipes, int offset) {

    HttpURLConnection conn = null;
    BufferedReader reader = null;
    String recipeJsonStr = null;

    try {
      String sUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?limitLicense=false&number="
       + numRecipes  + "&offset=" + offset + "&query=" + foodQuery +
       "&ranking=1&mashape-key=mP7f87A8jCmshXEEX8fWwtLskVSup1rHVURjsnkUd09UtPgq4v";
      URL url = new URL(sUrl);

      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.connect();

      InputStream inputStream = conn.getInputStream();
      // Read the input stream
      // Place input stream into a buffered reader
      reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      StringBuilder buffer = new StringBuilder();

      while ((line = reader.readLine()) != null) {
        buffer.append(line).append("\n");
      }

      recipeJsonStr = buffer.toString();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    finally {
      if (conn != null) {
        conn.disconnect();
      }
      if (reader != null) {
        try {
          reader.close();
        }
        catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
      System.out.println(recipeJsonStr);
    }
    return recipeJsonStr;
  }

  public static String fetchData(String foodId) {

    HttpURLConnection conn = null;
    BufferedReader reader = null;
    String recipeJsonStr = null;

    try {
      String sUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" +
      foodId + "/information?includeNutrition=false&mashape-key=mP7f87A8jCmshXEEX8fWwtLskVSup1rHVURjsnkUd09UtPgq4v";
      URL url = new URL(sUrl);

      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.connect();

      InputStream inputStream = conn.getInputStream();
      // Read the input stream
      // Place input stream into a buffered reader
      reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      StringBuilder buffer = new StringBuilder();

      while ((line = reader.readLine()) != null) {
        buffer.append(line).append("\n");
      }

      recipeJsonStr = buffer.toString();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
    finally {
      if (conn != null) {
        conn.disconnect();
      }
      if (reader != null) {
        try {
          reader.close();
        }
        catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
      System.out.println(recipeJsonStr);
    }
    return recipeJsonStr;
  }

  public static Recipe[] parseData(String recipeJsonStr, int numRecipes) {

  Recipe[] recipes = new Recipe[numRecipes];

    try {
      JSONObject recipeJson = new JSONObject(recipeJsonStr);
      JSONArray recipeArray = recipeJson.getJSONArray("results");

      for (int i = 0; i < numRecipes; i++) {

        int id;
        String title, link = "";

        JSONObject recipeObject = (JSONObject) recipeArray.get(i);

        id = recipeObject.getInt("id");
        title = recipeObject.getString("title");

        recipes[i] = new Recipe(id, title, link);
      }
    }
    catch (JSONException e) {
      System.out.println(e.getMessage());
    }
    return recipes;
  }

  public static String parseData(String recipeJsonStr) {

    String recipeUrl = null;
    try {
      JSONObject recipeJson = new JSONObject(recipeJsonStr);
      recipeUrl = recipeJson.getString("source_url");
    }
    catch (JSONException e) {
      System.out.println(e.getMessage());
    }
    return recipes;
  }
