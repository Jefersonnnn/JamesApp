package com.jm.jamesapp.services.interfaces;

import com.jm.jamesapp.models.UserModel;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService extends IBaseService<UserModel>{
    UserDetails findByUsername(String username);
}
