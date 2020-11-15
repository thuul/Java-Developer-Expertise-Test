/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.console.api.abs;

import java.math.BigDecimal;
import test.playsafe.console.api.player.BetStatus;

/**
 *
 * @author walles
 */
public interface IGame {

    /**
     *
     * @return {@link BigDecimal}
     */
    BigDecimal getAmountInBet();

    /**
     *
     * @return {@link BigDecimal}
     */
    BigDecimal getAmount();

    /**
     *
     * @return {@link BigDecimal}
     */
    BigDecimal getBalance();

    /**
     *
     * @return {@link String}
     */
    String getChoosenBet();

    /**
     *
     * @return {@link BetStatus}
     */
    BetStatus getStatus();

    void setAmount(BigDecimal amount);

    /**
     *
     * @param balance
     */
    void setBalance(BigDecimal balance);

    /**
     *
     * @param choosenBet
     */
    void setChoosenBet(String choosenBet);

    /**
     *
     * @param status
     */
    void setStatus(BetStatus status);

    /**
     *
     * @return {@link IGamingData}
     */
    IGamingData getGamingData();

    /**
     *
     * @param gamingData
     */
    void setGamingData(IGamingData gamingData);

}
