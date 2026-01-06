package com.algorceries.api.service;

import com.algorceries.api.entity.Household;
import io.vavr.control.Option;
import io.vavr.control.Try;

public interface IHouseholdService {
    Option<Household> findById(String id);
    Household save(Household household);
    Try<Household> addUser(String householdId, String userId);
    Try<Household> removeUser(String householdId, String userId);
}
