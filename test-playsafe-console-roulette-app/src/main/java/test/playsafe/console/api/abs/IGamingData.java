/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.console.api.abs;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author walles
 */
public interface IGamingData {

    /**
     *
     * @return {@link BigDecimal}
     */
    BigDecimal getAmountInBet();

    /**
     *
     * @return {@link BigDecimal}
     */
    BigDecimal getBalance();

    /**
     *
     * @return {@link List}
     */
    List<IGame> getGamesPlayed();

    /**
     *
     * @return {@link String}
     */
    String getNameOfPlayer();

    /**
     *
     * @return {@link int}
     */
    int getNumOfRounds();

    /**
     *
     * @param amountInBet
     */
    void setAmountInBet(BigDecimal amountInBet);

    /**
     *
     * @param balance
     */
    void setBalance(BigDecimal balance);

    /**
     *
     * @param nameOfPlayer
     */
    void setNameOfPlayer(String nameOfPlayer);

    /**
     *
     * @param numOfRounds
     */
    void setNumOfRounds(int numOfRounds);

    /**
     *
     * @return {@link BigDecimal[]}
     */
    BigDecimal[] calculateEarninigs();

}
