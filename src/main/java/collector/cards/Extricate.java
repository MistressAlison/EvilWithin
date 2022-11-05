package collector.cards;

import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class Extricate extends AbstractCollectorCard {
    public final static String ID = makeID("Extricate");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Extricate() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 12;
        baseBlock = 12;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (CollectorMod.isAfflicted(m)){
            addToBot(new GainBlockAction(p, block));
        }

    }

    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
    }
}