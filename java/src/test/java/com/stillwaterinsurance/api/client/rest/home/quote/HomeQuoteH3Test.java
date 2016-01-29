package com.stillwaterinsurance.api.client.rest.home.quote;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.stillwaterinsurance.api.client.HttpClient;
import com.stillwaterinsurance.api.client.HttpClient.ResponseVO;
import com.stillwaterinsurance.api.client.Utils;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ACORDType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.AddrType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.AddressType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.AssignedIdentifier;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.BathroomInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.BldgProtectionType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.C11;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.C20;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.C32;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.C40;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.C64;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ClientAppType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.CommunicationUse;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.CommunicationsType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ConstructionClass;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ConstructionType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ConstructionType2;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.CustIdType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.DURATION;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.Date;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.DateTime;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.Decimal;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.DoorLockType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.DwellInspectionValuationType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.DwellOccupancyType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.DwellType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.DwellingUse;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.EmailInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.FinishedBasementInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.FireplaceInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.Foundation;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.GarageInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.GarageType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.GeneralPartyInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.HomeLineBusinessType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.HomePolicyQuoteInqRqType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.HomePolicyQuoteInqRsType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.InsuranceSvcRqType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.InsuredOrPrincipalInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.InsuredOrPrincipalType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.InsuredPrincipalRoleType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ItemIdInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.KitchenInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.LineOfBusiness;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.LocationType2;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.MEASUREMENT;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.MsgStatusType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.NC128;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.NameInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ObjectFactory;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.OccupancyType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.OpenEnum;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.Ownership;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.PercentDecimal;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.PersApplicationInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.PersPolicyType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.PersonInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.PersonNameType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.PhoneInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.PhoneNumberType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.PolicySummaryInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.PolicyType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ProducerInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ProducerType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ProtectionDevice;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.ResidenceType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.RoofMaterialType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.RoofingMaterialType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.SignonRole;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.SignonRqType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.SignonTransportType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.SubstructureInfoType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.TaxIdType;
import com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.TaxIdentityType;



public class HomeQuoteH3Test {


	private static final String HOME_QUOTE = "/home/quote/v1.0";
	private static final DateTimeFormatter DATE_FMT_SHORT = DateTimeFormat.forPattern("yyyy-MM-dd");

