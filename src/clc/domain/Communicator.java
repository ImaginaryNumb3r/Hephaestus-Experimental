package clc.domain;

import clc.infrastructure.GameTagEntry;
import clc.infrastructure.VisionMapper;
import parsing.xml.XMLDocument;
import parsing.xml.XMLTag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static clc.domain.Project.PROJECT_PATH;

/**
 * @author Patrick Plieschnegger
 */
public class Communicator {

    // TODO: Update so only new entries in the index are overwritten.
    public static void forceUpdate(Map<Path, XMLDocument> xmlDirectory) throws IOException {
        VisionMapper mapper = VisionMapper.instance();
        mapper.resetCollections();

        var list = new ArrayList<GameTagEntry>();

        for (var entry : xmlDirectory.entrySet()) {
            Path absPath = entry.getKey();
            Path relPath = PROJECT_PATH.relativize(absPath);
            XMLDocument xml = entry.getValue();

            FileTime lastModifiedTime = Files.getLastModifiedTime(absPath);
            long millies = lastModifiedTime.to(TimeUnit.MILLISECONDS);
            Date date = new Date(millies);

            // Only save xml tags with ids, which are Templates.
            XMLTag tags = xml.getRoot();
            tags.children().stream()
                .filter(tag -> tag.getName().contains("Template") || tag.getName().contains("GameObject"))
                .filter(tag -> tag.hasAttributes("id"))
                .forEach(tag -> {
                        String tagName = tag.getName();
                        String id = tag.getAttribute("id").getValue();

                        GameTagEntry object = new GameTagEntry(id, date, tag, relPath, tagName);
                        list.add(object);
                    }
                );
        }

        // flush
        System.out.println("Flushing...");

        list.stream()
            .map(GameTagEntry::getCollectionName)
            .distinct()
            .forEach(mapper::drop);

        final int batchThreshold = 300;
        int count = 0;
        for (GameTagEntry entry : list) {
            mapper.commit(entry);

            if (++count % batchThreshold == 0) {
                mapper.persist();
            }
        }

        mapper.persist(list);
        System.out.println("Done!");
    }
}
