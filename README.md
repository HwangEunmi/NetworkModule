# Network Module
## 🤔 기능 요구사항
공통으로 사용할 수 있는 네트워크 모듈을 구현합니다. 
- 다양한 모습의 Response에 대응할 수 있도록 최대한 유연해야 합니다. (0)
- 여러 API를 호출할때는 combine을 사용합니다. (0)
- 문자열, Json 등 여러 값을 파싱할 수 있도록 구현합니다. (현재는 String, Json 파싱 가능) (0)
- 인터넷(LTE, Wifi)가 연결되어있지 않은 상태에서 API를 호출할 경우의 에러를 처리합니다. (0)
- Local Cache를 사용합니다. (Room)
- DSL을 사용하여 Gradle을 깔끔하게 유지합니다. 
- Timeout Exception이 발생할 경우 CoroutineExceptionHandler로 잡을 수 있습니다. (0)
- API 호출 실패시 재시도를 시도하면 해당 API들이 재호출됩니다. 
<br/>

🔍 로딩시 어떤 UI가 대세인지 찾아보기 (ex. 프로그래스바)
<br/>
❓ API 호출 실패시 재시도하기 -> 재시도 해야하는것과(ex.인터넷연결불안정) 재시도할 필요없는것 (ex.Exception등) 분리하기 (확장적으로)
<br/>
🔍 통신모듈 테스트할 수 있는 기법 찾아보기 
