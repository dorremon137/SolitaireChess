package backtracking;/*
 * backtracking.Backtracker.java
 *
 * This file comes from the backtracking lab. It should be useful
 * in this project. A second method has been added that you should
 * implement.
 */

import model.characters.Character;
import model.SoltrChessModel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.SoltrChessModel.CHARACTERS;

/**
 * This class represents the classic recursive backtracking algorithm.
 * It has a solver that can take a valid configuration and return a
 * solution, if one exists.
 * 
 * @author sps (Sean Strout @ RIT CS)
 * @author jeh (James Heliotis @ RIT CS)
 */
public class Backtracker {
    
    /**
     * Try find a solution, if one exists, for a given configuration.
     * 
     * @param config A valid configuration
     * @return A solution config, or null if no solution
     */
    public Optional<Configuration> solve(Configuration config) {
        if (config.isGoal()) {
            return Optional.of(config);
        } else {
            for (Configuration child : config.getSuccessors()) {
                if (child.isValid()) {
                    Optional<Configuration> sol = solve(child);
                    if (sol.isPresent()) {
                        return sol;
                    }
                }
            }

        } 
        return Optional.empty();
    }

    /**
     * Find a goal configuration if it exists, and how to get there.
     * @param current the starting configuration
     * @return a list of configurations to get to a goal configuration.
     *         If there are none, return null.
     */
    public List< Configuration > solveWithPath( Configuration current ) {
        List<Configuration> pathList = new ArrayList<>();
        if (current.isGoal()){
            return pathList;
        } else {
            if (!this.solve(current).equals(Optional.empty())){
                for (Configuration c : current.getSuccessors()){
                    if (!this.solve(c).equals(Optional.empty())){
                        pathList.add(c);
                        pathList.addAll(this.solveWithPath(c));
                        break;
                    }
                }
            }
        }
        return pathList;
    }


}
