package pl.psi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Range;

import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

public class HeroCreatureStatisticsTest {

    private static final Range<Integer> NOT_IMPORTANT_DMG = Range.closed(0, 0);

    @Test
    void luckAddsProperly(){
        final Creature Dwarf = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(20)
                        .damage(Range.closed(10, 10))
                        .attack(10)
                        .armor(0)
                        .build())
                .build();
                
        final Creature Gorgon = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(0)
                        .build())
                .build();

        assertThat(Dwarf.getLuck()).isEqualTo(0);
        assertThat(Gorgon.getLuck()).isEqualTo(0);

        Hero luckyHero = new Hero(List.of(Dwarf));
        Hero unluckyHero = new Hero(List.of(Gorgon));

        luckyHero.changeHeroStatistic("luck", 3);
        unluckyHero.changeHeroStatistic("luck", -3);

        assertThat(Dwarf.getLuck()).isEqualTo(3);
        assertThat(Gorgon.getLuck()).isEqualTo(-3);

        luckyHero.changeHeroStatistic("luck", -2);
        unluckyHero.changeHeroStatistic("luck", 2);

        assertThat(Dwarf.getLuck()).isEqualTo(1);
        assertThat(Gorgon.getLuck()).isEqualTo(-1);
    }

    @Test
    void luckConvertsIntoPercentageCorrectly(){
        final Creature Dwarf = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(20)
                        .damage(Range.closed(10, 10))
                        .attack(10)
                        .armor(0)
                        .build())
                .build();

        final Creature Gorgon = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(0)
                        .build())
                .build();

        Hero luckyHero = new Hero(List.of(Dwarf));
        Hero unluckyHero = new Hero(List.of(Gorgon));

        assertThat(Dwarf.getLuckPercentage()).isEqualTo(0f);
        assertThat(Gorgon.getLuckPercentage()).isEqualTo(0f);

        luckyHero.changeHeroStatistic("luck", 3);
        unluckyHero.changeHeroStatistic("luck", -3);

        assertThat(Dwarf.getLuckPercentage()).isEqualTo(12.5f);
        assertThat(Gorgon.getLuckPercentage()).isEqualTo(0);
    }

    @Test
    void luckPercentageWorksCorrectly(){
        final Creature Dwarf = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(20)
                        .damage(Range.closed(10, 10))
                        .attack(10)
                        .armor(0)
                        .build())
                .build();
                
        final Creature GigaGorgon = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100000)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(0)
                        .build())
                .build();

        Hero attackingHero = new Hero(List.of(Dwarf));
        attackingHero.changeHeroStatistic("luck", 3);

        for (int i = 0; 1000 > i; i++){
                Dwarf.attack(GigaGorgon);
        }

        if (GigaGorgon.getCurrentHp() > 83400){
                fail(String.valueOf(GigaGorgon.getCurrentHp()));
        }

        if (GigaGorgon.getCurrentHp() < 82400){
                fail(String.valueOf(GigaGorgon.getCurrentHp()));
        }

    }

    @Test
    void moraleAddsProperly(){
        final Creature Goblin = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(5)
                        .damage(Range.closed(1, 2))
                        .attack(4)
                        .armor(2)
                        .build())
                .build();

        final Creature Gremlin = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(4)
                        .damage(Range.closed(1, 2))
                        .attack(3)
                        .armor(3)
                        .build())
                .build();
        
        Hero heroWithGoblin = new Hero(List.of(Goblin));
        Hero heroWithGremlin = new Hero(List.of(Gremlin));

        assertThat(heroWithGoblin.getMorale()).isEqualTo(0);
        assertThat(heroWithGremlin.getMorale()).isEqualTo(0);

        heroWithGoblin.changeHeroStatistic("morale", -3);
        heroWithGremlin.changeHeroStatistic("morale", 3);

        assertThat(heroWithGoblin.getMorale()).isEqualTo(-3);
        assertThat(heroWithGremlin.getMorale()).isEqualTo(3);

        heroWithGoblin.changeHeroStatistic("morale", 2);
        heroWithGremlin.changeHeroStatistic("morale", -2);

        assertThat(heroWithGoblin.getMorale()).isEqualTo(-1);
        assertThat(heroWithGremlin.getMorale()).isEqualTo(1);
    }

    @Test
    void moraleConvertsIntoPercentageCorrectly(){
        final Creature Goblin = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(5)
                        .damage(Range.closed(1, 2))
                        .attack(4)
                        .armor(2)
                        .build())
                .build();

        final Creature Gremlin = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(4)
                        .damage(Range.closed(1, 2))
                        .attack(3)
                        .armor(3)
                        .build())
                .build();

        Hero heroWithGoblin = new Hero(List.of(Goblin));
        Hero heroWithGremlin = new Hero(List.of(Gremlin));

        assertThat(Goblin.getMoralePercentage()).isEqualTo(0f);
        assertThat(Gremlin.getMoralePercentage()).isEqualTo(0f);

        heroWithGoblin.changeHeroStatistic("morale", 3);
        heroWithGremlin.changeHeroStatistic("morale", -3);

        assertThat(Goblin.getMoralePercentage()).isEqualTo(12.5f);
        assertThat(Gremlin.getMoralePercentage()).isEqualTo(25f);

        heroWithGoblin.changeHeroStatistic("morale", 10);
        heroWithGremlin.changeHeroStatistic("morale", -10);

        assertThat(Goblin.getMoralePercentage()).isEqualTo(12.5f);
        assertThat(Gremlin.getMoralePercentage()).isEqualTo(25f);
    }

    @Test
    void moraleGivesExtraAttacks(){
        final Creature Goblin = new Creature.Builder().statistic(CreatureStats.builder()
                        .name("Goblin")
                        .maxHp(5)
                        .damage(Range.closed(1, 2))
                        .attack(4)
                        .armor(2)
                        .build())
                .build();

                final Creature Gremlin = new Creature.Builder().statistic(CreatureStats.builder()
                        .name("Gremlin")
                        .maxHp(4)
                        .damage(Range.closed(1, 2))
                        .attack(3)
                        .armor(3)
                        .build())
                .build();

                final Creature GorgonDummy = new Creature.Builder().statistic(CreatureStats.builder()
                        .name("Dummy")
                        .maxHp(100000)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(0)
                        .build())
                .build();

        Hero heroWithGoblin = new Hero(List.of(Goblin));

        heroWithGoblin.changeHeroStatistic("morale", 3);

        final TurnQueue turnQueue = new TurnQueue( List.of( Goblin ), List.of( Gremlin ) );

        int goblinExtraTurnCount = 0;

        for (int i = 0; 1000 > i; i++){
                if (turnQueue.getCurrentCreature().getName().equals("Goblin")){
                        Goblin.attack(GorgonDummy);
                        if (Goblin.hasExtraTurn()){
                                goblinExtraTurnCount++;
                        }       
                }
        }
        System.out.println(goblinExtraTurnCount);

        assertTrue(goblinExtraTurnCount > 100);
}

        
    @Test
    void lowMoraleSkipsTurns(){
        final Creature Goblin = new Creature.Builder().statistic(CreatureStats.builder()
                .name("Goblin")
                .maxHp(5)
                .damage(Range.closed(1, 2))
                .attack(4)
                .armor(2)
                .build())
        .build();

        final Creature Gremlin = new Creature.Builder().statistic(CreatureStats.builder()
                .name("Gremlin")
                .maxHp(4)
                .damage(Range.closed(1, 2))
                .attack(3)
                .armor(3)
                .build())
        .build();

        Hero heroWithGoblin = new Hero(List.of(Goblin));

        heroWithGoblin.changeHeroStatistic("morale", -3);

        final TurnQueue turnQueue = new TurnQueue( List.of( Goblin ), List.of( Gremlin ) );

        int goblinTurnCount = 0;
        int gremlinTurnCount = 0;

        for (int i = 0; 200 > i; i++){
                if (turnQueue.getCurrentCreature().getName().equals("Goblin")){
                        goblinTurnCount++;
                } else if (turnQueue.getCurrentCreature().getName().equals("Gremlin")){
                        gremlinTurnCount++;
                }
                turnQueue.next();
        }

        System.out.println(gremlinTurnCount);
        System.out.println(goblinTurnCount);

        assertTrue(gremlinTurnCount > goblinTurnCount + 15);

    }

    @Test
    void attackAddsToCreatureProperly(){
        final Creature Archer = new Creature.Builder().statistic(CreatureStats.builder()
                        .name("Archer")
                        .maxHp(10)
                        .damage(Range.closed(2, 2))
                        .attack(6)
                        .armor(3)
                        .build())
                .build();

        Hero attackingHero = new Hero(List.of(Archer));

        assertThat(Archer.getModifiedAttack()).isEqualTo(6);

        attackingHero.changeHeroStatistic("attack", 30);

        assertThat(Archer.getModifiedAttack()).isEqualTo(36);
    }

    @Test
    void attackContributesToDamageProperly(){
        final Creature Archer = new Creature.Builder().statistic(CreatureStats.builder()
                        .name("Archer")
                        .maxHp(10)
                        .damage(Range.closed(2, 2))
                        .attack(6)
                        .armor(3)
                        .build())
                .build();

        final Creature Troll = new Creature.Builder().statistic(CreatureStats.builder()
                        .name("Troll")
                        .maxHp(40)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(6)
                        .build())
                .build();

        Archer.attack(Troll);

        assertThat(Troll.getCurrentHp()).isEqualTo(38);

        Hero attackingHero = new Hero(List.of(Archer), 60, 0);
        
        Archer.attack(Troll);

        assertThat(Troll.getCurrentHp()).isEqualTo(30);
    }

    @Test
    void defenseAddsToCreatureProperly(){
        final Creature Troll = new Creature.Builder().statistic(CreatureStats.builder()
                        .name("Troll")
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(0)
                        .build())
                .build();

        Hero defendingHero = new Hero(List.of(Troll));

        assertThat(Troll.getModifiedArmor()).isEqualTo(0);

        defendingHero.changeHeroStatistic("defense", 20);

        assertThat(Troll.getModifiedArmor()).isEqualTo(20);
    }

    @Test
    void defenseContributesToArmorProperly(){
        final Creature Archer = new Creature.Builder().statistic(CreatureStats.builder()
                        .name("Archer")
                        .maxHp(10)
                        .damage(Range.closed(10, 10))
                        .attack(10)
                        .armor(0)
                        .build())
                .build();

        final Creature Troll = new Creature.Builder().statistic(CreatureStats.builder()
                        .name("Troll")
                        .maxHp(100)
                        .damage(NOT_IMPORTANT_DMG)
                        .attack(0)
                        .armor(0)
                        .build())
                .build();

        Archer.attack(Troll);

        assertThat(Troll.getCurrentHp()).isEqualTo(85);

        Hero defendingHero = new Hero(List.of(Troll), 0, 60);

        Archer.attack(Troll);

        assertThat(Troll.getCurrentHp()).isEqualTo(78);
    }

    @Test
    void secondarySkillsAddMoraleAndLuck(){
        final Creature Crusader = new Creature.Builder().statistic(CreatureStats.builder()
                        .name("Crusader")
                        .maxHp(35)
                        .damage(Range.closed(7, 10))
                        .attack(12)
                        .armor(12)
                        .build())
                .build();

        Hero levelingAdventurer = new Hero(List.of(Crusader));

        assertThat(Crusader.getLuck()).isEqualTo(0);
        assertThat(Crusader.getMorale()).isEqualTo(0);

        levelingAdventurer.newSkill(new SecondarySkill("Blessing of Tyche", "luck"));

        assertThat(Crusader.getLuck()).isEqualTo(1);
        assertThat(Crusader.getMorale()).isEqualTo(0);

        levelingAdventurer.newSkill(new SecondarySkill("Blessing of Tyche", "luck"));

        assertThat(Crusader.getLuck()).isEqualTo(2);
        assertThat(Crusader.getMorale()).isEqualTo(0);

        levelingAdventurer.newSkill(new SecondarySkill("Leadership", "morale"));

        assertThat(Crusader.getLuck()).isEqualTo(2);
        assertThat(Crusader.getMorale()).isEqualTo(1);

        levelingAdventurer.newSkill(new SecondarySkill("Leadership", "morale"));

        assertThat(Crusader.getLuck()).isEqualTo(2);
        assertThat(Crusader.getMorale()).isEqualTo(2);
    }

}
