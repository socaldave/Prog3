package events.listeners.modes;

import cli.Commands;
import cli.view.View;
import storageContract.administration.StorageManager;

import java.util.StringTokenizer;

public class PersistanceListener implements InputEventListener {

    private StorageManager management;
    private View view;

    public PersistanceListener(StorageManager management, View view) {
        this.management = management;
        this.view = view;
    }

    @Override
    public void onInputEvent(InputEvent event) throws Exception {
        String cmd = event.getCurrentCommand();
        StringTokenizer st = new StringTokenizer(cmd);
        String last = event.getLastCommand().toLowerCase();
        if (last.equals(Commands.PERS_MODE)) {
            switch (st.nextToken().toLowerCase()) {
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
                case "save": {
                    this.onSave(st);
                }
                break;
                case "load": {
                    this.onLoad(st);
                }
                break;
                default:{
                    this.view.printUnsupportCommand();
                } break;
            }
        }
    }
    private void onSaveJOS() {
        System.out.println("save jos enter");
        this.view.saveJOS(this.management);
    }
    private void onLoadJOS() {
        this.view.loadJOS(this.management);
    }
    public void onSaveJdb() {
        this.view.saveJDB(this.management);
    }
    public void onLoadJdb() {

        this.view.loadJDB(this.management);
    }
    public void onLoad(StringTokenizer st){
        if(st.hasMoreTokens()){
            try{
                int position = Integer.parseInt(st.nextToken());

                this.view.load(this.management,position);

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
                this.view.save(this.management,position);

            } catch (NumberFormatException e){
                this.view.printUnsupportCommand();
            } catch (Exception e){

            }
        }
    }
}
