package org.mockserver.collections;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.mockserver.model.NottableString;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;
import static org.mockserver.model.NottableString.string;
import static org.mockserver.model.NottableString.strings;

/**
 * @author jamesdbloom
 */
public class CaseInsensitiveRegexMultiMapTest {

    @Test
    public void shouldStoreMultipleValuesAgainstSingleKey() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();

        // when
        circularMultiMap.put("one", "one_one");
        circularMultiMap.put("one", "one_two");
        circularMultiMap.put("one", "one_three");
        circularMultiMap.put("two", "two");

        // then
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three"), string("two")), circularMultiMap.getAll("[a-z]{3}"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("o[a-z]{2}"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("one"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("ONE"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("One"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("oNE"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("two"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("TWO"));
        assertEquals(2, circularMultiMap.size());
    }

    @Test
    public void shouldSupportPuttingAllEntriesInAMap() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();

        // when
        circularMultiMap.put("one", "one_one");
        circularMultiMap.putAll(new HashMap<NottableString, NottableString>() {
            private static final long serialVersionUID = -580164440676146851L;

            {
                put(string("one"), string("one_two"));
                put(string("two"), string("two"));
            }
        });
        circularMultiMap.put("one", "one_three");

        // then
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three"), string("two")), circularMultiMap.getAll("[a-z]{3}"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("o[a-z]{2}"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("one"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("ONE"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("One"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("oNE"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("two"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("TWO"));
        assertEquals(2, circularMultiMap.size());
    }

    @Test
    public void shouldSupportPuttingValuesForNewKeysOnly() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();

        // when
        circularMultiMap.put("one", "one_one");
        circularMultiMap.putValuesForNewKeys(new CaseInsensitiveRegexMultiMap() {{
            put("one", "one_two");
            put("two", "two");
        }});
        circularMultiMap.put("one", "one_three");

        // then
        assertEquals(strings("one_one", "one_three", "two"), circularMultiMap.getAll("[a-z]{3}"));
        assertEquals(strings("one_one", "one_three"), circularMultiMap.getAll("o[a-z]{2}"));
        assertEquals(strings("one_one", "one_three"), circularMultiMap.getAll("one"));
        assertEquals(strings("one_one", "one_three"), circularMultiMap.getAll("ONE"));
        assertEquals(strings("one_one", "one_three"), circularMultiMap.getAll("One"));
        assertEquals(strings("one_one", "one_three"), circularMultiMap.getAll("oNE"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("two"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("TWO"));
        assertEquals(2, circularMultiMap.size());
    }

    @Test
    public void shouldSupportPuttingMultipleValuesInAList() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();

        // when
        circularMultiMap.put("one", Arrays.asList("one_one"));
        circularMultiMap.put("one", Arrays.asList("one_two", "one_three"));

        // then
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("[a-z]{3}"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("o[a-z]{2}"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("one"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("ONE"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("One"));
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.getAll("oNE"));
        assertEquals(1, circularMultiMap.size());
    }

    @Test

    public void shouldIndicateWhenEmpty() {
        assertTrue(new CaseInsensitiveRegexMultiMap().isEmpty());
    }

    @Test

    public void shouldSupportBeingCleared() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();
        circularMultiMap.put("one", "one_one");
        circularMultiMap.put("one", "one_two");
        circularMultiMap.put("one", "one_three");
        circularMultiMap.put("two", "two");

        // when
        circularMultiMap.clear();

        // then
        assertTrue(circularMultiMap.isEmpty());
        assertFalse(circularMultiMap.containsKey("one"));
        assertFalse(circularMultiMap.containsKey("two"));
        assertFalse(circularMultiMap.containsValue("one_two"));
        assertFalse(circularMultiMap.containsValue("two"));
        assertEquals(0, circularMultiMap.size());
    }

    @Test

    public void shouldReturnEntrySet() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();

        // when
        circularMultiMap.put("one", "one_one");
        circularMultiMap.put("one", "one_two");
        circularMultiMap.put("one", "one_three");
        circularMultiMap.put("two", "two");

        // then
        assertEquals(Sets.newHashSet(
                new CaseInsensitiveRegexMultiMap.ImmutableEntry[]{
                        circularMultiMap.new ImmutableEntry("one", "one_one"),
                        circularMultiMap.new ImmutableEntry("one", "one_two"),
                        circularMultiMap.new ImmutableEntry("one", "one_three"),
                        circularMultiMap.new ImmutableEntry("two", "two")
                }
        ), circularMultiMap.entrySet());
        assertEquals(2, circularMultiMap.size());
    }

    @Test

    public void shouldCorrectlyConstructAndGetEntryValue() {
        // when
        CaseInsensitiveRegexMultiMap.ImmutableEntry immutableEntry = new CaseInsensitiveRegexMultiMap().new ImmutableEntry("key", "value");

        // then
        assertEquals(immutableEntry.getKey(), string("key"));
        assertEquals(immutableEntry.getValue(), string("value"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotAllowImmutableEntryToBeModified() {
        new CaseInsensitiveRegexMultiMap().new ImmutableEntry("key", "value").setValue(string("new_value"));
    }

    @Test
    public void shouldSupportRemovingAllValues() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();
        circularMultiMap.put("one", "one_one");
        circularMultiMap.put("one", "one_two");
        circularMultiMap.put("one", "one_three");
        circularMultiMap.put("two", "two");

        // when
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.removeAll("one"));
        assertNull(circularMultiMap.removeAll("three"));

        // then
        assertEquals(1, circularMultiMap.size());
        // - should have correct keys
        assertFalse(circularMultiMap.containsKey("one"));
        assertTrue(circularMultiMap.containsKey("two"));
        assertThat(circularMultiMap.keySet(), containsInAnyOrder((Object) "two"));
        // - should have correct values
        assertFalse(circularMultiMap.containsValue("one_one"));
        assertFalse(circularMultiMap.containsValue("one_two"));
        assertFalse(circularMultiMap.containsValue("one_three"));
        assertTrue(circularMultiMap.containsValue("two"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.values());
        // - should have correct values per key
        assertNull(circularMultiMap.get("one"));
        assertEquals(string("two"), circularMultiMap.get("two"));
        assertEquals(0, circularMultiMap.getAll("one").size());
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("two"));
    }

    @Test
    public void shouldSupportRemovingAValue() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();
        circularMultiMap.put("one", "one_one");
        circularMultiMap.put("one", "one_two");
        circularMultiMap.put("one", "one_three");
        circularMultiMap.put("two", "two");

        // when
        assertEquals(string("one_one"), circularMultiMap.remove("one"));
        assertNull(circularMultiMap.remove("three"));

        // then
        assertEquals(2, circularMultiMap.size());
        // - should have correct keys
        assertTrue(circularMultiMap.containsKey("one"));
        assertTrue(circularMultiMap.containsKey("two"));
        assertThat(circularMultiMap.keySet(), containsInAnyOrder((Object) "one", "two"));
        // - should have correct values
        assertFalse(circularMultiMap.containsValue("one_one"));
        assertTrue(circularMultiMap.containsValue("one_two"));
        assertTrue(circularMultiMap.containsValue("one_three"));
        assertTrue(circularMultiMap.containsValue("two"));
        assertEquals(Arrays.asList(string("one_two"), string("one_three"), string("two")), circularMultiMap.values());
        // - should have correct values per key
        assertEquals(string("one_two"), circularMultiMap.get("one"));
        assertEquals(string("two"), circularMultiMap.get("two"));
        assertEquals(Arrays.asList(string("one_two"), string("one_three")), circularMultiMap.getAll("one"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("two"));
    }

    @Test
    public void shouldSupportRemovingAValueWithRegex() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();
        circularMultiMap.put("one", "one_one");
        circularMultiMap.put("one", "one_two");
        circularMultiMap.put("one", "one_three");
        circularMultiMap.put("two", "two");

        // when
        assertEquals(string("one_one"), circularMultiMap.remove("o[a-z]{2}"));
        assertNull(circularMultiMap.remove("t[a-z]{3}"));

        // then
        assertEquals(2, circularMultiMap.size());
        // - should have correct keys
        assertTrue(circularMultiMap.containsKey("o.*"));
        assertTrue(circularMultiMap.containsKey("T[a-z]{2}"));
        assertThat(circularMultiMap.keySet(), containsInAnyOrder((Object) "one", "two"));
        // - should have correct values
        assertFalse(circularMultiMap.containsValue("one_one"));
        assertTrue(circularMultiMap.containsValue("one_two"));
        assertTrue(circularMultiMap.containsValue("one_three"));
        assertTrue(circularMultiMap.containsValue("two"));
        assertEquals(Arrays.asList(string("one_two"), string("one_three"), string("two")), circularMultiMap.values());
        // - should have correct values per key
        assertEquals(string("one_two"), circularMultiMap.get(".*n.*"));
        assertEquals(string("two"), circularMultiMap.get(".*o"));
        assertEquals(Arrays.asList(string("one_two"), string("one_three")), circularMultiMap.getAll("one"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("two"));
    }

    @Test
    public void shouldSupportRemovingAllValuesWithRegex() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();
        circularMultiMap.put("one", "one_one");
        circularMultiMap.put("one", "one_two");
        circularMultiMap.put("one", "one_three");
        circularMultiMap.put("two", "two");

        // when
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.removeAll("o[a-z]{2}"));
        assertNull(circularMultiMap.removeAll("t[a-z]{3}"));

        // then
        assertEquals(1, circularMultiMap.size());
        // - should have correct keys
        assertFalse(circularMultiMap.containsKey("one"));
        assertTrue(circularMultiMap.containsKey("two"));
        assertThat(circularMultiMap.keySet(), containsInAnyOrder((Object) "two"));
        // - should have correct values
        assertFalse(circularMultiMap.containsValue("one_one"));
        assertFalse(circularMultiMap.containsValue("one_two"));
        assertFalse(circularMultiMap.containsValue("one_three"));
        assertTrue(circularMultiMap.containsValue("two"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.values());
        // - should have correct values per key
        assertNull(circularMultiMap.get("one"));
        assertEquals(string("two"), circularMultiMap.get("two"));
        assertEquals(0, circularMultiMap.getAll("one").size());
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("two"));
    }

    @Test
    public void shouldSupportRemovingAValueWithCaseInsensitivity() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();
        circularMultiMap.put("one", "one_one");
        circularMultiMap.put("one", "one_two");
        circularMultiMap.put("one", "one_three");
        circularMultiMap.put("two", "two");

        // when
        assertEquals(string("one_one"), circularMultiMap.remove("ONE"));
        assertNull(circularMultiMap.remove("three"));

        // then
        assertEquals(2, circularMultiMap.size());
        // - should have correct keys
        assertTrue(circularMultiMap.containsKey("oNE"));
        assertTrue(circularMultiMap.containsKey("Two"));
        assertThat(circularMultiMap.keySet(), containsInAnyOrder((Object) "one", "two"));
        // - should have correct values
        assertFalse(circularMultiMap.containsValue("one_one"));
        assertTrue(circularMultiMap.containsValue("one_two"));
        assertTrue(circularMultiMap.containsValue("one_three"));
        assertTrue(circularMultiMap.containsValue("two"));
        assertEquals(Arrays.asList(string("one_two"), string("one_three"), string("two")), circularMultiMap.values());
        // - should have correct values per key
        assertEquals(string("one_two"), circularMultiMap.get("oNe"));
        assertEquals(string("two"), circularMultiMap.get("twO"));
        assertEquals(Arrays.asList(string("one_two"), string("one_three")), circularMultiMap.getAll("one"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("two"));
    }

    @Test
    public void shouldSupportRemovingAllValuesWithCaseInsensitivity() {
        // given
        CaseInsensitiveRegexMultiMap circularMultiMap = new CaseInsensitiveRegexMultiMap();
        circularMultiMap.put("one", "one_one");
        circularMultiMap.put("one", "one_two");
        circularMultiMap.put("one", "one_three");
        circularMultiMap.put("two", "two");

        // when
        assertEquals(Arrays.asList(string("one_one"), string("one_two"), string("one_three")), circularMultiMap.removeAll("ONE"));
        assertNull(circularMultiMap.removeAll("three"));

        // then
        assertEquals(1, circularMultiMap.size());
        // - should have correct keys
        assertFalse(circularMultiMap.containsKey("one"));
        assertTrue(circularMultiMap.containsKey("two"));
        assertThat(circularMultiMap.keySet(), containsInAnyOrder((Object) "two"));
        // - should have correct values
        assertFalse(circularMultiMap.containsValue("one_one"));
        assertFalse(circularMultiMap.containsValue("one_two"));
        assertFalse(circularMultiMap.containsValue("one_three"));
        assertTrue(circularMultiMap.containsValue("two"));
        assertEquals(Arrays.asList(string("two")), circularMultiMap.values());
        // - should have correct values per key
        assertNull(circularMultiMap.get("one"));
        assertEquals(string("two"), circularMultiMap.get("two"));
        assertEquals(0, circularMultiMap.getAll("one").size());
        assertEquals(Arrays.asList(string("two")), circularMultiMap.getAll("two"));
    }
}
