package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); //эта штука магическим образом ищет по имени пользователя без реализации
    //под капотом вызывается метод invoke из JpaRepository
    //посмотреть, как это реализовано, какие похожие кейсы использования есть
}