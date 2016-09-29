import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DBRule extends ExternalResource {
  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/sparkemon_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection cn = DB.sql2o.open()) {
      String sql = "DELETE FROM players *;";
      cn.createQuery(sql).executeUpdate();
      sql = "DELETE FROM pets *;";
      cn.createQuery(sql).executeUpdate();
    }
  }

}
