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

        ExecutorService executor = app.getExecutor();
        executor.execute(() -> {

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
                RouletteConsole con = new RouletteConsole(gameEntries, executor);
                if (readLine.equals("S")) {
                    keepAlive = false;
                    con.rollWheel();
                    gameEntries.clear();
                    System.out.println("Name Of Players and betting information:\n");
                    selector.getSelections().entrySet().stream().forEach(p -> {
                        BigDecimal[] earn = p.getValue().calculateEarninigs();
                        System.out.println(String.format("%s %s, %s, %s\n", p.getKey(), p.getValue().getNameOfPlayer(), earn[0].doubleValue(), earn[1].doubleValue()));                        
                    });
                } else {
                    String[] splits = readLine.split(" ");
                    String key = splits[0].trim();
                    String betPaced = splits[1].trim();
                    String betAmnt = splits[2].trim();
                    IGamingData select = selector.select(key);
                    select.setAmountInBet(BigDecimal.valueOf(Double.valueOf(betAmnt)));
                    select.setBalance(select.getAmountInBet());
                    select.setNumOfRounds(1);
                    select.getGamesPlayed().add(new PlayedGame(betPaced, select.getAmountInBet(), select.getBalance(), select));
                    gameEntries.add(select);
                }
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            keepAlive = false;
            executor.shutdownNow();
        }));
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
