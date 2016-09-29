public class Community implements DatabaseManagement {
  @Override
  public boolean equals(Object otherCommunity) {
    if(!(otherCommunity instanceof Community)) {
      return false;
    } else {
      Community newCommunity = (Community) otherCommunity;
      return this.getName().equals(newCommunity.getName()) &&
             this.getDescription().equals(newCommunity.getDescription());
    }
  }

  @Override
  public void save() {
    try(Connection cn = DB.sql2o.open()) {
      String sql = "INSERT INTO communities (name, description) VALUES (:name, :description)";
      this.id = (int) cn.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public void delete() {
    try(Connection cn = DB.sql2o.open()) {
      String sql = "DELETE FROM communities WHERE id=:id;";
      cn.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
      sql = "DELETE FROM communities_persns WHERE communityId = :communityId";
      cn.createQuery(sql)
        .addParameter("communityId", this.getId())
        .executeUpdate();
    }
  }
}
