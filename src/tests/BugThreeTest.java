/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

/**
 *
 * @author Jason
 */
// Import tests
import Dice;
import Game;
import Player;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mockito;

// Import for testing
import src.DiceValue;

public class BugThreeTest {
  private int winnings;
    private String name;
    private int balance;
    private int limit;
    private Player player;
    
    private int bet;
    
    private Dice d1;
    private Dice d2;
    private Dice d3;
    
    private Game game;
    
    @Before
    public void setUp() throws Exception {
	this.winnings = 0;
	this.name = "Snake Eyes Joe";
    	this.balance = 15;
    	this.bet = 5;
    	this.limit = 0;

        this.d1 = Mockito.mock(Dice.class);
        this.d2 = Mockito.mock(Dice.class);
        this.d3 = Mockito.mock(Dice.class);
        this.game = new Game(d1, d2, d3);
    }

    @After
    public void tearDown() throws Exception {
	this.winnings = 0;
	this.name = null;
    	this.balance = 0;
    	this.limit = 0;
        this.player = null;
        
        this.bet = 0;
        
        this.d1 = null;
        this.d2 = null;
        this.d3 = null;
        
    }
  
  @Test
	public void testHypothesis() {

		// Creates 50 games
		DiceValue[] rolls = new DiceValue[50];

                //plays 50 games with random 
		int counter = 0;
		for (DiceValue d : rolls) {
			rolls[counter] = DiceValue.getRandom();
			System.out.println(counter + " - " + rolls[counter]);
			counter++;
		}

		// Looks for spades
		counter = 0;
		int match = 1;

		// Assert
		for (DiceValue d : rolls) {

			if (d == DiceValue.SPADE) {
				System.out.println("Spades: " + match);
				match++;
			}
			counter++;
		}

		assertTrue(match > 1); // if there are multiple matches, spades are coming up

	}
}

