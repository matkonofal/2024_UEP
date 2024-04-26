package pl.psi;

import java.util.List;

import pl.psi.creatures.Creature;

import lombok.Getter;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class Hero
{
    @Getter
    private final List< Creature > creatures;
    @Getter
    private HeroSkills skills;

    public Hero( final List< Creature > aCreatures, int initialAttack, int initialDefense )
    {
        creatures = aCreatures;
        skills = new HeroSkills(initialAttack, initialDefense);
        creatures.forEach(creature -> {
            creature.setHeroAttackModifier(skills.getAttack());
            creature.setHeroArmorModifier(skills.getDefense());
        });
        
    }

    public Hero(final List<Creature> aCreatures){
        this(aCreatures, 0, 0);
    }
    
    public void newSkill(SecondarySkill skill){
        this.updateCreatureStat(skills.progressSecondarySkill(skill));
    }
    
    public void changeHeroStatistic(String skillName, int amount)
    {
        skills.changeAttackDefenseLuckMorale(skillName, amount);
        this.updateCreatureStat(skillName.toLowerCase());
    }

    public int getAttack(){
        return skills.getAttack();
    }
    
    public int getDefense(){
        return skills.getDefense();
    }

    public int getMorale(){
        return skills.getMorale();
    }

    public int getLuck(){
        return skills.getLuck();
    }
    
    private void updateCreatureStat(String stat){
        switch (stat) {
            case "nonerelated":
                break;
    
            case "attack":
                creatures.forEach(creature -> creature.setHeroAttackModifier(this.getAttack()));
                break;
    
            case "defense":
                creatures.forEach(creature -> creature.setHeroArmorModifier(this.getDefense()));
                break;
    
            case "luck":
                creatures.forEach(creature -> creature.setLuck(getLuck()));
                break;
    
            case "morale":
                creatures.forEach(creature -> creature.setMorale(getMorale()));
                break;
    
            default:
                break;
        }
    }
}
