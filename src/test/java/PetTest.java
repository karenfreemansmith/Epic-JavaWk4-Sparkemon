import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class PetTest {
  private Pet testPet;

  @Rule
  public DBRule database = new DBRule();

  @Before
  public void setup() {
    testPet = new Pet("Bubbles", 1);
  }

  @Test
  public void pet_instantiatesCorrectly_true() {
    assertEquals(true, testPet instanceof Pet);
  }

}
