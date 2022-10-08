package com.eshanit.jwtdemo.service;

import com.eshanit.jwtdemo.controller.RoleController;
import com.eshanit.jwtdemo.entity.RoleEntity;
import com.eshanit.jwtdemo.entity.UserEntity;
import com.eshanit.jwtdemo.model.RoleModel;
import com.eshanit.jwtdemo.model.UserModel;
import com.eshanit.jwtdemo.repository.RoleRepository;
import com.eshanit.jwtdemo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserModel register(UserModel userModel){
       UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userModel,userEntity); // it does not do a deep copy

        Set<RoleEntity> roleEntities = new HashSet<>();
        // Fetch every role from DB based on role id and then set this role to user entity roles
        for(RoleModel rm: userModel.getRoles()){
            Optional<RoleEntity> optRole = roleRepository.findById(rm.getId());
            if(optRole.isPresent()){
                roleEntities.add(optRole.get());
            }
        }
        userEntity.setRoles(roleEntities);
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity =  userRepository.save(userEntity);
        BeanUtils.copyProperties(userEntity,userModel);

        // Convert Role Entities to Role Model
        Set<RoleModel> roleModels = new HashSet<>();
        RoleModel rm=null;
        for(RoleEntity re: userEntity.getRoles()){
            rm = new RoleModel();
            rm.setRoleName(re.getRoleName());
            rm.setId(re.getId());
            roleModels.add(rm);
        }
        userModel.setRoles(roleModels);
        return userModel;
    }

    // this method actually does the validation for user existence
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       /* if(username.equals("rajavelu")){ // here you can make a DB Call with help of Repository and do the validation
            return new User("rajavelu", "secret", new ArrayList<>()); // Assume these are return from Database upon success
        } else {
            throw new UsernameNotFoundException("user does not exist!");
        }*/
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            throw new UsernameNotFoundException("user does not exist!");
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userEntity,userModel);
        // Convert Role Entities to Role Model
        Set<RoleModel> roleModels = new HashSet<>();
        RoleModel rm=null;
        for(RoleEntity re: userEntity.getRoles()){
            rm = new RoleModel();
            rm.setRoleName(re.getRoleName());
            rm.setId(re.getId());
            roleModels.add(rm);
        }
        userModel.setRoles(roleModels);
        return userModel;
    }
}
