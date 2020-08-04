package com.simon.tools.oracle.rollout;

import com.simon.tools.services.GenTask;
import org.springframework.stereotype.Repository;

public interface IGenerator {
    void take(GenTask task) throws InterruptedException;
}
