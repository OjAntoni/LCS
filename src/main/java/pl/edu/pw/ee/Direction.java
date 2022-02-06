package pl.edu.pw.ee;

public enum Direction {
    LEFT {
        @Override
        public String toString() {
            return "<";
        }
    },
    TOP {
        @Override
        public String toString() {
            return "^";
        }
    },
    CORNER {
        @Override
        public String toString() {
            return "\\";
        }
    };

}
