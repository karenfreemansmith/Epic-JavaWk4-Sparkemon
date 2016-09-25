import org.sql2o.*;
import java.util.List;

public class Pet {
  private int id;
  private String name;
  private int playerId;

  public Pet(String name, int playerId) {
    this.name = name;
    this.playerId = playerId;
    this.save();
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getPlayerId() {
    return playerId;
  }

  public void save() {
    String sql = "INSERT INTO pets (name, playerId) VALUES (:name, :playerId)";
    try(Connection cn = DB.sql2o.open()) {
      this.id=(int) cn.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("playerId", this.playerId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Pet find(int id) {
    String sql = "SELECT * FROM pets WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      Pet pet = cn.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Pet.class);
      return pet;
    }
  }

  public static List<Pet> all() {
    String sql = "SELECT * FROM pets";
    try(Connection cn = DB.sql2o.open()) {
      return cn.createQuery(sql).executeAndFetch(Pet.class);
    }
  }

  @Override
  public boolean equals(Object otherPet) {
    if(!(otherPet instanceof Pet)) {
      return false;
    } else {
      Pet newPet = (Pet) otherPet;
      return this.getName().equals(newPet.getName()) &&
             this.getPlayerId() == newPet.getPlayerId();
    }
  }

}
