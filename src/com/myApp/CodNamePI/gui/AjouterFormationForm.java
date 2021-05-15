package com.myApp.CodNamePI.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.Graphics;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.layouts.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.myApp.CodNamePI.entities.Myformation;
import com.myApp.CodNamePI.services.ServiceFormation;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjouterFormationForm extends BaseForm {
    private  ButtonGroup ButtonGroupbg;
    Form current;
public AjouterFormationForm(Resources res){
    super("Newsfeed", BoxLayout.y());

    Toolbar tb = new Toolbar(true);
    current=this;
    setToolbar(tb);
    getTitleArea().setUIID("Container");
    setTitle("Ajout formation");
    getContentPane().setScrollVisible(false);



    tb.addSearchCommand(e->{

    });
    Tabs swipe =new Tabs();

    Label s1 =new Label();
    Label s2 =new Label();
    addTab(swipe, s1,res.getImage("download.jpg"),"","",res);

    //
    swipe.setUIID("Container");
    swipe.getContentPane().setUIID("Container");
    swipe.hideTabs();

    ButtonGroup bg =new ButtonGroup();
    int size =Display.getInstance().convertToPixels(1);
    Image unselecltedWalkthru = Image.createImage(size,size,0);
    Graphics g = unselecltedWalkthru.getGraphics();
    g.setColor(0xffffff);
    g.setAlpha(100);
    g.setAntiAliased(true);
    g.fillArc(0,0,size,size,0,360);
    Image selectedWalkthru = Image.createImage(size,size,0);

    g = selectedWalkthru.getGraphics();
    g.setColor(0xffffff);
  //  g.setAlpha(100);
    g.setAntiAliased(true);
    g.fillArc(0,0,size,size,0,360);

   // Image unselectedWalkthru =  Image.createImage(size,size,0);

    RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
    FlowLayout flow = new FlowLayout(CENTER);
    flow.setValign(BOTTOM);
    Container radioContainer = new Container(flow);
  /**************************************************/
    for (int iter = 0; iter < rbs.length;iter++){
        rbs[iter] = RadioButton.createToggle(unselecltedWalkthru,bg);
        rbs[iter].setPressedIcon(selectedWalkthru);
        rbs[iter].setUIID("Label");
        radioContainer.add(rbs[iter]);

    }
        rbs[0].setUIID("Label");
    swipe.addSelectionListener((i,ii)->{
        if (!rbs[ii].isSelected()){
            rbs[ii].setSelected(true);


        }
    });

    Component.setSameSize(radioContainer,s1,s2);
    add(LayeredLayout.encloseIn(swipe, radioContainer));

    ButtonGroup barGroup = new ButtonGroup();
    RadioButton mesListes = RadioButton.createToggle("Mes Formation",barGroup);//ttttttttttttttttttttttttttttttttttttt

    mesListes.setUIID("SelectedBar");
    RadioButton liste = RadioButton.createToggle("Reclamer",barGroup);//////////////////////////////////////////////////
    liste.setUIID("SelectedBar");
    RadioButton partage= RadioButton.createToggle("reclamer",barGroup);
    partage.setUIID("SelectedBar");
    Label arrow = new Label(res.getImage("news-tab-down-arrow.png"),"Container");

    mesListes.addActionListener((e)->{
        InfiniteProgress ip =new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        // ListeReclamation a = new ListReclamation(res);
        //a.show();
        refreshTheme();
    });
    add(LayeredLayout.encloseIn(
            GridLayout.encloseIn(3,mesListes,liste,partage),
            FlowLayout.encloseBottom(arrow)
            ));
    partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e->{
            arrow.setVisible(true);
            updateArrowPostion(partage,arrow);

                });
        bindButtonSelection(mesListes,arrow);
        bindButtonSelection(liste,arrow);
        bindButtonSelection(partage,arrow);
        // special case for rotation
    addOrientationListener(e->{
        updateArrowPostion(barGroup.getRadioButton(barGroup.getSelectedIndex()),arrow);
    });



    //


    /**************************************/


    TextField libelle= new TextField("","entrer libelle");
    libelle.setUIID("TextFieldBlack");
    addStringValue("Libelle",libelle);

    TextField description= new TextField("","entrer description");
    libelle.setUIID("TextFieldBlack");
    addStringValue("description",description);

    TextField type= new TextField("","entrer type");
    libelle.setUIID("TextFieldBlack");
    addStringValue("type",type);

    TextField image= new TextField("","entrer image");
    libelle.setUIID("TextFieldBlack");
    addStringValue("image",image);

    Button btnAjouter=new Button("Ajouter");
    addStringValue("",btnAjouter);

    //onclick button event

    btnAjouter.addActionListener((e)->{

        try {
            if(libelle.getText().equals("")||description.getText().equals("")
            ||type.getText().equals("")||image.getText().equals("")){
                Dialog.show("Veuillez vérifier les données","","annuler","ok" );
            }else{
                InfiniteProgress ip = new InfiniteProgress();

                final Dialog iDialog= ip.showInfiniteBlocking();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                Myformation r = new Myformation(String.valueOf(libelle.getText()),
                        String.valueOf(description.getText()),
                        String.valueOf(type.getText()),
                        String.valueOf(image.getText()),
                        format.format(new Date())
                        );

                System.out.println("data formation =="+r);

                /*********appelle du service d'ajout**********/

                ServiceFormation.getInstance().addFormationnew(r);
                iDialog.dispose();// eviter le load après l'ajout

                //après l'ajout list:
                new ListFormationForm(res).show();

                refreshTheme();

            }

        }catch (Exception ce){
            System.out.println("exception");
            ce.printStackTrace();
        }

    });

}



    private void addTab(Tabs swipe,Label spacer, Image image, String s, String text, Resources res) {

        int size =Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        if (image.getHeight()<size){
            image=image.scaledHeight(size);
        }
        if(image.getHeight()>Display.getInstance().getDisplayHeight()/2){
            image=image.scaledHeight(Display.getInstance().getDisplayHeight()/2);

        }
        ScaleImageLabel imageScale= new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("","ImageOverlay");
        Container page1=
                LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                new SpanLabel(text,"LargeWhiteText"),
                                       // FlowLayout.encloseIn(null),
                                        spacer

                                )
                        )
                );
        swipe.addTab("",res.getImage("download.jpg"),page1);
    }



    public void bindButtonSelection(Button btn, Label l){
    btn.addActionListener(e->{
        if(btn.isSelected()){
            updateArrowPostion(btn,l);
        }
    });
    }
    
    private void updateArrowPostion(Button btn, Label l){

    l.getUnselectedStyle().setMargin(LEFT,btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
    l.getParent().repaint();
    }


    private void addStringValue(String s, Component v) {
    add(BorderLayout.west(new Label(s, "PaddedLabel"))
    .add(BorderLayout.CENTER,v));
    add(createLineSeparator(0xeeeeee));

    }
}
