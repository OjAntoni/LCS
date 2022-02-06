package pl.edu.pw.ee;

import java.nio.charset.StandardCharsets;

class LongestCommonSubsequence {
    private final Elem[][] table;
    private final byte[] firstString;
    private final byte[] secondString;
    private byte[] result;
    private Point[] road;

    private static class Elem {
        int value;
        Direction direction;

        Elem() {
        }

        Elem(int value, Direction direction) {
            this.value = value;
            this.direction = direction;
        }
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
    }

    public LongestCommonSubsequence(String firstStr, String secondStr) {
        if (firstStr == null || secondStr == null) {
            throw new IllegalArgumentException("one of the arguments is null");
        }

        firstString = firstStr.getBytes(StandardCharsets.UTF_8);
        secondString = secondStr.getBytes(StandardCharsets.UTF_8);

        table = new Elem[firstStr.length() + 1][secondStr.length() + 1];
        for (int i = 0; i < table.length; i++) {
            table[i][0] = new Elem();
        }
        for (int i = 0; i < table[0].length; i++) {
            table[0][i] = new Elem();
        }

    }

    public String findLCS() {
        for (int i = 1; i < table.length; i++) {
            for (int j = 1; j < table[0].length; j++) {
                if (firstString[i - 1] != secondString[j - 1]) {
                    int topValue = table[i - 1][j].value;
                    int leftValue = table[i][j - 1].value;
                    table[i][j] = topValue >= leftValue ? new Elem(topValue, Direction.TOP) : new Elem(leftValue, Direction.LEFT);
                } else {
                    table[i][j] = new Elem(table[i - 1][j - 1].value + 1, Direction.CORNER);
                }
            }
        }

        int i = table.length - 1;
        int j = table[0].length - 1;
        byte[] lcs = new byte[table[i][j].value];
        Point[] roadTemp = new Point[table[i].length + table.length];
        int k = 0;
        int cur = 0;

        while (i > 0 && j > 0) {
            roadTemp[roadTemp.length - 1 - cur] = new Point(i, j);
            switch (table[i][j].direction) {
                case TOP:
                    i--;
                    break;
                case LEFT:
                    j--;
                    break;
                case CORNER:
                    lcs[lcs.length - k - 1] = firstString[i - 1];
                    k++;
                    i--;
                    j--;
                    break;
            }
            cur++;
        }
        result = lcs;
        road = new Point[cur];
        System.arraycopy(roadTemp, roadTemp.length - cur, road, 0, cur);
        return new String(lcs);
    }

    public void display() {
        StringBuilder format = new StringBuilder();
        for (int i = 0; i < table[0].length + 1; i++) {
            format.append("%").append(5).append("s ");
        }
        format.append("\n");

        Object[] highRow = new Object[table[0].length + 1];
        highRow[0] = " ";
        highRow[1] = " ";
        for (int i = 0; i < secondString.length; i++) {
            if (secondString[i] == '\n') {
                highRow[i + 2] = "\\n ";
            } else if (secondString[i] == '\t') {
                highRow[i + 2] = "\\t";
            } else {
                highRow[i + 2] = (char) secondString[i] + "  ";
            }
        }

        Object[] leftWord = new Object[table.length + 1];
        leftWord[0] = " ";
        for (int i = 0; i < firstString.length; i++) {
            leftWord[i + 1] = (char) firstString[i];
        }

        Object[] objects = new Object[table[0].length + 1];
        System.out.printf(format.toString(), highRow);
        for (int i = 0; i < table.length; i++) {
            objects[0] = leftWord[i];
            for (int j = 0; j < table[0].length; j++) {
                boolean isInRoad = false;
                for (Point point : road) {
                    if (point.x == i && point.y == j) {
                        objects[j + 1] = " " + table[i][j].value + "(" + table[i][j].direction + ")";
                        isInRoad = true;
                        break;
                    }
                }
                if (!isInRoad) {
                    objects[j + 1] = table[i][j].value + "  ";
                }
            }
            System.out.printf(format.toString(), objects);
            objects = new Object[table[0].length + 1];
        }
    }


}
