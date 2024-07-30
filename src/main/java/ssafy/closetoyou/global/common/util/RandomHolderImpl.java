package ssafy.closetoyou.global.common.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomHolderImpl implements RandomHolder{

    @Override
    public int getRandomEmailAuthenticateCode() {
        return (int)(Math.random() * (90000)) + 100000;
    }
}
