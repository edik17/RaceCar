package esame.unicam.cs.mp.racecar.model;


import java.util.List;
import java.util.Optional;

/**
 * This is a rule aggregating a list of rules. The rule is applicable with given parameters if at least one rule is
 * applicable and the state is determined by the first rule that can be applied.
 *
 * @param <S> type of race car states.
 * */
public class ApplyFirst<S> implements Rule<S> {

    private final List<Rule<S>> rules;

    /**
     * Creates a rule from the given array of rules.
     *
     * @param rules an array of rules.
     * */
    @SafeVarargs
    public ApplyFirst(Rule<S> ... rules) {
        this(List.of(rules));
    }

    /**
     * Create a rule from the given list of rules.
     *
     * @param rules an array of rules.
     * */
    public ApplyFirst(List<Rule<S>> rules) {
        this.rules = rules;
    }

    @Override
    public Optional<S> apply(S carStatus, List<S> neighboursStatus) {
        return this.rules
                .stream()
                .map(r -> r.apply(carStatus, neighboursStatus))
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(Optional.empty());
    }
}
