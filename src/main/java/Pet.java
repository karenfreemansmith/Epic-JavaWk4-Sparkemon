import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class Pet {
  public int id;
  public String name;
  public int playerId;
  public int foodLevel;
  public int sleepLevel;
  public int playLevel;
  public Timestamp birthday;
  public Timestamp lastSlept;
  public Timestamp lastAte;
  public Timestamp lastPlayed;
  public Timestamp lastSpecial;
  public int specialLevel;
  public Timer timer;
  public int type;

  public static final int MAX_FOOD_LEVEL = 6;
  public static final int MAX_SLEEP_LEVEL = 12;
  public static final int MAX_PLAY_LEVEL = 16;
  public static final int MIN_ALL_LEVELS = 0;

  public void save() {
    String sql = "INSERT INTO pets (name, playerId, birthday, type, foodLevel, sleepLevel, playLevel) VALUES (:name, :playerId, now(), :type, :food, :sleep, :play)";
    try(Connection cn = DB.sql2o.open()) {
      this.id=(int) cn.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("playerId", this.playerId)
        .addParameter("type", this.type)
        .addParameter("food", this.foodLevel)
        .addParameter("sleep", this.sleepLevel)
        .addParameter("play", this.playLevel)
        .executeUpdate()
        .getKey();
    }
  }

  public void startTimer() {
    Pet currentPet = this;
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        if(currentPet.isAlive() == false) {
          cancel();
        }
        depleteLevels();
      }
    };
    this.timer.schedule(timerTask, 0, 600);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String petname) {
    String sql = "UPDATE pets SET name=:petname WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      cn.createQuery(sql)
        .addParameter("petname", petname)
        .addParameter("id", this.id)
        .throwOnMappingFailure(false)
        .executeUpdate();
    }
    this.name=petname;
  }

  public int getPlayerId() {
    return playerId;
  }

  public Timestamp getBirthday() {
    return birthday;
  }

  public int getFoodLevel() {
    return foodLevel;
  }

  public Timestamp getLastAte() {
    return lastAte;
  }

  public int getSleepLevel() {
    return sleepLevel;
  }

  public Timestamp getLastSlept() {
    return lastSlept;
  }

  public int getPlayLevel() {
    return playLevel;
  }

  public Timestamp getLastPlayed() {
    return lastPlayed;
  }

  public int getType() {
    return type;
  }

  public void feed() {
    if(foodLevel>=MAX_FOOD_LEVEL) {
      throw new UnsupportedOperationException("Your pet is full, you cannot feed it more at this time.");
    }
    foodLevel++;
    String sql = "UPDATE pets SET lastate = now(), foodlevel=:food WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      cn.createQuery(sql)
        .addParameter("id", id)
        .addParameter("food", this.foodLevel)
        .executeUpdate();
    }

  }

  public void sleep() {
    if(sleepLevel>=MAX_SLEEP_LEVEL) {
      throw new UnsupportedOperationException("Your pet is not tired, it will not sleep right now.");
    }
    sleepLevel++;
    String sql = "UPDATE pets SET lastslept = now(), sleeplevel=:sleep WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      cn.createQuery(sql)
        .addParameter("id", id)
        .addParameter("sleep", this.sleepLevel)
        .executeUpdate();
    }

  }

  public void play() {
    if(playLevel>=MAX_PLAY_LEVEL) {
      throw new UnsupportedOperationException("Your pet is bored, it refuses to play anymore.");
    }
    playLevel++;
    String sql = "UPDATE pets SET lastplayed = now(), playlevel=:play  WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      cn.createQuery(sql)
        .addParameter("id", id)
        .addParameter("play", this.playLevel)
        .executeUpdate();
    }

  }

  public String getPetType() {
    String[] types = {
      "bug", "dark", "dragon", "electric", "fairy", "fighting", "fire",
      "flying", "ghost", "grass", "ground", "ice", "normal", "poison",
      "psychic", "rock", "steel", "unknown", "water"
    };
    return types[this.type-1];
  }

  public void depleteLevels() {
    if(isAlive()) {
      playLevel--;
      foodLevel--;
      sleepLevel--;
      specialLevel--;
      String sql = "UPDATE pets SET playlevel=:play, foodlevel=:food, sleeplevel=:sleep, speciallevel=:special  WHERE id=:id";
      try(Connection cn = DB.sql2o.open()) {
        cn.createQuery(sql)
          .addParameter("play", this.playLevel)
          .addParameter("food", this.foodLevel)
          .addParameter("sleep", this.sleepLevel)
          .addParameter("special", this.specialLevel)
          .addParameter("id", this.id)
          .executeUpdate();
      }
    }
  }

  public boolean isAlive() {
    if(foodLevel <= MIN_ALL_LEVELS ||
        playLevel <= MIN_ALL_LEVELS ||
        sleepLevel <= MIN_ALL_LEVELS ||
        specialLevel <= MIN_ALL_LEVELS) {
          return false;
        }
    return true;
  }



  public static Pet find(int id) {
    String sql = "SELECT * FROM pets WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      Pet pet = cn.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Pet.class);
      return pet;
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

  public void delete() {
    try(Connection cn = DB.sql2o.open()) {
      String sql = "DELETE FROM pets WHERE id=:id;";
      cn.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
