
package uk.ac.liv.proteoformer.simulator;

import gnu.trove.map.TDoubleDoubleMap;
import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.hash.TDoubleDoubleHashMap;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 28-Aug-2015 16:28:16
 */
public class BaseIsotopomer implements Isotopomer {

    private final String sequence;
    private final TDoubleDoubleMap peakMap;
    private final NormalDistribution nd = new NormalDistribution();
    private final int width;

    BaseIsotopomer(String seq, int wid) {
        this.sequence = seq;
        this.width = wid;
        this.peakMap = new TDoubleDoubleHashMap();
        double averageMass = calculateAverageMass(this.sequence);
        if (width < 2) {
            throw new IllegalArgumentException("The width of isotopomer window must bigger than 2!\n");
        }
        // assuming the x-axis range of normal distribution from -3 to 3
        double step = 6.0 / (this.width - 1);

        // calculates left side of the peaks including the average mass
        double x = 0.0;
        for (int i = 0; i < width / 2; i++) {
            double mass = averageMass - i;
            double intensity = nd.density(x);
            x -= step;
            peakMap.put(mass, intensity);
        }

        // calculate right side of the peaks excluding the average mass
        x = step; // reset x
        for (int i = 0; i < width / 2; i++) {
            double mass = averageMass + i + 1;
            double intensity = nd.density(x);
            x += step;
            peakMap.put(mass, intensity);
        }
    }

    BaseIsotopomer(String seq) {
        this(seq, 3);
    }

    @Override
    public String getSequence() {
        return this.sequence;
    }

    @Override
    public TDoubleDoubleMap getPeakMap() {
        return peakMap;
    }

    private double calculateAverageMass(String seq) {
        double aveM = 0.0;
        TObjectDoubleMap<String> aaMap = AAMap.getAaMap();
        for (int i = 0; i < seq.length(); i++) {
            String aa = String.valueOf(seq.charAt(i));
            if (aaMap.get(aa) != aaMap.getNoEntryValue()) {
                aveM += aaMap.get(aa);
            }
            else {
                throw new IllegalArgumentException("The input sequcne contain unrecognised symbol!\n");
            }
        }

        return aveM;
    }

}
