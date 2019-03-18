package xml;

import essentials.contract.NoImplementationException;

import java.util.ArrayList;

/**
 * Creator: Patrick
 * Created: 16.03.2019
 * Purpose:
 */
public class XMLDocument {

    public static XMLDocument parse(String rawXML) {
        CharacterPipe characterPipe = new CharacterPipe(rawXML);

        var tokenBuilders = new ArrayList<>();
        tokenBuilders.add(new Prologue());
        tokenBuilders.add(new Prologue());


        throw new NoImplementationException();
    }
}
