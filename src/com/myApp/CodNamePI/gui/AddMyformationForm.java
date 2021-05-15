/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myApp.CodNamePI.gui;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.myApp.CodNamePI.entities.Myformation;
import com.myApp.CodNamePI.services.ServiceFormation;
import com.myApp.CodNamePI.entities.Myformation;
import com.myApp.CodNamePI.services.ServiceFormation;


/**
 *
 * @author bhk
 */
public class AddMyformationForm extends Form{

    public AddMyformationForm(Form previous) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
        */
        setTitle("Add a new formation");
        setLayout(BoxLayout.y());
        
        TextField tfLibelle = new TextField("","TaskName");
        TextField tfDiscription= new TextField("", "Status: 0 - 1");
        TextField tfType = new TextField("","TaskName");
        TextField tfImage= new TextField("", "Status: 0 - 1");

        Button btnValider = new Button("Add formation");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfLibelle.getText().length()==0)
                        ||(tfDiscription.getText().length()==0)
                        ||(tfType.getText().length()==0)
                        ||(tfImage.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Myformation t = new Myformation(
                                tfLibelle.getText(),
                                tfDiscription.getText(),
                                tfType.getText(),
                                tfImage.getText()
                        );
                        if( ServiceFormation.getInstance().addFormation (t))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tfLibelle,tfDiscription,tfType,tfImage,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                , e-> previous.showBack()); // Revenir vers l'interface précédente
                
    }
    
    
}
