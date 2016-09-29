
import java.util.Map;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    ProcessBuilder process = new ProcessBuilder();
    Integer port;
    if (process.environment().get("PORT") != null) {
       port = Integer.parseInt(process.environment().get("PORT"));
    } else {
       port = 4567;
    }

    setPort(port);



    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("players", Player.all());
      model.put("template", "templates/players.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Player player = new Player(request.queryParams("player"), request.queryParams("email"));
      model.put("player", player);
      model.put("players", Player.all());
      model.put("template", "templates/players.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/players/:playerId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Player player = Player.find(Integer.parseInt(request.params(":playerId")));
      model.put("player", player);
      model.put("template", "templates/player.vtl");
      model.put("pets", player.getPets());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/addpet/:playerId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Pet newPet = Player.find(Integer.parseInt(request.params(":playerId"))).addPetToPlayer();
      response.redirect("/pets/" + newPet.getId());
      model.put("template", "templates/pet.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sleep/pets/:petId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Pet newPet = Pet.find(Integer.parseInt(request.params(":petId")));
      try {
        newPet.sleep();
      } catch (UnsupportedOperationException exception) {
        model.put("msg", exception.getMessage());
      }
      Pet pet = Pet.find(Integer.parseInt(request.params(":petId")));
      Player player = Player.find(pet.getPlayerId());
      model.put("pet", pet);
      model.put("player", player);
      model.put("template", "templates/pet.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/feed/pets/:petId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Pet newPet = Pet.find(Integer.parseInt(request.params(":petId")));
      try {
        newPet.feed();
      } catch (UnsupportedOperationException exception) {
        model.put("msg", exception.getMessage());
      }
      Pet pet = Pet.find(Integer.parseInt(request.params(":petId")));
      Player player = Player.find(pet.getPlayerId());
      model.put("pet", pet);
      model.put("player", player);
      model.put("template", "templates/pet.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/play/pets/:petId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Pet newPet = Pet.find(Integer.parseInt(request.params(":petId")));
      try {
        newPet.play();
      } catch (UnsupportedOperationException exception) {
        model.put("msg", exception.getMessage());
      }
      Pet pet = Pet.find(Integer.parseInt(request.params(":petId")));
      Player player = Player.find(pet.getPlayerId());
      model.put("pet", pet);
      model.put("player", player);
      model.put("template", "templates/pet.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/pets/:petId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Pet pet = Pet.find(Integer.parseInt(request.params(":petId")));
      Player player = Player.find(pet.getPlayerId());
      pet.depleteLevels();
      model.put("pet", pet);
      model.put("player", player);
      model.put("template", "templates/pet.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/pets/:petId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Pet pet = Pet.find(Integer.parseInt(request.params(":petId")));
      pet.setName(request.queryParams("petname"));
      Player player = Player.find(pet.getPlayerId());
      model.put("pet", pet);
      model.put("player", player);
      model.put("template", "templates/pet.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
