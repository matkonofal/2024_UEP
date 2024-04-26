package pl.psi;

public class SecondarySkill {
    private String skillName;
    private String relatedStatistic;
    private int currentTier;

    public SecondarySkill(String secondarySkillName, String statistic){
        relatedStatistic = statistic;
        skillName = secondarySkillName;
        currentTier = 1;
    }

    public SecondarySkill(String leveledSkill){
        this(leveledSkill, "NoneRelated");
    }

    public boolean tierUp(){
        if (currentTier == 3){
            return false;
        } else {
            currentTier++;
            return true;
        }
    }

    public String getSkillName() {
        return skillName;
    }

    public int getCurrentTier() {
        return currentTier;
    }

    public String getRelatedStatistic() {
        return relatedStatistic.toLowerCase();
    }

    @Override
    public String toString(){
        return relatedStatistic;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (!(this instanceof SecondarySkill)){
            return false;
        }

        SecondarySkill comparedObject = (SecondarySkill) obj;

        return comparedObject.getSkillName() == this.skillName;
    }
}
