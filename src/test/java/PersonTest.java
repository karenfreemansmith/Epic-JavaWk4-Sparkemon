import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PersonTest {
  private Person person;

  @Before
  public void setUp() {
    //DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test", null, null);
    person = new Person("Henry", "henry@henry.com");
 }

  @Test
  public void Task_instantiates_true() {
    assertEquals(true, person instanceof Person);
  }

  @Test
  public void getName_personInstantiatesWithName_Henry() {
    assertEquals("Henry", person.getName());
  }

  @Test
  public void getEmail_personInstantiatesWithEmail_String() {
    assertEquals("henry@henry.com", person.getEmail());
  }

  // @Test
  // public void equals_returnsTrueIfDescriptionsAretheSame() {
  //   Task firstTask = new Task("Mow the lawn");
  //   Task secondTask = new Task("Mow the lawn");
  //   assertTrue(firstTask.equals(secondTask));
  // }

  // @After
  // public void tearDown() {
  //   Task.clear();
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "DELETE FROM tasks *;";
  //     con.createQuery(sql).executeUpdate();
  //   }
  // }
}
