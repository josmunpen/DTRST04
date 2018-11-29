
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity {

	private String				systemName;
	private String				bannerURL;
	private String				welcomeMessage;
	private Collection<String>	spamWords;
	private Integer				VATPercentage;
	private String				phoneNumberCountryCode;
	private Collection<String>	creditCardMakes;
	private Collection<String>	negativeWords;
	private Collection<String>	positiveWords;
	private Integer				finderDuration;
	private Integer				resultsNumber;


	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@URL
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}
	@NotBlank
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}
	@NotNull
	public Integer getVATPercentage() {
		return this.VATPercentage;
	}

	public void setVATPercentage(final Integer vATPercentage) {
		this.VATPercentage = vATPercentage;
	}
	@NotBlank
	public String getPhoneNumberCountryCode() {
		return this.phoneNumberCountryCode;
	}

	public void setPhoneNumberCountryCode(final String phoneNumberCountryCode) {
		this.phoneNumberCountryCode = phoneNumberCountryCode;
	}

	@ElementCollection
	public Collection<String> getCreditCardMakes() {
		return this.creditCardMakes;
	}

	public void setCreditCardMakes(final Collection<String> creditCardMakes) {
		this.creditCardMakes = creditCardMakes;
	}

	@ElementCollection
	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

	@ElementCollection
	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}
	@Range(min = 1, max = 24)
	public Integer getFinderDuration() {
		return this.finderDuration;
	}

	public void setFinderDuration(final Integer finderDuration) {
		this.finderDuration = finderDuration;
	}
	@Range(min = 10, max = 100)
	public Integer getResultsNumber() {
		return this.resultsNumber;
	}
	public void setResultsNumber(final Integer resultsNumber) {
		this.resultsNumber = resultsNumber;
	}

}
