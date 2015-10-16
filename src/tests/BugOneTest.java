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

public class BugOneTest {
    
    private int winnings;;
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
    	this.balance = 100;
    	this.bet = 5;
    	this.limit = 0;

    	this. player = Mockito.mock(Player.class);
        
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
	System.out.println("TestBugReplication\n");
	List<DiceValue> cdv = game.getDiceValues();
        player = new Player(name, balance);
        player.setLimit(limit);
        int bet = 50;

        
	Mockito.when(d1.getValue()).thenReturn(DiceValue.DIAMOND);
	Mockito.when(d2.getValue()).thenReturn(DiceValue.HEART);
	Mockito.when(d3.getValue()).thenReturn(DiceValue.SPADE);

        System.out.println(String.format("\n\n%s starts with balance %d, limit %d\n", 
        		player.getName(), player.getBalance(), player.getLimit()));

        int turn = 0;
        
        if (!player.balanceExceedsLimitBy(bet) && !(player.getBalance() < 200)) { return; }; // end
        
        while (turn < 3)
        {
            turn++;                   
            // pick CROWN
            DiceValue pick = DiceValue.CROWN;
           
            System.out.printf("Turn %d: %s bet %d on %s\n", 
        	    turn, player.getName(), bet, pick); 
            //play a round of the game
            int winnings = game.playRound(player, pick, bet);
            cdv = game.getDiceValues();
            //output result of game
            System.out.printf("Rolled %s, %s, %s\n",
        	    cdv.get(0), cdv.get(1), cdv.get(2));
            
            System.out.printf("%s won %d, balance now %d\n\n",
        	    player.getName(), winnings, player.getBalance());
            
        } //while
    }
 
    
    /*
     * Test the Player balance now is displaying correctly
     */
    @Test
    public void testHypothesis(){
	System.out.println("Testing in progress");
	// Create a player to use for test
	this.player = new Player(name, balance);
	player.setLimit(limit);
	
	// Check that the object has been initiated correctly
	System.out.println("\t --Prior bet-- Player balance = " + player.getBalance());
	assertTrue(player.getBalance()==this.balance);
      
	// The player makes a bet
	player.makeBet(bet);
	System.out.println("\tPlayer bet = " + bet);
	System.out.println("\t --After bet-- Player balance = " + player.getBalance());
	int balance_after_wager = this.balance - bet;
	// Check that the balance has been updated after making bet e.g. balance - bet
	assertTrue(player.getBalance() == balance_after_wager);
      
	int winnings = bet * 1;
	System.out.println("\tPlayer win = " + winnings);
	int balance_after_winning = balance_after_wager + winnings;
	
	// The player  receives winnings
	player.receiveWinnings(winnings);
	
	// Check for the correct balance
	System.out.println("\t(After win) Player balance = " + player.getBalance());
	assertTrue(player.getBalance() == balance_after_winning);
      
    }    

}