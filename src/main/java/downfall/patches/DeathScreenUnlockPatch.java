package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;
import downfall.unlocks.*;
import javassist.CtBehavior;

import java.util.ArrayList;


@SpirePatch(
        clz = DeathScreen.class,
        method = "update"
)
public class DeathScreenUnlockPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )

    public static SpireReturn Insert(DeathScreen __instance) {
        ////downfallMod.logger.info("patch spam");

        if ((AbstractDungeon.unlocks.isEmpty()) || (Settings.isDemo)) {
            // //downfallMod.logger.info("first if");
            if ((Settings.isDemo) || (Settings.isDailyRun)) {
                //  //downfallMod.logger.info("second if");
                CardCrawlGame.startOver();
            } else {

                // //downfallMod.logger.info("Guardian is locked: " + UnlockTracker.isCharacterLocked("Guardian"));
                // //downfallMod.logger.info("Hexaghost is locked: " + UnlockTracker.isCharacterLocked("Hexaghost"));
                // //downfallMod.logger.info("Snecko is locked: " + UnlockTracker.isCharacterLocked("Snecko"));
                if ((AbstractDungeon.unlocks.isEmpty()) || (Settings.isDemo)) {
                    if ((Settings.isDemo) || (Settings.isDailyRun)) {
                        CardCrawlGame.startOver();
                    } else if (UnlockTracker.isCharacterLocked("Guardian") && !UnlockTracker.isCharacterLocked("The Silent") && (AbstractDungeon.player.chosenClass == downfallMod.Enums.SLIMEBOUND)) {
                        AbstractDungeon.unlocks.add(new GuardianUnlock());
                        AbstractDungeon.unlockScreen.open(AbstractDungeon.unlocks.remove(0));
                        ////downfallMod.logger.info("Triggered Guardian Unlock screen!");
                        return SpireReturn.Return(null);
                    } else if ((UnlockTracker.isCharacterLocked("Hexaghost")) && (AbstractDungeon.player.chosenClass == downfallMod.Enums.GUARDIAN)) {
                        AbstractDungeon.unlocks.add(new HexaghostUnlock());
                        AbstractDungeon.unlockScreen.open(AbstractDungeon.unlocks.remove(0));
                        // //downfallMod.logger.info("Triggered Hexaghost Unlock screen!");
                        return SpireReturn.Return(null);
                    } else if ((UnlockTracker.isCharacterLocked("Champ")) && (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_SPIRIT)) {
                        AbstractDungeon.unlocks.add(new ChampUnlock());
                        AbstractDungeon.unlockScreen.open(AbstractDungeon.unlocks.remove(0));
                        ////downfallMod.logger.info("Triggered Champ Unlock screen!");
                        return SpireReturn.Return(null);
                    } else if ((UnlockTracker.isCharacterLocked("Automaton")) && (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_CHAMP)) {
                        AbstractDungeon.unlocks.add(new AutomatonUnlock());
                        AbstractDungeon.unlockScreen.open(AbstractDungeon.unlocks.remove(0));
                        ////downfallMod.logger.info("Triggered Automaton Unlock screen!");
                        return SpireReturn.Return(null);
                    } else if ((UnlockTracker.isCharacterLocked("Gremlin")) && (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_AUTOMATON)) {
                        AbstractDungeon.unlocks.add(new GremlinUnlock());
                        AbstractDungeon.unlockScreen.open(AbstractDungeon.unlocks.remove(0));
                        ////downfallMod.logger.info("Triggered Gremlin Unlock screen!");
                        return SpireReturn.Return(null);
                    } else if ((UnlockTracker.isCharacterLocked("Snecko")) && (AbstractDungeon.player.chosenClass == downfallMod.Enums.GREMLIN)) {
                        AbstractDungeon.unlocks.add(new SneckoUnlock());
                        AbstractDungeon.unlockScreen.open(AbstractDungeon.unlocks.remove(0));
                        ////downfallMod.logger.info("Triggered Snecko Unlock screen!");
                        return SpireReturn.Return(null);
                    }
                }
            }
        }
        return SpireReturn.Continue();
    }


    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            //Matcher finalMatcher = new Matcher.FieldAccessMatcher(UnlockTracker.class, "isCharacterLocked");
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ReturnToMenuButton.class, "hide");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[0], LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[1]};
        }
    }
}