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

## 3. 인증
- 세션 기반의 폼 로그인 방식이 아닌, Multi Device 로그인을 위해 Stateless한 Token login 방식
- 로그인 성공시 Response Header의 Authorization 헤더에 인증토큰 발급
- 발급된 인증토큰은 sessionStorage에 저장
- 요청시 Request Header의 Authorization 헤더에 발급된 인증토큰 전송
- 로그인 실패시, 인증토큰 만료 및 인증토큰 오류시 401 응답 status 반환

## 4. 프로젝트 구조

### 1. Package 구조
- root package : 도메인
- /common/** : 공통단
- /framework/** : 프로젝트 구성에 필요한 설정
- /(sample)/** : 업무단

### 2. Layer 구조
- xxxController : 뷰 구성을 위한 컨트롤러. URI 패턴 : /view/*** [[코드]](https://github.com/libedi/spring-web-sample/blob/master/spring-web-sample/src/main/java/kr/co/tworld/shop/sample/controller/SampleController.java)
~~~
@Controller
@RequestMapping("/view/sample")
public class SampleController {

	@GetMapping("/customer")
	public void viewCustomer(final Model model) {}

}
~~~

- xxxApiController : 비즈니스 모듈 실행을 위한 컨트롤러. 요청과 응답설정. REST 방식 사용. URI 패턴 : /api/*** [[코드]](https://github.com/libedi/spring-web-sample/blob/master/spring-web-sample/src/main/java/kr/co/tworld/shop/sample/controller/SampleApiController.java)
~~~
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
~~~
@Mapper
public interface SampleMapper {

	List<Sample> selectCustomer(Integer customerId);

	void insertCustomer(Sample sample);

	void updateCustomer(Sample sample);

	void deleteCustomer(int customerId);

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
