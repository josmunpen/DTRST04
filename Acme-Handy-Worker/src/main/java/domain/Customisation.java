package domain;


import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity{

	private String				systemName;
	private String				bannerURL;
	private String				welcomeMessage;
	private Collection<String>	spamWords;
	private Integer				VATPercentage;
	private String 				phoneNumberCountryCode;
	private Collection<String> 	creditCardMakes;
	private Collection<String>	negativeWords;
	private Collection<String>	positiveWords;
	private Integer 			finderDuration;
	private Integer				resultsNumber;
	
	@NotBlank
	public String getSystemName() {
		return systemName;
	}
	
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	@URL
	public String getBannerURL() {
		return bannerURL;
	}
	
	public void setBannerURL(String bannerURL) {
		this.bannerURL = bannerURL;
	}
	@NotBlank
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	
	public Collection<String> getSpamWords() {
		return spamWords;
	}
	
	public void setSpamWords(Collection<String> spamWords) {
		this.spamWords = spamWords;
	}
	@NotNull
	public Integer getVATPercentage() {
		return VATPercentage;
	}
	
	public void setVATPercentage(Integer vATPercentage) {
		VATPercentage = vATPercentage;
	}
	@NotBlank
	public String getPhoneNumberCountryCode() {
		return phoneNumberCountryCode;
	}
	
	public void setPhoneNumberCountryCode(String phoneNumberCountryCode) {
		this.phoneNumberCountryCode = phoneNumberCountryCode;
	}
	
	public Collection<String> getCreditCardMakes() {
		return creditCardMakes;
	}
	
	public void setCreditCardMakes(Collection<String> creditCardMakes) {
		this.creditCardMakes = creditCardMakes;
	}
	
	public Collection<String> getNegativeWords() {
		return negativeWords;
	}
	
	public void setNegativeWords(Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}
	
	public Collection<String> getPositiveWords() {
		return positiveWords;
	}
	
	public void setPositiveWords(Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}
	@Range(min=1, max=24)
	public Integer getFinderDuration() {
		return finderDuration;
	}
	
	public void setFinderDuration(Integer finderDuration) {
		this.finderDuration = finderDuration;
	}
	@Range(min=10, max=100)
	public Integer getResultsNumber() {
		return resultsNumber;
	}
	public void setResultsNumber(Integer resultsNumber) {
		this.resultsNumber = resultsNumber;
	}
	
	
	
	
	
	
}
