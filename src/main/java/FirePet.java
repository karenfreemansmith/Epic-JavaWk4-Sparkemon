import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class FirePet extends Pet {
  private int fireLevel;
  public static final int MAX_FIRE_LEVEL = 8;

  public FirePet(String name, int playerId) {
    this.name = name;
    this.playerId = playerId;
    foodLevel=MAX_FOOD_LEVEL/2;
    sleepLevel=MAX_SLEEP_LEVEL/2;
    playLevel=MAX_PLAY_LEVEL/2;
    fireLevel=MAX_FIRE_LEVEL/2;
    timer = new Timer();
    save();
  }

  @Override
  public void depleteLevels() {
    if(isAlive()) {
      playLevel--;
      foodLevel--;
      sleepLevel--;
      fireLevel--;
    }
  }

  @Override
  public boolean isAlive() {
    if(foodLevel <= MIN_ALL_LEVELS ||
      playLevel <= MIN_ALL_LEVELS ||
      sleepLevel <= MIN_ALL_LEVELS ||
      fireLevel <= MIN_ALL_LEVELS) {
      return false;
    }
    return true;
  }

  public int getfireLevel() {
    return fireLevel;
  }

  public void fire() {
    if(fireLevel >= MAX_FIRE_LEVEL) {
      throw new UnsupportedOperationException ("Even fire pets can burn, don't give it too much kindling!");
    }
    fireLevel++;
  }

  public static List<FirePet> all() {
    String sql = "SELECT * FROM pets";
    try(Connection cn = DB.sql2o.open()) {
      return cn.createQuery(sql).executeAndFetch(FirePet.class);
    }
  }

  public static FirePet find(int id) {
    String sql = "SELECT * FROM pets WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      FirePet pet = cn.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(FirePet.class);
      return pet;
    }
  }

}
