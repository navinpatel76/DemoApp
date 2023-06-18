package ladd.marshall.androidmvvmexample.viewModel.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.repository.ProductRepository
import com.hadilq.liveevent.LiveEvent
import com.it.demoapp.data.ProductDataBase
import com.it.demoapp.model.EmpolyeeList
import com.it.demoapp.viewModel.repositories.EmployeeRepository
import com.it.demoproject.model.ProductForStore
import com.it.demoproject.model.ProductsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val employeeRepository = EmployeeRepository.getInstance(application)
    val entriesState = MutableLiveData<EmpolyeeList>()
    private val productRepository:ProductRepository
//    val getProduct: LiveData<List<ProductForStore>>

    init {
        val productDao= ProductDataBase.getDatabase(application)?.ProductDao()
        productRepository= ProductRepository(productDao!!)
//        getProduct=productRepository.getAllProduct
//        Log.i(javaClass.name,""+getProduct)

    }

    val authenticationAddUserState = LiveEvent<Long>()
    fun addProduct(productForStore: ProductForStore){
        viewModelScope.launch(Dispatchers.IO) {
            val resource = productRepository.addProduct(productForStore)
            authenticationAddUserState.postValue(resource)
        }
    }
    val authenticationGetState = LiveEvent<List<ProductForStore>>()
    fun getProduct(){
        viewModelScope.launch(Dispatchers.IO) {
            val resource = productRepository.getAllProduct()
            Log.i(javaClass.name, "resource==="+resource)
            authenticationGetState.postValue(resource)
        }
    }

    val authenticationGetByIdState = LiveEvent<ProductForStore>()
    fun getProduct(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            val resource = productRepository.getProduct(id)
            Log.i(javaClass.name, "resource==="+resource)
            authenticationGetByIdState.postValue(resource)
        }
    }

    val authenticationDeleteState = LiveEvent<Unit>()
    fun deleteById(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            val resource = productRepository.deleteById(id)
            Log.i(javaClass.name, "resource==="+resource)
            authenticationDeleteState.postValue(resource)
        }
    }

    val productListState = MutableLiveData<ProductsModel>()

    fun getProductList() {
        CoroutineScope(Dispatchers.IO).launch {
            val resource = employeeRepository.getProductList()
            Log.i(javaClass.name, "resource---"+resource)
            productListState.postValue(resource)
        }
    }
}
