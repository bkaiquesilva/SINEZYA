package com.edebelzaakso.sinezya.gravacao;

public class Oo {

    public static final int LQ_360P = 300000;
    public static final int MQ_360P = 700000;
    public static final int HQ_360P = 1000000;
    public static final int H_360P     = 360;
    public static final int W_360P      = 640;

    public static final int LQ_480P = 750000;
    public static final int MQ_480P = 1750000;
    public static final int HQ_480P = 2500000;
    public static final int H_480P     = 480;
    public static final int W_480P      = 640;

    public static final int LQ_720P = 1500000;
    public static final int MQ_720P = 3500000;
    public static final int HQ_720P = 5000000;
    public static final int H_720P     = 720;
    public static final int W_720P      = 1280;

    public static final int LQ_1080P = 2400000;
    public static final int MQ_1080P = 5600000;
    public static final int HQ_1080P = 8000000;
    public static final int H_1080P     = 1080;
    public static final int W_1080P      = 1920;

    public static final int LQ_1440P = 3000000;
    public static final int MQ_1440P = 7000000;
    public static final int HQ_1440P = 10000000;
    public static final int H_1440P     = 1440;
    public static final int W_1440P      = 2560;

    public static final int LQ_2160P = 12000000;
    public static final int MQ_2160P = 28000000;
    public static final int HQ_2160P = 40000000;
    public static final int H_2160P     = 2160;
    public static final int W_2160P      = 3840;

    public static final int FPS_30 = 30;
    public static final int FPS_20 = 20;
    public static final int FPS_24 = 24;
    public static final int FPS_60 = 60;

    public enum Uu {
        BA, ME, AU;
    }

    public enum Ijy {

        RES_360P(W_360P, H_360P, HQ_360P, MQ_360P, LQ_360P),
        RES_480P(W_480P, H_480P, HQ_480P, MQ_480P, LQ_480P),
        RES_720P(W_720P, H_720P, HQ_720P, MQ_720P, LQ_720P),
        RES_1080P(W_1080P, H_1080P, HQ_1080P, MQ_1080P, LQ_1080P),
        RES_1440P(W_1440P, H_1440P, HQ_1440P, MQ_1440P, LQ_1440P),
        RES_2160P(W_2160P, H_2160P, HQ_2160P, MQ_2160P, LQ_2160P);

        public int l;
        public int a;
        private final int ba;
        private final int me;
        private final int au;

        private Ijy(int l, int a, int au, int me, int ba) {
            this.l = l;
            this.a = a;
            this.au = au;
            this.me = me;
            this.ba = ba;
        }

        public int getKK(Uu uu) {
            int zz = au;
            switch (uu) {
                case AU:
                    zz = au;
                    break;
                case ME:
                    zz = me;
                    break;
                case BA:
                    zz = ba;
                    break;
            }
            return zz;
        }

    }
}