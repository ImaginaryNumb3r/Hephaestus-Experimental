package parsing.xml;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Creator: Patrick
 * Created: 25.03.2019
 * TODO: Simplified access for attribute/tag parsing only.
 * TODO: Have an enum as return parameter which controls the flow of the traversion (terminate, stop descent, continue)
 */
public interface XMLVisitor {

    void visitAttribute(AttributeToken attributeToken, XMLTag tag);

    void visitTag(XMLTag tag);

    static XMLVisitor of (@NotNull BiConsumer<AttributeToken, XMLTag> attributeConsumer,
                          @NotNull Consumer<XMLTag> tagConsumer
    ) {
        Contract.checkNulls(attributeConsumer, tagConsumer);

        return new XMLVisitor() {
            @Override
            public void visitAttribute(AttributeToken attributeToken, XMLTag tag) {
                attributeConsumer.accept(attributeToken, tag);
            }

            @Override
            public void visitTag(XMLTag tag) {
                tagConsumer.accept(tag);
            }
        };
    }
}
