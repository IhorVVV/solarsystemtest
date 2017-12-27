package com.ihor.solarsystem.network;

public class NetworkContract {

    private NetworkContract() {
//        no instance
    }

    static final class Base {

        static final String ENDPOINT = "http://54.202.34.250:3000/page/";

        static final int TIMEOUT = 30;

        private Base() {
//            no instance
        }
    }

    public static final class HttpStatus {

        public static final int PAGE_NOT_FOUND = 404;

        private HttpStatus() {
            //no instance
        }
    }

}
