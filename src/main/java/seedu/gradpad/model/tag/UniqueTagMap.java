package seedu.gradpad.model.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Encapsulates data and logic to maintain a set of unique {@code Tag} objects in GradPad.
 * With this, duplicate tags are not created in GradPad unnecessarily, thus encouraging reuse of {@code Tag} objects
 * across {@code Module} objects.
 */
public class UniqueTagMap {
    /**
     * A map of tag names to actual {@code Tag} objects
     */
    private final Map<String, Tag> tagMap = new HashMap<>();

    /**
     * Replaces the current map of {@code Tag} objects with the contents of a new {@code UniqueTagMap}.
     *
     * @param replacement The replacement {@code UniqueTagMap} to copy from.
     */
    public void setTags(UniqueTagMap replacement) {
        tagMap.clear();
        tagMap.putAll(replacement.tagMap);
    }

    /**
     * Given a set of {@code Tags}, decreases the module count of each tag, and if the tag ends up as not being
     * used by any {@code Module}, removes it from {@code UniqueTagMap}. This is used to effect "untagging"
     * operations, where a module no longer uses these tags, or if a module is deleted.
     *
     * @param tags The set of tags to "untag".
     */
    public void remove(Set<Tag> tags) {
        for (Tag tag : tags) {
            tag.decrementModuleCount();
            if (tag.isEmpty()) {
                tagMap.remove(tag.tagName);
            }
        }
    }

    /**
     * Given a set of {@code Tags}, checks if each tag already exists in {@code UniqueTagMap}.
     * If it does, then it replaces that tag with the existing tag so that there is no need to create a new tag.
     * Else, it adds that new tag to GradPad.
     *
     * @param tagsToCheck The set of tags to check through.
     * @return The new set of tags which has any duplicate new tags replaced with their existing equivalent.
     */
    public Set<Tag> checkAndReplaceTags(Set<Tag> tagsToCheck) {
        Set<Tag> replacedTagSet = new HashSet<>();

        for (Tag tag : tagsToCheck) {
            if (tagMap.containsKey(tag.tagName)) {
                Tag existingTag = tagMap.get(tag.tagName);
                existingTag.incrementModuleCount();
                replacedTagSet.add(existingTag);
            } else {
                tagMap.put(tag.tagName, tag);
                replacedTagSet.add(tag);
            }
        }

        return replacedTagSet;
    }

    /**
     * Returns a list of tag names of all tags in {@code UniqueTagMap}
     *
     * @return a list of tag names
     */
    public List<String> getTagNames() {
        return new ArrayList<>(tagMap.keySet());
    }

    @Override
    public String toString() {
        return tagMap.toString();
    }
}
