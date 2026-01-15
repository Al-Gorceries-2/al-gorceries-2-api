package com.algorceries.api.service.impl;

import java.util.List;
import java.util.function.BiConsumer;
import org.springframework.stereotype.Service;
import com.algorceries.api.entity.Household;
import com.algorceries.api.entity.User;
import com.algorceries.api.repository.HouseholdRepository;
import com.algorceries.api.service.IHouseholdService;
import com.algorceries.api.service.IUserService;
import io.vavr.control.Option;
import io.vavr.control.Try;
import jakarta.persistence.EntityNotFoundException;

@Service
public class HouseholdService implements IHouseholdService {

    private final HouseholdRepository householdRepository;
    private final IUserService userService;

    public HouseholdService(HouseholdRepository householdRepository, IUserService userService) {
        this.householdRepository = householdRepository;
        this.userService = userService;
    }

    @Override
    public Option<Household> findById(String id) {
        return householdRepository.findByIdOption(id);
    }

    @Override
    public Household create(Household household, String userId) {
        Try<User> user = userService.findById(userId)
            .toTry(() -> new EntityNotFoundException("User with id %s not found".formatted(userId)));

        return user.map(u -> {
            household.getUsers().add(u);
            u.setHousehold(household);
            return household;
        }).andThen(householdRepository::save).get();
    }

    @Override
    public Try<Household> addUser(String householdId, String userId) {
        return changeUserHousehold(householdId, userId, List::add, (h, u) -> u.setHousehold(h));
    }

    @Override
    public Try<Household> removeUser(String householdId, String userId) {
        return changeUserHousehold(householdId, userId, List::remove, (h, u) -> u.setHousehold(null));
    }

    private Try<Household> changeUserHousehold(String householdId, String userId, BiConsumer<List<User>, User> userListOperation, BiConsumer<Household, User> userOperation) {
        Try<Household> household = householdRepository.findByIdOption(householdId)
            .toTry(() -> new EntityNotFoundException("Household with id %s not found".formatted(householdId)));

        Try<User> user = userService.findById(userId)
            .toTry(() -> new EntityNotFoundException("User with id %s not found".formatted(userId)));

        return household.map(h -> {
            userListOperation.accept(h.getUsers(), user.get());
            userOperation.accept(h, user.get());
            return h;
        }).andThen(householdRepository::save);
    }
}
