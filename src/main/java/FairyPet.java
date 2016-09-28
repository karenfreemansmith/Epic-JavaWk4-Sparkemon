import java.util.Timer;
import org.sql2o.*;

public class FairyPet extends Pet {
  private int specialLevel;
  public static final int MAX_SPECIAL_LEVEL = 8;

  public FairyPet(String name, int playerId) {
    this.name = name;
    this.playerId = playerId;
    foodLevel=MAX_FOOD_LEVEL/2;
    sleepLevel=MAX_SLEEP_LEVEL/2;
    playLevel=MAX_PLAY_LEVEL/2;
    fireLevel=MAX_SPECIAL_LEVEL/2;
    time = new Timer();
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
  public void isAlive() {
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

  public void fire() {
    if(specialLevel >= MAX_FIRE_LEVEL) {
      throw new UnsupportedOperationException ("Even special pets can get too much attention - back off!");
    }
    fireLevel++;
  }

  public static List<FairyPet> all() {
    String sql = "SELECT * FROM pets WHERE type=1";
    try(Connection cn = DB.sql2o.open()) {
      return cn.createQuery(sql).executeAndFetch(FairyPet.class);
    }
  }

  public static FairyPet find(int id) {
    String sql = "SELECT * FROM pets WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      FairyPet pet = cn.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(FairyPet.class);
      return pet;
    }
  }

}
