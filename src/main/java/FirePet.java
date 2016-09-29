import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class FirePet extends Pet implements DatabaseManagement {
  private int fireLevel;
  public static final int PET_TYPE=7;
  public static final int MAX_FIRE_LEVEL = 8;

  public FirePet(String name, int playerId) {
    this.name = name;
    this.playerId = playerId;
    type=PET_TYPE;
    foodLevel=MAX_FOOD_LEVEL/2;
    sleepLevel=MAX_SLEEP_LEVEL/2;
    playLevel=MAX_PLAY_LEVEL/2;
    specialLevel=MAX_FIRE_LEVEL/2;
    timer = new Timer();
    save();
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
    String sql = "SELECT * FROM pets WHERE type=7";
    try(Connection cn = DB.sql2o.open()) {
      return cn.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(FirePet.class);
    }
  }

  public static FirePet find(int id) {
    String sql = "SELECT * FROM pets WHERE id=:id";
    try(Connection cn = DB.sql2o.open()) {
      FirePet pet = cn.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(FirePet.class);
      return pet;
    }
  }

}