	@Test
	public void homeQuoteH3Test() {

		final HttpClient client = new HttpClient();

		final ObjectFactory of = new ObjectFactory();
		final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
		final org.joda.time.DateTime dt = new org.joda.time.DateTime();

		final ACORDType acordTypeRequest = of.createACORDType();
		final SignonRqType signonRqType = of.createSignonRqType();

		//create all objects in signonRqType
		final SignonTransportType SignonTransportType = of.createSignonTransportType();
		final ClientAppType clientAppType = of.createClientAppType();

		// build SignonTransportType
		SignonTransportType.setSignonRoleCd(SignonRole.AGENT);
		final CustIdType custIdType = of.createCustIdType();
		final AssignedIdentifier spName = of.createAssignedIdentifier();
		//profit area which will be provided by stillwater insurance
		spName.setValue("to-be-provided-by-stillwater"); // <-- will not work in QUA or PROD

		assertNotEquals("You must use the profit area given to you by Stillwater Insurance.",
				"to-be-provided-by-stillwater", spName.getValue());

		// PRD Environment: This id should be the Stillwater Insurance provided id of the licensed agent performing the quote request.
		// QUA Environment: Stillwater provided test ID such as "xmlcattest"
		of.createSPName(spName);
		custIdType.setSPName(spName);
		custIdType.setCustLoginId("xmlcattest");// <-- WILL NOT WORK IN PROD
		custIdType.setCustPermId("xmlcattest");// <-- WILL NOT WORK IN PROD
		SignonTransportType.setCustId(custIdType);

		//build clientAppType
		clientAppType.setOrg("Internet");
		//profit area which will be provided by stillwater insurance
		clientAppType.setName("to-be-provided-by-stillwater");

		assertNotEquals("You must use the profit area given to you by Stillwater Insurance.",
				"to-be-provided-by-stillwater", clientAppType.getName());

		clientAppType.setVersion("1.0");

		//add all objects to signonRqType
		signonRqType.setSignonTransport(SignonTransportType);
		signonRqType.setClientDt(dt.toString(fmt));
		signonRqType.setCustLangPref("ENG");
		signonRqType.setClientApp(clientAppType);

		acordTypeRequest.setSignonRq(signonRqType);
		//end of signOnRq  --------------------------------------------------

		//start of InsuranceSvcRqType ----------------------------------------
		final InsuranceSvcRqType insuranceSvcRqType = of.createInsuranceSvcRqType();

		//Stillwater Insurance requires a unique RqUID for every service call
		final String rqUID = Utils.randomRqUid();
		insuranceSvcRqType.setRqUID(rqUID);

		final HomePolicyQuoteInqRqType homePolicyQuoteInqRqType = of.createHomePolicyQuoteInqRqType();
		insuranceSvcRqType.getINSURANCESVCRQMSGS().add(homePolicyQuoteInqRqType);

		acordTypeRequest.getInsuranceSvcRq().add(insuranceSvcRqType);
		//end of InsuranceSvcRq  --------------------------------------------

		//start of homePolicyQuoteInqRqType ---------------------------------
		homePolicyQuoteInqRqType.setRqUID(rqUID);
		final DateTime dateTime = of.createDateTime();
		dateTime.setValue(dt.toString(fmt));
		homePolicyQuoteInqRqType.setTransactionRequestDt(dateTime);
		homePolicyQuoteInqRqType.setTransactionEffectiveDt(dateTime);
		final OpenEnum openEnum = of.createOpenEnum();
		openEnum.setValue("USD");
		homePolicyQuoteInqRqType.setCurCd(openEnum);

		// producerType
		final C20 c20_1 = of.createC20();
		c20_1.setValue("FNF100015");
		final ProducerInfoType producerInfoType = of.createProducerInfoType();
		producerInfoType.setContractNumber(c20_1);
		final ProducerType producerType = of.createProducerType();
		producerType.setProducerInfo(producerInfoType);
		homePolicyQuoteInqRqType.getProducer().add(producerType);

		// start InsuredOrPrincipal
		final InsuredOrPrincipalType insuredOrPrincipalType = of.createInsuredOrPrincipalType();
		homePolicyQuoteInqRqType.getInsuredOrPrincipal().add(insuredOrPrincipalType);

		// ItemIdInfoType
		final ItemIdInfoType itemIdInfoType = of.createItemIdInfoType();
		insuredOrPrincipalType.setItemIdInfo(itemIdInfoType);
		final AssignedIdentifier assignedIdentifier = of.createAssignedIdentifier();
		assignedIdentifier.setValue("12345");
		itemIdInfoType.setAgencyId(assignedIdentifier);

		// start GeneralPartyInfo -----------------------------------------
		final GeneralPartyInfoType generalPartyInfoType = of.createGeneralPartyInfoType();
		insuredOrPrincipalType.setGeneralPartyInfo(generalPartyInfoType);

		// NameInfo
		final NameInfoType nameInfoType = of.createNameInfoType();
		generalPartyInfoType.getNameInfo().add(nameInfoType);

		final PersonNameType personNameType = of.createPersonNameType();
		nameInfoType.setPersonName(personNameType);
		final C40 c40_1 = of.createC40();
		c40_1.setValue("Public");
		personNameType.setSurname(c40_1);
		final C40 c40_2 = of.createC40();
		c40_2.setValue("John");
		personNameType.setGivenName(c40_2);
		final C40 c40_3 = of.createC40();
		c40_3.setValue("Q");
		personNameType.getOtherGivenName().add(c40_3);

		final TaxIdentityType taxIdentityType = of.createTaxIdentityType();
		nameInfoType.getTaxIdentity().add(taxIdentityType);
		final TaxIdType taxIdType = of.createTaxIdType();
		taxIdType.setValue("SSN");
		taxIdentityType.setTaxIdTypeCd(taxIdType);

		final AssignedIdentifier assignedIdentifier1 = of.createAssignedIdentifier();
		assignedIdentifier1.setValue("000112222");
		taxIdentityType.setTaxId(assignedIdentifier1);

		// Addr
		final AddrType addrType = of.createAddrType();
		generalPartyInfoType.getAddr().add(addrType);
		final AddressType addressType = of.createAddressType();
		addressType.setValue("MailingAddress");
		addrType.getAddrTypeCd().add(addressType);
		final C64 c64_1 = of.createC64();
		c64_1.setValue("353 Portland Cir");
		addrType.setAddr1(c64_1);
		final C32 c32_1 = of.createC32();
		c32_1.setValue("HUNTINGTON BEACH");
		addrType.setCity(c32_1);
		final OpenEnum openEnum_1 = of.createOpenEnum();
		openEnum_1.setValue("CA");
		addrType.setStateProvCd(openEnum_1);
		final C11 c11_1 = of.createC11();
		c11_1.setValue("92648");
		addrType.setPostalCode(c11_1);
		final OpenEnum openEnum_2 = of.createOpenEnum();
		openEnum_2.setValue("Region");
		addrType.setRegionCd(openEnum_2);

		// Communications
		final CommunicationsType communicationsType = of.createCommunicationsType();
		generalPartyInfoType.setCommunications(communicationsType);
		final PhoneInfoType phoneInfoType = of.createPhoneInfoType();
		communicationsType.getPhoneInfo().add(phoneInfoType);

		final CommunicationUse communicationUse = of.createCommunicationUse();
		communicationUse.setValue("Home");
		phoneInfoType.getCommunicationUseCd().add(communicationUse);

		final PhoneNumberType phoneNumberType = of.createPhoneNumberType();
		phoneNumberType.setValue("+1-540-555-1212");
		phoneInfoType.setPhoneNumber(phoneNumberType);

		final EmailInfoType emailInfoType = of.createEmailInfoType();
		communicationsType.getEmailInfo().add(emailInfoType);
		final NC128 nc128_1 = of.createNC128();
		nc128_1.setValue("test@test.com");
		emailInfoType.setEmailAddr(nc128_1);

		// end GeneralPartyInfo -----------------------------------------


		// InsuredOrPrincipalInfo
		final InsuredOrPrincipalInfoType insuredOrPrincipalInfoType = of.createInsuredOrPrincipalInfoType();
		insuredOrPrincipalType.setInsuredOrPrincipalInfo(insuredOrPrincipalInfoType);

		final InsuredPrincipalRoleType insuredPrincipalRoleType = of.createInsuredPrincipalRoleType();
		insuredPrincipalRoleType.setValue("FNI");
		insuredOrPrincipalInfoType.getContent().add(of.createInsuredOrPrincipalRoleCd(insuredPrincipalRoleType));

		final PersonInfoType personInfoType = of.createPersonInfoType();
		final Date date = of.createDate();
		date.setValue("1970-01-01");
		personInfoType.setBirthDt(date);
		insuredOrPrincipalInfoType.getContent().add(of.createPersonInfo(personInfoType));

		// end InsuredOrPrincipal

		// start PersPolicy
		final PersPolicyType persPolicyType = of.createPersPolicyType();
		homePolicyQuoteInqRqType.setPersPolicy(persPolicyType);

		final LineOfBusiness lineOfBusiness = of.createLineOfBusiness();
		lineOfBusiness.setValue("HOME");
		persPolicyType.setLOBCd(lineOfBusiness);

		final DURATION duration = of.createDURATION();
		final MEASUREMENT measurement = of.createMEASUREMENT();
		final Decimal decimal = of.createDecimal();
		decimal.setValue(new BigDecimal(12));
		measurement.setNumUnits(decimal);
		final OpenEnum openEnum_3 = of.createOpenEnum();
		openEnum_3.setValue("MO");
		measurement.setUnitMeasurementCd(openEnum_3);
		duration.setDurationPeriod(measurement);
		persPolicyType.setContractTerm(duration);

		final Date date_1 = of.createDate();
		date_1.setValue(dt.plusDays(1).toString(DATE_FMT_SHORT));
		persPolicyType.getContractTerm().setEffectiveDt(date_1);
		final Date date_2 = of.createDate();
		date_2.setValue(dt.plusYears(1).plusDays(1).toString(DATE_FMT_SHORT));
		persPolicyType.getContractTerm().setExpirationDt(date_2);

		final PersApplicationInfoType persApplicationInfoType = of.createPersApplicationInfoType();
		persPolicyType.setPersApplicationInfo(persApplicationInfoType);
		final InsuredOrPrincipalType insuredOrPrincipalType_1 = of.createInsuredOrPrincipalType();
		persApplicationInfoType.getInsuredOrPrincipal().add(insuredOrPrincipalType_1);
		final Ownership ownership = of.createOwnership();
		ownership.setValue("OWNED");
		persApplicationInfoType.setResidenceOwnedRentedCd(ownership);
		// end PersPolicy

		// start location
		final LocationType2 locationType2 = of.createLocationType2();
		homePolicyQuoteInqRqType.getLocation().add(locationType2);

		locationType2.setId("LOC1");
		final ItemIdInfoType itemIdInfoType_1 = of.createItemIdInfoType();
		final AssignedIdentifier assignedIdentifier_1 = of.createAssignedIdentifier();
		assignedIdentifier_1.setValue("12345");
		itemIdInfoType_1.setAgencyId(assignedIdentifier_1);
		locationType2.setItemIdInfo(itemIdInfoType_1);

		final AddrType addrType_1 = of.createAddrType();
		locationType2.setAddr(addrType_1);
		final AddressType addressType_1 = of.createAddressType();
		addressType_1.setValue("PhysicalRisk");
		addrType_1.getAddrTypeCd().add(addressType_1);
		final C64 c64_2 = of.createC64();
		c64_2.setValue("353 Portland Cir");
		addrType_1.setAddr1(c64_2);
		final C32 c32_2 = of.createC32();
		c32_2.setValue("HUNTINGTON BEACH");
		addrType_1.setCity(c32_2);
		final OpenEnum openEnum_4 = of.createOpenEnum();
		openEnum_4.setValue("CA");
		addrType_1.setStateProvCd(openEnum_4);
		final C11 c11_2 = of.createC11();
		c11_2.setValue("92648");
		addrType_1.setPostalCode(c11_2);
		final OpenEnum openEnum_5 = of.createOpenEnum();
		openEnum_5.setValue("Region");
		addrType_1.setRegionCd(openEnum_5);
		// end location

		// start HomeLineBusiness
		final HomeLineBusinessType homeLineBusinessType = of.createHomeLineBusinessType();
		homePolicyQuoteInqRqType.setHomeLineBusiness(homeLineBusinessType);

		final LineOfBusiness lineOfBusiness_2 = of.createLineOfBusiness();
		lineOfBusiness_2.setValue("HOME");
		homeLineBusinessType.setLOBCd(lineOfBusiness_2);

		//start Dwell
		final DwellType dwellType = of.createDwellType();
		dwellType.setLocationRef(locationType2);
		dwellType.setId("DWE1");
		homeLineBusinessType.getDwell().add(dwellType);

		final PolicyType policyType = of.createPolicyType();
		policyType.setValue("03"); // <-- H3 indicates an Owner
		dwellType.setPolicyTypeCd(policyType);

		final Date date_3 = of.createDate();
		date_3.setValue("2005");
		dwellType.setPurchaseDt(date_3);

		//start Construction
		final ConstructionType2 constructionType2 = of.createConstructionType2();
		dwellType.setConstruction(constructionType2);
		final ConstructionType constructionType = of.createConstructionType();
		constructionType.setValue("F");
		constructionType2.getConstructionCd().add(constructionType);

		final Date date_4 = of.createDate();
		date_4.setValue("1993");
		constructionType2.setYearBuilt(date_4);

		final Foundation foundation = of.createFoundation();
		foundation.setValue("BS");
		constructionType2.setFoundationCd(foundation);

		final Decimal decimal_1 = of.createDecimal();
		decimal_1.setValue(new BigDecimal(2));
		constructionType2.setNumStories(decimal_1);

		final RoofingMaterialType roofingMaterialType = of.createRoofingMaterialType();
		final RoofMaterialType roofMaterialType = of.createRoofMaterialType();
		roofMaterialType.setValue("ASPHS");
		roofingMaterialType.setRoofMaterialCd(roofMaterialType);
		constructionType2.getRoofingMaterial().add(roofingMaterialType);
		//end Construction

		//start dwellOccupancy
		final DwellOccupancyType dwellOccupancyType = of.createDwellOccupancyType();
		dwellType.setDwellOccupancy(dwellOccupancyType);

		final Date date_5 = of.createDate();
		date_5.setValue("2010");
		dwellOccupancyType.setYearOccupancy(date_5);

		final ResidenceType residenceType = of.createResidenceType();
		residenceType.setValue("DW");
		dwellOccupancyType.setResidenceTypeCd(residenceType);

		final DwellingUse dwellingUse = of.createDwellingUse();
		dwellingUse.setValue("1");
		dwellOccupancyType.setDwellUseCd(dwellingUse);

		final OccupancyType occupancyType = of.createOccupancyType();
		occupancyType.setValue("OWNER");
		dwellOccupancyType.setOccupancyTypeCd(occupancyType);
		//end dwellOccupancy

		// start BldgProtection
		final BldgProtectionType bldgProtectionType = of.createBldgProtectionType();
		dwellType.setBldgProtection(bldgProtectionType);

		final MEASUREMENT measurement_1 = of.createMEASUREMENT();
		final Decimal decimal_2 = of.createDecimal();
		decimal_2.setValue(new BigDecimal(3));
		measurement_1.setNumUnits(decimal_2);
		final OpenEnum openEnum_6 = of.createOpenEnum();
		openEnum_6.setValue("MI");
		measurement_1.setUnitMeasurementCd(openEnum_6);
		bldgProtectionType.setDistanceToFireStation(measurement_1);

		final MEASUREMENT measurement_2 = of.createMEASUREMENT();
		final Decimal decimal_3 = of.createDecimal();
		decimal_3.setValue(new BigDecimal(200));
		measurement_2.setNumUnits(decimal_3);
		final OpenEnum openEnum_7 = of.createOpenEnum();
		openEnum_7.setValue("FT");
		measurement_2.setUnitMeasurementCd(openEnum_7);
		bldgProtectionType.setDistanceToHydrant(measurement_2);

		//FireExtinguisherInd
		final com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.Boolean boolean_1 = of.createBoolean();
		boolean_1.setValue(true);
		bldgProtectionType.setFireExtinguisherInd(boolean_1);

		final ProtectionDevice protectionDevice = of.createProtectionDevice();
		protectionDevice.setValue("NO");
		bldgProtectionType.setProtectionDeviceBurglarCd(protectionDevice);

		final ProtectionDevice protectionDevice_1 = of.createProtectionDevice();
		protectionDevice_1.setValue("NO");
		bldgProtectionType.setProtectionDeviceFireCd(protectionDevice_1);

		final ProtectionDevice protectionDevice_2 = of.createProtectionDevice();
		protectionDevice_2.setValue("DE");
		bldgProtectionType.setProtectionDeviceSmokeCd(protectionDevice_2);

		final ProtectionDevice protectionDevice_3 = of.createProtectionDevice();
		protectionDevice_3.setValue("NO");
		bldgProtectionType.setProtectionDeviceSprinklerCd(protectionDevice_3);

		final DoorLockType doorLockType = of.createDoorLockType();
		doorLockType.setValue("DEADB");
		bldgProtectionType.setDoorLockCd(doorLockType);
		// end BldgProtection

		// start DwellInspectionValuation
		final DwellInspectionValuationType dwellInspectionValuationType = of.createDwellInspectionValuationType();
		dwellType.getDwellInspectionValuation().add(dwellInspectionValuationType);

		final com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.Long long_1 = of.createLong();
		long_1.setValue(1);
		dwellInspectionValuationType.setNumFamilies(long_1);

		final BathroomInfoType bathroomInfoType = of.createBathroomInfoType();
		dwellInspectionValuationType.getBathroomInfo().add(bathroomInfoType);
		final com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.Long long_2 = of.createLong();
		long_2.setValue(1);
		bathroomInfoType.setNumBathrooms(long_2);
		final ConstructionClass constructionClass = of.createConstructionClass();
		constructionClass.setValue("Basic");
		bathroomInfoType.setConstructionQualityCd(constructionClass);

		final FireplaceInfoType fireplaceInfoType = of.createFireplaceInfoType();
		dwellInspectionValuationType.getFireplaceInfo().add(fireplaceInfoType);
		final com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.Long long_3 = of.createLong();
		long_3.setValue(1);
		fireplaceInfoType.setNumFireplaces(long_3);

		final GarageInfoType garageInfoType = of.createGarageInfoType();
		dwellInspectionValuationType.getGarageInfo().add(garageInfoType);
		final GarageType garageType = of.createGarageType();
		garageType.setValue("BS");
		garageInfoType.setGarageTypeCd(garageType);

		final com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.Boolean boolean_2 = of.createBoolean();
		boolean_2.setValue(true);
		garageInfoType.setAttachedInd(boolean_2);

		final com.stillwaterinsurance.api.client.acord.homeQuoteV1_0.Long long_4 = of.createLong();
		long_4.setValue(1);
		garageInfoType.setNumGarages(long_4);

		final KitchenInfoType kitchenInfoType = of.createKitchenInfoType();
		final ConstructionClass constructionClass_1 = of.createConstructionClass();
		constructionClass_1.setValue("Basic");
		kitchenInfoType.setConstructionQualityCd(constructionClass_1);
		dwellInspectionValuationType.getKitchenInfo().add(kitchenInfoType);

		final SubstructureInfoType substructureInfoType = of.createSubstructureInfoType();
		dwellInspectionValuationType.getSubstructureInfo().add(substructureInfoType);
		final PercentDecimal percentDecimal = of.createPercentDecimal();
		percentDecimal.setValue(new BigDecimal(1.0));
		substructureInfoType.setGroundFloorPct(percentDecimal);

		final FinishedBasementInfoType finishedBasementInfoType = of.createFinishedBasementInfoType();
		substructureInfoType.setFinishedBasementInfo(finishedBasementInfoType);
		final PercentDecimal percentDecimal_1 = of.createPercentDecimal();
		percentDecimal_1.setValue(new BigDecimal(0.5));
		finishedBasementInfoType.setFinishedBasementPct(percentDecimal_1);

		final MEASUREMENT measurement_3 = of.createMEASUREMENT();
		final Decimal decimal_4 = of.createDecimal();
		decimal_4.setValue(new BigDecimal(2500));
		measurement_3.setNumUnits(decimal_4);
		dwellInspectionValuationType.setTotalLivingArea(measurement_3);
		// end DwellInspectionValuation
		// end Dwell
		// end HomeLineBusiness
		// end of homePolicyQuoteInqRqType ------------------------------------


		// -------------------------------------------------------------------
		checkH3RequestsForRequiredFields(acordTypeRequest);

		// generate request XML
		final String xml = Utils.jaxbMarshall(acordTypeRequest);
		System.out.println(xml);

		//Post xml to service URL
		final Long start = Calendar.getInstance().getTimeInMillis();
		final ResponseVO responseVO = client.doPost(HOME_QUOTE, xml);
		final Long end = Calendar.getInstance().getTimeInMillis();
		System.out.println(Utils.formatXML(responseVO.getBody()));

		assertTrue("Service call took longer than 10 seconds", end - start < 10000);

		commonSuccessReplyTests(responseVO);

	}

