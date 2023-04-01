import math

loan_principal_msg = "Enter the loan principal:\n"
num_of_periods_msg = "Enter the number of periods:\n"
loan_interest_msg = "Enter the loan interest:\n"
monthly_payment_msg = "Enter the monthly payment:\n"
annuity_payment_msg = "Enter the annuity payment:\n"

calc_type = input("What do you want to calculate?\n"
                  "type \"n\" for number of monthly payments,\n"
                  "type \"a\" for annuity monthly payment amount,\n"
                  "type \"p\" for loan principal:\n")


def cal_time(principal_, monthly_payment_, nominal_interest_):
    months = math.ceil(math.log((monthly_payment_ / (monthly_payment_ - nominal_interest_ * principal_)), 1 + nominal_interest_))
    time = divmod(months, 12)
    if time[0] == 0:
        print("It will take %d months to repay this loan!" % time[1])
    elif time[1] == 0:
        print("It will take %d years to repay this loan!" % time[0])
    else:
        print("It will take %d years and %d months to repay this loan!" % (time[0], time[1]))


def cal_monthly_payment(principal_, num_periods_, nominal_interest_):
    upper_calculation = nominal_interest_ * pow((nominal_interest_ + 1), num_periods_)
    lower_calculation = pow((nominal_interest_ + 1), num_periods_) - 1
    monthly_payments = math.ceil(principal_ * (upper_calculation / lower_calculation))
    print("Your monthly payment = %d!" % monthly_payments)


def cal_loan_principal(annuity_payment_, num_periods_, interest_):
    upper_calculation = interest_ * pow((1 + interest_), num_periods_)
    lower_calculation = pow((1 + nominal_interest), num_periods_) - 1
    loan_principal = float(annuity_payment_ / (upper_calculation / lower_calculation))
    print("Your loan principal = %d!" % loan_principal)


if calc_type == "n":
    principal = int(input(loan_principal_msg))
    monthly_payment = int(input(monthly_payment_msg))
    interest = float(input(loan_interest_msg))
    nominal_interest = (interest / 100) / 12
    cal_time(principal, monthly_payment, nominal_interest)
elif calc_type == "a":
    principal = int(input(loan_principal_msg))
    num_periods = int(input(num_of_periods_msg))
    interest = float(input(loan_interest_msg))
    nominal_interest = (interest / 100) / 12
    cal_monthly_payment(principal, num_periods, nominal_interest)
else:
    annuity_payment = float(input(annuity_payment_msg))
    num_periods = int(input(num_of_periods_msg))
    interest = float(input(loan_interest_msg))
    nominal_interest = (interest / 100) / 12
    cal_loan_principal(annuity_payment, num_periods, nominal_interest)
