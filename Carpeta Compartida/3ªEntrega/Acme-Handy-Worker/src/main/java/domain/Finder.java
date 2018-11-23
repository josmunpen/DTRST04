
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String	keyWord;
	private String	category;
	private Money	minPrice;
	private Money	maxPrice;
	private Date	startDate;
	private Date	endDate;
	private double	warranty;


	public String getKeyWord() {
		return this.keyWord;
	}
	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}
	public String getCategory() {
		return this.category;
	}
	public void setCategory(final String category) {
		this.category = category;
	}
	@AttributeOverrides({
		@AttributeOverride(name = "amount", column = @Column(name = "minimumAmount")), @AttributeOverride(name = "currency", column = @Column(name = "minimumCurrency"))
	})
	public Money getMinPrice() {
		return this.minPrice;
	}
	public void setMinPrice(final Money minPrice) {
		this.minPrice = minPrice;
	}

	@AttributeOverrides({
		@AttributeOverride(name = "amount", column = @Column(name = "maximumAmount")), @AttributeOverride(name = "currency", column = @Column(name = "maximumCurrency"))
	})
	public Money getMaxPrice() {
		return this.maxPrice;
	}
	public void setMaxPrice(final Money maxPrice) {
		this.maxPrice = maxPrice;
	}
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}
	@Digits(integer = 6, fraction = 2)
	public double getWarranty() {
		return this.warranty;
	}
	public void setWarranty(final double warranty) {
		this.warranty = warranty;
	}


	//Relationships
	Collection<FixUpTask>	fixUpTasks;


	@OneToMany
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}
	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

}
