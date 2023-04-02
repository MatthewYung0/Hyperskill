import math
import argparse
from sys import argv


def parse_arguments():
    parser = argparse.ArgumentParser(description='Loan calculator')
    parser.add_argument("--type", help='Specify type of loan: diff or annuity')
    parser.add_argument("--principal", type=float, help='Value of loan principal')
    parser.add_argument("--periods", type=int, help='Loan duration, in months')
    parser.add_argument("--interest", type=float, help='Monthly interest')
    parser.add_argument("--payments", type=float, help='Value of each payment made. Annuity only')
    return parser.parse_args()


def check_arguments(arguments_):
    if len(argv) < 5:
        print("Incorrect parameters: fewer than 4 arguments filled")
        print(len(argv))
        print(argv)
        quit()
    if arguments_.type is None:
        print("Incorrect parameters: no type")
        quit()
    if arguments_.principal is not None:
        if arguments_.principal < 0:
            print("Incorrect parameters: negative principal")
            quit()
    if arguments_.periods is not None:
        if arguments_.periods < 0:
            print("Incorrect parameters: negative periods")
            quit()
    if arguments_.interest is None or arguments_.interest < 0:
        print("Incorrect parameters: missing or negative interest")
        quit()
    if arguments_.payments is not None:
        if arguments_.payments < 0:
            print("Incorrect parameters: negative payments")
            quit()
    if arguments_.type == "diff":
        if arguments_.payments is not None:
            print("Incorrect parameters: diff/payments illegal combo")
            quit()
    return arguments_.type, arguments_.interest, arguments_.payments, arguments_.periods, arguments_.principal


def cal_diff(principal_, interest_, periods_):
    month = 0
    total_paid = 0
    while month < periods_:
        month += 1
        diff = math.ceil(principal_ / periods_ + interest_ * (principal_ - (principal_ * (month - 1)) / periods_))
        total_paid += diff
        print(f"Month {month}: payment is {diff}")
    overpayment = total_paid - principal_
    print(f"\nOverpayment = %d" % overpayment)


def cal_time(principal_, monthly_payment_, nominal_interest_):
    months = math.ceil(math.log((monthly_payment_ / (monthly_payment_ - nominal_interest_ * principal_)), 1 + nominal_interest_))
    time = divmod(months, 12)
    if time[0] == 0:
        print("It will take %d months to repay this loan!" % time[1])
    elif time[1] == 0:
        print("It will take %d years to repay this loan!" % time[0])
    else:
        print("It will take %d years and %d months to repay this loan!" % (time[0], time[1]))
    overpayment = monthly_payment_ * months - principal_
    print("Overpayment = %d" % overpayment)


def cal_monthly_payment(principal_, num_periods_, nominal_interest_):
    upper_calculation = nominal_interest_ * pow((nominal_interest_ + 1), num_periods_)
    lower_calculation = pow((nominal_interest_ + 1), num_periods_) - 1
    monthly_payments = math.ceil(principal_ * (upper_calculation / lower_calculation))
    print("Your monthly payment = %d!\n" % monthly_payments)
    overpayment = monthly_payments * num_periods_ - principal_
    print("Overpayment = %d" % overpayment)


def cal_loan_principal(annuity_payment_, num_periods_, nominal_interest_):
    upper_calculation = nominal_interest_ * pow((1 + nominal_interest_), num_periods_)
    lower_calculation = pow((1 + nominal_interest_), num_periods_) - 1
    loan_principal = float(annuity_payment_ / (upper_calculation / lower_calculation))
    print("Your loan principal = %d!" % loan_principal)
    overpayment = annuity_payment_ * num_periods_ - loan_principal
    print("Overpayment = %d" % overpayment)


def main():
    type_selected, interest, payments, periods, principal = check_arguments(parse_arguments())
    nominal_interest = (interest / 100) / 12
    if type_selected == "diff":
        cal_diff(principal, nominal_interest, periods)
    if type_selected == "annuity":
        if periods is None:
            cal_time(principal, payments, nominal_interest)
        elif payments is None:
            cal_monthly_payment(principal, periods, nominal_interest)
        else:
            cal_loan_principal(payments, periods, nominal_interest)


if __name__ == "__main__":
    main()
