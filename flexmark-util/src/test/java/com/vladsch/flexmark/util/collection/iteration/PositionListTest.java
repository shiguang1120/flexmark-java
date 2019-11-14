package com.vladsch.flexmark.util.collection.iteration;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;

public class PositionListTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getList() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);
        assertSame(list, positions.getList());
    }

    @Test
    public void iterator() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        Iterator<ListPosition<Integer>> iterator = positions.iterator();
        while (iterator.hasNext()) {
            ListPosition<Integer> position = iterator.next();
            list2.add(position.get());
        }
        assertEquals(list, list2);
    }

    @Test
    public void iterator2() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        for (ListPosition<Integer> position : positions) {
            list2.add(position.get());
        }
        assertEquals(list, list2);
    }

    @Test
    public void iterator_AddBefore() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        int i = 1;
        for (ListPosition<Integer> position : positions) {
            position.add(-(i++));
            list2.add(position.get());
        }
        List<Integer> expected = Arrays.asList(-1, 9, -2, 8, -3, 7, -4, 6, -5, 5, -6, 4, -7, 3, -8, 2, -9, 1, -10, 0);
        assertEquals(input, list2);
        assertEquals(expected, list);
    }

    @Test
    public void iterator_AddAfter() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        int i = 1;
        for (ListPosition<Integer> position : positions) {
            position.add(1, -(i++));
            list2.add(position.get());
        }

        List<Integer> expected = Arrays.asList(9, -1, 8, -2, 7, -3, 6, -4, 5, -5, 4, -6, 3, -7, 2, -8, 1, -9, 0, -10);
        assertEquals(input, list2);
        assertEquals(expected, list);
    }

    @Test
    public void iterator_Remove() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        int i = 1;
        Iterator<ListPosition<Integer>> iterator = positions.iterator();
        while (iterator.hasNext()) {
            ListPosition<Integer> position = iterator.next();
            list2.add(position.get());
            iterator.remove();
        }

        List<Integer> expected = Arrays.asList(9, -1, 8, -2, 7, -3, 6, -4, 5, -5, 4, -6, 3, -7, 2, -8, 1, -9, 0, -10);
        assertEquals(input, list2);
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    public void iterator_RemoveBeforeNext() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        thrown.expect(IllegalStateException.class);

        int i = 1;
        Iterator<ListPosition<Integer>> iterator = positions.iterator();
        iterator.remove();

        while (iterator.hasNext()) {
            ListPosition<Integer> position = iterator.next();
            list2.add(position.get());
            iterator.remove();
        }

        List<Integer> expected = Arrays.asList(9, -1, 8, -2, 7, -3, 6, -4, 5, -5, 4, -6, 3, -7, 2, -8, 1, -9, 0, -10);
        assertEquals(input, list2);
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    public void iterator_UseThenRemove() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        int i = 1;
        for (ListPosition<Integer> position : positions) {
            list2.add(position.get());
            position.remove();
        }

        List<Integer> expected = Arrays.asList(9, -1, 8, -2, 7, -3, 6, -4, 5, -5, 4, -6, 3, -7, 2, -8, 1, -9, 0, -10);
        assertEquals(input, list2);
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    public void iterator_UseThenRemove2() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        int i = 1;
        for (ListPosition<Integer> position : positions) {
            list2.add(position.get());
            position.remove(0, 2);
        }

        List<Integer> expected = Arrays.asList(9, 7, 5, 3, 1);
        assertEquals(expected, list2);
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    public void iterator_RemoveThenUse() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        thrown.expect(IllegalStateException.class);

        int i = 1;
        for (ListPosition<Integer> position : positions) {
            position.remove();
            list2.add(position.get());
        }

        List<Integer> expected = Arrays.asList(9, -1, 8, -2, 7, -3, 6, -4, 5, -5, 4, -6, 3, -7, 2, -8, 1, -9, 0, -10);
        assertEquals(input, list2);
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    public void get_outOfRange1() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        thrown.expect(IndexOutOfBoundsException.class);

        ListPosition<Integer> pos = positions.get(-1);
    }

    @Test
    public void get_outOfRange2() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        thrown.expect(IndexOutOfBoundsException.class);

        ListPosition<Integer> pos = positions.get(positions.size() + 1);
    }

    @Test
    public void get_valid() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);
        int iMax = list.size();
        for (int i = 0; i <= iMax; i++) {
            ListPosition<Integer> position = positions.get(i);
            assertEquals("" + i, i, position.getIndex());
            assertTrue("" + i, position.isValidIndex());

            assertEquals(i < iMax, position.isValidPosition());
            if (i < iMax) {
                assertEquals("" + i, 9 - i, (int) position.get());
                assertEquals("" + i, 9 - i, (int) position.get(0));
            }
        }
    }

    @Test
    public void get_invalid() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);
        int iMax = list.size();
        for (int i = 0; i <= iMax; i++) {
            ListPosition<Integer> position = positions.getPosition(i, false);
            assertEquals("" + i, i, position.getIndex());
            assertTrue("" + i, position.isValidIndex());
            assertFalse("" + i, position.isValid());

            position = position.getNext();
            assertEquals("" + i, i < iMax, position.isValidPosition());
            if (i < iMax) {
                assertEquals("" + i, 9 - i, (int) position.get());
                assertEquals("" + i, 9 - i, (int) position.get(0));
            }
        }
    }

    @Test
    public void clear() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<ListPosition<Integer>> listPositions = new ArrayList<>();

        int iMax = list.size();
        for (int i = 0; i <= iMax; i++) {
            ListPosition<Integer> position = positions.get(i);
            assertTrue("" + i, position.isValid());
            listPositions.add(position);
        }

        positions.clear();
        assertEquals(0, list.size());

        for (int i = 0; i <= iMax; i++) {
            ListPosition<Integer> position = listPositions.get(i);
            assertFalse("" + i, position.isValid());
        }
    }

    @Test
    public void posClear() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<ListPosition<Integer>> listPositions = new ArrayList<>();

        int iMax = list.size();
        for (int i = 0; i <= iMax; i++) {
            ListPosition<Integer> position = positions.get(i);
            assertTrue("" + i, position.isValid());
            listPositions.add(position);
        }

        positions.getEnd().clear();
        assertEquals(0, list.size());

        for (int i = 0; i <= iMax; i++) {
            ListPosition<Integer> position = listPositions.get(i);
            assertFalse("" + i, position.isValid());
        }
    }

    @Test
    public void inserted1() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

        int jMax = input.size();

        for (int j = 0; j < jMax; j++) {
            ArrayList<Integer> list = new ArrayList<>(input);
            PositionList<Integer> positions = new PositionList<>(list);
            ArrayList<ListPosition<Integer>> listPositions = new ArrayList<>();

            int iMax = list.size();
            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = positions.get(i);
                assertTrue("" + i, position.isValid());
                listPositions.add(position);
            }

            list.add(j, -1);
            positions.inserted(j, 1);

            assertEquals(input.size() + 1, list.size());

            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = listPositions.get(i);
                assertEquals(i < j ? i : i + 1, position.getIndex());
                assertTrue("" + i, position.isValidIndex());
                assertTrue("" + i, position.isValid());

                assertEquals("" + i, i < iMax, position.isValidPosition());
                if (i < iMax) {
                    assertEquals("" + i, list.get(i < j ? i : i + 1), position.get());
                    assertEquals("" + i, list.get(i < j ? i : i + 1), position.get(0));
                }
            }
        }
    }

    @Test
    public void deleted() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

        int jMax = input.size();

        for (int j = 0; j < jMax; j++) {
            ArrayList<Integer> list = new ArrayList<>(input);
            PositionList<Integer> positions = new PositionList<>(list);
            ArrayList<ListPosition<Integer>> listPositions = new ArrayList<>();

            int iMax = list.size();
            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = positions.get(i);
                assertTrue("" + i, position.isValid());
                listPositions.add(position);
            }

            list.remove(j);
            positions.deleted(j, 1);

            assertEquals(input.size() - 1, list.size());

            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = listPositions.get(i);
                assertEquals("" + i, i <= j ? i : i - 1, position.getIndex());
                assertTrue("" + i, position.isValidIndex());
                assertEquals("" + i, i != j, position.isValid());

                assertEquals("" + i, i != j && i < iMax, position.isValidPosition());
                if (i != j && i < iMax) {
                    assertEquals("" + i, list.get(i < j ? i : i - 1), position.get());
                    assertEquals("" + i, list.get(i < j ? i : i - 1), position.get(0));
                }
            }
        }
    }

    @Test
    public void add_Indexed() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

        int jMax = input.size();

        for (int j = 0; j <= jMax; j++) {
            ArrayList<Integer> list = new ArrayList<>(input);
            PositionList<Integer> positions = new PositionList<>(list);
            ArrayList<ListPosition<Integer>> listPositions = new ArrayList<>();

            int iMax = list.size();
            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = positions.get(i);
                assertTrue("" + i, position.isValid());
                listPositions.add(position);
            }

            positions.addItem(j, -1);
            assertEquals(input.size() + 1, list.size());

            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = listPositions.get(i);
                assertEquals("" + i, i < j ? i : i + 1, position.getIndex());
                assertTrue("" + i, position.isValidIndex());
                assertTrue("" + i, position.isValid());

                assertEquals("" + i, i < iMax, position.isValidPosition());
                if (i < iMax) {
                    assertEquals("" + i, list.get(i < j ? i : i + 1), position.get());
                    assertEquals("" + i, list.get(i < j ? i : i + 1), position.get(0));
                }
            }
        }
    }

    @Test
    public void add() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

        int jMax = input.size();
        int j = jMax;

        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<ListPosition<Integer>> listPositions = new ArrayList<>();

        int iMax = list.size();
        for (int i = 0; i <= iMax; i++) {
            ListPosition<Integer> position = positions.get(i);
            assertTrue("" + i, position.isValid());
            listPositions.add(position);
        }

        positions.addItem(-1);

        assertEquals(input.size() + 1, list.size());

        for (int i = 0; i <= iMax; i++) {
            ListPosition<Integer> position = listPositions.get(i);
            assertEquals("" + i, i < j ? i : i + 1, position.getIndex());
            assertTrue("" + i, position.isValidIndex());
            assertTrue("" + i, position.isValid());

            assertEquals("" + i, i < iMax, position.isValidPosition());
            if (i < iMax) {
                assertEquals("" + i, list.get(i < j ? i : i + 1), position.get());
                assertEquals("" + i, list.get(i < j ? i : i + 1), position.get(0));
            }
        }
    }

    @Test
    public void addAll() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

        int jMax = input.size();

        for (int j = 0; j <= jMax; j++) {
            ArrayList<Integer> list = new ArrayList<>(input);
            PositionList<Integer> positions = new PositionList<>(list);
            ArrayList<ListPosition<Integer>> listPositions = new ArrayList<>();

            int iMax = list.size();
            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = positions.get(i);
                assertTrue("" + i, position.isValid());
                listPositions.add(position);
            }

            positions.addAllItems(j, Arrays.asList(-2, -1));
            assertEquals(input.size() + 2, list.size());

            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = listPositions.get(i);
                assertEquals("" + i, i < j ? i : i + 2, position.getIndex());
                assertTrue("" + i, position.isValidIndex());
                assertTrue("" + i, position.isValid());

                assertEquals("" + i, i < iMax, position.isValidPosition());
                if (i < iMax) {
                    assertEquals("" + i, list.get(i < j ? i : i + 2), position.get());
                    assertEquals("" + i, list.get(i < j ? i : i + 2), position.get(0));
                }
            }
        }
    }

    @Test
    public void remove() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

        int jMax = input.size();

        for (int j = 0; j < jMax; j++) {
            ArrayList<Integer> list = new ArrayList<>(input);
            PositionList<Integer> positions = new PositionList<>(list);
            ArrayList<ListPosition<Integer>> listPositions = new ArrayList<>();

            int iMax = list.size();
            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = positions.get(i);
                assertTrue("" + i, position.isValid());
                listPositions.add(position);
            }

            positions.removeItem(j);

            assertEquals(input.size() - 1, list.size());

            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = listPositions.get(i);
                assertEquals("" + i, i <= j ? i : i - 1, position.getIndex());
                assertTrue("" + i, position.isValidIndex());
                assertEquals("" + i, i != j, position.isValid());

                assertEquals("" + i, i != j && i < iMax, position.isValidPosition());
                if (i != j && i < iMax) {
                    assertEquals("" + i, list.get(i < j ? i : i - 1), position.get());
                    assertEquals("" + i, list.get(i < j ? i : i - 1), position.get(0));
                }
            }
        }
    }

    @Test
    public void testRemove() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

        int jMax = input.size();

        for (int j = 0; j < jMax; j++) {
            ArrayList<Integer> list = new ArrayList<>(input);
            PositionList<Integer> positions = new PositionList<>(list);
            ArrayList<ListPosition<Integer>> listPositions = new ArrayList<>();

            int iMax = list.size();
            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = positions.get(i);
                assertTrue("" + i, position.isValid());
                listPositions.add(position);
            }

            positions.removeItems(j, j + 1);

            assertEquals(input.size() - 1, list.size());

            for (int i = 0; i <= iMax; i++) {
                ListPosition<Integer> position = listPositions.get(i);
                assertEquals("" + i, i <= j ? i : i - 1, position.getIndex());
                assertTrue("" + i, position.isValidIndex());
                assertEquals("" + i, i != j, position.isValid());

                assertEquals("" + i, i != j && i < iMax, position.isValidPosition());
                if (i != j && i < iMax) {
                    assertEquals("" + i, list.get(i < j ? i : i - 1), position.get());
                    assertEquals("" + i, list.get(i < j ? i : i - 1), position.get(0));
                }
            }
        }
    }

    @Test
    public void test_posValidation1() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        thrown.expect(IndexOutOfBoundsException.class);
        ListPosition<Integer> position = positions.get(-1);
    }

    @Test
    public void test_posValidation2() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        thrown.expect(IndexOutOfBoundsException.class);
        ListPosition<Integer> position = positions.get(11);
    }

    @Test
    public void test_posValidation3() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        thrown.expect(IndexOutOfBoundsException.class);
        ListPosition<Integer> position = positions.get(0);
        position.get(-1);
    }

    @Test
    public void test_posValidation4() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        thrown.expect(IndexOutOfBoundsException.class);
        ListPosition<Integer> position = positions.get(0);
        position.get(11);
    }

    @Test
    public void test_posValidation5() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        thrown.expect(IndexOutOfBoundsException.class);
        ListPosition<Integer> position = positions.get(0);
        position.getPrevious();
    }

    @Test
    public void test_posValidation6() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        thrown.expect(IllegalStateException.class);
        ListPosition<Integer> position0 = positions.get(0);
        ListPosition<Integer> position1 = position0.getPosition(1);
        position0.remove(1);
        position1.get();
    }

    @Test
    public void test_posValidation7() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        thrown.expect(IndexOutOfBoundsException.class);
        ListPosition<Integer> position = positions.get(0);
        position.remove(0, 11);
    }

    @Test
    public void test_posValidation8() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        thrown.expect(IllegalArgumentException.class);
        ListPosition<Integer> position = positions.get(0);
        position.remove(10, 0);
    }

    @Test
    public void posGetPosition() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
        PositionList<Integer> positions = new PositionList<>(list);

        ListPosition<Integer> position0 = positions.get(1);
        assertTrue(position0.isPreviousValid());
        ListPosition<Integer> position1 = position0.getPrevious();
        assertEquals(9, (int) position1.get());
        assertFalse(position1.isPreviousValid());
    }

    @Test
    public void posSet() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        int i = -1;
        for (ListPosition<Integer> position : positions) {
            list2.add(position.get());
            position.set(i--);
        }

        List<Integer> expected = Arrays.asList(-1, -2, -3, -4, -5, -6, -7, -8, -9, -10);
        assertEquals(expected, list);
        assertEquals(input, list2);
    }

    @Test
    public void posSet1() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ArrayList<Integer> list2 = new ArrayList<>();

        int i = -1;
        for (ListPosition<Integer> position : positions) {
            list2.add(position.get());
            position.set(1, i--);
        }

        List<Integer> expected = Arrays.asList(9, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10);
        assertEquals(expected, list);
        List<Integer> expected1 = Arrays.asList(9, -1, -2, -3, -4, -5, -6, -7, -8, -9);
        assertEquals(expected1, list2);
    }

    @Test
    public void test_getFirst() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ListPosition<Integer> position = positions.getFirst();

        assertEquals(0, position.getIndex());
        assertTrue(position.isValidPosition());
    }

    @Test
    public void test_getFirstEmpty() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(Collections.emptyList());
        ListPosition<Integer> position = positions.getFirst();

        assertEquals(0, position.getIndex());
        assertFalse(position.isValidPosition());
    }

    @Test
    public void test_getLast() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ListPosition<Integer> position = positions.getLast();

        assertEquals(list.size() - 1, position.getIndex());
        assertTrue(position.isValidPosition());
    }

    @Test
    public void test_getLastEmpty() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(Collections.emptyList());
        ListPosition<Integer> position = positions.getLast();

        assertEquals(0, position.getIndex());
        assertFalse(position.isValidPosition());
    }

    @Test
    public void test_getEnd() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);
        ListPosition<Integer> position = positions.getEnd();

        assertEquals(list.size(), position.getIndex());
        assertTrue(position.isValidIndex());
        assertFalse(position.isValidPosition());
    }

    @Test
    public void test_getEndEmpty() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(Collections.emptyList());
        ListPosition<Integer> position = positions.getEnd();

        assertEquals(0, position.getIndex());
        assertFalse(position.isValidPosition());
    }

    @Test
    public void posSetEnd() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);

        ListPosition<Integer> position = positions.getLast().getNext();
        assertEquals(list.size(), position.getIndex());

        position.set(-1);

        List<Integer> expected = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1);
        assertEquals(expected, list);
    }

    @Test
    public void posGetOrNull() {
        List<Object> input = Arrays.asList(9, "8", 7, "6", 5, "4", 3, "2", 1, "0");
        ArrayList<Object> list = new ArrayList<>(input);
        PositionList<Object> positions = new PositionList<>(list);

        ListPosition<Object> position = positions.get(0);
        Integer i = position.getOrNull(Integer.class);
        assertEquals(9, (int) i);

        i = position.getOrNull(1, Integer.class);
        assertNull(i);

        String s = position.getOrNull(String.class);
        assertNull(s);

        s = position.getOrNull(1, String.class);
        assertEquals("8", s);
    }

    @Test
    public void posAddAll() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);

        ListPosition<Integer> position = positions.get(5);
        ListPosition<Integer> position1 = position.getPosition(3);

        position.addAll(Arrays.asList(-1, -2, -3));
        position1.addAll(Arrays.asList(-10, -20));
        assertEquals(input.size() + 5, position.size());
        assertEquals(input.size() + 5, position1.size());
        assertEquals(input.size() + 5, positions.size());

        List<Integer> expected = Arrays.asList(9, 8, 7, 6, 5, -1, -2, -3, 4, 3, 2, -10, -20, 1, 0);
        assertEquals(expected, list);

        assertFalse(position.isEmpty());
        positions.clear();
        assertTrue(position.isEmpty());
    }

    @Test
    public void posAppend() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);

        ListPosition<Integer> position = positions.get(5);
        ListPosition<Integer> position1 = positions.getEnd();

        position.append(-1);

        assertEquals(input.size() + 1, position1.getIndex());
        assertFalse(position1.isValidPosition());

        assertEquals(input.size() + 1, positions.size());

        List<Integer> expected = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1);
        assertEquals(expected, list);
    }

    @Test
    public void posSize() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);

        ListPosition<Integer> position = positions.get(5);
        ListPosition<Integer> position1 = position.getPosition(3);

        assertEquals(input.size(), position.size());
        assertEquals(input.size(), position1.size());
        assertEquals(input.size(), positions.size());
    }

    @Test
    public void posIsEmpty() {
        List<Integer> input = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        ArrayList<Integer> list = new ArrayList<>(input);
        PositionList<Integer> positions = new PositionList<>(list);

        ListPosition<Integer> position = positions.get(5);
        ListPosition<Integer> position1 = position.getPosition(3);

        assertEquals(input.size(), position.size());
        assertEquals(input.size(), position1.size());
        assertEquals(input.size(), positions.size());

        assertFalse(position.isEmpty());
        positions.clear();
        assertTrue(position.isEmpty());
    }

    @Test
    public void posIndexOf1() {
        List<Object> input = Arrays.asList(0, "1", 2, "3", 4, "5", 6, "7", 8, "9", 0, "1", 2, "3", 4, "5", 6, "7", 8, "9");
        ArrayList<Object> list = new ArrayList<>(input);
        PositionList<Object> positions = new PositionList<>(list);

        ListPosition<Object> position = positions.get(0);
        ListPosition<Object> index = position.indexOf("5");
        assertTrue(index.isValidPosition());
        assertEquals(5, index.getIndex());
        assertEquals("5", index.getOrNull(String.class));

        index = position.indexOf(6, "5");
        assertTrue(index.isValidPosition());
        assertEquals(15, index.getIndex());
        assertEquals("5", index.getOrNull(String.class));

        index = position.indexOf(6);
        assertTrue(index.isValidPosition());
        assertEquals(6, index.getIndex());
        assertEquals((Integer) 6, index.getOrNull(Integer.class));

        index = position.indexOf(7, 6);
        assertTrue(index.isValidPosition());
        assertEquals(16, index.getIndex());
        assertEquals((Integer) 6, index.getOrNull(Integer.class));

        // predicate search
        index = position.indexOf(p-> p.get() == (Integer)6);
        assertTrue(index.isValidPosition());
        assertEquals(6, index.getIndex());
        assertEquals((Integer) 6, index.getOrNull(Integer.class));

        index = position.indexOf(7, p-> p.get() == (Integer)6);
        assertTrue(index.isValidPosition());
        assertEquals(16, index.getIndex());
        assertEquals((Integer) 6, index.getOrNull(Integer.class));

        index = position.indexOf(17, p-> p.get() == (Integer)6);
        assertFalse(index.isValidPosition());
    }

    @Test
    public void posLastIndexOf1() {
        List<Object> input = Arrays.asList(0, "1", 2, "3", 4, "5", 6, "7", 8, "9", 0, "1", 2, "3", 4, "5", 6, "7", 8, "9");
        ArrayList<Object> list = new ArrayList<>(input);
        PositionList<Object> positions = new PositionList<>(list);

        ListPosition<Object> position = positions.get(20);
        ListPosition<Object> index = position.lastIndexOf(6);
        assertTrue(index.isValidPosition());
        assertEquals(16, index.getIndex());
        assertEquals((Integer) 6, index.getOrNull(Integer.class));

        index = position.lastIndexOf(-4, 6);
        assertTrue(index.isValidPosition());
        assertEquals(6, index.getIndex());
        assertEquals((Integer) 6, index.getOrNull(Integer.class));

        index = position.lastIndexOf("5");
        assertTrue(index.isValidPosition());
        assertEquals(15, index.getIndex());
        assertEquals("5", index.getOrNull(String.class));

        index = position.lastIndexOf(-5, "5");
        assertTrue(index.isValidPosition());
        assertEquals(5, index.getIndex());
        assertEquals("5", index.getOrNull(String.class));

        // predicate search
        index = position.lastIndexOf(p -> p.get() == "7");
        assertTrue(index.isValidPosition());
        assertEquals(17, index.getIndex());
        assertEquals("7", index.getOrNull(String.class));

        index = position.lastIndexOf(-3, p -> p.get() == "7");
        assertTrue(index.isValidPosition());
        assertEquals(7, index.getIndex());
        assertEquals("7", index.getOrNull(String.class));

        index = position.lastIndexOf(-13, p -> p.get() == "7");
        assertFalse(index.isValidPosition());
    }
}
