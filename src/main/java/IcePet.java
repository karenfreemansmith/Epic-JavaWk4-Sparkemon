import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class IcePet extends Pet {
  private int specialLevel;
  public static final int PET_TYPE=12;
  public static final int MAX_SPECIAL_LEVEL = 8;

  public IcePet(String name, int playerId) {
    this.name = name;
    this.playerId = playerId;
    type=PET_TYPE;
    foodLevel=MAX_FOOD_LEVEL/2;
    sleepLevel=MAX_SLEEP_LEVEL/2;
    playLevel=MAX_PLAY_LEVEL/2;
    specialLevel=MAX_SPECIAL_LEVEL/2;
    timer = new Timer();
    save();
  }

  @Override
  public void depleteLevels() {
    if(isAlive()) {
      playLevel--;
      foodLevel--;
      sleepLevel--;
      specialLevel--;
    }
  }

  @Override
  public boolean isAlive() {
    if(foodLevel <= MIN_ALL_LEVELS ||
      playLevel <= MIN_ALL_LEVELS ||
      sleepLevel <= MIN_ALL_LEVELS ||
      specialLevel <= MIN_ALL_LEVELS) {
      return false;
    }
    return true;
  }

  public int getSpecialLevel() {
    return specialLevel;
  }

  public void special() {
    if(specialLevel >= MAX_SPECIAL_LEVEL) {
      throw new UnsupportedOperationException ("Even special pets can get too much attention - back off!");
    }
    specialLevel++;
  }

  public static List<IcePet> all() {
    String sql = "SELECT * FROM pets WHERE type=12";
    try(Connection cn = DB.sql2o.open()) {
      return cn.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(IcePet.class);
    }
  }

  public static IcePet find(int id) {
    String sql = "SELECT * FROM pets WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      IcePet pet = cn.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(IcePet.class);
      return pet;
    }
  }

}
