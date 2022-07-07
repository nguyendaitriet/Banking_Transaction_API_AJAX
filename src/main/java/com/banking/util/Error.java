package com.banking.util;

import org.springframework.validation.ObjectError;

public class Error {
    public static final ObjectError WRONG_ID = new ObjectError("wrongId", "Customer ID doesn't exist!");
    public static final ObjectError NOT_ENOUGH_BALANCE = new ObjectError("notEnoughBalance", "Balance is not enough for this transaction.");
    public static final ObjectError DUPLICATE_TRANSFER_CUSTOMER = new ObjectError("duplicateCustomer", "Sender and recipient must NOT be the same.");

}
