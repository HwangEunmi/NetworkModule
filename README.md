# Network Module
Modules for Network Communication
<br/>
<br/>
**🖥 프로젝트 소개**
-------

Network Module은 통신 모듈입니다. 안드로이드 프로젝트에서 통신 모듈은 필수적인 존재이기 때문에 프로젝트마다 
<br/>공통으로 사용할 수 있는 통신 모듈을 만들면 좋겠다는 생각을 하였고, 그에따라 최대한 확장성과 유연함을 생각하며 개발하려고 노력하고 있습니다. 
<br/>
<br/>
해당 프로젝트는 현재 개발 진행중이며, Kotlin, Coroutine, Flow를 사용합니다 :)
</br>
<br/>

## 🤔 기능 요구사항
공통으로 사용할 수 있는 네트워크 모듈을 구현합니다. 
- 다양한 모습의 Response에 대응할 수 있도록 최대한 유연해야 합니다. (0)
- 여러 API를 호출할때는 combine을 사용합니다. (0)
- 문자열, Json 등 여러 값을 파싱할 수 있도록 구현합니다. (현재는 String, Json 파싱 가능) (0)
- 인터넷(LTE, Wifi)가 연결되어있지 않은 상태에서 API를 호출할 경우의 에러를 처리합니다. (0)
- Local Cache를 사용합니다. (Room)
- DSL을 사용하여 Gradle을 깔끔하게 유지합니다. 
- Timeout Exception이 발생할 경우 CoroutineExceptionHandler로 잡을 수 있습니다. (0)
- API 호출 실패시 재시도를 시도하면 해당 API들이 재호출됩니다. (0) -> 추후 개선예정
- 로딩상태를 표시합니다. (0)
- Entity <-> Domain용 Mapper로 각 레이어에 맞는 데이터 형식을 만듭니다.  
<br/>

❓ API 호출 실패시 재시도하기 (확장적이게 코드로 분리) (0)
<br/>
- 재시도 해야하는것과(ex.인터넷연결불안정) (0)
- 재시도할 필요없는것 (ex.Exception등) (0)
- 토큰만료로 인한 새 액세스 토큰 가져오기 시도 분리하기 (0)
<br/>
🔍 통신모듈 테스트할 수 있는 기법 찾아보기 
<br/>
<br/>

## 💡 사용방법 
## 1. Response의 인터페이스를 생성한다. 
  
  ```kotlin
  interface IServerResponse<out T> {  
     val code: String  
     val msg: String  
     val data: T  
  }
   ``` 

## 2. ApiService를 생성한다. 

 ```kotlin
 interface GithubApi {  
    // 1) Json 타입의 Response
    @GET("/users/{username}")  
    @Json  
    suspend fun getUserInfo(@Path("username") username: String): Response<UserInfoResponse>  
      
    // 2) String 타입의 Response
    @GET("/users")  
    @Json  
    @Scalars  
    suspend fun getUserInfo2(): Response<List<UserInfoResponse>>  
 }
 ```

## 3. ApiModule에 해당 Api provide를 생성한다.
```kotlin
@InstallIn(SingletonComponent::class)
@Module
internal object ApiModule {
    @Provides
    @Singleton
    fun provideGithubApi(
        retrofit: Retrofit
    ): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }
}
 ```
 
## 4. Response 데이터 클래스를 생성한다.  
 ```kotlin
 data class UserInfoResponse(  
    override val code: String,  
    override val msg: String,  
    override val data: UserInfo  
      
 ) : IServerResponse<UserInfoResponse.UserInfo> {  
       data class UserInfo(  
          val id: Int,  
          val login: String,  
          val avatar_url: String  
       )}
 ```

## 5. DataSource / impl을 생성한다.    

 ```kotlin
 // DataSource 
 interface GitDataSource {  
    suspend fun getUserInfo(username: String): ServerApiResponse<UserInfoResponse>  
 }

 // DataSourceImpl 
 class GitDataSourceImpl @Inject constructor(  
    private val githubApi: GithubApi  
 ) : GitDataSource {  
      
     override suspend fun getUserInfo(username: String): ServerApiResponse<UserInfoResponse> {  
        val result = githubApi.getUserInfo(username)  
        return HttpApiResponse.of { result }.toServerFormat()  
     } 
 }
 ```
 
