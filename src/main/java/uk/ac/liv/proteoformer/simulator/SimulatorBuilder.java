
package uk.ac.liv.proteoformer.simulator;

import gnu.trove.map.TDoubleDoubleMap;
import gnu.trove.map.TIntDoubleMap;
import gnu.trove.map.hash.TDoubleDoubleHashMap;
import gnu.trove.map.hash.TIntDoubleHashMap;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 28-Aug-2015 13:07:56
 */
public class SimulatorBuilder {

    private final String sequence;
    private final int isotopomerWidth;
    private final List<Isotopomer> isotopomerList;
    private final TDoubleDoubleMap peakMap;

    SimulatorBuilder(String seq, int isoWidth) {
        this.sequence = seq;
        this.isotopomerWidth = isoWidth;
        isotopomerList = new ArrayList<>();
        peakMap = new TDoubleDoubleHashMap();
        initial();
    }

    /**
     * @return the sequence
     */
    public String getSequence() {
        return sequence;
    }

    private void initial() {
        NormalDistribution nd = new NormalDistribution();
        BaseIsotopomer bIso = new BaseIsotopomer(this.sequence, this.isotopomerWidth);
        TIntDoubleMap chargeScaleMap = new TIntDoubleHashMap();
        int centralCharge = bIso.getCentralChargeState();
        int totalChargeState = bIso.getMaxChargeState() - 9;
        double step = 6.0 / totalChargeState;
        //calculate right side of the central charge state (inclusive) to 10+ charge
        double x = 0.0;
        for (int i = centralCharge; i > 8; i--) {
            double factor = nd.density(x);
            x += step;
            chargeScaleMap.put(i, factor);
        }

        //calculate left side of the central charge state (exclusive) to max charge
        x = step;
        for (int i = centralCharge + 1; i <= bIso.getMaxChargeState(); i++) {
            double factor = nd.density(x);
            x += step;
            chargeScaleMap.put(i, factor);
        }

        //build list of Isotopomer
        for (int charge : chargeScaleMap.keys()) {
            ChargedIsotopomer cIso = new ChargedIsotopomer(bIso, charge);
            isotopomerList.add(cIso);

            TDoubleDoubleMap tempPeakMap = cIso.getScaledPeakMap(chargeScaleMap.get(charge));

            for (double mz : tempPeakMap.keys()) {
                double intensity = peakMap.get(mz);
                if (intensity == peakMap.getNoEntryValue()) {
                    peakMap.put(mz, tempPeakMap.get(mz));
                }
                else {
                    peakMap.put(mz, intensity + tempPeakMap.get(mz));
                }
            }
        }
    }

    /**
     * @return the isotopomerWidth
     */
    public int getIsotopomerWidth() {
        return isotopomerWidth;
    }

    /**
     * @return the isotopomerList
     */
    public List<Isotopomer> getIsotopomerList() {
        return isotopomerList;
    }

    /**
     * @return the peakMap
     */
    public TDoubleDoubleMap getPeakMap() {
        return peakMap;
    }

}
