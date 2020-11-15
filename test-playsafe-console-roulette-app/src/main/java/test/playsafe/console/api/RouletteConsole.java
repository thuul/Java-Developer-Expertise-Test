/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.console.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.playsafe.console.api.abs.IGame;
import test.playsafe.console.api.abs.IGamingData;
import test.playsafe.console.api.player.BetStatus;

/**
 *
 * @author walles
 */
public class RouletteConsole {

    private final List<IGamingData> gameEntries;
    private final ExecutorService e;

    /**
     * constructor.
     *
     * @param gameEntries
     * @param e
     */
    public RouletteConsole(List<IGamingData> gameEntries, ExecutorService e) {
        this.gameEntries = gameEntries;
        this.e = e;
    }

    /**
     *
     * @return {@link List}
     */
    public List<IGamingData> getGameEntries() {
        return gameEntries;
    }

    public void rollWheel() {
        List<RouletteCallable> callables = new ArrayList<>();
        gameEntries.forEach(g -> {
            callables.add(new RouletteCallable(g.getGamesPlayed().get(0)));
        });
        try {
            e.invokeAll(callables);
        } catch (InterruptedException ex) {
            Logger.getLogger(RouletteConsole.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        ScheduledExecutorService se
                = Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
        se.scheduleAtFixedRate(() -> {

        }, 0, 30, TimeUnit.SECONDS);
    }

}

/**
 * Package-Private {@link Callable} interface for running games.
 *
 * @author walles
 */
class RouletteCallable implements Callable<IGame> {

    private final IGame game;

    /**
     * constructor.
     *
     * @param game
     */
    public RouletteCallable(IGame game) {
        this.game = game;
    }

    @Override
    public IGame call() throws Exception {
        if (!game.isFirst()) {

        }
        game.setBalance(BigDecimal.valueOf(game.getBalance().doubleValue() - game.getAmountInBet().doubleValue()));
        Random r = new Random();
        int next = r.nextInt(37);
        String bet = game.getChoosenBet();
        if (bet.equals("EVEN") || bet.equals("ODD")) {
            if (bet.equals("EVEN")) {
                int reminder = next % 2;
                if (reminder == 0) {
                    game.setStatus(BetStatus.WIN);
                    game.setAmount(BigDecimal.valueOf(game.getAmountInBet().doubleValue() * 2));
                } else {
                    game.setStatus(BetStatus.LOSS);
                    game.setAmount(BigDecimal.ZERO);
                }
            } else {
                int reminder = next % 2;
                if (reminder > 0) {
                    game.setStatus(BetStatus.WIN);
                    game.setAmount(BigDecimal.valueOf(game.getAmountInBet().doubleValue() * 2));
                } else {
                    game.setStatus(BetStatus.LOSS);
                    game.setAmount(BigDecimal.ZERO);
                }
            }
        } else {
            Integer valueOfBet = Integer.valueOf(bet);
            if (valueOfBet > 0) {
                game.setStatus(BetStatus.WIN);
                game.setAmount(BigDecimal.valueOf(game.getAmountInBet().doubleValue() * 36));
            } else {
                game.setStatus(BetStatus.LOSS);
                game.setAmount(BigDecimal.ZERO);
            }
        }
        game.setBalance(BigDecimal.valueOf(game.getBalance().doubleValue() + game.getAmount().doubleValue()));
        return game;
    }
}
