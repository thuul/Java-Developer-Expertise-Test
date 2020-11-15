/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author walles
 */
@RestController
public class K2CResponseController implements IResponseController {

    @PostMapping("/conversations/ktoc/doCalculate")
    @Override
    public String response(@RequestParam(value = "inval") String inval) {
        Double d = Double.valueOf(inval);
        return doConversion(d);
    }

    @Override
    public String doConversion(double inval) {
        double klv = inval - 273.15;
        return String.format("Input: %s Kelvin = %s celsius", inval, klv);
    }

}
