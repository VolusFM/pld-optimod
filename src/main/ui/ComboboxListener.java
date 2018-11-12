package main.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import main.controler.Controler;

public class ComboboxListener implements ItemListener {

    private Controler controler;
    
    public ComboboxListener(Controler controler) {
	this.controler = controler;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
	controler.getWindow().planningPanel.addingPanel.updatePreviousDeliveryCombobox();
    }

}
