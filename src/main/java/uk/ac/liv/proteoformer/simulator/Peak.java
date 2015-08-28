
package uk.ac.liv.proteoformer.simulator;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 28-Aug-2015 16:39:17
 */
public class Peak {

    private final double mz;
    private final double intensity;

    Peak(double mz, double inten) {
        this.mz = mz;
        this.intensity = inten;
    }

    /**
     * @return the mz
     */
    public double getMz() {
        return mz;
    }

    /**
     * @return the intensity
     */
    public double getIntensity() {
        return intensity;
    }

}
