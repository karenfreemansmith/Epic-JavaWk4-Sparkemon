import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;

public class FairyPetTest {
  private FairyPet testPet;
  private FairyPet anotherPet;

  @Rule
  public DBRule database = new DBRule();

  @Before
  public void setup() {
    testPet = new FairyPet("Bubbles", 1);
  }

  @Test
  public void pet_instantiatesCorrectly_true() {
    assertEquals(true, testPet instanceof FairyPet);
  }

  @Test
  public void pet_instantiatesWithHalfPlayLevel() {
    assertEquals(testPet.getPlayLevel(), FairyPet.MAX_PLAY_LEVEL/2);
  }

  @Test
  public void pet_instantiatesWithHalfSleepLevel() {
    assertEquals(testPet.getSleepLevel(), FairyPet.MAX_SLEEP_LEVEL/2);
  }

  @Test
  public void pet_instantiatesWithHalfFoodLevel() {
    assertEquals(testPet.getFoodLevel(), FairyPet.MAX_FOOD_LEVEL/2);
  }

  @Test
  public void isAlive_cofirmPetIsAliveAboveMinimum_true() {
    assertTrue(testPet.isAlive());
  }

  @Test
  public void depleteLevels_reducesAllLevels() {
    testPet.depleteLevels();
    assertEquals(testPet.getFoodLevel(), (FairyPet.MAX_FOOD_LEVEL/2)-1);
    assertEquals(testPet.getSleepLevel(), (FairyPet.MAX_SLEEP_LEVEL/2)-1);
    assertEquals(testPet.getPlayLevel(), (FairyPet.MAX_PLAY_LEVEL/2)-1);
  }

  @Test
  public void sleep_increasesPetSleepLevel() {
    testPet.sleep();
    assertTrue(testPet.getSleepLevel()>(FairyPet.MAX_SLEEP_LEVEL/2));
  }

  @Test
  public void feed_increasesPetFoodLevel() {
    testPet.feed();
    assertTrue(testPet.getFoodLevel()>(FairyPet.MAX_FOOD_LEVEL/2));
  }

  @Test
  public void play_increasesPetPlayLevel() {
    testPet.play();
    assertTrue(testPet.getPlayLevel()>(FairyPet.MAX_PLAY_LEVEL/2));
  }

  @Test
  public void play_recordsTimeLastPlayedInDatabase() {
    testPet.play();
    Timestamp lastPlayed = Pet.find(testPet.getId()).getLastPlayed();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(lastPlayed));
  }

  @Test
  public void pet_foodLevelCannotGoBeyondMaxValue(){
    for(int i = FairyPet.MIN_ALL_LEVELS; i <= (FairyPet.MAX_FOOD_LEVEL + 2); i++){
      try {
        testPet.feed();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testPet.getFoodLevel() <= FairyPet.MAX_FOOD_LEVEL);
  }

  @Test
  public void feed_recordsTimeLastAteInDatabase() {
    testPet.feed();
    Timestamp lastAte = FairyPet.find(testPet.getId()).getLastAte();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(lastAte));
  }

  @Test
  public void pet_sleepLevelCannotGoBeyondMaxValue(){
    for(int i = FairyPet.MIN_ALL_LEVELS; i <= (FairyPet.MAX_SLEEP_LEVEL + 2); i++){
      try {
        testPet.sleep();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testPet.getSleepLevel() <= FairyPet.MAX_SLEEP_LEVEL);
  }

  @Test
  public void sleep_recordsTimeLastSleptInDatabase() {
    testPet.sleep();
    Timestamp lastSlept = FairyPet.find(testPet.getId()).getLastSlept();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(lastSlept));
  }

  @Test
  public void pet_playLevelCannotGoBeyondMaxValue(){
    for(int i = FairyPet.MIN_ALL_LEVELS; i <= (FairyPet.MAX_PLAY_LEVEL + 2); i++){
      try {
        testPet.play();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testPet.getPlayLevel() <= FairyPet.MAX_PLAY_LEVEL);
  }

  @Test
  public void isAlive_recognizeWhenPetDies_true() {
    anotherPet = new FairyPet("Spud", 1);
    for(int i = FairyPet.MIN_ALL_LEVELS; i<= FairyPet.MAX_FOOD_LEVEL; i++) {
      anotherPet.depleteLevels();
    }
    assertEquals(anotherPet.isAlive(), false);
  }

  @Test
  public void save_assignsIdToPet() {
    FairyPet savedPet = FairyPet.all().get(0);
    assertEquals(savedPet.getId(), testPet.getId());
  }

  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    Timestamp savedPetBirthday = FairyPet.find(testPet.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedPetBirthday.getDay());
  }

  @Test
  public void equals_returnsTrueIfNameAndPLayerIDAreSame_true() {
    anotherPet = new FairyPet("Bubbles", 1);
    assertTrue(anotherPet.equals(testPet));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    assertTrue(FairyPet.all().get(0).equals(testPet));
  }

  @Test
  public void save_savesPlayerIdToDB_true() {
    Player player = new Player("Henry", "henry@henry.com");
    anotherPet = new FairyPet("Spud", player.getId());
    FairyPet savedPet = Pet.find(anotherPet.getId());
    assertTrue(savedPet.getPlayerId()==player.getId());
  }

  @Test
  public void find_returnsPetWithSameId_otherPet() {
    anotherPet = new FairyPet("Spud", 1);
    assertTrue(FairyPet.find(anotherPet.getId()).equals(anotherPet));
  }

  @Test
  public void all_returnsAllInstancesOfPet_true() {
    anotherPet = new FairyPet("Spud", 1);
    assertTrue(FairyPet.all().get(0).equals(testPet));
    assertTrue(FairyPet.all().get(1).equals(anotherPet));
  }

  @Test
  public void timer_executesDepleteLevelsMethod() {
    int firstPlayLevel = testPet.getPlayLevel();
    testPet.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception) {}
    int secondPlayLevel = testPet.getPlayLevel();
    assertTrue(firstPlayLevel > secondPlayLevel);
  }

  @Test
  public void timer_haltsAfterMonsterDies() {
    testPet.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception) {}
    assertFalse(testPet.isAlive());
    assertTrue(testPet.getFoodLevel() >=0);
  }

}
