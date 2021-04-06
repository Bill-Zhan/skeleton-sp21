package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gh2.GuitarHeroLite.CONCERT_A;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static Map<String, GuitarString> GUITAR_STR_MAP = new HashMap<>();
    public static final double CONCERT_BASE = 440.0;


    public static void main(String[] args) {
        /* Instantiate CONCERT_MAP */
        String k;
        Double concert;
        Double ith;
        GuitarString gstr;

        for (int i = 0; i < KEYBOARD.length(); i++) {
            k = String.valueOf(KEYBOARD.charAt(i));
            ith = (double) i;
            concert = CONCERT_BASE * Math.pow(2.0, (ith-24.0) / 12.0);
            gstr = new GuitarString(concert);
            GUITAR_STR_MAP.put(k, gstr);
        }
        /* create guitar strings map, for all concert */

        GuitarString currGStr;
        String key;
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                key = String.valueOf(StdDraw.nextKeyTyped());
                if (!GUITAR_STR_MAP.containsKey(key)) {
                    continue;
                }
                currGStr = GUITAR_STR_MAP.get(key);
                currGStr.pluck();
                GUITAR_STR_MAP.put(key, currGStr);
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (GuitarString gs : GUITAR_STR_MAP.values()) {
                sample += gs.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString gs : GUITAR_STR_MAP.values()) {
                gs.tic();
            }
        }
    }
}

