/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.api.abs;

/**
 *
 * @author walles
 */
public interface IErrorHandler {

    /**
     * Container error handling handler.
     *
     * @param ex
     * @return {@link Exception
     */
    String handler(Exception ex);

}
