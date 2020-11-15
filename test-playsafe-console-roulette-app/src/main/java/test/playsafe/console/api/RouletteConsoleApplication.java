/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.console.api;

import java.io.Console;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import test.playsafe.console.api.abs.IGamingData;
import test.playsafe.console.api.abs.IKeyValueBasedSelector;
import test.playsafe.console.api.player.PlayedGame;
import test.playsafe.console.api.selector.GamingDataInformationSelector;

/**
 *
 * @author walles
 */
public class RouletteConsoleApplication {

    private static boolean keepAlive = true;

    public static void main(String[] args) {

        RouletteConsoleApplication app = new RouletteConsoleApplication();

        ExecutorService e = app.getExecutor();
        e.execute(() -> {

            Console c = System.console();
            List<IGamingData> gameEntries = new ArrayList<>();
            IKeyValueBasedSelector<String, IGamingData> selector = new GamingDataInformationSelector();
            selector.initialize();

            System.out.println("Name Of Players:\n");
            selector.getSelections().entrySet().stream().forEach(p -> {
                System.out.println(String.format("%s %s\n", p.getKey(), p.getValue().getNameOfPlayer()));
            });
            while (keepAlive) {
                System.out.println("Select player [P1, P2 or Other], Bet [1-36, EVEN or ODD], Amount [0.00]: ");
                System.out.println("A sample entry = P3 ODD 100.00");
                System.out.println("To start the round, Press S: ");
                String readLine = c.readLine();
                if (readLine.equals("S")) {
                    keepAlive = false;
                    RouletteConsole con = new RouletteConsole(gameEntries, e);
                    con.rollWheel();
                } else {
                    String[] splits = readLine.split(" ");
                    IGamingData select = selector.select(splits[0]);
                    select.setAmountInBet(BigDecimal.valueOf(Double.valueOf(splits[2])));
                    select.setBalance(select.getAmountInBet());
                    select.setNumOfRounds(1);
                    select.getGamesPlayed().add(new PlayedGame(splits[1], select.getAmountInBet(), select.getBalance(), true));
                    gameEntries.add(select);
                }
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            keepAlive = false;
            e.shutdownNow();
        }));
    }

    private void play(ExecutorService e, IGamingData selected) {
    }

    /**
     * Cached Thread executors for running game process.
     *
     * @return {@link ExecutorService}
     */
    private ExecutorService getExecutor() {
        return Executors.newCachedThreadPool(Executors.defaultThreadFactory());
    }

}
