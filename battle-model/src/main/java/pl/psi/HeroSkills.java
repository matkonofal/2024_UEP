package pl.psi;

import java.util.ArrayList;
import java.util.HashMap;

public class HeroSkills {
    private ArrayList<SecondarySkill> skillTree = new ArrayList<>();
    private HashMap<String, Integer> attackDefenseMoralLuck = new HashMap<>();

    public HeroSkills(int initialAttack, int initialDefense){
        attackDefenseMoralLuck.put("attack", initialAttack);
        attackDefenseMoralLuck.put("defense", initialDefense);
        attackDefenseMoralLuck.put("morale", 0);
        attackDefenseMoralLuck.put("luck", 0);
    }

    public String progressSecondarySkill(SecondarySkill progressedSkill){
        if (skillTree.contains(progressedSkill)){
            for (SecondarySkill skill: skillTree){
                if (skill.equals(progressedSkill)){
                    if (skill.tierUp()){
                        increaseSkill(skill.getRelatedStatistic());
                        return skill.getRelatedStatistic();
                    }
                }
            }
        } else {
            skillTree.add(progressedSkill);
            increaseSkill(progressedSkill.getRelatedStatistic());
        }
        return progressedSkill.getRelatedStatistic();
    }

    public String increaseSkill(String statName){
        if (!(statName.toLowerCase().equals("nonerelated"))){
            int currentStatAmount = attackDefenseMoralLuck.get(statName);
            attackDefenseMoralLuck.put(statName, currentStatAmount + 1);
            return statName;
        }
        return "nonerelated";
    }

    public void changeAttackDefenseLuckMorale(String statName, int statAmountChange){
        int currentStatAmount = attackDefenseMoralLuck.get(statName);
        attackDefenseMoralLuck.put(statName, currentStatAmount + statAmountChange);
    }

    public int getAttack(){
        return attackDefenseMoralLuck.get("attack");
    }

    public int getDefense(){
        return attackDefenseMoralLuck.get("defense");
    }

    public int getMorale(){
        return attackDefenseMoralLuck.get("morale");
    }

    public int getLuck(){
        return attackDefenseMoralLuck.get("luck");
    }
}
