/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.api.controller;

import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author walles
 */
public interface IResponseController {

    /**
     *
     * @param inval
     * @return {@link String}
     */
    String doConversion(double inval);

    /**
     *
     * @param inval
     * @return {@link String}
     */
    String response(@RequestParam(value = "inval") String inval);

}
