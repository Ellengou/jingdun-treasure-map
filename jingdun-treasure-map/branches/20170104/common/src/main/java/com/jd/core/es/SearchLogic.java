package com.jd.core.es;

/**
 * Created by ylc on 2015/12/24.
 */
public enum SearchLogic {
    MUST {
        public String toString() {
            return "must";
        }
    },
    SHOULD {
        public String toString() {
            return "should";
        }
    },
    MUSTNOT {
        public String toString() {
            return "mustnot";
        }
    };

    private SearchLogic() {
    }
}
