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
  public void pet_instantiatesWithHalfPlayLevel() {
    assertEquals(testPet.getPlayLevel(), Pet.MAX_PLAY_LEVEL/2);
  }

  @Test
  public void pet_instantiatesWithHalfSleepLevel() {
    assertEquals(testPet.getSleepLevel(), Pet.MAX_SLEEP_LEVEL/2);
  }

  @Test
  public void pet_instantiatesWithHalfFoodLevel() {
    assertEquals(testPet.getFoodLevel(), Pet.MAX_FOOD_LEVEL/2);
  }

  @Test
  public void isAlive_cofirmPetIsAliveAboveMinimum_true() {
    assertTrue(testPet.isAlive());
  }

  @Test
  public void depleteLevels_reducesAllLevels() {
    testPet.depleteLevels();
    assertEquals(testPet.getFoodLevel(), (Pet.MAX_FOOD_LEVEL/2)-1);
    assertEquals(testPet.getSleepLevel(), (Pet.MAX_SLEEP_LEVEL/2)-1);
    assertEquals(testPet.getPlayLevel(), (Pet.MAX_PLAY_LEVEL/2)-1);
  }

  @Test
  public void sleep_increasesPetSleepLevel() {
    testPet.sleep();
    assertTrue(testPet.getSleepLevel()>(Pet.MAX_SLEEP_LEVEL/2));
  }

  @Test
  public void feed_increasesPetFoodLevel() {
    testPet.feed();
    assertTrue(testPet.getFoodLevel()>(Pet.MAX_FOOD_LEVEL/2));
  }

  @Test
  public void play_increasesPetPlayLevel() {
    testPet.play();
    assertTrue(testPet.getPlayLevel()>(Pet.MAX_PLAY_LEVEL/2));
  }

  @Test
  public void pet_foodLevelCannotGoBeyondMaxValue(){
    for(int i = Pet.MIN_ALL_LEVELS; i <= (Pet.MAX_FOOD_LEVEL + 2); i++){
      try {
        testPet.feed();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testPet.getFoodLevel() <= Pet.MAX_FOOD_LEVEL);
  }

  @Test
  public void pet_sleepLevelCannotGoBeyondMaxValue(){
    for(int i = Pet.MIN_ALL_LEVELS; i <= (Pet.MAX_SLEEP_LEVEL + 2); i++){
      try {
        testPet.sleep();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testPet.getSleepLevel() <= Pet.MAX_SLEEP_LEVEL);
  }

  @Test
  public void pet_playLevelCannotGoBeyondMaxValue(){
    for(int i = Pet.MIN_ALL_LEVELS; i <= (Pet.MAX_PLAY_LEVEL + 2); i++){
      try {
        testPet.play();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testPet.getPlayLevel() <= Pet.MAX_PLAY_LEVEL);
  }

  @Test
  public void isAlive_recognizeWhenPetDies_true() {
    anotherPet = new Pet("Spud", 1);
    for(int i = Pet.MIN_ALL_LEVELS; i<= Pet.MAX_FOOD_LEVEL; i++) {
      anotherPet.depleteLevels();
    }
    assertEquals(anotherPet.isAlive(), false);
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