## 6. (Remote/Local) DataModule에 해당 DataSource를 provide한다. 
```kotlin
// LocalDataModule인 경우
@InstallIn(SingletonComponent::class)
@Module(includes = [LocalDataModule.ProvideModule::class])
internal abstract class LocalDataModule {

    @Binds
    abstract fun bindsGitLocalDataSource(
        dataSource: GitDataSourceImpl
    ): GitDataSource
    
    // 만약 provide 타입이 필요한 경우
    @InstallIn(SingletonComponent::class)
    @Module
    internal object ProvideModule {
       @Provides
       @Singleton
       fun provideGitLocalDataSource(): GitLocalDataSource =
              GitLocalDataSourceImpl()
    }
}

// RemoteDataModule인 경우
@InstallIn(SingletonComponent::class)
@Module(includes = [RemoteDataModule.ProvideModule::class])
internal abstract class RemoteDataModule {
    @Binds
    abstract fun bindsGitRemoteDataSource(
        dataSource: GitDataSourceImpl
    ): GitDataSource
    
    // 만약 provide 타입이 필요한 경우 
    @InstallIn(SingletonComponent::class)
    @Module
    internal object ProvideModule {
       @Provides
       @Singleton
       fun provideGitRemoteDataSource(): GitRemoteDataSource =
           GitRemoteDataSourceImpl(
              ApiModule.provideGithubApi()
           )
    }
}
```

## 7. Repository / impl을 생성한다.    
 ```kotlin
 // Repository
 interface GitRepository {  
    suspend fun getUserInfo(username: String): ServerApiResponse<UserInfoResponse>  
 }
    
 // RepositoryImpl
 class GitRepositoryImpl @Inject constructor(  
    private val gitRemoteDataSource: GitDataSource  
 ) : GitRepository {  
      
     override suspend fun getUserInfo(username: String): ServerApiResponse<UserInfoResponse> {  
        return gitRemoteDataSource.getUserInfo(username)  
     }     
 }
 ```
 
## 8. RepositoryModule에 해당 Repository를 provide한다. 
 ```kotlin
 @InstallIn(SingletonComponent::class)
 @Module
 internal abstract class RepositoryModule {
    @Binds
    abstract fun bindsGitRepository(
        repository: GitRepositoryImpl
    ): GitRepository

    // 만약 provide 타입이 필요한 경우 
    @InstallIn(SingletonComponent::class)
    @Module
    internal object ProvideModule {
        @Provides
        @Singleton
        fun provideGitRepository(): GitRepository =
            TokenRepositoryImpl(
                localDataSource = LocalDataModule.ProvideModule.provideGitLocalDataSource(),
                remoteDataSource = RemoteDataModule.ProvideModule.provideGitRemoteDataSource()
            )
    }
}
```

## 9-1. UseCase를 생성한다. 
 ```kotlin
 class GetUserInfoUseCase @Inject constructor(  
    private val gitRepository: GitRepository,  
    @IoDispatcher dispatcher: CoroutineDispatcher  
 ) : ParamUseCase<GetUserInfoUseCase.Params, TestServerApiResponse<TestUserInfoResponse>>(dispatcher) {  
          
     override suspend fun execute(param: Params) = gitRepository.getUserInfo(param.username)  
          
     data class Params(  
        val username: String  
     )
 }
 ```
 </br>
 
## 9-1-1. 참고로 사용된 ParamUseCase는 아래와 같다. 
 ```kotlin
 // 1) Param이 존재 할 경우 
 abstract class ParamUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
   suspend operator fun invoke(parameters: P): R {
      return withContext(coroutineDispatcher) {
          execute(parameters)
      }
   }

   @Throws(RuntimeException::class)
   protected abstract suspend fun execute(param: P): R
}

// 2) Param이 존재하지 않을 경우
abstract class NonParamUseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {

   suspend operator fun invoke(): R {
      return withContext(coroutineDispatcher) {
          execute()
      }
   }

   @Throws(RuntimeException::class)
   protected abstract suspend fun execute(): R
}
```

## 9-1-2. 참고로 사용된 Dispatcher Annotation은 아래와 같다. 
 ```kotlin
 import javax.inject.Qualifier

 @Retention(AnnotationRetention.BINARY)
 @Qualifier
 annotation class DefaultDispatcher

 @Retention(AnnotationRetention.BINARY)
 @Qualifier
 annotation class IoDispatcher

 @Retention(AnnotationRetention.BINARY)
 @Qualifier
 annotation class MainDispatcher

 @Retention(AnnotationRetention.BINARY)
 @Qualifier
 annotation class MainImmediateDispatcher // Main (+순서보장)
 ```

