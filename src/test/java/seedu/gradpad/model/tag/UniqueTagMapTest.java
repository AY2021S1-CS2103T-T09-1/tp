package seedu.gradpad.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class UniqueTagMapTest {
    @Test
    public void checkAndReplaceTags_emptySet_noChange() {
        UniqueTagMap map = new UniqueTagMap();
        map.checkAndReplaceTags(new HashSet<>());
        assertTrue(map.getTagNames().isEmpty());
    }

    @Test
    public void checkAndReplaceTags_newDistinctTag_populatesMap() {
        UniqueTagMap map = new UniqueTagMap();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("core"));
        map.checkAndReplaceTags(tagSet);

        List<String> actualNames = map.getTagNames();
        List<String> expectedNames = Collections.singletonList("core");
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void checkAndReplaceTags_existingTag_notAdded() {
        UniqueTagMap map = new UniqueTagMap();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("core"));
        map.checkAndReplaceTags(tagSet);

        // Add a tag with duplicate tagName
        Set<Tag> duplicateTagSet = new HashSet<>();
        duplicateTagSet.add(new Tag("core"));
        map.checkAndReplaceTags(duplicateTagSet);

        List<String> actualNames = map.getTagNames();
        // there should only be one name, i.e. duplicates aren't added
        List<String> expectedNames = Collections.singletonList("core");
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void remove_emptySet_noChange() {
        // set up and populate
        UniqueTagMap map = new UniqueTagMap();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("core"));
        map.checkAndReplaceTags(tagSet);

        // empty set
        map.remove(new HashSet<>());

        List<String> actualNames = map.getTagNames();
        List<String> expectedNames = Collections.singletonList("core");
        assertEquals(expectedNames, actualNames);
    }

    @Test
    public void remove_tagWithMoreThanOneModule_moduleCountDecreased() {
        // set up and populate
        UniqueTagMap map = new UniqueTagMap();
        Set<Tag> tagSet = new HashSet<>();
        Tag tag = new Tag("core");
        tag.incrementModuleCount();
        tagSet.add(tag);
        map.checkAndReplaceTags(tagSet);
        map.remove(tagSet);

        // tag should still remain in map
        List<String> actualNames = map.getTagNames();
        List<String> expectedNames = Collections.singletonList("core");
        assertEquals(expectedNames, actualNames);

        // tag's moduleCount should be decremented
        assertEquals(1, tag.getModuleCount());
    }

    @Test
    public void remove_tagWithOnlyOneModule_tagRemoved() {
        // set up and populate
        UniqueTagMap map = new UniqueTagMap();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("core"));
        map.checkAndReplaceTags(tagSet);
        map.remove(tagSet);

        // tag should be removed from map
        assertTrue(map.getTagNames().isEmpty());
    }

    @Test
    public void setTags_nonEmptyUniqueTagMap_tagsReplaced() {
        // set up and populate
        UniqueTagMap map = new UniqueTagMap();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("core"));
        map.checkAndReplaceTags(tagSet);

        UniqueTagMap replacement = new UniqueTagMap();
        Set<Tag> replacementSet = new HashSet<>();
        replacementSet.add(new Tag("nonCore"));
        replacement.checkAndReplaceTags(replacementSet);

        map.setTags(replacement);
        assertEquals(replacement.getTagNames(), map.getTagNames());
    }
}
