package clc.infrastructure;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.*;

import static clc.infrastructure.GameTagEntry.ID_KEY;

/**
 * @author Patrick Plieschnegger
 */
public class VisionMapper {
    private static final String DB_NAME = "vision-db";
    private static final String INDEX_NAME = "index";
    private static VisionMapper _instance;
    private final List<GameTagEntry> _entries = new ArrayList<>();
    private final DB _visionDB;

    private VisionMapper(DB visionDB) { _visionDB = visionDB; }

    public static VisionMapper instance() {
        if (_instance != null) return _instance;

        try {
            var mongo = new MongoClient();
            DB db = mongo.getDB(DB_NAME);

            _instance = new VisionMapper(db);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return _instance;
    }

    public void commit(GameTagEntry entry) {
        _entries.add(entry);
    }

    public void commit(Iterable<GameTagEntry> entries) {
        for (GameTagEntry entry : entries) {
            _entries.add(entry);
        }
    }

    public void persist() {
        Map<String, List<DBObject>> collectionMap = new HashMap<>();
        ArrayList<DBObject> indicies = new ArrayList<>();
        collectionMap.put(INDEX_NAME, indicies);

        for (var entry : _entries) {
            var collection = entry.getCollectionName();

            collectionMap.computeIfAbsent(collection, ignore -> new ArrayList<>());
            collectionMap.computeIfPresent(collection, (key, list) -> {
                list.add(entry.toEntry());
                indicies.add(entry.toPath());

                return list;
            });
        }

        // Flush.
        persist(collectionMap);
    }

    private void persist(Map<String, List<DBObject>> collectionEntries) {
        System.out.println();
        for (var entries : collectionEntries.entrySet()) {
            var collectionName = entries.getKey();
            var collectionList = entries.getValue();

            System.out.println("Persisting " + collectionList.size() + " items in " + collectionName);
            DBCollection dbCollection = _visionDB.getCollection(collectionName);

            dbCollection.insert(collectionList);
        }

        _entries.clear();
    }

    public void resetCollections() {
        Set<String> collectionNames = _visionDB.getCollectionNames();
        for (String collectionName : collectionNames) {
            DBCollection collection = _visionDB.getCollection(collectionName);

            BasicDBObject indices = new BasicDBObject();
            indices.append(ID_KEY, "");

            collection.drop();
            collection.createIndex(indices);
        }
    }

    public void persist(Iterable<GameTagEntry> list) {
        commit(list);
        persist();
    }

    public void drop(String name) {
        DBCollection collection = _visionDB.getCollection(name);
        collection.drop();
    }
}
