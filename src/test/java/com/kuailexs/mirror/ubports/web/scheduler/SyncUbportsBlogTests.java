package com.kuailexs.mirror.ubports.web.scheduler;

import com.kuailexs.kuailexs.KuailexsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author ：dhl
 * @Date ：Created in 14:53 2019/8/22
 * @Description：
 * @Modified By：
 * @Version:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KuailexsApplication.class )
public class SyncUbportsBlogTests extends SyncUbportsBlog{


    @Test
    public void onTimeSyncUbportsBlog_Test() {
        onTimeSyncUbportsBlog();
    }
}