## 9-2. Combine용 UseCase를 생성한다. 
 ```kotlin
 class GetUserCombineUseCase @Inject constructor(  
    private val gitRepository: GitRepository,  
    @IoDispatcher private val dispatcher: CoroutineDispatcher  
 ) : CombineParamUseCase<GetUserCombineUseCase.Params>() {  
      
      override suspend fun execute(param: Params): Any {  
         val userInfo = gitRepository.getUserInfo(param.username).toFlow()  
         val userInfo2 = gitRepository.getUserInfo(param.username).toFlow()  
      
      combines(userInfo, userINfo2, dispatcher = dispatcher,  
         onSuccessResult = {  
            var userInfoResponse: UserInfoResponse? = null  
            var userInfoResponse2: UserInfoResponse? = null  
      
            this.forEach { result ->  
               val successData = result as ServerApiResponse.Success  
               when (successData.data) {  
               is UserInfoResponse -> {  
                   if (UserInfoResponse == null) userInfoResponse = successData.data  
                   else UserInfoResponse2 = successData.data  
               }  
      
               whenAllNotNull(userInfoResponse, userInfoResponse2,  
                  notNullBlock = {  
                    resultCallback(  
                       ServerApiResponse.Success(  
                          CombineUserInfoResponse(  
                             userInfoResponse = userInfoResponse!!,  
                             userInfoResponse2 = userInfoResponse2!!  
                          )  
                       )  
                    )  
              }, nullBlock = {  
                    resultCallback(  
                        ServerApiResponse.Failure.Error<CombineUserInfoResponse>(  
                           errorType = NetworkErrorType.SERVER_API_ERROR,  
                           errorCode = ServerStatusCode.HttpError  
                        )  
                    )  
             })  
         },  
             onFailResult = {  
                 resultCallback(this)  
             })  
      
             return combineResult  
       }  
      
       data class Params(  
          val username: String  
       )
     }
 ```
 
## 9-2-1. 참고로 사용된 Combine용 ParamUseCase는 아래와 같다. 
  ```kotlin
  // 1) Param이 존재 할 경우 
  private typealias ResultCallback = (Any) -> Unit

  abstract class CombineParamUseCase<in P> {
     suspend operator fun invoke(parameters: P): Any {
        return execute(parameters)
     }

     @Throws(RuntimeException::class)
     protected abstract suspend fun execute(param: P): Any
 
     protected var combineResult: Any = TestServerApiResponse.Failure.Error<Any>(
        errorCode = ServerStatusCode.HttpError
     )

     protected val resultCallback: ResultCallback = object : ResultCallback {
        override fun invoke(result: Any) {
           combineResult = result
        }
    }
 }
  
 // 2) Param이 존재하지 않을 경우
 abstract class CombineNonParamUseCase<R> {
    suspend operator fun invoke(): R {
      return execute()
    }

   @Throws(RuntimeException::class)
   protected abstract suspend fun execute(): R
}
 ```
 
## 10. ViewModel에서 MutableStateFlow/StateFlow를 만든다. 
 ```kotlin
 @HiltViewModel  
 class MainViewModel @Inject constructor(  
    private val getUserInfoUseCase: GetUserInfoUseCase,  
 ) : BaseViewModel() {  
      
   private val _userInfo = MutableStateFlow<State>(IdleState.Idle)
      val userInfo: StateFlow<State> = _userInfo.asStateFlow(
   }
 ```
 
 ## 11. MutableStateFlow에 UseCase를 셋팅한다. 
 ```kotlin
 init {
    viewModelScope.launch {
       requestUserInfo()
    }
 }
    
 private suspend fun requestUserInfo() = _userInfo.requestOperator(
    onApi = {
       getUserInfoUseCase(GetUserInfoUseCase.Params("octocat"))
    },
       onSideEffectEvent = sideEffectEventCallback,
 )
 ```
 
## 12. apiOperator에 SideEffectEventCallback을 셋팅한다.
 ```kotlin
 private val sideEffectEventCallback = object : SideEffectEventCallback {
    override fun invoke(event: Event<FailureState>) {
        _sideEffect.value = event
    }
 }
    
 val userInfoApiOperator = userInfo.apiOperator(sideEffectEventCallback)
 ```

## 13. View에서 collect를 호출한다. 
 ```kotlin
 // collect 두개부터 launch로 감싸줘야 한다. 
 launch {
    viewModel.userInfoApiOperator.collect(
        NetworkResultObserver(
            loadingHandleCallback,
            onSuccessCallback = {
                     // ...
            }
        )
    )
 }
 ```
