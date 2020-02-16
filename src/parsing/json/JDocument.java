package parsing.json;

import parsing.model.CopyNode;
import parsing.model.ParseResult;
import parsing.model.SequenceNode;
import parsing.model.WhitespaceToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Arrays.asList;

/**
 * Creator: Patrick
 * Created: 27.10.2019
 * Purpose:
 */
public final class JDocument extends SequenceNode implements CopyNode<JDocument> {
    private final JObject _root;

    public JDocument() {
        _root = new JObject();
        _sequence.addAll(asList(new WhitespaceToken(), _root));
    }

    public static JDocument ofFile(Path path) throws IOException {
        String rawDocument = Files.readString(path);

        return JDocument.of(rawDocument);
    }

    public static JDocument of(String string) {
        JDocument document = new JDocument();
        ParseResult result = document.parse(string, 0);

        return result.isValid() ? document : null;
    }

    @Override
    public void setData(JDocument other) {
        super.setData(other);
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public JDocument deepCopy() {
        JDocument other = new JDocument();
        other.setData(this);

        return other;
    }
}
