/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.console.api.player;

import test.playsafe.console.api.abs.IGame;
import java.math.BigDecimal;
import test.playsafe.console.api.abs.IGamingData;

/**
 *
 * @author walles
 */
public class PlayedGame implements IGame {

    private String choosenBet;
    private BetStatus status;
    private final BigDecimal amountInBet;
    private BigDecimal amount;
    private BigDecimal balance;
    private IGamingData gamingData;

    /**
     * constructor.
     *
     * @param choosenBet
     * @param amountInBet
     * @param balance
     */
    public PlayedGame(String choosenBet, BigDecimal amountInBet, BigDecimal balance) {
        this.choosenBet = choosenBet;
        this.amountInBet = amountInBet;
        this.balance = balance;
    }

    /**
     * constructor.
     *
     * @param choosenBet
     * @param amountInBet
     * @param balance
     * @param gamingData
     */
    public PlayedGame(String choosenBet, BigDecimal amountInBet, BigDecimal balance, IGamingData gamingData) {
        this.choosenBet = choosenBet;
        this.amountInBet = amountInBet;
        this.balance = balance;
        this.gamingData = gamingData;
    }

    /**
     *
     * @return {@link String}
     */
    @Override
    public String getChoosenBet() {
        return choosenBet;
    }

    /**
     *
     * @param choosenBet
     */
    @Override
    public void setChoosenBet(String choosenBet) {
        this.choosenBet = choosenBet;
    }

    /**
     *
     * @return {@link BetStatus}
     */
    @Override
    public BetStatus getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    @Override
    public void setStatus(BetStatus status) {
        this.status = status;
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
     * @return {@link BigDecimal}
     */
    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
     * @return {@link IGamingData}
     */
    @Override
    public IGamingData getGamingData() {
        return gamingData;
    }

    /**
     *
     * @param gamingData
     */
    @Override
    public void setGamingData(IGamingData gamingData) {
        this.gamingData = gamingData;
    }
}
