/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.playsafe.console.api.selector;

import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import test.playsafe.console.api.abs.AbstractKeyValueBasedSelector;
import test.playsafe.console.api.abs.IGamingData;
import test.playsafe.console.api.player.PlayerGamingData;

/**
 *
 * @author walles
 */
public class GamingDataInformationSelector extends AbstractKeyValueBasedSelector<String, IGamingData> {

    @Override
    public Map<String, IGamingData> getSelectorMap() {
        return new IdentityHashMap<>();
    }

    @PostConstruct
    @Override
    public void initialize() {

        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/players.properties"));
        } catch (IOException ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
        prop.entrySet().stream().forEach(e -> {
            addSelection(String.valueOf(e.getKey()), new PlayerGamingData(String.valueOf(e.getValue())));
        });

    }

}
