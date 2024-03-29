package com.moh.clinicalguideline.views.algorithm;

import android.arch.lifecycle.MutableLiveData;

import com.moh.clinicalguideline.core.AlgorithmDescription;
import com.moh.clinicalguideline.helper.view.BaseViewModel;
import com.moh.clinicalguideline.repository.NodeRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AlgorithmViewModel extends BaseViewModel<AlgorithmNavigator> {

    private final NodeRepository nodeRepository;
    private MutableLiveData<AlgorithmDescription> node;
    private OnNodeSelectedListener onNodeSelectedListener;
    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<String> symptomTitle = new MutableLiveData<>();

    @Inject
    public AlgorithmViewModel(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
        this.node = new MutableLiveData<>();
        this.onNodeSelectedListener = algorithmDescription -> {

        };

    }

    public void LoadNode(int nodeId) {
        nodeRepository.getNode(nodeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNodeLoaded, this::onLoadError);
    }

    public void LoadPage(int page) {
        nodeRepository.getNodeByPage(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNodeLoaded, this::onLoadError);

    }

    private void onNodeLoaded(AlgorithmDescription node) {
        this.node.setValue(node);
        this.onNodeSelectedListener.onNodeSelected(node);
        symptomTitle.setValue(node.getTitle());

    }


    public void PreviewNode(AlgorithmDescription node) {
        this.node.setValue(node);
    }

    public void SelectNode(AlgorithmDescription node) {
        SelectNode(node, true);
    }

    public void SelectNode(AlgorithmDescription node, boolean skipSingleNode) {

        if (!skipSingleNode || node.getHasDescription() || node.getChildCount() > 1 || node.getFirstChildNodeId() == null) {

            onNodeLoaded(node);
        } else {
            this.onNodeSelectedListener.onNodeSelected(node);
            LoadNode(node.getFirstChildNodeId());
        }
    }

    //region Properties
    public MutableLiveData<AlgorithmDescription> getNode() {

        return node;
    }

    public void setOnNodeSelectedListener(OnNodeSelectedListener onNodeSelectedListener) {
        this.onNodeSelectedListener = onNodeSelectedListener;
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public MutableLiveData<String> getSymptomTitle() {
        return symptomTitle;
    }

    //endregion

    //region Listeners
    public interface OnNodeSelectedListener {
        void onNodeSelected(AlgorithmDescription algorithmDescription);
    }
    //endregion

}
