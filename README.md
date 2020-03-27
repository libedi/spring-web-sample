# spring-web-sample

## 1. 개요
- Spring Boot로 만든 Sample Web Project

## 2. Dependency Library
- Spring Boot 2.0.4
- Spring Securiy
- MyBatis 1.3.2
- Thymeleaf 3
- H2 Embedded DB
- lombok
- Jasypt
- jjwt
- Webjars
- PageHelper

## 3. 인증
- 세션 기반의 폼 로그인 방식이 아닌, Multi Device 로그인을 위해 Stateless한 Token login 방식
- 로그인 성공시 Response Header의 Authorization 헤더에 인증토큰 발급
- 발급된 인증토큰은 sessionStorage에 저장
- 요청시 Request Header의 Authorization 헤더에 발급된 인증토큰 전송
- 로그인 실패시, 인증토큰 만료 및 인증토큰 오류시 401 응답 status 반환
- 간단한 토큰 인증방식으로 실구현시 OAuth 2 인증방식으로 전환 필요 [[Spring Security OAuth Project]](https://projects.spring.io/spring-security-oauth/docs/oauth2.html)

## 4. 프로젝트 구조

### 1. Package 구조
- root package : 도메인
- /common/** : 공통단
- /framework/** : 프로젝트 구성에 필요한 설정
- /(sample)/** : 업무단

### 2. Layer 구조
- xxxController : 뷰 구성을 위한 컨트롤러. URI 패턴 : /view/*** [[코드]](https://github.com/libedi/spring-web-sample/blob/master/spring-web-sample/src/main/java/kr/co/tworld/shop/sample/controller/SampleController.java)
~~~java
@Controller
@RequestMapping("/view/sample")
public class SampleController {

	@GetMapping("/customer")
	public void viewCustomer(final Model model) {}

}
~~~

- xxxApiController : 비즈니스 모듈 실행을 위한 컨트롤러. 요청과 응답설정. REST 방식 사용. URI 패턴 : /api/*** [[코드]](https://github.com/libedi/spring-web-sample/blob/master/spring-web-sample/src/main/java/kr/co/tworld/shop/sample/controller/SampleApiController.java)
~~~java
@RestController
@RequestMapping("/api/samples")
@RequiredArgsConstructor
public class SampleApiController {
	
	private final SampleService sampleService;

	/**
	 * get customer list
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Sample> getCustomerList(@AuthenticationPrincipal final User user) {
		log.info(user.toString());
		return this.sampleService.getCustomerList();
	}
}
~~~

- xxxService : 서비스. 비즈니스 로직이 실행되는 계층. 트랜잭션 설정 계층. [[코드]](https://github.com/libedi/spring-web-sample/blob/master/spring-web-sample/src/main/java/kr/co/tworld/shop/sample/service/SampleService.java)
~~~
@Service
@RequiredArgsConstructor
public class SampleService {
	
	private final SampleMapper sampleMapper;
  
	/**
	 * get customer all list
	 * @return
	 */
	public List<Sample> getCustomerList() {
		return this.sampleMapper.selectCustomer(null);
	}
	
	/**
	 * create customer
	 * @param sample
	 */
	@Transactional
	public void createCustomer(final Sample sample) {
		this.sampleMapper.selectCustomer(sample.getCustomerId()).stream().findFirst().ifPresent(s -> {
			throw new ResourceConflictException("Customer already exists.");
		});
		this.sampleMapper.insertCustomer(sample);
	}
}
~~~

