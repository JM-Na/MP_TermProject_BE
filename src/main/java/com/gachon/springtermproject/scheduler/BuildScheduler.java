package com.gachon.springtermproject.scheduler;


import com.gachon.springtermproject.service.PeriodicBuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class BuildScheduler {
    private PeriodicBuildService periodicBuildService;

    // 매주 토요일 새벽 3시에 자동으로 시작하는 스케쥴러
    @Scheduled(cron = "0 0 3 * * 6")
    public void autoUpdate() throws Exception {
        //periodicBuildService.setEventByDate();
    }
}
