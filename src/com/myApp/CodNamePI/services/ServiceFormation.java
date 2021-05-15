/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myApp.CodNamePI.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.myApp.CodNamePI.entities.Myformation;
import com.myApp.CodNamePI.utils.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceFormation {

    public ArrayList<Myformation> myformations;

    public static ServiceFormation instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceFormation() {
        req = new ConnectionRequest();
    }

    public static ServiceFormation getInstance() {
        if (instance == null) {
            instance = new ServiceFormation();
        }
        return instance;
    }


    public ArrayList<Myformation> parseTasks(String jsonText) {
        try {
            myformations = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient 
            l'utilité de new CharArrayReader(json.toCharArray())
            
            La méthode parse json retourne une MAP<String,Object> ou String est 
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets 
                    c'est la clé définissant le tableau de tâches.
            */
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
              /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche.               
            
            Le format Json impose que l'objet soit définit sous forme
            de clé valeur avec la valeur elle même peut être un objet Json.
            Pour cela on utilise la structure Map comme elle est la structure la
            plus adéquate en Java pour stocker des couples Key/Value.
            
            Pour le cas d'un tableau (Json Array) contenant plusieurs objets
            sa valeur est une liste d'objets Json, donc une liste de Map
            */
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Myformation t = new Myformation();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                // t.setStatus(((int)Float.parseFloat(obj.get("status").toString())));
                t.setLibelle(obj.get("libelle").toString());
                t.setDiscription(obj.get("discription").toString());
                t.setType(obj.get("type").toString());
                t.setImage(obj.get("image").toString());

                //Ajouter la tâche extraite de la réponse Json à la liste
                myformations.add(t);
            }


        } catch (IOException ex) {

        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return myformations;
    }


    public ArrayList<Myformation> getAllTasks() {
        String url = Statics.BASE_URL + "/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                myformations = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return myformations;
    }


    public boolean addFormation(Myformation t) {
        String url = Statics.BASE_URL + "/addJSON?libelle=" + t.getLibelle() + "&description=" + t.getDiscription() + "&type=" + t.getType() + "&image=" + t.getImage(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    } //la fonction addFormation original

/*****************************add***********************************/
    public void addFormationnew(Myformation t) {
        String url = Statics.BASE_URL+"/addJSON?libelle="+t.getLibelle()
                +"&description="+t.getDiscription()
                +"&type="+t.getType()+"&image="+t.getImage(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data==" + str);

        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

/*****************************affichage***********************************/


    public ArrayList<Myformation>affichage(){
        ArrayList<Myformation> result=new ArrayList<>();
        String url = Statics.BASE_URL+"/all";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp=new JSONParser();
                try {
                    Map<String,Object>mapFormations=jsonp.parseJSON(new java.io.CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps=(List<Map<String,Object>>) mapFormations.get("root");
                    for(Map<String,Object>obj:listOfMaps){
                        Myformation re =new Myformation();

                        float id =Float.parseFloat(obj.get("id").toString());
                        String libelle=obj.get("libelle").toString();
                        String description=obj.get("description").toString();
                       // String dateCreation=obj.get("dateCreation").toString();
                        String type=obj.get("type").toString();
                        String image=obj.get("image").toString();

                        re.setId((int)id);
                        re.setLibelle(libelle);
                        re.setDiscription(description);
                        re.setType(type);
                        re.setImage(image);

                        //date
                        String DateConverter =obj.get("date").toString().substring(obj.get("date").toString().indexOf("timestamp")+ 10, obj.get("date").toString().lastIndexOf("}"));

                        Date currentTime =new Date(Double.valueOf(DateConverter).longValue()*1000);
                        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-mm-dd");
                        String dateString =formatter.format(currentTime);
                        re.setDate(dateString);

                        //insert data into arraylist:
                        result.add(re);




                    }
                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

/*****************************detail***********************************/

public Myformation Detail(int id, Myformation myformation){
    String url = Statics.BASE_URL+"/detailformation?"+id;
    req.setUrl(url);

    String str = new String(req.getResponseData());

    req.addResponseListener(((evt) -> {
        JSONParser jsonp = new JSONParser();
        try{
            Map<String,Object>obj=jsonp.parseJSON(new java.io.CharArrayReader(new String(str).toCharArray()));

            myformation.setLibelle(obj.get("libelle").toString());
            myformation.setDiscription(obj.get("description").toString());
            myformation.setType(obj.get("type").toString());
            myformation.setImage(obj.get("image").toString());


        }catch (IOException ex){
            System.out.println("error related to sql:( "+ex.getMessage());
        }
        System.out.println("data ==== "+str);

    }));
    NetworkManager.getInstance().addToQueueAndWait(req);

    return myformation;

}


}


