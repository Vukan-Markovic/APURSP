package vukan.com.apursp.ui.filteri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.ProductCategory;
import vukan.com.apursp.repository.Repository;

public class FilteriViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<ProductCategory>> mCategories;

    public FilteriViewModel() {
        repository = new Repository();
        mCategories = new MutableLiveData<>();
    }

    MutableLiveData<List<ProductCategory>> getCategories() {
        mCategories = repository.getCategories();
        return mCategories;
    }
}
