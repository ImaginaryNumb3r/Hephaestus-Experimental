package clc;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import parsing.xml.XMLTag;

import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrick Plieschnegger
 */
public class GameTagEntry  {
    public static final String ID_KEY = "id";
    public static final String MODIFY_DATE_KEY = "modify_data";
    public static final String HASH_KEY = "hash";
    public static final String DATA_KEY = "data";
    public static final String PATH_KEY = "path";

    // Unfortunately, only "java.util.Date" is supported by the Mongo DB driver.
    private final Date _modifyDate;
    private final String _id;
    private final String _data;
    private final String _path;
    private final String _collectionName;
    private final int _hash;

    public GameTagEntry(String id, Date modifyDate, XMLTag data, Path path, String templateName) {
        _id = id;
        _modifyDate = modifyDate;
        _data = data.toString();
        _path = path.toString();
        _hash = _data.hashCode();

        _collectionName = templateName;
    }

    public String getId() {
        return _id;
    }

    public Date getModifyDate() {
        return _modifyDate;
    }

    public int getHash() {
        return _hash;
    }

    public String getData() {
        return _data;
    }

    public String getPath() {
        return _path;
    }

    public String getCollectionName() {
        return _collectionName;
    }

    public DBObject toPath() {
        Map<String, Object> map = new HashMap<>();
        map.put(ID_KEY, _id);
        map.put(MODIFY_DATE_KEY, _modifyDate);
        map.put(HASH_KEY, _hash);
        map.put(PATH_KEY, _path);

        return new BasicDBObject(map);
    }

    public DBObject toEntry() {
        DBObject dbObject = toPath();
        dbObject.put(DATA_KEY, _data);

        return dbObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameTagEntry)) return false;

        GameTagEntry that = (GameTagEntry) o;

        if (_hash != that._hash) return false;
        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (_modifyDate != null ? !_modifyDate.equals(that._modifyDate) : that._modifyDate != null) return false;
        return _data != null ? _data.equals(that._data) : that._data == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (_modifyDate != null ? _modifyDate.hashCode() : 0);
        result = 31 * result + _hash;
        result = 31 * result + (_data != null ? _data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return _id + " at " + _path;
    }
}
