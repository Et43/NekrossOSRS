package Nodes;

import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.TaskNode;

public class drop extends TaskNode {
    @Override
    public int priority(){
        return 3;
    }

    @Override
    public boolean accept(){
        return getInventory().isFull();
    }

    @Override
    public int execute(){

        if(getInventory().contains("Bronze Axe")){
            getInventory().interact("Bronze Axe", "Wield");
        }else{
            getInventory().dropAll();
        }

        return 300;
    }
}
