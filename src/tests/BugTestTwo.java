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
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BugTestTwo {
    
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
    public void testBugReplication(){

	List<DiceValue> cdv = game.getDiceValues();
        player = new Player(name, balance);
        player.setLimit(limit);

        
	Mockito.when(d1.getValue()).thenReturn(DiceValue.DIAMOND);
	Mockito.when(d2.getValue()).thenReturn(DiceValue.HEART);
	Mockito.when(d3.getValue()).thenReturn(DiceValue.SPADE);

        System.out.println(String.format("\n\n%s starts with balance %d, limit %d\n", 
        		player.getName(), player.getBalance(), player.getLimit()));

        int turn = 0;
        
               
        while ( player.balanceExceedsLimitBy(bet) && player.getBalance() < 200 )
        {
            turn++;                   
            // pick ANCHOR
            DiceValue pick = DiceValue.ANCHOR;
           
            System.out.printf("Turn %d: %s bet %d on %s\n", 
        	    turn, player.getName(), bet, pick); 
            // play a round of the game
            int winnings = game.playRound(player, pick, bet);
            cdv = game.getDiceValues();
            // output results of game
            System.out.printf("Rolled %s, %s, %s\n",
        	    cdv.get(0), cdv.get(1), cdv.get(2));
            
            System.out.printf("%s won %d, balance now %d\n\n",
        	    player.getName(), winnings, player.getBalance());
            
        } //while
        System.out.print(String.format("%d turns later.\nEnd Game ", turn));
        System.out.println(String.format("%s now has balance %d\n", player.getName(), player.getBalance()));
    }
    
    
    
    @Test
    public void testHyothesis() {
	// set the balance 
	this.balance = 15;
	
	this.player = Mockito.spy(new Player(this.name, this.balance));
	this.player.setLimit(limit);
	
	System.out.println("\nTest Hypothesis One");
	System.out.println("\tPlayer starting balance = " + player.getBalance());
	
	
	boolean thrown = false;
	int count = 0;
	while (player.getBalance() != player.getLimit()) {
	    // catch exception 
	    try {
        	System.out.print("Player takes bet.");
        	player.takeBet(bet);
	    } 
	    catch (Exception e) {
        	thrown = true;
	    }
	    
	    if (thrown) {
		System.out.println("\n\tException has been thrown!");
		System.out.println(String.format("\tBalance is %d however should be %d",
			player.getBalance(), player.getLimit()));
		break;
	    }
	    else {
		System.out.println("  Player balance = " + player.getBalance());
	    }
	}
	
	Mockito.verify(player, Mockito.atLeastOnce()).balanceExceedsLimitBy(Mockito.anyInt());
    }


}
