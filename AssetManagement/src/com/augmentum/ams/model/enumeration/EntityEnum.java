package com.augmentum.ams.model.enumeration;

/**
 * <p>Description: the enum about entity</p>
 * <p>Date: 2014.02.17</p>
 * <p>Company: Augmentum</p> 
 * @author John Li
 *
 */
public enum EntityEnum {

    QunshuoShanghai {

        @Override
        public String toString() {
            return "群硕上海";
        }
        
    },
    QunshuoBeijing {

        @Override
        public String toString() {
            return "群硕北京";
        }
    },
    QunshuoWuhan {

        @Override
        public String toString() {
            return "群硕武汉";
        }
        
    },
    QunshuoYangzhou {

        @Override
        public String toString() {
            return "群硕扬州";
        }
        
    }
}
