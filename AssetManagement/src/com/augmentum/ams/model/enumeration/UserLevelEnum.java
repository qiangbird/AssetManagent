/**
 * 
 */
package com.augmentum.ams.model.enumeration;

/**
 * @author Grylls.Xu
 * @time Oct 16, 2013 7:10:18 PM
 */
public enum UserLevelEnum {

    EXT_MANAGER("ExtManager"),
    MANAGER("Manager"),
    SR_MANAGER("SrManager"),
    DIRECTOR("Director"),
    SR_DIRECTOR("SrDirector"),
    VP("VP");

    private String level;

    private UserLevelEnum(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public static boolean hasLevel(String level) {
        boolean hasLevel = false;

        for (UserLevelEnum userLevel : UserLevelEnum.values()) {
            if (userLevel.getLevel().equals(level)) {
                hasLevel = true;
                break;
            }
        }
        return hasLevel;
    }
}
