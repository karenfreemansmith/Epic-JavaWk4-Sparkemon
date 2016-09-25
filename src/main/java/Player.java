import org.sql2o.*;
import java.util.List;

public class Player {
  private int id;
  private String name;
  private String email;

  public Player(String name, String email) {
    this.name = name;
    this.email = email;
    this.save();
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public boolean equals(Object otherPlayer) {
    if (!(otherPlayer instanceof Player)) {
      return false;
    } else {
      Player newPlayer = (Player) otherPlayer;
      return this.getName().equals(newPlayer.getName()) &&
             this.getEmail().equals(newPlayer.getEmail());
    }
  }

  public void save() {
    String sql = "INSERT INTO players (name, email) VALUES (:name, :email)";
    try(Connection cn = DB.sql2o.open()) {
      this.id = (int) cn.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("email", this.email)
        .executeUpdate()
        .getKey();
    }
  }

  public static Player find(int id) {
    String sql = "SELECT * FROM players where id=:id";
    try(Connection cn = DB.sql2o.open()) {
      Player player = cn.createQuery(sql)
        .addParameter("id",id)
        .executeAndFetchFirst(Player.class);
      return player;
    }
  }

  public static List<Player> all() {
    String sql = "SELECT * FROM players";
    try(Connection cn = DB.sql2o.open()) {
      return cn.createQuery(sql).executeAndFetch(Player.class);
    }
  }

}
