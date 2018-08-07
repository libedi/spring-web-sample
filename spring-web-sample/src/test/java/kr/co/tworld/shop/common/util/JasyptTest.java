//package kr.co.tworld.shop.common.util;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.Set;
//import java.util.TreeSet;
//
//import org.jasypt.encryption.StringEncryptor;
//import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
//import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
//import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
//import org.jasypt.registry.AlgorithmRegistry;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * Jasypt test case
// * @author Sang jun, Park
// *
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class JasyptTest {
//	
//	@Autowired
//	private StringEncryptor jasyptStringEncryptor;
//	
//	@Test
//    public void test01_availableJasyptAlgorithm() throws Exception {
//        Set<String> supported = new TreeSet<>();
//        Set<String> unsupported = new TreeSet<>();
//        for (Object oAlgorithm : AlgorithmRegistry.getAllPBEAlgorithms()) {
//            String algorithm = (String) oAlgorithm;
//            try {
//                SimpleStringPBEConfig pbeConfig = new SimpleStringPBEConfig();
//                pbeConfig.setAlgorithm(algorithm);
//                pbeConfig.setPassword("changeme");
//                StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//                encryptor.setConfig(pbeConfig);
//
//                String encrypted = encryptor.encrypt("foo");
//                String decrypted = encryptor.decrypt(encrypted);
//                Assert.assertEquals("foo", decrypted);
//                supported.add(algorithm);
//            } catch (EncryptionOperationNotPossibleException e) {
//                unsupported.add(algorithm);
//            }
//        }
//        System.out.println("Supported");
//        supported.forEach(alg -> System.out.println("   " + alg)); 
//        System.out.println("Unsupported");
//        unsupported.forEach(alg -> System.out.println("   " + alg)); 
//    }
//	
//	@Test
//	public void test02_encryptPropertyValueByJasypt() throws Exception {
//		String expected = "7Y+s7Iqk7L2USUNU7JqpSldUU2VjcmV0S2V5";
//		String enc = this.jasyptStringEncryptor.encrypt(expected);
//		String actual = this.jasyptStringEncryptor.decrypt(enc);
//		assertEquals(expected, actual);
//		System.out.println(enc);
//	}
//}
//
