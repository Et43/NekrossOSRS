import Nodes.cut;
import Nodes.drop;
import Nodes.walk;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.utilities.Timer;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.IOException;
import java.net.URL;


@ScriptManifest(version=1.3, author="Et43", category = Category.WOODCUTTING, name = "NekrossCutter")
public class nekrossCutter extends TaskScript {

    private Image image;
    private String currentPhase;
    private int lvl = Skills.getRealLevel(Skill.WOODCUTTING);
    private Timer timer;


    // MAIN FUNC +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public void onStart(){
        timer = new Timer();
        lvl = getSkills().getRealLevel(Skill.WOODCUTTING); // Gets the current real skill level for Woodcutting
        if(lvl < 15){  // If the lvl is smaller than 15
            currentPhase = "Current Phase ("+ lvl +"): " + " Cutting" +"Tree's";
        }else if(lvl < 30 && lvl >= 15){ // If the lvl is smaller than 30 and bigger than or equal to 15
            currentPhase = "Current Phase ("+ lvl +"): " + " Cutting" +"Oak";
        }else if(lvl < 60 && lvl >= 30){
            currentPhase = "Current Phase ("+ lvl +"): " + " Cutting" +" Willow's";
        }
        try{
            image = ImageIO.read(new URL("https://i.imgur.com/12YZiUX.png"));
        }catch (IOException e){
            log("Failed to load iamge.");
        }
        addNodes(new walk(), new cut(), new drop());
    }


    // GUI ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public void onPaint(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(3, 341, 513, 135);
        g.setColor(Color.YELLOW);
        g.drawRect(3, 341, 513, 135);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Nekross Cutter", 20, 356);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Current Time Running: " + timer.formatTime(), 20, 380);
        g.drawString("Current Woddcuting Level: " + Skills.getRealLevel(Skill.WOODCUTTING), 20, 395);
        g.drawString("Current Woddcuting Exp: " + Skills.getExperience(Skill.WOODCUTTING), 20, 410);
        g.drawString("Current Woddcuting Exp to Level: " + Skills.getExperienceToLevel(Skill.WOODCUTTING), 20, 425);
        g.drawString(currentPhase, 20, 440);
        if (image != null){
            g.drawImage(image, 350, 330, null);
        }
    }
}
