import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class PetTest {
  private Pet testPet;
  private Pet anotherPet;

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

  @Test
  public void save_assignsIdToPet() {
    Pet savedPet = Pet.all().get(0);
    assertEquals(savedPet.getId(), testPet.getId());
  }

  @Test
  public void equals_returnsTrueIfNameAndPLayerIDAreSame_true() {
    anotherPet = new Pet("Bubbles", 1);
    assertTrue(anotherPet.equals(testPet));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    assertTrue(Pet.all().get(0).equals(testPet));
  }

  @Test
  public void save_savesPlayerIdToDB_true() {
    Player player = new Player("Henry", "henry@henry.com");
    anotherPet = new Pet("Spud", player.getId());
    Pet savedPet = Pet.find(anotherPet.getId());
    assertTrue(savedPet.getPlayerId()==player.getId());
  }

  @Test
  public void find_returnsPetWithSameId_otherPet() {
    anotherPet = new Pet("Spud", 1);
    assertTrue(Pet.find(anotherPet.getId()).equals(anotherPet));
  }

  @Test
  public void all_returnsAllInstancesOfPet_true() {
    anotherPet = new Pet("Spud", 1);
    assertTrue(Pet.all().get(0).equals(testPet));
    assertTrue(Pet.all().get(1).equals(anotherPet));
  }

}
