
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Money {


	private Double	amount;
	private String	currency;

	public Money() {

	}


	private Money(Double amount, String currency) {
		super();
		this.amount = amount;
		this.currency = currency;
	}


	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public double getAmount() {
		return this.amount;
	}
	public void setAmount(final double amount) {
		this.amount = amount;
	}

	@NotBlank
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	public Money newMoney(final double amount2, final String currency2) {
		return new Money(amount2, currency2);
	}

	public Money add(final Money money) {
		return this.newMoney(money.getAmount() + this.amount, this.currency);
	}

	public Money substract(final Money money) {
		return this.newMoney(this.amount - money.getAmount(), this.currency);
	}

}
