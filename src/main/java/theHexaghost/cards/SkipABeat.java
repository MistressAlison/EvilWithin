package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.actions.ExtinguishCurrentFlameAction;

public class SkipABeat extends AbstractHexaCard {

    public final static String ID = makeID("SkipABeat");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public SkipABeat() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        tags.add(HexaMod.GHOSTWHEELCARD);
        // exhaust = true;
        this.tags.add(downfallMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // if (!GhostflameHelper.activeGhostFlame.charged)  atb(new ChargeCurrentFlameAction());
        //  atb(new ExtinguishCurrentFlameAction());

        if (!upgraded) {
            if (!GhostflameHelper.activeGhostFlame.charged) atb(new ChargeCurrentFlameAction());
        } else {
            atb(new ExtinguishCurrentFlameAction());
            atb(new ChargeCurrentFlameAction());
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(0);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}