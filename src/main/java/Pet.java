import org.sql2o.*;
import java.util.List;

public class Pet {
  private int id;
  private String name;
  private int playerId;
  private int foodLevel;
  private int sleepLevel;
  private int playLevel;

  public static final int MAX_FOOD_LEVEL = 6;
  public static final int MAX_SLEEP_LEVEL = 12;
  public static final int MAX_PLAY_LEVEL = 16;
  public static final int MIN_ALL_LEVELS = 0;

  public Pet(String name, int playerId) {
    this.name = name;
    this.playerId = playerId;
    this.foodLevel=MAX_FOOD_LEVEL/2;
    this.sleepLevel=MAX_SLEEP_LEVEL/2;
    this.playLevel=MAX_PLAY_LEVEL/2;
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

  public int getFoodLevel() {
    return foodLevel;
  }

  public int getSleepLevel() {
    return sleepLevel;
  }

  public int getPlayLevel() {
    return playLevel;
  }

  public void feed() {
    if(foodLevel>=MAX_FOOD_LEVEL) {
      throw new UnsupportedOperationException("Your pet is full, you cannot feed it more at this time.");
    }
    foodLevel++;
  }

  public void sleep() {
    if(sleepLevel>=MAX_SLEEP_LEVEL) {
      throw new UnsupportedOperationException("Your pet is not tired, it will not sleep right now.");
    }
    sleepLevel++;
  }

  public void play() {
    if(playLevel>=MAX_PLAY_LEVEL) {
      throw new UnsupportedOperationException("Your pet is bored, it refuses to play anymore.");
    }
    playLevel++;
  }

  public void depleteLevels() {
    playLevel--;
    foodLevel--;
    sleepLevel--;
  }

  public boolean isAlive() {
    if(foodLevel <= MIN_ALL_LEVELS ||
        playLevel <= MIN_ALL_LEVELS ||
        sleepLevel <= MIN_ALL_LEVELS) {
          return false;
        }
    return true;
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
