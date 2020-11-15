/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.console.api.player;

import test.playsafe.console.api.abs.IGamingData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import test.playsafe.console.api.abs.IGame;

/**
 *
 * @author walles
 */
public class PlayerGamingData implements IGamingData {

    private String nameOfPlayer;
    private BigDecimal amountInBet;
    private BigDecimal balance;
    private int numOfRounds;
    private final List<IGame> gamesPlayed = new ArrayList<>();

    /**
     * constructor.
     *
     * @param nameOfPlayer
     */
    public PlayerGamingData(String nameOfPlayer) {
        this.nameOfPlayer = nameOfPlayer;
    }

    /**
     *
     * @return {@link String}
     */
    @Override
    public String getNameOfPlayer() {
        return nameOfPlayer;
    }

    /**
     *
     * @param nameOfPlayer
     */
    @Override
    public void setNameOfPlayer(String nameOfPlayer) {
        this.nameOfPlayer = nameOfPlayer;
    }

    /**
     *
     * @return {@link BigDecimal}
     */
    @Override
    public BigDecimal getAmountInBet() {
        return amountInBet;
    }

    /**
     *
     * @param amountInBet
     */
    @Override
    public void setAmountInBet(BigDecimal amountInBet) {
        this.amountInBet = amountInBet;
    }

    /**
     *
     * @return {@link BigDecimal}
     */
    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     *
     * @param balance
     */
    @Override
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     *
     * @return {@link int}
     */
    @Override
    public int getNumOfRounds() {
        return numOfRounds;
    }

    /**
     *
     * @param numOfRounds
     */
    @Override
    public void setNumOfRounds(int numOfRounds) {
        this.numOfRounds += numOfRounds;
    }

    /**
     *
     * @return {@link List}
     */
    @Override
    public List<IGame> getGamesPlayed() {
        return gamesPlayed;
    }

}
