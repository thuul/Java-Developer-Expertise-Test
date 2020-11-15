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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.playsafe.console.api.abs.IGame;
import test.playsafe.console.api.abs.IGamingData;
import test.playsafe.console.api.player.BetStatus;
import test.playsafe.console.api.player.PlayedGame;

/**
 *
 * @author walles
 */
public class RouletteConsole {

    private static final int GAMES_PER_ROUND = 5;

    private ScheduledExecutorService scheduledExecutor;
    private final List<IGamingData> gameEntries;
    private final ExecutorService executor;
    private int numberOfGames;
    private boolean schedulerDown;

    /**
     * constructor.
     *
     * @param gameEntries
     * @param e
     */
    public RouletteConsole(List<IGamingData> gameEntries, ExecutorService e) {
        this.gameEntries = gameEntries;
        this.executor = e;
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
            List<Future<IGame>> invokeAll = executor.invokeAll(callables);
            printBettingResultsToConsole(invokeAll);
            callables.clear();
        } catch (InterruptedException ex) {
            Logger.getLogger(RouletteConsole.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
        scheduledExecutor.scheduleAtFixedRate(() -> {
            gameEntries.forEach(g -> {
                IGame previousBet = g.getGamesPlayed().get(g.getGamesPlayed().size() - 1);
                IGame newBet = new PlayedGame(previousBet.getChoosenBet(), previousBet.getAmountInBet(), g.getBalance(), g);
                g.getGamesPlayed().add(newBet);
                callables.add(new RouletteCallable(newBet));
            });
            try {
                List<Future<IGame>> invokeAll = executor.invokeAll(callables);
                printBettingResultsToConsole(invokeAll);
                callables.clear();
            } catch (InterruptedException ex) {
                Logger.getLogger(RouletteConsole.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
            if (numberOfGames == GAMES_PER_ROUND) {
                scheduledExecutor.shutdownNow();
                schedulerDown = true;
            }
        }, 30, 30, TimeUnit.SECONDS);
        CountDownLatch latch = new CountDownLatch(1);
        while (latch.getCount() != 0) {
            try {
                latch.await(30, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                Logger.getLogger(RouletteConsole.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            if (numberOfGames == GAMES_PER_ROUND && schedulerDown) {
                latch.countDown();
            }
        }
    }

    /**
     *
     * @param invokes
     */
    private void printBettingResultsToConsole(List<Future<IGame>> invokes) {
        ++numberOfGames;
        System.out.println(String.format("Number: %s", numberOfGames));
        System.out.println(String.format("Player\t\tBet\tOutcome\t\tWinnings\n\n"));
        invokes.forEach(i -> {
            try {
                IGame result = i.get();
                System.out.println(String.format("%s\t\t%s\t%s\t%s",
                        result.getGamingData().getNameOfPlayer(), result.getChoosenBet(), result.getStatus().getStatus(), result.getAmount()));
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(RouletteConsole.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        System.out.println(String.format("\n\n"));
    }

    /**
     *
     * @return {@link ScheduledExecutorService}
     */
    public ScheduledExecutorService getScheduledExecutor() {
        return scheduledExecutor;
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
