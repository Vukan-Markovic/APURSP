package vukan.com.apursp.ui.filters;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vukan.com.apursp.models.ProductCategory;
import vukan.com.apursp.repository.Repository;

public class FiltersViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<ProductCategory>> mCategories;

    public FiltersViewModel() {
        repository = new Repository();
        mCategories = new MutableLiveData<>();
    }

    MutableLiveData<List<ProductCategory>> getCategories() {
        mCategories = repository.getCategories();
        return mCategories;
    }
}
