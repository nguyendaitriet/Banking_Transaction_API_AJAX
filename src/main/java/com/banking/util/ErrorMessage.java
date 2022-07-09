package com.banking.util;

public class ErrorMessage {
    public static final String DUPLICATE_EMAIL = "Email address exists.";
    public static final String DUPLICATE_PHONE = "Phone number exists.";

    public static final String EMPTY_AMOUNT = "Transaction amount must NOT be empty.";
    public static final String INVALID_AMOUNT_FORMAT = "Transaction amount contains digits only and has no digit to the right of the decimal point.";
    public static final String MAX_AMOUNT_LENGTH = "Max character of transaction amount is 12.";
    public static final String MINIMUM_TRANSACTION_AMOUNT = "Transaction amount must NOT be LESS than 100.";
    public static final String MAXIMUM_TRANSACTION_AMOUNT = "Transaction amount must NOT be GREATER than 50,000,000.";
    public static final String MAXIMUM_WITHDRAW_AMOUNT = "Balance is not enough for this transaction.";


    public static final String SENDER_NOT_EMPTY = "Sender ID must NOT be empty.";
    public static final String RECIPIENT_NOT_EMPTY = "Recipient ID must NOT be empty.";
    public static final String EMPTY_TRANSFER_AMOUNT = "Transfer amount must NOT be empty.";
    public static final String SENDER_NOT_EXIST = "Sender ID doesn't exist.";
    public static final String RECIPIENT_NOT_EXIST = "Recipient ID doesn't exist.";
    public static final String DUPLICATE_SENDER = "Sender and recipient must NOT be the same.";



}
