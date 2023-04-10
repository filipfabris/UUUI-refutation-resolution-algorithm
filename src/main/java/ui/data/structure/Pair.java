package ui.data.structure;

import java.util.Objects;

public class Pair {

    public  LogicPremises first;
    public LogicPremises second;

    public Pair(LogicPremises first, LogicPremises second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {

    }

    public LogicPremises getFirst() {
        return first;
    }

    public LogicPremises getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "{" + first + ", " + second + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair pair)) return false;
        return (Objects.equals( first, pair.first ) && Objects.equals( second, pair.second ) || (Objects.equals( first, pair.second ) && Objects.equals( second, pair.first )));
    }

    @Override
    public int hashCode() {
        return Objects.hash( first, second );
    }
}
