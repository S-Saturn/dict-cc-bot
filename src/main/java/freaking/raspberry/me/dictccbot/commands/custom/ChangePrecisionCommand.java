package freaking.raspberry.me.dictccbot.commands.custom;

import freaking.raspberry.me.dictccbot.model.CustomCommandName;
import freaking.raspberry.me.dictccbot.model.Emoji;
import freaking.raspberry.me.dictccbot.model.EntryPrecision;
import freaking.raspberry.me.dictccbot.model.TranslationDirection;
import freaking.raspberry.me.dictccbot.services.TranslationService;

import java.util.Locale;

public class ChangePrecisionCommand implements CustomCommand {
    private final static CustomCommandName customCommandName = CustomCommandName.CHANGE_PRECISION;

    @Override
    public String execute() {
        TranslationService.changePrecision();
        EntryPrecision newEntryPresicion = TranslationService.getEntryPrecision();
        return "Search precision changed to " + newEntryPresicion.name().toLowerCase(Locale.US);
    }

    @Override
    public CustomCommandName getCustomCommandName() {
        return customCommandName;
    }
}
