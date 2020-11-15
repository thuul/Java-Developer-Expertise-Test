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

            Console console = System.console();
            List<IGamingData> gameEntries = new ArrayList<>();
            IKeyValueBasedSelector<String, IGamingData> selector = new GamingDataInformationSelector();
            selector.initialize();

            System.out.println("Name Of Players:\n");
            selector.getSelections().entrySet().stream().forEach(p -> {
                System.out.println(String.format("%s %s\n", p.getKey(), p.getValue().getNameOfPlayer()));
            });
            app.consoleProcess(app, executor, console, selector, gameEntries);
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            keepAlive = false;
            executor.shutdownNow();
        }));
    }

    /**
     *
     * @param app
     * @param executor
     * @param console
     * @param selector
     * @param gameEntries
     */
    private void consoleProcess(RouletteConsoleApplication app, ExecutorService executor,
            Console console, IKeyValueBasedSelector<String, IGamingData> selector, List<IGamingData> gameEntries) {
        while (keepAlive) {
            System.out.println("Select player [P1, P2 or Other], Bet [1-36, EVEN or ODD], Amount [0.00]: ");
            System.out.println("A sample entry = P3 ODD 100.00");
            System.out.println("To start the round, Press S: ");
            String readLine = console.readLine();
            RouletteConsole con = new RouletteConsole(gameEntries, executor);
            if (readLine.equals("S")) {
                keepAlive = false;
                con.rollWheel();
                System.out.println("Name Of Players and betting information:\n");
                selector.getSelections().entrySet().stream().forEach(p -> {
                    BigDecimal[] earn = p.getValue().calculateEarninigs();
                    System.out.println(String.format("%s %s, %s, %s\n", p.getKey(), p.getValue().getNameOfPlayer(), earn[0].doubleValue(), earn[1].doubleValue()));
                });
                app.printBettingResultsTabularToConsole(selector);
                keepAlive = true;
                gameEntries.clear();
                selector.getSelections().entrySet().stream().forEach(p -> {
                    p.getValue().getGamesPlayed().clear();
                    p.getValue().setAmountInBet(BigDecimal.ZERO);
                    p.getValue().setBalance(BigDecimal.ZERO);
                    p.getValue().setNumOfRounds(1);
                });
                consoleProcess(app, executor, console, selector, gameEntries);
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
    }

    /**
     *
     * @param invokes
     */
    private void printBettingResultsTabularToConsole(IKeyValueBasedSelector<String, IGamingData> selector) {
        System.out.println(String.format("Player\t\tTotal Win\t\tTotal Bet\n\n"));
        selector.getSelections().entrySet().forEach(p -> {
            IGamingData data = p.getValue();
            BigDecimal[] earn = data.calculateEarninigs();
            System.out.println(String.format("%s\t\t%s\t\t%s",
                    data.getNameOfPlayer(), earn[0].doubleValue(), earn[1].doubleValue()));
        });
        System.out.println(String.format("\n\n"));
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
