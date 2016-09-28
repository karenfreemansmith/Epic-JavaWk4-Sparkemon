import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class PlayerTest {
  private Player player;
  private Player player2;

  @Rule
  public DBRule database = new DBRule();

  @Before
  public void setUp() {
    player = new Player("Henry", "henry@henry.com");
 }

  @Test
  public void Task_instantiates_true() {
    assertEquals(true, player instanceof Player);
  }

  @Test
  public void getName_PlayerInstantiatesWithName_Henry() {
    assertEquals("Henry", player.getName());
  }

  @Test
  public void getEmail_PlayerInstantiatesWithEmail_String() {
    assertEquals("henry@henry.com", player.getEmail());
  }

  @Test
  public void equals_returnsTrueIfNameAndEmailAretheSame() {
    player2 = new Player("Henry", "henry@henry.com");
    assertTrue(player.equals(player2));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Player() {
    assertTrue(player.all().get(0).equals(player));
  }

  @Test
  public void save_assignsIdToPlayer() {
    Player savedPlayer = Player.all().get(0);
    assertEquals(player.getId(), savedPlayer.getId());
  }

  @Test
  public void find_returnsPlayerWithSameId_player2() {
    player2 = new Player("Harriet", "harriet@harriet.com");
    assertEquals(Player.find(player2.getId()), player2);
  }

  @Test
  public void all_returnsAllInstancesOfPlayer_true() {
    player2 = new Player("Harriet", "harriet@harriet.com");
    assertTrue(Player.all().get(0).equals(player));
    assertTrue(Player.all().get(1).equals(player2));
  }

  @Test
  public void getPets_returnsAllPlayerPets_petList() {
    WaterPet pet1 = new WaterPet("Bubbles", player.getId());
    GrassPet pet2 = new GrassPet("Spud", player.getId());
    Pet[] pets = new Pet[] {pet1, pet2};
    assertTrue(player.getPets().containsAll(Arrays.asList(pets)));
  }

}
