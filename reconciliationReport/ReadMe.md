
## Problem Statement:
You are building a simplified internal ledger reconciliation tool. Stripe receives transaction logs from multiple payment processors. Sometimes duplicate transactions, refunds, and malformed records occur.
You need to process a list of transaction records and produce a reconciliation report.

## Input

You are given a list of transaction strings.

Each transaction follows this format:
## TYPE|transactionId|amount|currency
Where:

TYPE can be:
PAYMENT
REFUND
CHARGEBACK
transactionId is a string
amount is positive integer
currency is a string (USD, INR, EUR, etc.)

## Example:
PAYMENT|txn1|100|USD
PAYMENT|txn2|200|USD
REFUND|txn1|50|USD
CHARGEBACK|txn2|200|USD
PAYMENT|txn1|100|USD
INVALID_RECORD
PAYMENT|txn3|-100|USD

Goal

Write a method:
## ReconciliationReport reconcile(List<String> logs)

Required Output
Your method should return a report object containing:

1. Valid Transaction Count

Number of successfully parsed valid records.

2. Invalid Record Count

Invalid means:

malformed input
missing fields
negative amount
unsupported type

3. Duplicate Transaction IDs

Duplicate means:

Same TYPE + transactionId + amount + currency already seen.

4. Final Net Balance Per Currency

Rules:

PAYMENT → add amount
REFUND → subtract amount
CHARGEBACK → subtract amount

Example: USD = 50

5. Suspicious Transaction IDs

A transactionId is suspicious if:

Total refunds + chargebacks exceed payment amount.

Example:
txn1 payment = 100
refund = 120
Suspicious.

Constraints
Input size <= 10,000 records
Focus on correctness and readability
Optimization is NOT primary



## I/P: "PAYMENT|txn1|100|USD", "PAYMENT|txn2|200|USD", "REFUND|txn1|50|USD", "CHARGEBACK|txn2|200|USD", "PAYMENT|txn1|100|USD", "INVALID_RECORD", "PAYMENT|txn3|-100|USD", "REFUND|txn1|1000|USD"
## O/P: ReconciliationReport{validTransactionCounter=5, inValidTransactionCounter=3, duplicateTransactionIdList=[txn1], currencyToNetBalanceMap={USD=-950}, suspiciousTransactionIdList=[txn1]}