	/**
	 * Set of common assertions that need to happen after every request.
	 *
	 * @param responseVO
	 * @return
	 */
	public static final ACORDType commonSuccessReplyTests(final ResponseVO responseVO){

		assertEquals(new Integer(200), responseVO.getCode());
		assertEquals(true, responseVO.getBody() != null);
		assertEquals(true, !responseVO.getBody().equals(""));


		// unmarshal XML to object.
		final ACORDType acordTypeResult = Utils.jaxbUnmarshall(responseVO.getBody());

		//will only be null if request times out or unmarshalling fails.
		assertNotNull("ACORD element not found in XML reply. Possible timeout or error parsing XML",
				acordTypeResult);

		// did msg timeout?
		final MsgStatusType msgStatusType = acordTypeResult.getInsuranceSvcRs().get(0).getINSURANCESVCRSMSGS().get(0).getMsgStatus();
		if(msgStatusType != null && msgStatusType.getMsgStatusDesc() != null && msgStatusType.getMsgStatusDesc().getValue() != null){
			assertFalse("Error, request timed out", msgStatusType.getMsgStatusDesc().getValue().toLowerCase().contains("timeout"));
		}


		// do we have a homePolicyQuoteInqRsType?
		final HomePolicyQuoteInqRsType homePolicyQuoteInqRsType = acordTypeResult.getInsuranceSvcRs().get(0).getINSURANCESVCRSMSGS().get(0);
		assertNotNull("HomePolicyQuoteInqRs was not found in reply.", homePolicyQuoteInqRsType);

		//does the object with the quote $$ exist?
		final PolicySummaryInfoType policySummaryInfoType = homePolicyQuoteInqRsType.getPolicySummaryInfo();
		assertNotNull("PolicySummaryInfo element was not found in reply.", policySummaryInfoType);

		//did we get a message status?
		final MsgStatusType msgStatusType_1 = homePolicyQuoteInqRsType.getMsgStatus();
		assertNotNull("MsgStatus element not found in reply.", msgStatusType_1);
		assertNotNull("MsgStatusCd element not found in reply.", msgStatusType_1.getMsgStatusCd());
		assertNotNull("MsgStatusCd element was empty in reply.", msgStatusType_1.getMsgStatusCd().getValue());
		assertNotEquals("MsgStatusCd was rejected instead of success.", "rejected", msgStatusType_1.getMsgStatusCd().getValue().toLowerCase());
		assertNotEquals("MsgStatusCd was error instead of success.", "error", msgStatusType_1.getMsgStatusCd().getValue().toLowerCase());

		//quote should be bound
		assertEquals("PolicyStatusCd should be QuotedNotBound", "QuotedNotBound", policySummaryInfoType.getPolicyStatusCd().getValue());

		// make sure some policy number exists.
		assertTrue("PolicyNumber should exist and be longer than 3 chars.", policySummaryInfoType.getPolicyNumber().getValue().length() > 3);

		// make sure some quote more than $0 is given.
		assertNotNull("FullTermAmt/Amt element does not exist.", policySummaryInfoType.getFullTermAmt().getAmt());
		assertNotNull("FullTermAmt/Amt element is empty.", policySummaryInfoType.getFullTermAmt().getAmt().getValue());
		assertTrue("FullTermAmt should be a positive decimal value.",
				policySummaryInfoType.getFullTermAmt().getAmt().getValue().compareTo(new BigDecimal(0)) > 0);

		// quote should not be bound yet, since no paymnet info was sent.
		assertEquals("QuotedNotBound", policySummaryInfoType.getPolicyStatusCd().getValue());

		return acordTypeResult;
	}


