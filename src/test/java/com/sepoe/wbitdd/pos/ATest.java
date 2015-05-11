package com.sepoe.wbitdd.pos;

import org.junit.Test;

/**
 * Class to ...
 *
 * @author lumiha
 * @since 11/05/15.
 */
public class ATest {
    @Test
    public void when_given_then() {
        A a = new A();

        a.meth(1);

    }

    static class A{
        public void meth(int i){
            if (sub(i)) return;

            System.out.printf("not null");
        }

        private boolean sub(final int i) {
            if(i == 0) {
                System.out.println("is 0");
                return true;
            }
            return false;
        }
    }
}
