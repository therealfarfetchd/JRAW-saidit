package net.dean.jraw.managers;

import net.dean.jraw.models.Thing;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Maintains a map of Thing names and Things inside of WeakReferences.
 * ThingManager is a singleton and can be accessed anywhere.
 *
 * @author Phani Gaddipati
 */
public class ThingManager {

    private static ThingManager instance = null;

    /**
     * Whether the manager should be storing references.
     */
    private boolean isEnabled = true;

    /**
     * ThingManager is a singleton.
     */
    private ThingManager() {
    }

    /**
     * A map associating Things to thing full names.
     * Soft references used to avoid a build up of unused objects.
     */
    private HashMap<String, WeakReference<Thing>> thingMap = new HashMap<>();

    /**
     * Add a Thing to the map if the map is enabled.
     *
     * @param thing The thing to add
     */
    public void addThing(Thing thing) {
        if (this.isEnabled()) {
            this.thingMap.put(thing.getFullName(), new WeakReference<>(thing));
        }
    }

    /**
     * Remove a Thing's WeakReference, if it exists.
     *
     * @param thing The thing to remove
     */
    public void removeThing(Thing thing) {
        this.thingMap.remove(thing.getFullName());
    }

    /**
     * If it exists, return the Thing with the given full name.
     *
     * @param fullName The name to look for
     * @return The associated Thing or null if it doesn't exist
     */
    public Thing getThing(String fullName) {
        if (this.thingMap.containsKey(fullName)) {
            if (this.thingMap.get(fullName).get() == null) {
                //SoftReference no longer references anything, remove key
                this.thingMap.remove(fullName);
            } else {
                return this.thingMap.get(fullName).get();
            }
        }
        return null;
    }

    /**
     * Clear the map of all references.
     */
    public void clearMap() {
        this.thingMap.clear();
    }

    /**
     * Check if ThingManager is updated.
     *
     * @return
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Enable or disable the updating of ThingMap.
     *
     * @param isEnabled
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Obtain an instance of ThingManager.
     *
     * @return An instance of ThingManager
     */
    public static ThingManager get() {
        if (instance == null) {
            instance = new ThingManager();
        }
        return instance;
    }

}
