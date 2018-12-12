package com.moh.clinicalguideline.views.algorithm;

import android.util.Log;

import com.moh.clinicalguideline.R;
import com.moh.clinicalguideline.core.AlgorithmDescription;
import com.moh.clinicalguideline.helper.SimpleLayoutAdapter;
import com.moh.clinicalguideline.helper.ViewModel;
import com.moh.clinicalguideline.repository.NodeRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AlgorithmViewModel extends ViewModel<AlgorithmNavigator> {

    private SimpleLayoutAdapter<AlgorithmCardViewModel> adapter;
    private SimpleLayoutAdapter<AlgorithmDescription> conditionalAdapter;
    private final NodeRepository nodeRepository;
    private AlgorithmDescription nodeDescription;
    private boolean loading;

    @Inject
    public AlgorithmViewModel(NodeRepository nodeRepository){

         this.nodeRepository = nodeRepository;
         this.adapter = new SimpleLayoutAdapter<>(R.layout.activity_algorithm_list, item -> {
             navigator.openAlgorithm(item.getId(),nodeDescription.getId());
         });
         this.conditionalAdapter = new SimpleLayoutAdapter<>(R.layout.activity_algorithm_clist, item -> {
             navigator.openAlgorithm(item.getId(),nodeDescription.getId());
         });
    }

    public void loadNode(int nodeId,int parentId) {
        setLoading(true);
        loadAlgorithmDescription(nodeId);
        loadNonConditionalChildNodes(nodeId);
        loadConditionalChildNodes(nodeId);
    }

    private void loadAlgorithmDescription(int nodeId){
        setLoading(true);
        nodeRepository.getNode(nodeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::OnAlgorithmNodeLoaded,this::onLoadError);
    }

    private void loadNonConditionalChildNodes(int nodeId) {
        setLoading(true);
        nodeRepository.getChildNode(nodeId,false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNonConditionalChildNodesLoaded,this::onLoadError);
    }

    private void loadConditionalChildNodes(int nodeId) {
        setLoading(true);
        nodeRepository.getChildNode(nodeId,true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::OnConditionalChildNodesLoaded,this::onLoadError);
    }

    public SimpleLayoutAdapter<AlgorithmCardViewModel> getAdapter(){
         return adapter;
    }

    public SimpleLayoutAdapter<AlgorithmDescription> getConditionalAdapter(){
        return conditionalAdapter;
    }

    private void OnAlgorithmNodeLoaded(AlgorithmDescription nodeDescription) {
        setLoading(false);
        this.nodeDescription = nodeDescription;
        notifyChange();
    }

    private void onNonConditionalChildNodesLoaded(List<AlgorithmDescription> nodeDescriptionList) {
        setLoading(false);
        List<AlgorithmCardViewModel> algorithmNodeViewModels = new ArrayList();
        for (AlgorithmDescription nodeDescription: nodeDescriptionList) {
            algorithmNodeViewModels.add(new AlgorithmCardViewModel(nodeDescription));
            }
        adapter.setData(algorithmNodeViewModels);
        notifyChange();
    }

    private void OnConditionalChildNodesLoaded(List<AlgorithmDescription> nodeDescriptionList) {
        setLoading(false);

        conditionalAdapter.setData(nodeDescriptionList);
        notifyChange();
    }

    public void onLoadError(Throwable throwable) {
        Log.e("Error Fetching data", throwable.getMessage());
        setLoading(false);
    }

    public boolean isLoading() {

        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyChange();
    }

    public String getTitle(){
        if(nodeDescription==null || !nodeDescription.getHasTitle())
            return "";
        return nodeDescription.getTitle();
    }

    public String getDescription(){
        if(nodeDescription==null)
            return "";
        return nodeDescription.getDescription();
    }

}
