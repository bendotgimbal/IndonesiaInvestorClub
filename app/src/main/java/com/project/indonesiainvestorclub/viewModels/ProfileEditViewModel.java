package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.project.indonesiainvestorclub.databinding.ProfileEditActivityBinding;
import com.project.indonesiainvestorclub.helper.StringHelper;
import com.project.indonesiainvestorclub.models.response.ProfileUpdateRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;
import com.project.indonesiainvestorclub.views.ProfileEditActivity;
import com.project.indonesiainvestorclub.views.SignUpActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProfileEditViewModel extends BaseViewModelWithCallback {

    private final static String UPDATE = "Patch User Success";
    private final static String STATUS = "true";

    private ProfileEditActivityBinding binding;
    public ObservableBoolean loadingState;
    public ObservableField<String> firstNameValueTx;
    public ObservableField<String> lastNameValueTx;
    public ObservableField<String> dobValueTx;
    public ObservableField<String> maritalStatusValueTx;
    public ObservableField<String> addressValueTx;
    public ObservableField<String> postalCodeValueTx;
    public ObservableField<String> genderValueTx;
    public ObservableField<String> nationalityValueTx;
    public ObservableField<String> cityValueTx;
    public ObservableField<String> countryValueTx;
    public ObservableField<String> phoneNumberValueTx;
    public ObservableField<String> occupationValueTx;

    private String firstNameValueStr;
    private String lastNameValueStr;
    private String dobValueStr;
    private String maritalStatusValueStr;
    private String addressValueStr;
    private String postalCodeValueStr;
    private String genderValueStr;
    private String nationalityValueStr;
    private String cityValueStr;
    private String countryValueStr;
    private String phoneNumberValueStr;
    private String occupationValueStr;

    public ProfileEditViewModel(Context context, ProfileEditActivityBinding binding) {
        super(context);
        this.binding = binding;

        firstNameValueTx = new ObservableField<>("");
        lastNameValueTx = new ObservableField<>("");
        dobValueTx = new ObservableField<>("");
        maritalStatusValueTx = new ObservableField<>("");
        addressValueTx = new ObservableField<>("");
        postalCodeValueTx = new ObservableField<>("");
        genderValueTx = new ObservableField<>("");
        nationalityValueTx = new ObservableField<>("");
        cityValueTx = new ObservableField<>("");
        countryValueTx = new ObservableField<>("");
        phoneNumberValueTx = new ObservableField<>("");
        occupationValueTx = new ObservableField<>("");

    }

    private void loading(boolean load){
        loadingState.set(load);
    }

    public void start(String firstNameStr, String lastNameStr, String dobStr, String maritalStatusStr, String addressStr, String postalCodeStr, String genderStr
            , String nationalityStr, String cityStr, String countryStr, String phoneNumberStr, String occupationStr){
//        hideLoading();

        firstNameValueTx.set(firstNameStr);
        lastNameValueTx.set(lastNameStr);
        dobValueTx.set(dobStr);
        maritalStatusValueTx.set(maritalStatusStr);
        addressValueTx.set(addressStr);
        postalCodeValueTx.set(postalCodeStr);
        genderValueTx.set(genderStr);
        nationalityValueTx.set(nationalityStr);
        cityValueTx.set(cityStr);
        countryValueTx.set(countryStr);
        phoneNumberValueTx.set(phoneNumberStr);
        occupationValueTx.set(occupationStr);

//        firstNameValueStr = firstNameStr;
//        lastNameValueStr = lastNameStr;
//        dobValueStr = dobStr;
//        maritalStatusValueStr = maritalStatusStr;
//        addressValueStr = addressStr;
//        postalCodeValueStr = postalCodeStr;
//        genderValueStr = genderStr;
//        nationalityValueStr = nationalityStr;
//        cityValueStr = cityStr;
//        countryValueStr = countryStr;
//        phoneNumberValueStr = phoneNumberStr;
//        occupationValueStr = occupationStr;

//        firstNameValueStr = firstNameValueTx.get();
//        lastNameValueStr = lastNameValueTx.get();
//        dobValueStr = dobValueTx.get();
//        maritalStatusValueStr = maritalStatusValueTx.get();
//        addressValueStr = addressValueTx.get();
//        postalCodeValueStr = postalCodeValueTx.get();
//        genderValueStr = genderValueTx.get();
//        nationalityValueStr = nationalityValueTx.get();
//        cityValueStr = cityValueTx.get();
//        countryValueStr = countryValueTx.get();
//        phoneNumberValueStr = phoneNumberValueTx.get();
//        occupationValueStr = occupationValueTx.get();
//        Toast.makeText(context, "First Name "+firstNameValueStr, Toast.LENGTH_SHORT).show();

    }

//    private String getFirstName(){
//        if (firstNameValueStr == null) return "";
//        return firstNameValueStr;
//    }
//
//    private String getLastName(){
//        if (lastNameValueStr == null) return "";
//        return lastNameValueStr;
//    }
//
//    private String getDOB(){
//        if (dobValueStr == null) return "";
//        return dobValueStr;
//    }
//
//    private String getMaritalStatus(){
//        if (maritalStatusValueStr == null) return "";
//        return maritalStatusValueStr;
//    }
//
//    private String getAddress(){
//        if (addressValueStr == null) return "";
//        return addressValueStr;
//    }
//
//    private String getPostalCode(){
//        if (postalCodeValueStr == null) return "";
//        return postalCodeValueStr;
//    }
//
//    private String getGender(){
//        if (genderValueStr == null) return "";
//        return genderValueStr;
//    }
//
//    private String getNationality(){
//        if (nationalityValueStr == null) return "";
//        return nationalityValueStr;
//    }
//
//    private String getCity(){
//        if (cityValueStr == null) return "";
//        return cityValueStr;
//    }
//
//    private String getCountry(){
//        if (countryValueStr == null) return "";
//        return countryValueStr;
//    }
//
//    private String getPhoneNumber(){
//        if (phoneNumberValueStr == null) return "";
//        return phoneNumberValueStr;
//    }
//
//    private String getOccupation(){
//        if (occupationValueStr == null) return "";
//        return occupationValueStr;
//    }


    private String getFirstName(){
        if (firstNameValueTx.get() == null) return "";
        return firstNameValueTx.get();
    }

    private String getLastName(){
        if (firstNameValueTx.get() == null) return "";
        return firstNameValueTx.get();
    }

    private String getDOB(){
        if (dobValueTx.get() == null) return "";
        return dobValueTx.get();
    }

    private String getMaritalStatus(){
        if (maritalStatusValueTx.get() == null) return "";
        return maritalStatusValueTx.get();
    }

    private String getAddress(){
        if (addressValueTx.get() == null) return "";
        return addressValueTx.get();
    }

    private String getPostalCode(){
        if (postalCodeValueTx.get() == null) return "";
        return postalCodeValueTx.get();
    }

    private String getGender(){
        if (genderValueTx.get() == null) return "";
        return genderValueTx.get();
    }

    private String getNationality(){
        if (nationalityValueTx.get() == null) return "";
        return nationalityValueTx.get();
    }

    private String getCity(){
        if (cityValueTx.get() == null) return "";
        return cityValueTx.get();
    }

    private String getCountry(){
        if (countryValueTx.get() == null) return "";
        return countryValueTx.get();
    }

    private String getPhoneNumber(){
        if (phoneNumberValueTx.get()== null) return "";
        return phoneNumberValueTx.get();
    }

    private String getOccupation(){
        if (occupationValueTx.get() == null) return "";
        return occupationValueTx.get();
    }

    @SuppressWarnings("unused")
    public void onClickEditProfile(View view) {
//        loading(true);
//        Toast.makeText(context, "Update "+getFirstName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Update "+firstNameValueTx.get(), Toast.LENGTH_SHORT).show();

//        Disposable disposable = ServiceGenerator.service.profileUpdateRequest(getFirstName(), getLastName(), getDOB(), getMaritalStatus(), getAddress(), getPostalCode(), getGender()
//                , getNationality(), getCity(), getCountry(), getPhoneNumber(), getOccupation())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new CallbackWrapper<Response<ProfileUpdateRes>>(this, () -> onClickEditProfile(view)) {
//                    @Override
//                    protected void onSuccess(Response<ProfileUpdateRes> updateResponse) {
//                        String cookie = updateResponse.headers().get("Set-Cookie");
//                        StringHelper.getCookie(cookie);
//                        onSuccessUpdate(updateResponse.body());
//                    }
//
//                    @Override public void onNext(Response<ProfileUpdateRes> updateResResponse) {
//                        super.onNext(updateResResponse);
////                        loading(false);
//                    }
//                });
//        compositeDisposable.add(disposable);

    }

    private void onSuccessUpdate(ProfileUpdateRes profileUpdateRes){
        if (profileUpdateRes != null && profileUpdateRes.getStatus().equalsIgnoreCase(STATUS)) {
            Toast.makeText(getContext(), "Selamat Anda Berhasil Update", Toast.LENGTH_SHORT).show();
            ((ProfileEditActivity)context).finish();
        }else {
            Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
        }

    }

    @Override public void hideLoading() {
        loading(false);
    }
}