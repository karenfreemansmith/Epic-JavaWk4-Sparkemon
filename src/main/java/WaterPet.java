import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class WaterPet extends Pet {
  private int waterLevel;
  public static final int MAX_WATER_LEVEL = 8;

  public WaterPet(String name, int playerId) {
    this.name = name;
    this.playerId = playerId;
    foodLevel=MAX_FOOD_LEVEL/2;
    sleepLevel=MAX_SLEEP_LEVEL/2;
    playLevel=MAX_PLAY_LEVEL/2;
    waterLevel=MAX_WATER_LEVEL/2;
    timer = new Timer();
    save();
  }

  @Override
  public void depleteLevels() {
    if(isAlive()) {
      playLevel--;
      foodLevel--;
      sleepLevel--;
      waterLevel--;
    }
  }

  @Override
  public boolean isAlive() {
    if(foodLevel <= MIN_ALL_LEVELS ||
      playLevel <= MIN_ALL_LEVELS ||
      sleepLevel <= MIN_ALL_LEVELS ||
      waterLevel <= MIN_ALL_LEVELS) {
      return false;
    }
    return true;
  }

  public int getWaterLevel() {
    return waterLevel;
  }

  public void water() {
    if(waterLevel >= MAX_WATER_LEVEL) {
      throw new UnsupportedOperationException ("Even water pets can drown, don't overwater it!");
    }
    waterLevel++;
  }

  public static List<WaterPet> all() {
    String sql = "SELECT * FROM pets";
    try(Connection cn = DB.sql2o.open()) {
      return cn.createQuery(sql).executeAndFetch(WaterPet.class);
    }
  }

  public static WaterPet find(int id) {
    String sql = "SELECT * FROM pets WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      WaterPet pet = cn.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(WaterPet.class);
      return pet;
    }
  }

}
