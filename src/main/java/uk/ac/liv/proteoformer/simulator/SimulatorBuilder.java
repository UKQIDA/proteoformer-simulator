
package uk.ac.liv.proteoformer.simulator;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 28-Aug-2015 13:07:56
 */
public class SimulatorBuilder {

    private final String sequence;

    SimulatorBuilder(String seq) {
        this.sequence = seq;
        initial();
    }

    /**
     * @return the sequence
     */
    public String getSequence() {
        return sequence;
    }

    private void initial() {
        
    }

}
