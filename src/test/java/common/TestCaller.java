package common;

import lombok.RequiredArgsConstructor;
import rate.limiter.RateLimiter;

@RequiredArgsConstructor
public class TestCaller implements Runnable {

    private final String id;
    private final RateLimiter rateLimiter;
    private final int sleepMilliseconds;

    @Override
    public void run() {
        System.out.println(String.format("caller %s started", id));
        int total = 0; int throttled = 0; int success = 0;

        double throttledPercent = 0; double successPercent = 0;
        double totalPerSec = 0; double successPerSec = 0;

        long lastStatTime = System.currentTimeMillis();
        while (true) {
            total++;
            totalPerSec++;
            if (!rateLimiter.shouldAllow(MainOrchestrator.NAME_SPACE, id)) {
                System.out.println(String.format("%s Throttled. Current stats Total %s Throttled %s, Success %s", id, total, throttled, success));
                throttled++;
            } else {
                //System.out.println(String.format("%s Success. Current stats Total %s Throttled %s, Success %s", id, total, throttled, success));
                success++;
                successPerSec++;
            }

            if (System.currentTimeMillis() - lastStatTime > 1000) {
                throttledPercent = ((throttled * 1.0) / total) * 100;
                successPercent = ((success * 1.0) / total) * 100;

                //System.out.println(String.format("%s Current stats Total %s Throttled %s, Success %s", id, total, throttledPercent, successPercent));
                //System.out.println(String.format("%s Current stats TPS %s Success TPS %s", id, totalPerSec, successPerSec));
                totalPerSec = 0;
                successPerSec = 0;
                lastStatTime = System.currentTimeMillis();
            }
            try {
                Thread.sleep(sleepMilliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