- xxxMapper : 매퍼. MyBatis Mapper Interface. [[코드]](https://github.com/libedi/spring-web-sample/blob/master/spring-web-sample/src/main/java/kr/co/tworld/shop/sample/mapper/SampleMapper.java)
~~~java
@Mapper
public interface SampleMapper {

	List<Sample> selectCustomer(Integer customerId);

	void insertCustomer(Sample sample);

	void updateCustomer(Sample sample);

	void deleteCustomer(int customerId);

}
~~~

- Entity : 엔티티. lombok을 사용하여 코드 간소화. 로깅을 위해 toString()을 반드시 구현.
~~~java
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString @Builder
public class Sample {
	
	// All groups. Hibernate default message.
	@NotEmpty
	private Integer customerId;
	
	// Specific group. custom message.
	@NotEmpty(groups = Create.class, message = "${valid.msg.not-empty}")
	private String customerName;
	
	private String company;

}
~~~

### 3. Exception Handling
- Exception은 ExceptionHanlder에서 공통으로 처리
- Controller에서 발생한 Exception은 ControllerExceptionHandler에서 처리하여 에러페이지로 리다이렉트. [[코드]](https://github.com/libedi/spring-web-sample/blob/master/spring-web-sample/src/main/java/kr/co/tworld/shop/framework/handler/ControllerExceptionHandler.java)
- ApiController에서 발생한 Exception은 RestControllerExceptionHandler에서 처리하여 적합한 Http Status 반환 [[코드]](https://github.com/libedi/spring-web-sample/blob/master/spring-web-sample/src/main/java/kr/co/tworld/shop/framework/handler/RestControllerExceptionHandler.java)
- 가능하면 Exception은 RuntimeException으로 생성하여 Check 로직 제거

### 4. Bean Validation
- JSR-303 지원
- @Validated 애노테이션을 이용하여 validation group 지정 가능.
- Validation 메세지 작성시 메세지 프로퍼티 사용 가능.

### 5. PageHelper
- lambda 식으로 사용
~~~java
// PageHelper.startPage(int pageNum, int pageSize)
Page<Sample> page = PageHelper.startPage(1, 10).doSelectPage(() -> sampleMapper.selectCustomers());
PageInfo<Sample> pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(() -> sampleMapper.selectCustomers());
~~~
- PageHelper 설정 : [PageHelper Github](https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/en/HowToUse.md#3-pagehelper-parameters)
	- dialect :
		1. Custom Dialect를 구성시 설정
		2. 반드시 com.github.pagehelper.Dialect를 implements 해야함
		3. full package 경로로 설정.
		4. 기본값은 com.github.pagehelper.PageHelper
	- **_helperDialect_** : 특정 데이터베이스를 지정. 미설정시 자동감지.
		- 지원DB 약어: oracle, mysql, mariadb, sqlite, hsqldb, postgresql, db2, sqlserver, informix, h2, sqlserver2012, derby
	- offsetAsPageNum :
		1. MyBatis 페이징 파라미터로 RowBounds를 사용할 때. (Mapper인 경우는 해당X)
		2. true인 경우, RowBounds의 offset 파라미터가 pageNum으로 사용됨. (원래 offset은 건너뛰는 건수)
		3. 기본값은 false
	- rowBoundsWithCount :
		1. MyBatis 페이징 파라미터로 RowBounds를 사용할 때. (Mapper인 경우는 해당X)
		2. true인 경우, PageHelper가 Count 쿼리 실행
		3. 기본값은 false
	- pageSizeZero : pageSize=0 으로 실행. 기본값은 false
	- reasonable : true 설정시, pageNum >=0, pageNum < pages 로 실행. 기본값은 false
	- params : pageNum,pageSize,count,pageSizeZero,reasonable의 에 대한 이름 매핑설정. 굳이 건드릴 필요는 없을듯.
	- **_supportMethodsArguments_** :
		1. Mapper 인터페이스로 페이지 매개변수를 전달할 수 있게 함
		2. Mapper 사용시 true 설정
		3. 기본값은 false
	- autoRuntimeDialect : 동적으로 데이터베이스 자동설정. helperDialect보다 우선순위가 높음. 기본값은 false
	- closeConn : DB connection을 닫을지 여부 설정. 기본값은 true

~~~properties
# PageHelper Configuration
pagehelper.dialect=com.sample.CustomDialect
pagehelper.helper-dialect=h2
pagehelper.offset-as-page-num=true
pagehelper.row-bounds-with-count=true
pagehelper.page-size-zero=true
pagehelper.reasonable=true
pagehelper.params=pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero
pagehelper.support-methods-arguments=true
pagehelper.auto-dialect=true
pagehelper.auto-runtime-dialect=true
pagehelper.close-conn=false
~~~
