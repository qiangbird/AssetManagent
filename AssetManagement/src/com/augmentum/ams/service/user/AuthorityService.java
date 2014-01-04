package com.augmentum.ams.service.user;

import com.augmentum.ams.model.user.Authority;

public interface AuthorityService {

    /**
     * @author Grylls.Xu
     * @time Oct 11, 2013 4:01:05 PM
     * @description TODO
     */
    void deleteAllAuthority();

    /**
     * @author Grylls.Xu
     * @time Oct 11, 2013 4:04:28 PM
     * @description TODO
     * @param permissionName
     */
    void saveAuthority(String authorityName);

    /**
     * @author Grylls.Xu
     * @time Oct 11, 2013 5:22:32 PM
     * @description TODO
     * @param authorityName
     * @return
     */
    Authority getAuthorityByName(String authorityName);

}
