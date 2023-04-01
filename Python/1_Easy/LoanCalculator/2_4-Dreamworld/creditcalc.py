import math

principal = int(input("Enter the loan principal:\n"))
calc_type = input("What do you want to calculate?\n")

if calc_type == "m":
    month_pay = int(input("Enter the monthly payment:\n"))
    amount = math.ceil(principal / month_pay)
    if amount == 1:
        print("\nIt will take 1 month to repay the loan")
    else:
        print("\nIt will take %d months to repay the loan" % amount)
else:
    no_of_months = int(input("Enter the number of months:\n"))
    remainder = principal % no_of_months
    if remainder == 0:
        print("\nYour monthly payment = %d" % (principal / no_of_months))
    else:
        monthly_payment = int(math.ceil(principal / no_of_months))
        last_payment = int(principal - (monthly_payment * (no_of_months - 1)))
        print("\nYour monthly payment = %d and the last payment = %d." % (monthly_payment, last_payment))
