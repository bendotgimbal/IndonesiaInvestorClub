package com.project.indonesiainvestorclub.viewModels;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.google.gson.JsonElement;
import com.project.indonesiainvestorclub.databinding.AboutFragmentBinding;
import com.project.indonesiainvestorclub.interfaces.ActionInterface;
import com.project.indonesiainvestorclub.models.About;
import com.project.indonesiainvestorclub.models.Childs;
import com.project.indonesiainvestorclub.models.response.AboutRes;
import com.project.indonesiainvestorclub.services.CallbackWrapper;
import com.project.indonesiainvestorclub.services.ServiceGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AboutViewModel extends BaseViewModelWithCallback
        implements ActionInterface.AdapterItemListener<About> {

    private AboutFragmentBinding binding;
    public ObservableBoolean loadingState;
    public ObservableField<String> parentAboutValueTx;
    public ObservableField<String> parentVisionMissionValueTx;
    public ObservableField<String> parentAboutChildsValueTx;
    public ObservableField<String> parentVisionMissionChildsValueTx;

    public AboutViewModel(Context context, AboutFragmentBinding binding) {
        super(context);
        this.binding = binding;

        loadingState = new ObservableBoolean(false);
        parentAboutValueTx = new ObservableField<>("");
        parentVisionMissionValueTx = new ObservableField<>("");
        parentAboutChildsValueTx = new ObservableField<>("");
        parentVisionMissionChildsValueTx = new ObservableField<>("");

        start();
    }

    private void start() {
        getAbout();
    }

    private void loading(boolean load) {
        loadingState.set(load);
    }

    //API CALL
    public void getAbout() {
        loading(true);

        Disposable disposable = ServiceGenerator.service.aboutRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<Response<JsonElement>>(this, this::getAbout) {
                    @Override
                    protected void onSuccess(Response<JsonElement> response) {
                        if (response.body() != null) {
                            readAboutJSON(response.body());
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void readAboutJSON(JsonElement response) {
        JSONObject jsonObject;
        try {
            AboutRes aboutRes;

            jsonObject = new JSONObject(response.toString());
            JSONObject objectAbout = jsonObject.getJSONObject("ABOUT_IIC");

            List<About> about = new ArrayList<>();

            for (int i = 1; i <= objectAbout.length(); i++) {
                JSONObject objAbout = objectAbout.getJSONObject(i + "");

                String parent = objAbout.getString("Parent");
                List<Childs> childsList = new ArrayList<>();

                if (objAbout.has("Childs")) {

                    //Correction childObject get value from objAbout not from objectAbout
                    JSONObject childsObject = objAbout.getJSONObject("Childs");

                    for (int o = 1; o <= childsObject.length(); o++) {
                        Childs childs = new Childs(childsObject.getString(o + ""));
                        childsList.add(childs);
                    }
                } else {
                    Childs childs = new Childs("");
                    childsList.add(childs);
                }

                about.add(new About(parent, childsList));
            }

            aboutRes = new AboutRes(about);

            showAbout(aboutRes);
            hideLoading();
        } catch (JSONException e) {
            e.printStackTrace();
            hideLoading();
        }
    }

    private void showAbout(AboutRes response) {
        hideLoading();
        if (response == null) return;
        if (response.getAbout() == null) return;
        StringBuilder about = new StringBuilder();
        StringBuilder vision_mission = new StringBuilder();

        parentAboutValueTx.set(response.getAbout().get(0).getParent());
        parentVisionMissionValueTx.set(response.getAbout().get(1).getParent());

        if (response.getAbout().get(0).getChilds() != null) {
            for (int o = 0; o < response.getAbout().get(0).getChilds().size(); o++) {
                StringBuilder tempAbout;
                tempAbout = new StringBuilder(response.getAbout().get(0).getChilds().get(o).getPhrase() + "\n\n");
                about.append(tempAbout);
            }
        }

        if (response.getAbout().get(1).getChilds() != null) {
            for (int o = 0; o < response.getAbout().get(1).getChilds().size(); o++) {
                StringBuilder tempVisionMission;
                tempVisionMission = new StringBuilder(response.getAbout().get(1).getChilds().get(o).getPhrase() + "\n\n");
                vision_mission.append(tempVisionMission);
            }
        }
        parentAboutChildsValueTx.set(about.toString());
        parentVisionMissionChildsValueTx.set(vision_mission.toString());
    }

    @Override
    public void hideLoading() {
        loading(false);
    }

    @Override public void onClickAdapterItem(int index, About model) {

    }
}