/*
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.varoplugin.varo.tasks;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class DailyTasksTest {

    @Test
    public void testGetNextRun() {
        Assertions.assertEquals(OffsetDateTime.parse("2000-01-02T00:00Z"),
                DailyTasks.getNextRun(OffsetDateTime.parse("2000-01-01T00:00Z"), 0));
        Assertions.assertEquals(OffsetDateTime.parse("2000-01-02T00:00Z"),
                DailyTasks.getNextRun(OffsetDateTime.parse("2000-01-01T12:00Z"), 0));
        Assertions.assertEquals(OffsetDateTime.parse("2000-01-04T00:00Z"),
                DailyTasks.getNextRun(OffsetDateTime.parse("2000-01-03T12:00Z"), 0));

        Assertions.assertEquals(OffsetDateTime.parse("2000-01-02T23:00Z"),
                DailyTasks.getNextRun(OffsetDateTime.parse("2000-01-01T23:00Z"), 23));
        Assertions.assertEquals(OffsetDateTime.parse("2000-01-02T23:00Z"),
                DailyTasks.getNextRun(OffsetDateTime.parse("2000-01-01T23:01Z"), 23));
        Assertions.assertEquals(OffsetDateTime.parse("2000-01-04T23:00Z"),
                DailyTasks.getNextRun(OffsetDateTime.parse("2000-01-03T23:01Z"), 23));

        Assertions.assertEquals(OffsetDateTime.parse("2000-01-01T23:00Z"),
                DailyTasks.getNextRun(OffsetDateTime.parse("2000-01-01T00:00Z"), 23));
        Assertions.assertEquals(OffsetDateTime.parse("2000-01-02T00:00Z"),
                DailyTasks.getNextRun(OffsetDateTime.parse("2000-01-01T23:00Z"), 0));
        Assertions.assertEquals(OffsetDateTime.parse("2000-01-01T02:00Z"),
                DailyTasks.getNextRun(OffsetDateTime.parse("2000-01-01T01:00Z"), 2));
    }

    @Test
    public void testGetCatchUp() {
        Assertions.assertEquals(0L,
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-02T12:00Z"), OffsetDateTime.parse("2000-01-02T00:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 0).getLeft());
        Assertions.assertEquals(new ImmutablePair<>(1L, OffsetDateTime.parse("2000-01-03T00:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-03T12:00Z"), OffsetDateTime.parse("2000-01-02T00:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 0));
        Assertions.assertEquals(new ImmutablePair<>(2L, OffsetDateTime.parse("2000-01-04T00:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-04T12:00Z"), OffsetDateTime.parse("2000-01-02T00:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 0));

        Assertions.assertEquals(0L,
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-02T12:00Z"), OffsetDateTime.parse("2000-01-01T23:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 23).getLeft());
        Assertions.assertEquals(new ImmutablePair<>(1L, OffsetDateTime.parse("2000-01-02T23:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-03T12:00Z"), OffsetDateTime.parse("2000-01-01T23:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 23));
        Assertions.assertEquals(new ImmutablePair<>(2L, OffsetDateTime.parse("2000-01-03T23:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-04T12:00Z"), OffsetDateTime.parse("2000-01-01T23:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 23));

        Assertions.assertEquals(0L,
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-02T12:00Z"), OffsetDateTime.parse("2000-01-02T00:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 13).getLeft());
        Assertions.assertEquals(new ImmutablePair<>(1L, OffsetDateTime.parse("2000-01-02T11:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-02T12:00Z"), OffsetDateTime.parse("2000-01-02T00:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 11));
        Assertions.assertEquals(new ImmutablePair<>(1L, OffsetDateTime.parse("2000-01-02T13:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-03T12:00Z"), OffsetDateTime.parse("2000-01-02T00:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 13));
        Assertions.assertEquals(new ImmutablePair<>(2L, OffsetDateTime.parse("2000-01-03T11:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-03T12:00Z"), OffsetDateTime.parse("2000-01-02T00:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 11));
        Assertions.assertEquals(new ImmutablePair<>(2L, OffsetDateTime.parse("2000-01-03T13:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-04T12:00Z"), OffsetDateTime.parse("2000-01-02T00:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 13));
        Assertions.assertEquals(new ImmutablePair<>(3L, OffsetDateTime.parse("2000-01-04T11:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-04T12:00Z"), OffsetDateTime.parse("2000-01-02T00:00Z"), OffsetDateTime.parse("2000-01-01T12:00Z"), 11));
    }

    @Test
    public void testGetCatchUpNull() {
        Assertions.assertEquals(0L,
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-01T13:00Z"), null, OffsetDateTime.parse("2000-01-01T12:00Z"), 0).getLeft());
        Assertions.assertEquals(new ImmutablePair<>(1L, OffsetDateTime.parse("2000-01-02T00:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-02T12:00Z"), null, OffsetDateTime.parse("2000-01-01T12:00Z"), 0));
        Assertions.assertEquals(new ImmutablePair<>(2L, OffsetDateTime.parse("2000-01-03T00:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-03T12:00Z"), null, OffsetDateTime.parse("2000-01-01T12:00Z"), 0));

        Assertions.assertEquals(0L,
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-01T13:00Z"), null, OffsetDateTime.parse("2000-01-01T12:00Z"), 23).getLeft());
        Assertions.assertEquals(new ImmutablePair<>(1L, OffsetDateTime.parse("2000-01-01T23:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-02T12:00Z"), null, OffsetDateTime.parse("2000-01-01T12:00Z"), 23));
        Assertions.assertEquals(new ImmutablePair<>(2L, OffsetDateTime.parse("2000-01-02T23:00Z")),
                DailyTasks.getCatchUp(OffsetDateTime.parse("2000-01-03T12:00Z"), null, OffsetDateTime.parse("2000-01-01T12:00Z"), 23));
    }
}
