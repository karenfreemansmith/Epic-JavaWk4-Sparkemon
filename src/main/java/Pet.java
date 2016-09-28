import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Pet {
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
  public Timer timer;

  public static final int MAX_FOOD_LEVEL = 6;
  public static final int MAX_SLEEP_LEVEL = 12;
  public static final int MAX_PLAY_LEVEL = 16;
  public static final int MIN_ALL_LEVELS = 0;

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

  public void feed() {
    if(foodLevel>=MAX_FOOD_LEVEL) {
      throw new UnsupportedOperationException("Your pet is full, you cannot feed it more at this time.");
    }
    String sql = "UPDATE pets SET lastate = now() WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      cn.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
    foodLevel++;
  }

  public void sleep() {
    if(sleepLevel>=MAX_SLEEP_LEVEL) {
      throw new UnsupportedOperationException("Your pet is not tired, it will not sleep right now.");
    }
    String sql = "UPDATE pets SET lastslept = now() WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      cn.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
    sleepLevel++;
  }

  public void play() {
    if(playLevel>=MAX_PLAY_LEVEL) {
      throw new UnsupportedOperationException("Your pet is bored, it refuses to play anymore.");
    }
    String sql = "UPDATE pets SET lastplayed = now() WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      cn.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
    playLevel++;
  }

  public void depleteLevels() {
    if(isAlive()) {
      playLevel--;
      foodLevel--;
      sleepLevel--;
    }
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
    String sql = "INSERT INTO pets (name, playerId, birthday) VALUES (:name, :playerId, now())";
    try(Connection cn = DB.sql2o.open()) {
      this.id=(int) cn.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("playerId", this.playerId)
        .executeUpdate()
        .getKey();
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
