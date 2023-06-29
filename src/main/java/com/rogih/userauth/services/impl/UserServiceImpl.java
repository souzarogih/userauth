package com.rogih.userauth.services.impl;

import com.rogih.userauth.models.UserCourseModel;
import com.rogih.userauth.models.UserModel;
import com.rogih.userauth.repositories.UserCourseRepository;
import com.rogih.userauth.repositories.UserRepository;
import com.rogih.userauth.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCourseRepository userCourseRepository;


    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    @Override
    public void delete(UserModel userModel) {
        log.info("Processando a deleção de um usuário");
        List<UserCourseModel> userCourseModelList = userCourseRepository.findAllUserCourseIntoUser(userModel.getUserId());
        if(!userCourseModelList.isEmpty()){
            userCourseRepository.deleteAll(userCourseModelList);
            log.info("Deletando a lista de cursos associada ao usuário: {}", userCourseModelList);
        }
        userRepository.delete(userModel);
        log.info("Usuário {} deletado com sucesso.", userModel.getUserId());
    }

    @Override
    public void save(UserModel userModel) {
        userRepository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }
}
