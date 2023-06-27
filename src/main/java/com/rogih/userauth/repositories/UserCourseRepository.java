package com.rogih.userauth.repositories;

import com.rogih.userauth.models.UserCourseModel;
import com.rogih.userauth.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {

    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);
}
