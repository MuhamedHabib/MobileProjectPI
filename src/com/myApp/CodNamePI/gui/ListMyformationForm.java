/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myApp.CodNamePI.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.myApp.CodNamePI.services.ServiceFormation;

/**
 *
 * @author bhk
 */
public class ListMyformationForm extends Form{

    public ListMyformationForm(Form previous) {
        setTitle("List formations");
        
        SpanLabel sp = new SpanLabel();
        sp.setText(ServiceFormation.getInstance().getAllTasks().toString());
        add(sp);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
    
}