	public static final ACORDType commonErrorReplyTests(
			final ResponseVO responseVO, final String expectedMsgStatusCd,
			final String expectedMsgStatusDesc, final String msgStatusDescAssertMessage){

		assertEquals("HTTP status code should be 200", new Integer(200), responseVO.getCode());
		assertEquals("Empty body recieved from service request.", true, responseVO.getBody() != null);
		assertEquals("Empty body recieved from service request.", true, !responseVO.getBody().equals(""));

		// unmarshal XML to object.
		final ACORDType acordTypeResult = Utils.jaxbUnmarshall(responseVO.getBody());

		//will only be null if request times out or unmarshalling fails.
		assertNotNull("ACORD element not found in XML reply. Possible timeout or error parsing XML",
				acordTypeResult);

		// did msg timeout?
		final MsgStatusType msgStatusType = acordTypeResult.getInsuranceSvcRs().get(0).getINSURANCESVCRSMSGS().get(0).getMsgStatus();
		if(msgStatusType != null && msgStatusType.getMsgStatusDesc() != null && msgStatusType.getMsgStatusDesc().getValue() != null){
			assertFalse("Error, request timed out", msgStatusType.getMsgStatusDesc().getValue().toLowerCase().contains("timeout"));
		}

		//does the object with the quote $$ exist?
		final HomePolicyQuoteInqRsType homePolicyQuoteInqRsType = acordTypeResult.getInsuranceSvcRs().get(0).getINSURANCESVCRSMSGS().get(0);
		assertNotNull("HomePolicyQuoteInqRs element not found in reply.", homePolicyQuoteInqRsType);

		//did we get a message status?
		final MsgStatusType msgStatusType_1 = homePolicyQuoteInqRsType.getMsgStatus();
		assertNotNull("MsgStatus element not found in reply.", msgStatusType_1);
		assertNotNull("MsgStatusCd element not found in reply.", msgStatusType_1.getMsgStatusCd());
		assertNotNull("MsgStatusCd element was empty in reply.", msgStatusType_1.getMsgStatusCd().getValue());
		assertEquals("MsgStatusCd value did not match expected value.",
				expectedMsgStatusCd, msgStatusType_1.getMsgStatusCd().getValue());

		assertNotNull("MsgStatusDesc element not found in reply.", msgStatusType_1.getMsgStatusDesc());
		assertNotNull("MsgStatusDesc element was empty in reply.", msgStatusType_1.getMsgStatusDesc().getValue());
		//eg:  <MsgStatusDesc>ERR0005: Not Authorized to Write in this Area - Regress test Soft close</MsgStatusDesc>

		assertEquals(msgStatusDescAssertMessage,
				expectedMsgStatusDesc,
				msgStatusType_1.getMsgStatusDesc().getValue());

		return acordTypeResult;

	}


