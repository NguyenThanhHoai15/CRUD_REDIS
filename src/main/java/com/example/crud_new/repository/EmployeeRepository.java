package com.example.crud_new.repository;

import com.example.crud_new.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class EmployeeRepository {

    private HashOperations hashOperations;//crud hash
    private RedisTemplate redisTemplate;
    private ListOperations listOperations;
    private SetOperations setOperations;

    public EmployeeRepository(RedisTemplate redisTemplate) {

//        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
//        this.listOperations = redisTemplate.opsForList();
        this.setOperations = redisTemplate.opsForSet();

    }

    public void saveEmployee(Employee employee){

//        hashOperations.put("EMPLOYEE", employee.getId(), employee);
        setOperations.add("EMPLOYEE", employee);
    }

    public Set<Employee> findAll(){

        //return hashOperations.values("EMPLOYEE");
        return setOperations.members("EMPLOYEE");
    }

    public Employee findById(Integer id){

//        return (Employee) hashOperations.get("EMPLOYEE", id);
        return (Employee) setOperations.intersect("EMPLOYEE", id);
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }

    public void delete(Integer id){
        setOperations.remove("EMPLOYEE", id);
//        hashOperations.delete("EMPLOYEE", id);
    }
}
