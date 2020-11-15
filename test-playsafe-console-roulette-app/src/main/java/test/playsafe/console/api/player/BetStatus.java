/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.console.api.player;

/**
 *
 * @author walles
 */
public enum BetStatus {

    LOSS("LOSE"),
    WIN("WIN");

    private final String status;

    private BetStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
