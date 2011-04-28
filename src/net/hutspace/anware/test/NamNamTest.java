package net.hutspace.anware.test;

import junit.framework.TestCase;
import net.hutspace.anware.Game;
import net.hutspace.anware.NamNamGame;



/**
 * Nam-Nam - A traditional Ghanaian variant of Oware
 * 
 * Nam-nam: A Twi word (language spoken amongst the Akans of Ghana) meaning, 
 * "to roam" which is descriptive of the nature of play in this version of the 
 * game. This version of Oware is also played all along the West African Coast 
 * and the Caribbean. It also has many names: Jerin-Jerin (Yoruba-Nigeria), 
 * Round & Round (Antigua) etc.
 * 
 * Arrangement of board at the beginning of each game or round:
 * Four seeds are placed in each house on a board that is made up of two rows of 
 * six houses making twelve in total. 
 * 
 * Each row of six houses is the territory of the player sitting nearest to 
 * them, with two end houses used as stores for captured seeds.
 * 
 * Object of the game:
 * The object of the game is to capture all the opponents, territory. This 
 * ultimate aim is usually achieved after at least several rounds.
 * 
 * Starting:
 * Each player takes it in turns to start. The player must then choose a house 
 * from their own territory, from which all the seeds are scooped.
 * 
 * Sowing seeds
 * The seeds are then sown in an anticlockwise direction placing one seed in 
 * each house as one travel, around the board. If the last seed drops in a 
 * house with seeds in it, all the seeds are scooped up and then sown until 
 * ones last seed lands in a house, which is empty. If at any point in the 
 * game one player does not have any seeds, the other player must sow seeds 
 * from a house that would provide the opponent with seeds to continue playing 
 * the game. If this is not possible then the game comes to an end, with the 
 * remaining seeds going the player who has the seeds on their territory.
 * 
 * Capturing seeds
 * Seeds are captured when the sowing of seeds by either player creates four 
 * seeds on ones territory. One can only capture seeds on ones own territory, 
 * except when four seeds are made with ones last seed on the opponent's 
 * territory. Once the seeds are captured they are placed in ones store or in 
 * front of one, in the case of portable boards which do not have stores.
 * 
 * End of round
 * When there are eight seeds left in play, since it is impossible to play for 
 * the last four seeds. The player who captures the penultimate set of four 
 * seeds, also gains the last set of four on the board, this then ends the 
 * round. The winner of the first round is the one who has captured the most 
 * seeds.
 * 
 * Second round
 * Upon completion of the first round each player places the seeds they 
 * have won back in the houses on their territory four seeds in each house. 
 * 
 * If each is able to fill the same number of houses then it is a draw. 
 * However if one player is able to fill more houses than that which they 
 * started with then those houses filled, on the opponents side now become 
 * part of their territory in the next round. The winner of each round is the 
 * one with the most territory. This capturing of territory can go on for 
 * quite a while as lost territory can always be reclaimed in subsequent rounds.
 * 
 * End of game
 * The game ends when one persons territory (six houses) is completely captured.
 * 
 * @author chief
 */
public class NamNamTest extends TestCase {
	private Game game;
	
	protected void setUp() throws Exception {
		game = new NamNamGame();
	}

	public void testBeginState() {
		for (int i = 0; i < 12; ++i)
			assertEquals(4, game.pit(i));
		
		for (int i = 0; i < 2; ++i)
			assertEquals(0, game.store(i));
		
		for (int i = 0; i < 6; ++i)
			assertTrue("Player 1 can make move > " + i, game.valid(i));
		for (int i = 6; i < 12; ++i)
			assertFalse("Player 1 cannot make move > " + i, game.valid(i));
	}
	
	public void testLegalMoveAtStart() {
		game.move(0);
		
		int[] pits = new int[]{2, 7, 1, 6, 1, 6, 6, 6, 0, 1, 6, 6};
		assertEquals(48, sum(pits));
		validatePits(pits);
	}

	private void validatePits(int[] pits) {
		for (int i = 0; i < pits.length; ++i)
			assertEquals(pits[i], game.pit(i));
	}
	
	public void testAPairOfLegalMoves() {
		game.move(2);
		validatePits(new int[]{6, 6, 2, 7, 1, 6, 1, 6, 6, 6, 0, 1});
		game.move(6);
		validatePits(new int[]{1, 9, 2, 10, 0, 0, 3, 0, 1, 10, 0, 0});
	}
	
	
	public void testPlayOutOfTurn() {
	}
	
	
	public void testAttemptToMoveFromEmptyPit() {
		
	}
	
	public void testBoardLimits() {
		
	}
	
	public void testMoveThatCaptures() {
		
	}
	
	protected void tearDown() throws Exception {
	}
	
	
	public void testSum() {
		assertEquals(0, sum(new int[]{-1,1,0,0}));
		assertEquals(1, sum(new int[]{-1,1,0,0,1}));
		assertEquals(10, sum(new int[]{1,2,3,4}));
		assertEquals(-10, sum(new int[]{-1,-2,-3,-4}));
	}
	
	private static int sum(int[] pits) {
		int sum = 0;
		for (int i = 0; i < pits.length; ++i)
			sum += pits[i];
		return sum;
	}
}
