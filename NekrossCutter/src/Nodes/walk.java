package Nodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.TaskNode;

public class walk extends TaskNode {
    Area LogArea = new Area(new Tile(3193, 3253), new Tile(3206, 3238));
    Area OakArea = new Area(new Tile(3202,3249), new Tile(3207,3238));
    Area WillowArea = new Area(new Tile(3161,3275), new Tile(3168,3265));
    Tile[] pathToYew = {new Tile(3163, 3287), new Tile(3148, 3296), new Tile(3131, 3295), new Tile(3113, 3295), new Tile(3095, 3292), new Tile(3086, 3303), new Tile(3075, 3314), new Tile(3071, 3329), new Tile(3071, 3347), new Tile(3070, 3358), new Tile(3070, 3375), new Tile(3068, 3389), new Tile(3066,3406), new Tile(3068, 3424), new Tile(3068, 3441), new Tile(3073, 3453), new Tile(3086, 3462), new Tile(3082, 3476)};
    Area yewArea = new Area(new Tile(3098, 3482), new Tile(3080, 3466));
    Area currentArea;
    private boolean AIDS;

    public int lvl;

    @Override
    public int priority(){
        return 2; // runs first
    }

    @Override
    public boolean accept(){
        lvl = getSkills().getRealLevel(Skill.WOODCUTTING); // Gets the current real skill level for Woodcutting
        if(lvl < 15){  // If the lvl is smaller than 15
            currentArea = LogArea; // Set logging area to lvl < 15 area [ LOGS ]
        }else if(lvl < 30 && lvl >= 15){ // If the lvl is smaller than 30 and bigger than or equal to 15
            currentArea = OakArea; // Set logging area to lvl > 15 area [ OAK ]
        }else if(lvl < 99 && lvl >= 30){
            currentArea = WillowArea;
        }
        if(!currentArea.contains(getLocalPlayer())){ // If currentArea [ the current Logging area ] does not contain player
            return true;
        }else if(currentArea.contains(getLocalPlayer())){ // If current area does contain player no need to continue
            return false;
        }
        return false;
    }

    @Override
    public int execute(){
        if(currentArea.contains(getLocalPlayer())){ // Check if user is in current Area
            return 300; // If user is in the area continue
        }else if(!currentArea.contains(getLocalPlayer())){ // If user is not in current area
            move(currentArea.getRandomTile()); // Walk to the current area
        }
        return 300;
    }

    public void move(Tile tile){
        getWalking().walk(tile);
        sleepUntil(() -> getLocalPlayer().isStandingStill(), Calculations.random(4000, 5500));
    }
}
