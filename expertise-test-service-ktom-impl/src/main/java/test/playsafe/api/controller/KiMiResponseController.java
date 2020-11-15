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
public class KiMiResponseController implements IResponseController {

    @PostMapping("/conversations/ktom/doCalculate")
    @Override
    public String response(@RequestParam(value = "inval") String inval) {
        Double d = Double.valueOf(inval);
        return doConversion(d);
    }

    @Override
    public String doConversion(double klm) {
        double mile = klm / 1.609;
        return String.format("Input: %s Kilometer(s) = %s Mile(s)", klm, mile);
    }

}
