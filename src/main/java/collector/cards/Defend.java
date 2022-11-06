package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class Defend extends AbstractCollectorCard {
    public final static String ID = makeID("Defend");
    // intellij stuff skill, self, basic, , , 5, 3, , 

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        blck();
    }

    public void upp() {
        upgradeBlock(3);
    }
}