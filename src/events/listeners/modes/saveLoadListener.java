package events.listeners.modes;

import cli.view.View;
import events.events.PersistanceEvent;
import storageContract.administration.StorageManager;

import java.util.StringTokenizer;

public class saveLoadListener {

    private StorageManager storageManager;
    private View view;

    public saveLoadListener(StorageManager management, View view) {
        this. storageManager= management;
        this.view = view;
    }

    public void onStateChangeEvent(PersistanceEvent persistanceEvent){
        switch (persistanceEvent.getState().toLowerCase()) {
            case "savejdb": {
                this.onSaveJdb();
            }
            break;
            case "loadjdb": {
                this.onLoadJdb();
            }
            break;
            case "savejos": {
                this.onSaveJOS();
            }
            break;
            case "loadjos": {
                this.onLoadJOS();
            }
            break;

            default:{
                this.view.printUnsupportCommand();
            } break;
        }
    }

    private void onSaveJOS() {
        System.out.println("save jos enter");
        this.view.saveJOS(this.storageManager);
    }
    private void onLoadJOS() {
        this.view.loadJOS(this.storageManager);
    }
    public void onSaveJdb() {
        this.view.saveJDB(this.storageManager);
    }
    public void onLoadJdb() {

        this.view.loadJDB(this.storageManager);
    }
    public void onLoad(StringTokenizer st){
        if(st.hasMoreTokens()){
            try{
                int position = Integer.parseInt(st.nextToken());

                this.view.load(this.storageManager,position);

            } catch (NumberFormatException e){
                this.view.printUnsupportCommand();
            } catch (Exception e){
                System.out.println("on load error:"+e.getMessage());

            }
        }
    }
    public void onSave(StringTokenizer st){
        if(st.hasMoreTokens()){
            try{
                int position = Integer.parseInt(st.nextToken());
                this.view.save(this.storageManager,position);

            } catch (NumberFormatException e){
                this.view.printUnsupportCommand();
            } catch (Exception e){

            }
        }
    }

}