	/**
	 * Make sure that the request we send follow the requirements found on this page:
	 *
	 * https://api-qua.stillwaterinsurance.com/api-docs/page?id=faqs-home-quote-1-0-h3
	 *
	 * @param acord
	 */
	public void checkH3RequestsForRequiredFields(final ACORDType acord){

		assertNotNull(acord);

		//ACORD/SignonRq/SignonTransport/SignonRoleCd
		assertNotNull(acord.getSignonRq());
		assertNotNull(acord.getSignonRq().getSignonTransport());
		assertNotNull(acord.getSignonRq().getSignonTransport().getSignonRoleCd());

		//ACORD/SignonRq/SignonTransport/CustId/SPName
		assertNotNull(acord.getSignonRq().getSignonTransport().getCustId());
		assertNotNull(acord.getSignonRq().getSignonTransport().getCustId().getSPName());
		assertNotNull(acord.getSignonRq().getSignonTransport().getCustId().getSPName().getValue());
		assertNotNull(acord.getSignonRq().getSignonTransport().getCustId().getCustPermId());
		assertNotNull(acord.getSignonRq().getSignonTransport().getCustId().getCustPermId().getBytes());
		assertNotNull(acord.getSignonRq().getSignonTransport().getCustId().getCustLoginId());
		assertNotNull(acord.getSignonRq().getSignonTransport().getCustId().getCustLoginId().getBytes());

		//ACORD/SignonRq/SignonTransport/CustId/CustLoginId
		assertNotNull(acord.getSignonRq().getSignonTransport().getCustId().getCustLoginId());
		assertNotNull(acord.getSignonRq().getSignonTransport().getCustId().getCustLoginId().getBytes());

		//ACORD/SignonRq/ClientDt
		assertNotNull(acord.getSignonRq().getClientDt());

		//ACORD/SignonRq/CustLangPref
		assertNotNull(acord.getSignonRq().getCustLangPref());

		//ACORD/SignonRq/ClientApp/Org
		assertNotNull(acord.getSignonRq().getClientApp());
		assertNotNull(acord.getSignonRq().getClientApp().getOrg());

		//ACORD/SignonRq/ClientApp/Name
		assertNotNull(acord.getSignonRq().getClientApp().getName());

		//ACORD/SignonRq/ClientApp/Version
		assertNotNull(acord.getSignonRq().getClientApp().getVersion());

		//ACORD/InsuranceSvcRq/RqUID
		assertNotNull(acord.getInsuranceSvcRq());
		assertNotNull(acord.getInsuranceSvcRq().get(0));
		assertNotNull(acord.getInsuranceSvcRq().get(0).getRqUID());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/RqUID
		assertNotNull(acord.getInsuranceSvcRq().get(0).getINSURANCESVCRQMSGS());
		assertNotNull(acord.getInsuranceSvcRq().get(0).getINSURANCESVCRQMSGS().get(0));
		assertNotNull(acord.getInsuranceSvcRq().get(0).getINSURANCESVCRQMSGS().get(0).getRqUID());

		final HomePolicyQuoteInqRqType homePolicyQuoteInqRqType = acord.getInsuranceSvcRq().get(0).getINSURANCESVCRQMSGS().get(0);

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/TransactionRequestDt
		assertNotNull(homePolicyQuoteInqRqType.getTransactionRequestDt());
		assertNotNull(homePolicyQuoteInqRqType.getTransactionRequestDt().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/CurCd
		assertNotNull(homePolicyQuoteInqRqType.getCurCd());
		assertNotNull(homePolicyQuoteInqRqType.getCurCd().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/InsuredOrPrincipal/GeneralPartyInfo/NameInfo/PersonName/Surname
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal());
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0));
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getGeneralPartyInfo());
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getGeneralPartyInfo().getNameInfo());
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getGeneralPartyInfo().getNameInfo().get(0));
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getGeneralPartyInfo().getNameInfo().get(0).getPersonName());
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getGeneralPartyInfo().getNameInfo().get(0).getPersonName().getSurname());
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getGeneralPartyInfo().getNameInfo().get(0).getPersonName().getSurname().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/InsuredOrPrincipal/GeneralPartyInfo/NameInfo/PersonName/GivenName
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getGeneralPartyInfo().getNameInfo().get(0).getPersonName().getGivenName());
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getGeneralPartyInfo().getNameInfo().get(0).getPersonName().getGivenName().getValue());

		//NOTE: we assume InsuredOrPrincipalRoleCd will be first in the list
		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/InsuredOrPrincipal/InsuredOrPrincipalInfo/InsuredOrPrincipalRoleCd
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo());
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo().getContent());
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo().getContent().get(0));
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo().getContent().get(0).getName());
		assertEquals("InsuredOrPrincipalRoleCd", homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo().getContent().get(0).getName().getLocalPart());
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo().getContent().get(0).getValue());

		//NOTE: we assume PersonInfo will be second in the list
		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/InsuredOrPrincipal/InsuredOrPrincipalInfo/PersonInfo
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo().getContent().get(1));
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo().getContent().get(1).getName());
		assertEquals("PersonInfo", homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo().getContent().get(1).getName().getLocalPart());
		assertNotNull(homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo().getContent().get(1).getValue());
		final PersonInfoType personInfoType = (PersonInfoType)homePolicyQuoteInqRqType.getInsuredOrPrincipal().get(0).getInsuredOrPrincipalInfo().getContent().get(1).getValue();
		assertNotNull(personInfoType.getBirthDt());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/PersPolicy/PersApplicationInfo/ResidenceOwnedRentedCd
		assertNotNull(homePolicyQuoteInqRqType.getPersPolicy());
		assertNotNull(homePolicyQuoteInqRqType.getPersPolicy().getPersApplicationInfo());
		assertNotNull(homePolicyQuoteInqRqType.getPersPolicy().getPersApplicationInfo().getResidenceOwnedRentedCd());
		assertNotNull(homePolicyQuoteInqRqType.getPersPolicy().getPersApplicationInfo().getResidenceOwnedRentedCd().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/PersPolicy/LOBCd
		assertNotNull(homePolicyQuoteInqRqType.getPersPolicy().getLOBCd());
		assertNotNull(homePolicyQuoteInqRqType.getPersPolicy().getLOBCd().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/Location/ItemIdInfo
		assertNotNull(homePolicyQuoteInqRqType.getLocation());
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0));
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getItemIdInfo());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/Location/Addr/AddrTypeCd
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr());
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getAddrTypeCd());
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getAddrTypeCd().get(0));
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getAddrTypeCd().get(0).getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/Location/Addr/Addr1
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getAddr1());
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getAddr1().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/Location/Addr/City
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getCity());
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getCity().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/Location/Addr/StateProvCd
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getStateProvCd());
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getStateProvCd().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/Location/Addr/PostalCode
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getPostalCode());
		assertNotNull(homePolicyQuoteInqRqType.getLocation().get(0).getAddr().getPostalCode().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/HomeLineBusiness/LOBCd
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness());
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getLOBCd());
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getLOBCd().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/HomeLineBusiness/Dwell/Construction/YearBuilt
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell());
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0));
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getConstruction());
		//assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getConstruction().getYearBuilt());
		//assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getConstruction().getYearBuilt().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/HomeLineBusiness/Dwell/DwellOccupancy/ResidenceTypeCd
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellOccupancy());
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellOccupancy().getResidenceTypeCd());
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellOccupancy().getResidenceTypeCd().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/HomeLineBusiness/Dwell/DwellOccupancy/DwellUseCd
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellOccupancy().getDwellUseCd());
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellOccupancy().getDwellUseCd().getValue());

		//ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/HomeLineBusiness/Dwell/DwellOccupancy/OccupancyTypeCd
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellOccupancy().getOccupancyTypeCd());
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellOccupancy().getOccupancyTypeCd().getValue());

		///ACORD/InsuranceSvcRq/HomePolicyQuoteInqRq/HomeLineBusiness/Dwell/DwellInspectionValuation/TotalLivingArea/NumUnits
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellInspectionValuation());
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellInspectionValuation().get(0).getTotalLivingArea());
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellInspectionValuation().get(0).getTotalLivingArea().getNumUnits());
		assertNotNull(homePolicyQuoteInqRqType.getHomeLineBusiness().getDwell().get(0).getDwellInspectionValuation().get(0).getTotalLivingArea().getNumUnits().getValue());


	}

}
