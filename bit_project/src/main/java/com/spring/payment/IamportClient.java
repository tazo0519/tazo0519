package com.spring.payment;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.AgainPaymentData;
import com.siot.IamportRestClient.request.AuthData;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.OnetimePaymentData;
import com.siot.IamportRestClient.request.ScheduleData;
import com.siot.IamportRestClient.request.ScheduleEntry;
import com.siot.IamportRestClient.request.UnscheduleData;
import com.siot.IamportRestClient.request.escrow.EscrowLogisData;
import com.siot.IamportRestClient.request.escrow.EscrowLogisInvoiceData;
import com.siot.IamportRestClient.request.naver.*;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.Certification;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.PagedDataList;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.PaymentBalance;
import com.siot.IamportRestClient.response.PaymentBalanceEntry;
import com.siot.IamportRestClient.response.Schedule;
import com.siot.IamportRestClient.response.escrow.EscrowLogisInvoice;
import com.siot.IamportRestClient.response.naver.NaverProductOrder;
import com.siot.IamportRestClient.response.naver.NaverReview;
import com.siot.IamportRestClient.serializer.BalanceEntrySerializer;
import com.siot.IamportRestClient.serializer.EscrowInvoiceEntrySerializer;
import com.siot.IamportRestClient.serializer.ScheduleEntrySerializer;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IamportClient {

	public static final String API_URL = "https://api.iamport.kr";
	protected String api_key = "6309798726474324";
	protected String api_secret = "mDbo9qHQOKOwHGhVmJHP2Xhw2Hyzyf7VSij2I1SwLG3tmkpJ47y74QE7ycH6rO2k6lmPmfl8VoBiJ12O";
	protected Iamport iamport = null;
	
	public IamportClient(String api_key, String api_secret) {
		this.api_key = api_key;
		this.api_secret = api_secret;
		this.iamport = this.create();
	}
	
	public IamportResponse<AccessToken> getAuth() throws IamportResponseException, IOException {
		Call<IamportResponse<AccessToken>> call = this.iamport.token( new AuthData(this.api_key, this.api_secret) );
		Response<IamportResponse<AccessToken>> response = call.execute();
		
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}
	
	public IamportResponse<PaymentBalance> paymentBalanceByImpUid(String imp_uid) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<PaymentBalance>> call = this.iamport.balance_by_imp_uid(auth.getToken(), imp_uid);
		
		Response<IamportResponse<PaymentBalance>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );
		
		return response.body();
	}
	
	public IamportResponse<Payment> paymentByImpUid(String imp_uid) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<Payment>> call = this.iamport.payment_by_imp_uid(auth.getToken(), imp_uid);
			
		Response<IamportResponse<Payment>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );
		
		return response.body();
	}
	
	public IamportResponse<PagedDataList<Payment>> paymentsByStatus(String status) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<PagedDataList<Payment>>> call = this.iamport.payments_by_status(auth.getToken(), status);
		
		Response<IamportResponse<PagedDataList<Payment>>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );
		
		return response.body();
	}
	
	public IamportResponse<Payment> cancelPaymentByImpUid(CancelData cancel_data) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<Payment>> call = this.iamport.cancel_payment(auth.getToken(), cancel_data);
			
		Response<IamportResponse<Payment>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}
	
	public IamportResponse<Payment> onetimePayment(OnetimePaymentData onetime_data) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<Payment>> call = this.iamport.onetime_payment(auth.getToken(), onetime_data);
			
		Response<IamportResponse<Payment>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}
	
	public IamportResponse<Payment> againPayment(AgainPaymentData again_data) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<Payment>> call = this.iamport.again_payment(auth.getToken(), again_data);
			
		Response<IamportResponse<Payment>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );
			
		return response.body();
	}
	
	public IamportResponse<List<Schedule>> subscribeSchedule(ScheduleData schedule_data) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		System.out.println(auth.getToken());
		Call<IamportResponse<List<Schedule>>> call = this.iamport.schedule_subscription(auth.getToken(), schedule_data);
		
		Response<IamportResponse<List<Schedule>>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );
		
		return response.body();
	}
	
	public IamportResponse<List<Schedule>> unsubscribeSchedule(UnscheduleData unschedule_data) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<List<Schedule>>> call = this.iamport.unschedule_subscription(auth.getToken(), unschedule_data);
		
		Response<IamportResponse<List<Schedule>>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}
	
	/* 본인인증 */
	public IamportResponse<Certification> certificationByImpUid(String imp_uid) throws IamportResponseException, IOException {
		AccessToken auth = getAuth().getResponse();
		Call<IamportResponse<Certification>> call = this.iamport.certification_by_imp_uid(auth.getToken(), imp_uid);
		
		Response<IamportResponse<Certification>> response = call.execute();
		if ( !response.isSuccessful() )	throw new IamportResponseException( getExceptionMessage(response), new HttpException(response) );

		return response.body();
	}
	
	
	
	protected Iamport create() {
		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(30, TimeUnit.SECONDS)
				.connectTimeout(10, TimeUnit.SECONDS)
				.build();
		
		Retrofit retrofit = new Retrofit.Builder()
								.baseUrl(API_URL)
								.addConverterFactory(buildGsonConverter())
								.client(client)
								.build();
		
		return retrofit.create(Iamport.class);
	}
	
	protected GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Adding custom deserializers
        Object escrowInvoiceStrategy = new EscrowInvoiceEntrySerializer();
        
        gsonBuilder.registerTypeAdapter(ScheduleEntry.class, new ScheduleEntrySerializer());
        gsonBuilder.registerTypeAdapter(Schedule.class, new ScheduleEntrySerializer());
        gsonBuilder.registerTypeAdapter(PaymentBalanceEntry.class, new BalanceEntrySerializer());
        gsonBuilder.registerTypeAdapter(EscrowLogisInvoiceData.class, escrowInvoiceStrategy);
        gsonBuilder.registerTypeAdapter(EscrowLogisInvoice.class, escrowInvoiceStrategy);
        
        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);
    }
	
	protected String getExceptionMessage(Response<?> response) {
		String error = null;
		try {
			JsonElement element = new JsonParser().parse(response.errorBody().string());
			error = element.getAsJsonObject().get("message").getAsString();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if ( error == null )	error = response.message();
		
		return error;
	}
	
}


