package com.thg.accelerator23.connectn.ai.Connect4CaptainsMistress;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class Connect4CaptainsMistress extends Player {
  public Connect4CaptainsMistress(Counter counter) {
    //TODO: fill in your name here
    super(counter, Connect4CaptainsMistress.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    MonteCarloTS ai = new MonteCarloTS(board, 5000);

    return ai.getOptimalMove();
  }
}
