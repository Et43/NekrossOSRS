package Nodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.GameObject;

public class cut extends TaskNode {
    Area LogArea = new Area(new Tile(3193, 3253), new Tile(3206, 3238));
    Area OakArea = new Area(new Tile(3202,3249), new Tile(3207,3238));
    Area WillowArea = new Area(new Tile(3161,3275), new Tile(3168,3265));
    Area yewArea = new Area(new Tile(3098, 3482), new Tile(3080, 3466));
    Area currentArea;
    public String normalTree= "Tree";
    public String oakTree = "Oak";
    public String willowTree = "Willow";
    public String yewTree = "Yew";
    public String currentTree;
    public GameObject tree;

    public int lvl;

    @Override
    public int priority(){
        return 1;
    }

    @Override
    public boolean accept(){
        lvl = getSkills().getRealLevel(Skill.WOODCUTTING); // Gets the current real skill level for Woodcutting
        if(lvl < 15){  // If the lvl is smaller than 15
            currentTree = normalTree;
            currentArea = LogArea;  // Set logging area to lvl < 15 area [ LOGS ]
        }else if(lvl < 30 && lvl >= 15){ // If the lvl is smaller than 30 and bigger than or equal to 15
            currentTree = oakTree;
            currentArea = OakArea;  // Set logging area to lvl > 15 area [ OAK ]
        }else if(lvl < 99 && lvl >= 30){
            currentArea = WillowArea;
            currentTree = willowTree;
        }
        tree = getGameObjects().closest(gameObject -> gameObject != null&& gameObject.getName().equals(currentTree));
        if(currentArea.contains(tree)){
            return true;
        }else if(!currentArea.contains(tree)){
            return false;
        }

        return false;
    }

    @Override
    public int execute(){
        GameObject currTree = getGameObjects().closest(gameObject -> gameObject != null&& gameObject.getName().equals(currentTree));
        if(currentArea.contains(getLocalPlayer())){ // If user is in the tree area
            if(tree != null && !getLocalPlayer().isAnimating()){ // If the tree exists
                if(currTree.interact("Chop Down")){ // Interact with the tree by choosing the chop down option
                    sleepUntil(() -> getLocalPlayer().isMoving(), 2000); // Waits until you are done choppiung wood
                    sleepUntil(() -> getLocalPlayer().isAnimating(), 5000); // Prevents you from spam clicking.
                }
            }
        }
        return 300;
    }
}
